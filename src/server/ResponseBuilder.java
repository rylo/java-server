package server;

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
        if(routeIsTime(route)) {
            String time = new Time().get();
            String responseParameters = getResponseParameters("text/html");
            String response = responseParameters + time;
            sendResponse(response);
        } else if(routeIsEcho(route)) {
            String params = new Echo().get(route);
            String responseParameters = getResponseParameters("text/html");
            String response = responseParameters + params;
            sendResponse(response);
        } else if (routeIsDirectory(route)) {
            // display directory contents
        } else {
            String response = "Not /time or /echo";
            sendResponse(response);
        }
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

    public boolean routeIsDirectory(String route) {
        // it knows current path
        // check if adding route to the current path is a directory
        // return true/false
        return false;
    }

    public String getResponseParameters(String contentType) {
        return httpRequestParameters.get("protocol") + " 200 OK" + "\r\n" + "Content-Type: " + contentType + "; charset=UTF-8\r\n\r\n";
    }

}