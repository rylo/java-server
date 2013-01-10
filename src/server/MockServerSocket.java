package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MockServerSocket extends ServerSocket {
    boolean mockSocketCreated = false;

    public MockServerSocket() throws IOException {
        super();
    }

    public Socket accept() throws IOException {
        mockSocketCreated = true;
        return new MockSocket();
    }

    public boolean mockSocketCreated() {
        return mockSocketCreated;
    }


}