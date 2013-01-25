package specs;

import org.junit.Test;
import server.ThreadBuilder;
import server.responses.FailureResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
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

    @Test
    public void canProcessAnInputStream() throws IOException {
        MockServerSocket mockServerSocket = new MockServerSocket(5555);
        Socket clientSocket = mockServerSocket.accept();
        ThreadBuilder threadBuilder = new ThreadBuilder(clientSocket);
        String testHeaders = "GET /time HTTP/1.1\nHost: localhost:5555";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(testHeaders.getBytes());
        HashMap<String, String> httpRequestContent = threadBuilder.processInputStream(testInputStream);
        assertEquals(httpRequestContent.get("method"), "GET");
        assertEquals(httpRequestContent.get("route"), "time");
        assertEquals(httpRequestContent.get("parsedRoute"), "");
        assertEquals(httpRequestContent.get("protocol"), "HTTP/1.1");
    }

}