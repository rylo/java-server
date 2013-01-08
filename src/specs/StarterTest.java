package specs;

import org.junit.Test;
import server.Starter;

import java.io.IOException;
import java.net.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class StarterTest {
    class TestStarter extends Starter {

    }

    TestStarter testStarter;
    ServerSocket serverSocketListening = null;
    Socket clientSocket;

    public StarterTest() {
        testStarter = new TestStarter();
        clientSocket = new Socket();
    }

    @Test
    public void portIsSetCorrectly() throws IOException {
        serverSocketListening = testStarter.listenOnPort(4444);
        int port = serverSocketListening.getLocalPort();
        assertEquals(port, 4444);
        testStarter.stopServer();
    }

    @Test
    public void serverSocketIsOpen() throws IOException {
        serverSocketListening = testStarter.listenOnPort(4444);
        assertNotNull(serverSocketListening);
        testStarter.stopServer();
    }

    @Test
    public void serverSocketCanBeClosed() throws IOException {
        serverSocketListening = testStarter.listenOnPort(4444);
        assertEquals(serverSocketListening.isClosed(), false);
        testStarter.stopServer();
        assertEquals(serverSocketListening.isClosed(), true);
    }

}