import server.MyServerSocket;
import server.Server;
import server.ServerSocketGenerator;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        MyServerSocket serverSocketGenerator = new ServerSocketGenerator();
        Server server = new Server();
        server.setServerSocketGenerator(serverSocketGenerator);
        server.start();
    }

}