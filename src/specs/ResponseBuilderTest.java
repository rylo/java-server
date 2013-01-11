package specs;

import org.junit.Before;
import org.junit.Test;
import server.ResponseBuilder;
import server.requests.RequestParser;
import server.requests.RequestReader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ResponseBuilderTest {
    MockSocket mockSocket;
    HashMap<String, String> testHttpRequestParameters;
    ResponseBuilder responseBuilder;

    @Before
    public void beforeEach() throws IOException {
        this.mockSocket = new MockSocket();
        this.testHttpRequestParameters = new HashMap<String, String>();
        this.responseBuilder = new ResponseBuilder(mockSocket, testHttpRequestParameters);
    }

    @Test
    public void canTellThatRouteIsTime() throws IOException {
        String headers = "GET /time HTTP/1.1\nHost: localhost:4444";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(headers.getBytes());
        RequestReader requestReader = new RequestReader(testInputStream);
        String headersRead = requestReader.getHeaders();
        RequestParser requestParser = new RequestParser(headersRead);
        requestParser.parseHeaders();
        String route = requestParser.getRoute();
        testHttpRequestParameters.put("protocol", "HTTP/1.1");
        testHttpRequestParameters.put("route", "/time");
        testHttpRequestParameters.put("method", "GET");
        assertTrue(responseBuilder.routeIsTime(route));
    }

    @Test
    public void canTellThatRouteIsEcho() throws IOException {
        String headers = "GET /echo?abc=123 HTTP/1.1\nHost: localhost:4444";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(headers.getBytes());
        RequestReader requestReader = new RequestReader(testInputStream);
        String headersRead = requestReader.getHeaders();
        RequestParser requestParser = new RequestParser(headersRead);
        requestParser.parseHeaders();
        String route = requestParser.getRoute();
        testHttpRequestParameters.put("protocol", "HTTP/1.1");
        testHttpRequestParameters.put("route", "/echo");
        testHttpRequestParameters.put("method", "GET");
        assertTrue(responseBuilder.routeIsEcho(route));
    }

    @Test
    public void canTellThatRouteIsDirectory() throws IOException {
        String headers = "GET /src HTTP/1.1\nHost: localhost:4444";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(headers.getBytes());
        RequestReader requestReader = new RequestReader(testInputStream);
        String headersRead = requestReader.getHeaders();
        RequestParser requestParser = new RequestParser(headersRead);
        requestParser.parseHeaders();
        String route = requestParser.getRoute();
        testHttpRequestParameters.put("protocol", "HTTP/1.1");
        testHttpRequestParameters.put("route", "/blah");
        testHttpRequestParameters.put("method", "GET");
        assertTrue(responseBuilder.routeIsDirectory(route));
    }

    @Test
    public void canGetTheRequestedDirectory() {
        String route = "/src";
        File requestedDirectory = responseBuilder.getRequestedDirectory(route);
        File testDirectory = new File(System.getProperty("user.dir") + route);
        assertEquals(requestedDirectory, testDirectory);
    }

}