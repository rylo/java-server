package server;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RequestReader {
    private BufferedReader bufferedReader;

    public RequestReader(InputStream inputStream) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        this.bufferedReader = new BufferedReader(inputStreamReader);
    }

    public List<String> getRequestContent() throws IOException {
        String headers = "";
        String body = "";
        final String contentLengthString = "Content-Length: ";
        int contentLength = 0;
        boolean isPostRequest = false;

        String line = bufferedReader.readLine();
        while(lineReadable(line)) {
            if(line.contains("POST")) {
                isPostRequest = true;
            }
            if(line.startsWith(contentLengthString)) {
                contentLength = Integer.parseInt(line.substring(contentLengthString.length()));
            }
            headers += line + "\n";
            line = bufferedReader.readLine();
        }
        if(isPostRequest) {
            body = getBody(bufferedReader, contentLength);
        }

        List<String> requestContent = new ArrayList<String>();
        requestContent.add(removeWhiteSpace(headers));
        requestContent.add(body);
        return requestContent;
    }

    private String getBody(BufferedReader bufferedReader, int contentLength) throws IOException {
        char[] characters = new char[contentLength];
        StringBuilder postContent = new StringBuilder();
        bufferedReader.read(characters, 0, contentLength);
        postContent.append(new String(characters));
        return postContent.toString();
    }

    public String removeWhiteSpace(String headers) {
        return headers.trim();
    }

    public boolean lineReadable(String line) {
        return (line != null) && (!line.equals(""));
    }

}