package server;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketGenerator extends MyServerSocket {
    public ServerSocket serverSocket;

    public void listen() {
        serverSocket = openServerSocket();
        try {
            serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ServerSocket openServerSocket() {
        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverSocket;
    }

    public void closeSocket() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}