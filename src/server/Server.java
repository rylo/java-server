package server;

public class Server {
    private MyServerSocketGenerator serverSocketGenerator;

    public void start() {
        serverSocketGenerator.listen();
    }

    public void setServerSocketGenerator(MyServerSocketGenerator anyServerSocketGenerator) {
        this.serverSocketGenerator = anyServerSocketGenerator;
    }

}