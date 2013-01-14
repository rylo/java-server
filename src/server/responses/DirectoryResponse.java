package server.responses;

import server.RouteResponse;

import java.io.*;

public class DirectoryResponse extends RouteResponse {

    public String getHeaders() {
        return "HTTP/1.1" + " 200 OK" + "\r\n" + "Content-Type: " + "text/html" + "; charset=UTF-8\r\n\r\n";
    }

    public String getBody(String route) {
        String directoryContents;
        File requestedDirectory = getRequestedDirectory(route);
        if(routeIsDirectory(requestedDirectory)) {
            directoryContents = getDirectoryContents(requestedDirectory);
        } else {
            directoryContents = "404: Not a file or directory";
            //responseParameters = getResponseParameters(" 404 Not Found", "text/html");
        }
        return directoryContents;
    }

    public boolean routeIsDirectory(File requestedDirectory) {
        return requestedDirectory.isDirectory();
    }

    public File getRequestedDirectory(String route) {
        String requestedPath = System.getProperty("user.dir") + "/" + route;
        return new File(requestedPath);
    }

    public String getDirectoryContents(File requestedDirectory) {
        File[] listOfFiles = requestedDirectory.listFiles();
        String response = addTags(listOfFiles);
        return response;
    }

    private String addTags(File[] listOfFiles) {
        String response = "";
        for (File file : listOfFiles) {
            response += "<p>" + "<a href=http://localhost:4444/" + file.getName() + ">" + file.getName() + "</a><br />\r\n" + "</p>";
        }
        return response;
    }












//    public File[] getDirectoryContents(File requestedPath) {
//        File[] listOfFiles = requestedPath.listFiles();
//        return listOfFiles;
//    }




    public void getFileResponse(String route) {
        File requestedFile = getRequestedDirectory(route);
        BufferedReader reader = null;
        try {
            FileReader fileReader = new FileReader(requestedFile);
            reader = new BufferedReader(fileReader);
            String line;
            String response = "";
            while ((line = reader.readLine()) != null) {
                response += line;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean routeIsFile(String route) {
        File requestedFile = getRequestedDirectory(route);
        return requestedFile.isFile();
    }

//    public String getResponseParameters(String statusCode, String contentType) {
//
//    }

}