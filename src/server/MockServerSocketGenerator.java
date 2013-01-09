package server;

public class MockServerSocketGenerator extends MyServerSocket {

    public void listen() {
        listening = true;
        isListening();
    }

}