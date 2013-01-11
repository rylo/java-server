package server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerBuilder implements Runnable {
    protected Socket clientSocket;
    private ServerSocket serverSocket;
    public volatile int requests;
    private int limit = 100;


    private MyServerSocketGenerator serverSocketGenerator;

    public void start() {
        serverSocketGenerator.listen();
    }

    public void setServerSocketGenerator(MyServerSocketGenerator anyServerSocketGenerator) {
        this.serverSocketGenerator = anyServerSocketGenerator;
    }



    public void beginProcess() {

        new Thread(this).start();
    }

    public void setServerSocket(ServerSocket anyServerSocket) {
        this.serverSocket = anyServerSocket;
    }

    public void setRequestParser(MyRequestParser anyRequestParser) {
        this.requestParser = anyRequestParser;
    }

    public void setRequestLimit(int anyLimit) {
        this.limit = anyLimit;
    }
















    @Override
    public void run() {
        while(requests < limit) {
            try {
                clientSocket = serverSocket.accept();
//                requestPaer.beginProcess();

                // you create new Threads within here
                OutputStream binaryWriter = clientSocket.getOutputStream();
                PrintWriter stringWriter = new PrintWriter(binaryWriter, true);
                stringWriter.println("Hello world");
                requests++;
                binaryWriter.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
        }
    }

}