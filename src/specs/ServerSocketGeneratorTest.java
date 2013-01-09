package specs;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import server.ServerSocketGenerator;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketGeneratorTest {
    private ServerSocketGenerator serverSocketGenerator;

    @Before
    public void beforeEach() {
        serverSocketGenerator = new ServerSocketGenerator();
    }

    @Test
    public void serverSocketPortSet() throws IOException {
        ServerSocket serverSocket = serverSocketGenerator.openServerSocket();
        assertEquals(serverSocket.getLocalPort(), 4444);
        serverSocketGenerator.closeSocket();
    }

    @Test
    public void serverSocketCanBeClosed() throws IOException {
        ServerSocket serverSocket = serverSocketGenerator.openServerSocket();
        assertFalse(serverSocket.isClosed());
        serverSocketGenerator.closeSocket();
        assertTrue(serverSocket.isClosed());
    }

}