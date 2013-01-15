package specs;

import org.junit.Before;
import org.junit.Test;
import server.ResponseBuilder;
import server.requests.RequestParser;
import server.requests.RequestReader;
import server.responses.DirectoryResponse;
import server.responses.EchoResponse;
import server.responses.TimeResponse;

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
        String testHeaders = "GET /time HTTP/1.1\nHost: localhost:4444";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(testHeaders.getBytes());

        RequestReader requestReader = new RequestReader(testInputStream);
        String headers = requestReader.getHeaders();

        RequestParser requestParser = new RequestParser(headers);
        requestParser.parseHeaders();
        String route = requestParser.getRoute();

        assertTrue(responseBuilder.routeIsTime(route));
    }

    @Test
    public void canTellThatRouteIsEcho() throws IOException {
        String testHeaders = "GET /echo?abc=123 HTTP/1.1\nHost: localhost:4444";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(testHeaders.getBytes());

        RequestReader requestReader = new RequestReader(testInputStream);
        String headers = requestReader.getHeaders();

        RequestParser requestParser = new RequestParser(headers);
        requestParser.parseHeaders();
        String route = requestParser.getRoute();

        assertTrue(responseBuilder.routeIsEcho(route));
    }

    @Test
    public void canGetTheRequestedPath() {
        String route = "src";
        assertEquals(responseBuilder.getRequestedPath(route), new File("/Users/ryanzverner/Documents/Coding/8thLight/java-server-two/src"));
    }

    @Test
    public void generatesEchoResponseObject() throws IOException {
        String testHeaders = "GET /echo?abc=123&size=big HTTP/1.1\nHost: localhost:4444";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(testHeaders.getBytes());

        RequestReader requestReader = new RequestReader(testInputStream);
        String headers = requestReader.getHeaders();

        RequestParser requestParser = new RequestParser(headers);
        requestParser.parseHeaders();
        testHttpRequestParameters.put("route", requestParser.getRoute());

        assertTrue(responseBuilder.generateResponseObject() instanceof EchoResponse);
    }

    @Test
    public void generatesTimeResponseObject() throws IOException {
        String testHeaders = "GET /time HTTP/1.1\nHost: localhost:4444";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(testHeaders.getBytes());

        RequestReader requestReader = new RequestReader(testInputStream);
        String headers = requestReader.getHeaders();

        RequestParser requestParser = new RequestParser(headers);
        requestParser.parseHeaders();
        testHttpRequestParameters.put("route", requestParser.getRoute());

        assertTrue(responseBuilder.generateResponseObject() instanceof TimeResponse);
    }

    @Test
    public void generatesDirectoryResponseObject() throws IOException {
        String testHeaders = "GET /src HTTP/1.1\nHost: localhost:4444";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(testHeaders.getBytes());

        RequestReader requestReader = new RequestReader(testInputStream);
        String headers = requestReader.getHeaders();

        RequestParser requestParser = new RequestParser(headers);
        requestParser.parseHeaders();
        testHttpRequestParameters.put("route", requestParser.getRoute());

        assertTrue(responseBuilder.generateResponseObject() instanceof DirectoryResponse);
    }

}