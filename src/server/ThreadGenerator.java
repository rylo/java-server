package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadGenerator implements Runnable {
    protected Socket clientSocket;
    private ServerSocket serverSocket;
    public int requests;
    private int limit = 100;

    public void beginProcess() {
        setServerSocket(serverSocket);
        Thread thread = generate();
        thread.start();
    }

    public Thread generate() {
        return new Thread(this);
    }

    public void setServerSocket(ServerSocket anyServerSocket) {
        this.serverSocket = anyServerSocket;
    }

    public void setRequestLimit(int anyLimit) {
        this.limit = anyLimit;
    }

    @Override
    public void run() {
        while(requests < limit) {
            try {
                clientSocket = serverSocket.accept();
                OutputStream binaryWriter = clientSocket.getOutputStream();
                PrintWriter stringWriter = new PrintWriter(binaryWriter, true);
                stringWriter.println("Hello world");
                requests++;
                binaryWriter.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}