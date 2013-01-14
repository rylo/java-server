package server.responses;

import server.RouteResponse;

import java.io.*;

public class FileResponse extends RouteResponse {
    private final File requestedFile;

    public FileResponse(File requestedFile) {
        this.requestedFile = requestedFile;
    }

    @Override
    public String getHeaders() {
        return "HTTP/1.1" + " 200 OK" + "\r\n" + "Content-Type: " + "text/plain" + "; charset=UTF-8\r\n\r\n";
    }

    @Override
    public String getBody(String route) {
        BufferedReader reader;
        String response = "";
        try {
            FileReader fileReader = new FileReader(requestedFile);
            reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
                response += line + "\n";
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}