package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Starter {
    private ServerSocket serverSocketListening;
    private Socket clientSocket;

    public Starter() {
        serverSocketListening = null;
        clientSocket = null;
    }

    public void go(int count) throws IOException {
        serverSocketListening  = listenOnPort(4444);
        try {
            while(count == 1) {
                clientSocket = waitForSocketConnection(serverSocketListening);
                openThread(clientSocket);
                count++;
            }
        } catch(SocketException e) {
            e.printStackTrace();
        }
    }

    public void openThread(Socket clientSocket) throws SocketException {
        ThreadGenerator threadGenerator = new ThreadGenerator(clientSocket);
        new Thread(threadGenerator).start();
    }

    public ServerSocket listenOnPort(int port) {
        try {
            serverSocketListening = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverSocketListening ;
    }

    public Socket waitForSocketConnection(ServerSocket serverSocketListening) {
        try {
            clientSocket = serverSocketListening.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientSocket;
    }

    public void stopServer() throws IOException {
        serverSocketListening.close();
    }

}