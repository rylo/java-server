package server;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketGenerator extends MyServerSocket {
    public ServerSocket serverSocket;

    public void listen() {
        serverSocket = openServerSocket(4444);
        ThreadGenerator threadGenerator = new ThreadGenerator();
        threadGenerator.setServerSocket(this.serverSocket);
        threadGenerator.beginProcess();
    }

    public ServerSocket openServerSocket(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverSocket;
    }

    public void closeServerSocket() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}