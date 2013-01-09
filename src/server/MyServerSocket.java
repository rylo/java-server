package server;

public abstract class MyServerSocket {
    public boolean listening = false;

    public abstract void listen();

    public boolean isListening() {
        return listening;
    }

}