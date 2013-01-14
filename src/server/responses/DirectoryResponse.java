package server.responses;

import server.ResponseObject;

import java.io.*;

public class DirectoryResponse extends ResponseObject {
    private final File requestedDirectory;

    public DirectoryResponse(File requestedDirectory) {
        this.requestedDirectory = requestedDirectory;
    }

    public String getHeaders() {
        return "HTTP/1.1" + " 200 OK" + "\r\n" + "Content-Type: " + "text/html" + "; charset=UTF-8\r\n\r\n";
    }

    public String getBody(String route) {
        File[] listOfFiles = requestedDirectory.listFiles();
        return linkFiles(route, listOfFiles);
    }

    private String linkFiles(String route, File[] listOfFiles) {
        String response = "";
        for (File file : listOfFiles) {
            response += "<p>" + "<a href=http://localhost:4444/" + route + "/" + file.getName() + ">" + file.getName() + "</a><br />\r\n" + "</p>";
        }
        return response;
    }

}