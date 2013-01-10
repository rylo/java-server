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
        ServerSocket serverSocket = serverSocketGenerator.openServerSocket(5555);
        assertEquals(serverSocket.getLocalPort(), 5555);
        serverSocketGenerator.closeServerSocket();
    }

    @Test
    public void serverSocketCloses() throws IOException {
        ServerSocket serverSocket = serverSocketGenerator.openServerSocket(6644);
        assertFalse(serverSocket.isClosed());
        serverSocketGenerator.closeServerSocket();
        assertTrue(serverSocket.isClosed());
    }

}