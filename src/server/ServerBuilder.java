package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerBuilder {
    public ServerSocket serverSocket;

    public ServerBuilder(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void begin() {
        while(true) {
            try {
                Socket clientSocket = serverSocket.accept();
                ThreadBuilder threadBuilder = new ThreadBuilder(clientSocket);
                new Thread(threadBuilder).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}