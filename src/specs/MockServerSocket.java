package specs;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MockServerSocket extends ServerSocket {
    boolean socketStatus = false;

    public MockServerSocket(int port) throws IOException {
        super(port);
    }

    public Socket accept() throws IOException {
        socketStatus = true;
        return new MockSocket();
    }

    public boolean socketIsOpen() {
        return socketStatus;
    }

}