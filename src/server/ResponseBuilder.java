package server;

import server.responses.*;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class ResponseBuilder {
    private final Socket clientSocket;
    public final HashMap<String, String> httpRequestContent;

    public ResponseBuilder(Socket clientSocket, HashMap<String, String> httpRequestContent) {
        this.clientSocket = clientSocket;
        this.httpRequestContent = httpRequestContent;
    }

    public HashMap<String, ResponseObject> getRoutesMap() {
        HashMap<String, ResponseObject> routes = new HashMap<String, ResponseObject>();
        routes.put("time", new TimeResponse());
        routes.put("echo", new EchoResponse(httpRequestContent.get("parsedRoute")));
        if(httpRequestContent.get("method").equals("GET")) {
            routes.put("echopost", new GetEchoPostResponse());
        } else if(httpRequestContent.get("method").equals("POST")) {
            routes.put("echopost", new PostEchoPostResponse(httpRequestContent.get("body")));
        }
        return routes;
    }

    public ResponseObject generateResponseObject() {
        String route = httpRequestContent.get("route");
        HashMap<String, ResponseObject> routes = getRoutesMap();
        ResponseObject responseObject;
        if(routes.containsKey(route)) {
            responseObject = routes.get(route);
        } else {
            responseObject = getOtherResponseObject(route);
        }
        return responseObject;
    }

    private ResponseObject getOtherResponseObject(String route) {
        ResponseObject responseObject;
        File path = getRequestedPath(route);
        if(path.isDirectory()) {
            responseObject = new DirectoryResponse(path);
        } else if(path.isFile()) {
            responseObject = getFileResponse(path);
        } else {
            responseObject = new FailureResponse();
        }
        return responseObject;
    }

    private ResponseObject getFileResponse(File path) {
        String extension = String.valueOf(path).split("\\.")[1];
        String contentType;
        if(fileIsImage(extension)) {
            contentType = "image/" + extension;
        } else {
            contentType = "text/plain";
        }
        return new FileResponse(path, extension, contentType);
    }

    public void sendResponse(ResponseObject responseObject) throws IOException {
        try {
            OutputStream outputStream = clientSocket.getOutputStream();
            String route = httpRequestContent.get("route");
            String headers = responseObject.getHeaders();
            String body = responseObject.getBody(route);
            if(headers.contains("image")) {
                respondWithImage(headers, body, outputStream);
            } else {
                respondWithText(headers, body, outputStream);
            }
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void respondWithImage(String headers, String body, OutputStream outputStream) {
        String[] byteValues = body.substring(1, body.length() - 1).split(",");
        byte[] byteArray = new byte[byteValues.length];
        int limit = byteArray.length;
        for (int i=0; i<limit; i++) {
            byteArray[i] = Byte.valueOf(byteValues[i].trim());
        }
        FilterOutputStream binaryOutputStream = new FilterOutputStream(outputStream);
        byte [] binaryHeaders = headers.getBytes();
        byte [] fullBinaryResponse = new byte[binaryHeaders.length + byteArray.length];

        System.arraycopy(binaryHeaders, 0, fullBinaryResponse, 0, binaryHeaders.length);
        System.arraycopy(byteArray, 0, fullBinaryResponse, binaryHeaders.length, byteArray.length);

        try {
            binaryOutputStream.write(fullBinaryResponse);
            binaryOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void respondWithText(String headers, String body, OutputStream outputStream) {
        PrintWriter stringWriter = new PrintWriter(outputStream, true);
        stringWriter.println(headers + body);
    }

    private boolean fileIsImage(String extension) {
        return extension.matches("^.*?(jpeg|jpg|png).*$");
    }

    public File getRequestedPath(String route) {
        String requestedPath = System.getProperty("user.dir") + "/" + route;
        return new File(requestedPath);
    }

}