package server;

import server.responses.*;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class ResponseBuilder {
    public final HashMap<String, String> httpRequestContent;
    private final Socket clientSocket;

    public ResponseBuilder(Socket clientSocket, HashMap<String, String> httpRequestContent) {
        this.clientSocket = clientSocket;
        this.httpRequestContent = httpRequestContent;
    }

    public HashMap<String, ResponseObject> getRoutesMap() {
        HashMap<String, ResponseObject> routes = new HashMap<String, ResponseObject>();
        routes.put("time", new TimeResponse());
        routes.put("echopost", new EchoPostResponse(httpRequestContent.get("body")));
        routes.put("echo", new EchoResponse(httpRequestContent.get("parsedRoute")));
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
            responseObject = new FileResponse(path);
        } else {
            responseObject = new FailureResponse();
        }
        return responseObject;
    }

    public File getRequestedPath(String route) {
        String requestedPath = System.getProperty("user.dir") + "/" + route;
        return new File(requestedPath);
    }

    public void formResponse(ResponseObject responseObject) {
        String headers = responseObject.getHeaders();
        String body = responseObject.getBody(httpRequestContent.get("route"));
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