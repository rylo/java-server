package server.responses;

import java.io.File;

public class Directory {

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


}