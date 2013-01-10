package server;

import java.net.ServerSocket;

public abstract class MyServerSocketGenerator {
    public boolean listening = false;

    public abstract void listen();

    public boolean isListening() {
        return listening;
    }

}