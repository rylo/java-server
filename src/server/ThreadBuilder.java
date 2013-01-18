package server;

import server.responses.ResponseObject;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

public class ThreadBuilder implements Runnable {
    private final Socket clientSocket;
    private ResponseObject responseObject;

    public ThreadBuilder(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            HashMap<String, String> httpRequestContent = processInputStream(inputStream);
            ResponseBuilder responseBuilder = new ResponseBuilder(clientSocket, httpRequestContent);
            ResponseObject responseObject = responseBuilder.generateResponseObject();
            setResponseObject(responseObject);
            responseBuilder.sendResponse(responseObject);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HashMap<String, String> processInputStream(InputStream inputStream) throws IOException {
        List<String> requestContent = new RequestReader(inputStream).getRequestContent();
        RequestParser requestParser = new RequestParser(requestContent);
        requestParser.parseContent();
        return requestParser.storeParsedContent();
    }

    private void setResponseObject(ResponseObject responseObject) {
        this.responseObject = responseObject;
    }

    public ResponseObject getResponseObject() {
        return responseObject;
    }

}