package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerBuilder {
    public int limit;
    private ThreadBuilder threadBuilder;
    public int count;

    public ServerBuilder(int limit) {
        this.limit = limit;
    }

    public void begin() throws IOException {
        ServerSocket serverSocket = new ServerSocket(4444);
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