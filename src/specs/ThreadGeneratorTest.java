package specs;

import org.junit.Before;
import org.junit.Test;
import server.MockServerSocket;
import server.ThreadGenerator;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ThreadGeneratorTest {
    private MockServerSocket mockServerSocket;
    private ThreadGenerator threadGenerator;

    @Before
    public void beforeEach() throws IOException {
        mockServerSocket = new MockServerSocket();
        threadGenerator = new ThreadGenerator();
        threadGenerator.setServerSocket(mockServerSocket);
    }

    @Test
    public void oneRequestMade() throws IOException {
        Thread thread = threadGenerator.generate();
        thread.start();
        threadGenerator.setRequestLimit(1);
        try {
            thread.join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(threadGenerator.requests, 1);
    }

    @Test
    public void sixRequestsMade() throws IOException {
        Thread thread = threadGenerator.generate();
        thread.start();
        threadGenerator.setRequestLimit(6);
        try {
            thread.join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(threadGenerator.requests, 6);
    }

    @Test
    public void socketIsCreated() {
        Thread thread = threadGenerator.generate();
        thread.start();
        threadGenerator.setRequestLimit(6);
        try {
            thread.join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(mockServerSocket.mockSocketCreated());
    }

}