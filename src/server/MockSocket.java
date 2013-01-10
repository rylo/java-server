package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class MockSocket extends Socket {

    public MockSocket() throws IOException {
        super();
    }

    public OutputStream getOutputStream() throws IOException {
        return new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }
        };
    }

}