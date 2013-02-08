package server.responses;

import java.io.*;

public class DirectoryResponse extends ResponseObject {
    private final File requestedDirectory;

    public DirectoryResponse(File requestedDirectory) {
        this.requestedDirectory = requestedDirectory;
    }

    @Override
    public String getHeaders() {
        return "HTTP/1.1" + " 200 OK" + "\r\n" + "Content-Type: " + "text/html" + "; charset=UTF-8\r\n\r\n";
    }

    @Override
    public String getBody(String route, String parsedRoute) {
        File[] listOfFiles = requestedDirectory.listFiles();
        return linkFiles(route, listOfFiles);
    }

    public String linkFiles(String route, File[] listOfFiles) {
        String response = "";
        String divider = "";
        if(route.length() > 0) {
            divider = "/";
        }
        for (File file : listOfFiles) {
            response += "<p>" + "<a href=http://localhost:5000/" + route + divider + file.getName() + ">" + file.getName() + "</a>" + "</p>\n";
        }
        return response;
    }

}