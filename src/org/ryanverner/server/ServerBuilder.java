package server;

import server.responses.ResponseObject;

import java.io.IOException;
import java.lang.String;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ServerBuilder {
    public HashMap<String, ResponseObject> routes = new HashMap<String, ResponseObject>();
    public int limit;
    public ServerSocket serverSocket;
    public ThreadBuilder threadBuilder;
    public int count;

    public ServerBuilder(int limit) {
        this.limit = limit;
    }

    public void begin() throws IOException {
        if(socketNotSet()) {
            setToDefaultSocket();
        }
        int count = 0;
        while(count < limit) {
            createThreadBuilder(serverSocket);
            new Thread(threadBuilder).start();
            count = count + 1;
            this.count = count;
        }
    }

    private void createThreadBuilder(ServerSocket serverSocket) {
        ThreadBuilder threadBuilder = null;
        try {
            Socket clientSocket = serverSocket.accept();
            threadBuilder = new ThreadBuilder(clientSocket, routes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.threadBuilder = threadBuilder;
    }

    public void setRoutes(HashMap<String, ResponseObject> routes) {
          this.routes = routes;
    }

    public void setServerSocket(ServerSocket serverSocket) throws IOException {
        this.serverSocket = serverSocket;
    }

    private void setToDefaultSocket() throws IOException {
        this.serverSocket = new ServerSocket(4444);
    }

    private boolean socketNotSet() {
        return serverSocket == null;
    }

    public ThreadBuilder getThreadBuilder() {
        return threadBuilder;
    }

}