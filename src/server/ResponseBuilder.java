package server;

import server.responses.*;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class ResponseBuilder {
    public final HashMap<String, String> httpRequestHeaders;
    private final Socket clientSocket;

    public ResponseBuilder(Socket clientSocket, HashMap<String, String> httpRequestHeaders) {
        this.clientSocket = clientSocket;
        this.httpRequestHeaders = httpRequestHeaders;
    }

    public ResponseObject generateResponseObject() {
        String route = httpRequestHeaders.get("route");
        String parsedRoute = httpRequestHeaders.get("parsedRoute");
        ResponseObject responseObject;
        if(routeIsTime(route)) {
            responseObject = new TimeResponse();
        } else if(routeIsEcho(route)) {
            responseObject = new EchoResponse(parsedRoute);
        } else {
            File path = getRequestedPath(route);
            if(path.isDirectory()) {
                responseObject = new DirectoryResponse(path);
            } else if(path.isFile()) {
                responseObject = new FileResponse(path);
            } else {
                responseObject = new FailureResponse();
            }
        }
        return responseObject;
    }

    public File getRequestedPath(String route) {
        String requestedPath = System.getProperty("user.dir") + "/" + route;
        return new File(requestedPath);
    }

    public void formResponse(ResponseObject responseObject) {
        String headers = responseObject.getHeaders();
        String body = responseObject.getBody(httpRequestHeaders.get("route"));
        sendResponse(headers + body);
    }

    public void sendResponse(String response) {
        try {
            OutputStream outputStream = clientSocket.getOutputStream();
            PrintWriter stringWriter = new PrintWriter(outputStream, true);
            stringWriter.println(response);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean routeIsTime(String route) {
        return route.contains("time");
    }

    public boolean routeIsEcho(String route) {
        return route.contains("echo");
    }

}