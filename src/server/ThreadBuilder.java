package server;

import server.requests.RequestParser;
import server.requests.RequestReader;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class ThreadBuilder implements Runnable {
    private final Socket clientSocket;

    public ThreadBuilder(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            String headers = new RequestReader(inputStream).getHeaders();
            HashMap<String,String> httpRequestParameters = new RequestParser(headers).parseHeaders();
            ResponseBuilder responseBuilder = new ResponseBuilder(clientSocket, httpRequestParameters);
            responseBuilder.generateResponse();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}