package server;

import server.requests.RequestParser;
import server.requests.RequestReader;
import server.responses.ResponseObject;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

public class ThreadBuilder implements Runnable {
    private final Socket clientSocket;

    public ThreadBuilder(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            List<String> requestContent = new RequestReader(inputStream).getRequestContent();
            RequestParser requestParser = new RequestParser(requestContent);

            requestParser.parseContent();
            HashMap<String, String> httpRequestContent = requestParser.storeParsedContent();

            ResponseBuilder responseBuilder = new ResponseBuilder(clientSocket, httpRequestContent);
            ResponseObject responseObject = responseBuilder.generateResponseObject();
            responseBuilder.formResponse(responseObject);

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}