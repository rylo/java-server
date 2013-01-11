package server.responses;

import java.io.File;
import java.util.HashMap;

public class Directory {
    private final HashMap<String, String> httpRequestParameters;

    public Directory(HashMap<String, String> httpRequestParameters) {
        this.httpRequestParameters = httpRequestParameters;
    }

    public String getResponse(File requestedPath) {
        File[] listOfFiles = getDirectoryContents(requestedPath);
        String response = addTags(listOfFiles);
        return response;
    }

    public File[] getDirectoryContents(File requestedPath) {
        File[] listOfFiles = requestedPath.listFiles();
        return listOfFiles;
    }

    private String addTags(File[] listOfFiles) {
        String response = "";
        for (File file : listOfFiles) {
            response += "<p>" + "<a href=http://localhost:4444/" + file.getName() + ">" + file.getName() + "</a><br />\r\n" + "</p>";
        }
        return response;
    }

    public String getDirectoryResponse(String route) {
        String params;
        String responseParameters;
        if(routeIsDirectory(route)) {
            File requestedDirectory = getRequestedDirectory(route);
            params = getResponse(requestedDirectory);
            responseParameters = getResponseParameters(" 200 OK", "text/html");
        } else {
            params = "NOT A DIRECTORY";
            responseParameters = getResponseParameters(" 404 Not Found", "text/html");
        }
        return responseParameters + params;
    }

    public File getRequestedDirectory(String route) {
        String requestedPath = System.getProperty("user.dir") + "/" + route;
        return new File(requestedPath);
    }

    public boolean routeIsDirectory(String route) {
        File requestedDirectory = getRequestedDirectory(route);
        return requestedDirectory.isDirectory();
    }

    public String getResponseParameters(String statusCode, String contentType) {
        return httpRequestParameters.get("protocol") + statusCode + "\r\n" + "Content-Type: " + contentType + "; charset=UTF-8\r\n\r\n";
    }

}