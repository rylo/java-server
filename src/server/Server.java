package server;

public class Server {
    private MyServerSocket serverSocketGenerator;

    public void start() {
        serverSocketGenerator.listen();
    }

    public void setServerSocketGenerator(MyServerSocket anyServerSocketGenerator) {
        this.serverSocketGenerator = anyServerSocketGenerator;
    }
}