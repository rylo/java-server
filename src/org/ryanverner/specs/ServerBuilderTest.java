package specs;

import org.junit.Test;
import server.ServerBuilder;

import java.io.IOException;

import static junit.framework.Assert.*;

public class ServerBuilderTest {

//    @Test
//    public void opensServerSocket() throws IOException {
//        MockServerSocket mockServerSocket = new MockServerSocket(5555);
//        ServerBuilder serverBuilder = new ServerBuilder(mockServerSocket, 1);
//        assertFalse(mockServerSocket.socketIsOpen());
//        serverBuilder.begin();
//        assertTrue(mockServerSocket.socketIsOpen());
//        mockServerSocket.close();
//    }
//
//    @Test
//    public void runsEntireProcess() throws IOException, InterruptedException {
//        MockServerSocket mockServerSocket = new MockServerSocket(5555);
//        ServerBuilder serverBuilder = new ServerBuilder(mockServerSocket, 1);
//        serverBuilder.begin();
//        Thread.sleep(800);
//        assertNotNull(serverBuilder.getThreadBuilder().getResponseObject());
//        mockServerSocket.close();
//    }
//
//    @Test
//    public void countsThreadsStarted() throws IOException, InterruptedException {
//        MockServerSocket mockServerSocket = new MockServerSocket(5555);
//        int limit = 3;
//        ServerBuilder serverBuilder = new ServerBuilder(mockServerSocket, limit);
//        serverBuilder.begin();
//        assertEquals(3, serverBuilder.count);
//        mockServerSocket.close();
//    }

}