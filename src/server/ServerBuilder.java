package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerBuilder {
    public ServerSocket serverSocket;
    public volatile int requests = 0;
    public int limit = 5;

    public ServerBuilder(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void begin() {
        while(requests < limit) {
            try {
                Socket clientSocket = serverSocket.accept();
                ThreadBuilder threadBuilder = new ThreadBuilder(clientSocket);
                threadBuilder.run();
                requests++;
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}