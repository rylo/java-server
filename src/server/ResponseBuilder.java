package server;

import server.responses.Directory;
import server.responses.Echo;
import server.responses.Time;

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
        String route = httpRequestParameters.get("route").substring(1);
        String params;
        if(routeIsTime(route)) {
            params = new Time().get();
        } else if(routeIsEcho(route)) {
            params = new Echo().get(route);
        } else if(route.trim().contains("favicon.ico")) {
            params = "";
        } else if (routeIsDirectory(route)) {
            File requestedDirectory = getRequestedDirectory(route);
            params = new Directory().getResponse(requestedDirectory);
        } else {
            params = "Not /time or /echo or a directory";
        }
        String responseParameters = getResponseParameters("text/html");
        String response = responseParameters + params;
        sendResponse(response);
    }

    public File getRequestedDirectory(String route) {
        String requestedPath = System.getProperty("user.dir") + route;
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

    public boolean routeIsDirectory(String route) {
        File requestedDirectory = getRequestedDirectory(route);
        return requestedDirectory.isDirectory();
    }

    public String getResponseParameters(String contentType) {
        return httpRequestParameters.get("protocol") + " 200 OK" + "\r\n" + "Content-Type: " + contentType + "; charset=UTF-8\r\n\r\n";
    }

}