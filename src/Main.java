import server.MyServerSocketGenerator;
import server.Server;
import server.ServerSocketGenerator;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        MyServerSocketGenerator serverSocketGenerator = new ServerSocketGenerator();
        Server server = new Server();
        server.setServerSocketGenerator(serverSocketGenerator);
        server.start();
    }

}