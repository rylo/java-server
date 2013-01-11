import server.ServerBuilder;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4444);
        ServerBuilder serverBuilder = new ServerBuilder(serverSocket);
        serverBuilder.begin();
    }

}