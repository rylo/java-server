package specs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    public InputStream getInputStream() {
        String headers = "GET /somefancyroute HTTP/1.1\nHost: localhost:4444";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(headers.getBytes());
        return testInputStream;
//        return new InputStream() {
//            @Override
//            public int read() throws IOException {
//                return 0;
//            }
//        };
    }

}