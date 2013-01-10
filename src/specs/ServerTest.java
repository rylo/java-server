package specs;

import org.junit.Before;
import org.junit.Test;
import server.MockServerSocketGenerator;
import server.MyServerSocketGenerator;
import server.Server;

import static junit.framework.Assert.assertTrue;

public class ServerTest {
    private Server server;
    private MyServerSocketGenerator mockServerSocketGenerator;

    @Before
    public void beforeEach() {
        server = new Server();
        mockServerSocketGenerator = new MockServerSocketGenerator();
    }

    @Test
    public void socketListens() {
        server.setServerSocketGenerator(mockServerSocketGenerator);
        mockServerSocketGenerator.listen();
        assertTrue(mockServerSocketGenerator.isListening());
    }

}