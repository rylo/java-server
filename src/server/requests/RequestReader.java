package server.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestReader {
    private BufferedReader bufferedReader;

    public RequestReader(InputStream inputStream) {
        InputStream inputStream1 = inputStream;
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream1);
        this.bufferedReader = new BufferedReader(inputStreamReader);
    }

    public String getHeaders() throws IOException {
        String headers = "";
        String line = bufferedReader.readLine();
        while(lineReadable(line)) {
            headers += line + "\n";
            line = bufferedReader.readLine();
        }
        return headers.trim();
    }

    public boolean lineReadable(String line) {
        return (line != null) && (!line.equals(""));
    }

}