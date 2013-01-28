package server;

import server.responses.ResponseObject;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

public class ThreadBuilder implements Runnable {
    private final Socket clientSocket;
    private final HashMap<String, ResponseObject> routes;
    private ResponseObject responseObject;

    public ThreadBuilder(Socket clientSocket, HashMap<String, ResponseObject> routes) throws IOException {
        this.clientSocket = clientSocket;
        this.routes = routes;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            HashMap<String, String> httpRequestContent = processInputStream(inputStream);
            ResponseBuilder responseBuilder = new ResponseBuilder(clientSocket, httpRequestContent);
            ResponseObject responseObject = responseBuilder.generateResponseObject(routes);
            setResponseObject(responseObject);
            responseBuilder.sendResponse(responseObject);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, String> processInputStream(InputStream inputStream) throws IOException {
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