package server;

public class MockServerSocketGenerator extends MyServerSocketGenerator {

    public void listen() {
        listening = true;
        isListening();
    }

}