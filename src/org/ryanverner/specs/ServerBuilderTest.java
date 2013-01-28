package specs;

import org.junit.Test;
import server.ServerBuilder;

import java.io.IOException;

import static junit.framework.Assert.*;

public class ServerBuilderTest {

    @Test
    public void opensServerSocket() throws IOException {
        ServerBuilder serverBuilder = new ServerBuilder(1);
        MockServerSocket mockServerSocket = new MockServerSocket(5555);
        serverBuilder.setServerSocket(mockServerSocket);
        assertFalse(mockServerSocket.socketIsOpen());
        serverBuilder.begin();
        assertTrue(mockServerSocket.socketIsOpen());
        mockServerSocket.close();
    }

    @Test
    public void runsEntireProcess() throws IOException, InterruptedException {
        ServerBuilder serverBuilder = new ServerBuilder(1);
        MockServerSocket mockServerSocket = new MockServerSocket(6666);
        serverBuilder.setServerSocket(mockServerSocket);
        serverBuilder.begin();
        Thread.sleep(800);
        assertNotNull(serverBuilder.getThreadBuilder().getResponseObject());
        mockServerSocket.close();
    }

    @Test
    public void countsThreadsStarted() throws IOException, InterruptedException {
        ServerBuilder serverBuilder = new ServerBuilder(3);
        MockServerSocket mockServerSocket = new MockServerSocket(7777);
        serverBuilder.setServerSocket(mockServerSocket);
        serverBuilder.begin();
        assertEquals(3, serverBuilder.count);
        mockServerSocket.close();
    }

}