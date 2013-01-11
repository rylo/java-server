package server;

import server.responses.Directory;
import server.responses.Echo;
import server.responses.Time;

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
        String route = httpRequestParameters.get("route").substring(1);
        String params = "";
        String response;
        if(routeIsTime(route)) {
            params = new Time().get();
            String responseParameters = getResponseParameters(" 200 OK", "text/html");
            response = responseParameters + params;
        } else if(routeIsEcho(route)) {
            params = new Echo().get(route);
            String responseParameters = getResponseParameters(" 200 OK", "text/plain");
            response = responseParameters + params;
        } else {
            response = new Directory(httpRequestParameters).getDirectoryResponse(route);
        }
        sendResponse(response);
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

    public String getResponseParameters(String statusCode, String contentType) {
        return httpRequestParameters.get("protocol") + statusCode + "\r\n" + "Content-Type: " + contentType + "; charset=UTF-8\r\n\r\n";
    }

}