package specs;

import org.junit.Test;
import server.requests.RequestReader;

import java.io.ByteArrayInputStream;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class RequestReaderTest {

    @Test
    public void canReadSimpleHeaders() throws IOException {
        String headers = "test";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(headers.getBytes());
        RequestReader requestReader = new RequestReader(testInputStream);
        assertEquals(requestReader.getHeaders(), headers);
    }

    @Test
    public void canReadHeadersWithNewlines() throws IOException {
        String headers = "GET /hellomate HTTP/1.1\nHost: localhost:4444";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(headers.getBytes());
        RequestReader requestReader = new RequestReader(testInputStream);
        assertEquals(requestReader.getHeaders(), headers);
    }

    @Test
    public void canTellLineIsValid() {
        String headers = "I love spicy food";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(headers.getBytes());
        RequestReader requestReader = new RequestReader(testInputStream);
        assertTrue(requestReader.lineReadable(headers));
    }

    @Test
    public void canTellLineIsInvalid() {
        String headers = "";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(headers.getBytes());
        RequestReader requestReader = new RequestReader(testInputStream);
        assertFalse(requestReader.lineReadable(headers));
    }

}