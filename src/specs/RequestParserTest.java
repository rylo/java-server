package specs;

import org.junit.Test;
import server.requests.RequestParser;
import server.requests.RequestReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;

public class RequestParserTest {

    @Test
    public void canParseHeadersThatHaveBeenRead() throws IOException {
        String testHeaders = "GET /hellomate HTTP/1.1\nHost: localhost:4444";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(testHeaders.getBytes());

        RequestReader requestReader = new RequestReader(testInputStream);
        String headers = requestReader.getHeaders();

        RequestParser requestParser = new RequestParser(headers);
        requestParser.parseHeaders();
        HashMap<String, String> httpRequestHeaders = requestParser.storeParsedHeaders();

        HashMap<String, String> testHttpRequestHeaders = new HashMap<String, String>();
        testHttpRequestHeaders.put("parsedRoute", "");
        testHttpRequestHeaders.put("protocol", "HTTP/1.1");
        testHttpRequestHeaders.put("route", "hellomate");
        testHttpRequestHeaders.put("method", "GET");

        assertEquals(httpRequestHeaders, testHttpRequestHeaders);

    }

    @Test
    public void canRetrieveTheMethod() throws IOException {
        String headers = "GET /hellomate HTTP/1.1\nHost: localhost:4444";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(headers.getBytes());

        RequestReader requestReader = new RequestReader(testInputStream);
        String headersRead = requestReader.getHeaders();

        RequestParser requestParser = new RequestParser(headersRead);
        requestParser.parseHeaders();

        assertEquals("GET", requestParser.getMethod());
    }

    @Test
    public void canRetrieveTheRoute() throws IOException {
        String headers = "GET /aswellroute HTTP/1.1\nHost: localhost:4444";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(headers.getBytes());

        RequestReader requestReader = new RequestReader(testInputStream);
        String headersRead = requestReader.getHeaders();

        RequestParser requestParser = new RequestParser(headersRead);
        requestParser.parseHeaders();

        assertEquals("aswellroute", requestParser.getRoute());
    }

    @Test
    public void canRetrieveTheProtocol() throws IOException {
        String headers = "GET /hellomate HTTP/1.1\nHost: localhost:4444";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(headers.getBytes());

        RequestReader requestReader = new RequestReader(testInputStream);
        String headersRead = requestReader.getHeaders();

        RequestParser requestParser = new RequestParser(headersRead);
        requestParser.parseHeaders();

        assertEquals("HTTP/1.1", requestParser.getProtocol());
    }

}