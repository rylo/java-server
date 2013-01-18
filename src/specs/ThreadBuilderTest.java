package specs;

import org.junit.Test;
import server.ThreadBuilder;
import server.responses.FailureResponse;

import java.io.IOException;
import java.net.Socket;

import static junit.framework.Assert.assertTrue;

public class ThreadBuilderTest {

    @Test
    public void createsNewThreadAndGeneratesResponseObject() throws IOException, InterruptedException {
        MockServerSocket mockServerSocket = new MockServerSocket(5555);
        Socket clientSocket = mockServerSocket.accept();
        ThreadBuilder threadBuilder = new ThreadBuilder(clientSocket);
        new Thread(threadBuilder).start();
        Thread.sleep(1000);
        assertTrue(threadBuilder.getResponseObject() instanceof FailureResponse);
        mockServerSocket.close();
    }

}