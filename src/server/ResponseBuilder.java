package server;

import server.responses.*;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class ResponseBuilder {
    private final HashMap<String, String> httpRequestParameters;
    private final Socket clientSocket;

    public ResponseBuilder(Socket clientSocket, HashMap<String, String> httpRequestParameters) {
        this.clientSocket = clientSocket;
        this.httpRequestParameters = httpRequestParameters;
    }

    public void generateResponse() {
        String route = httpRequestParameters.get("route");
        String parsedRoute = httpRequestParameters.get("parsedRoute");
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
        String headers = responseObject.getHeaders();
        String body = responseObject.getBody(route);
        sendResponse(headers + body);
    }

    public File getRequestedPath(String route) {
        String requestedPath = System.getProperty("user.dir") + "/" + route;
        return new File(requestedPath);
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