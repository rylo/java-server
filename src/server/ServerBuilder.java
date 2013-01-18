package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerBuilder {
    public ServerSocket serverSocket;
    public int limit;
    private ThreadBuilder threadBuilder;

    public ServerBuilder(ServerSocket serverSocket, int limit) {
        this.serverSocket = serverSocket;
        this.limit = limit;
    }

    public void begin() {
        int count = 0;
        while(count < limit) {
            createThreadBuilder();
            new Thread(threadBuilder).start();
            count++;
        }
    }

    private void createThreadBuilder() {
        ThreadBuilder threadBuilder = null;
        try {
            Socket clientSocket = serverSocket.accept();
            threadBuilder = new ThreadBuilder(clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.threadBuilder = threadBuilder;
    }

    public ThreadBuilder getThreadBuilder() {
        return threadBuilder;
    }

}