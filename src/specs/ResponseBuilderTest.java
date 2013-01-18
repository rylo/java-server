package specs;

import org.junit.Before;
import org.junit.Test;
import server.ResponseBuilder;
import server.RequestParser;
import server.RequestReader;
import server.responses.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ResponseBuilderTest {
    MockSocket mockSocket;

    @Before
    public void beforeEach() throws IOException {
        this.mockSocket = new MockSocket();
    }

    @Test
    public void canGetTheRequestedPath() {
        HashMap<String, String> testHttpRequestParameters = new HashMap<String, String>();
        ResponseBuilder responseBuilder = new ResponseBuilder(mockSocket, testHttpRequestParameters);
        String route = "src";
        assertEquals(responseBuilder.getRequestedPath(route), new File("/Users/ryanzverner/Documents/Coding/8thLight/java-server-two/src"));
    }

    @Test
    public void generatesEchoResponseObject() throws IOException {
        String testHeaders = "GET /echo?abc=123&name=tim HTTP/1.1\nHost: localhost:4444";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(testHeaders.getBytes());

        RequestReader requestReader = new RequestReader(testInputStream);
        List<String> requestContent = requestReader.getRequestContent();

        RequestParser requestParser = new RequestParser(requestContent);
        requestParser.parseContent();
        String route = requestParser.getRoute();

        HashMap<String, String> testHttpRequestParameters = new HashMap<String, String>();
        testHttpRequestParameters.put("route", route);
        testHttpRequestParameters.put("method", requestParser.getMethod());
        testHttpRequestParameters.put("parsedRoute", requestParser.getParsedRoute());
        testHttpRequestParameters.put("body", requestParser.getBody());
        ResponseBuilder responseBuilder = new ResponseBuilder(mockSocket, testHttpRequestParameters);

        assertTrue(responseBuilder.generateResponseObject() instanceof EchoResponse);
    }

    @Test
    public void generatesTimeResponseObject() throws IOException {
        String testHeaders = "GET /time HTTP/1.1\nHost: localhost:4444";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(testHeaders.getBytes());

        RequestReader requestReader = new RequestReader(testInputStream);
        List<String> requestContent = requestReader.getRequestContent();

        RequestParser requestParser = new RequestParser(requestContent);
        requestParser.parseContent();
        String route = requestParser.getRoute();

        HashMap<String, String> testHttpRequestParameters = new HashMap<String, String>();
        testHttpRequestParameters.put("route", route);
        testHttpRequestParameters.put("method", requestParser.getMethod());
        testHttpRequestParameters.put("parsedRoute", requestParser.getParsedRoute());
        testHttpRequestParameters.put("body", requestParser.getBody());
        ResponseBuilder responseBuilder = new ResponseBuilder(mockSocket, testHttpRequestParameters);

        assertTrue(responseBuilder.generateResponseObject() instanceof TimeResponse);
    }

    @Test
    public void generatesDirectoryResponseObject() throws IOException {
        String testHeaders = "GET / HTTP/1.1\nHost: localhost:4444";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(testHeaders.getBytes());

        RequestReader requestReader = new RequestReader(testInputStream);
        List<String> requestContent = requestReader.getRequestContent();

        RequestParser requestParser = new RequestParser(requestContent);
        requestParser.parseContent();
        String route = requestParser.getRoute();

        HashMap<String, String> testHttpRequestParameters = new HashMap<String, String>();
        testHttpRequestParameters.put("route", route);
        testHttpRequestParameters.put("method", requestParser.getMethod());
        testHttpRequestParameters.put("parsedRoute", requestParser.getParsedRoute());
        testHttpRequestParameters.put("body", requestParser.getBody());
        ResponseBuilder responseBuilder = new ResponseBuilder(mockSocket, testHttpRequestParameters);

        assertTrue(responseBuilder.generateResponseObject() instanceof DirectoryResponse);
    }

    @Test
    public void generatesGetEchoPostResponseObject() throws IOException {
        String testHeaders = "GET /echopost HTTP/1.1\nHost: localhost:4444";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(testHeaders.getBytes());

        RequestReader requestReader = new RequestReader(testInputStream);
        List<String> requestContent = requestReader.getRequestContent();

        RequestParser requestParser = new RequestParser(requestContent);
        requestParser.parseContent();
        String route = requestParser.getRoute();

        HashMap<String, String> testHttpRequestParameters = new HashMap<String, String>();
        testHttpRequestParameters.put("route", route);
        testHttpRequestParameters.put("method", requestParser.getMethod());
        testHttpRequestParameters.put("parsedRoute", requestParser.getParsedRoute());
        testHttpRequestParameters.put("body", requestParser.getBody());
        ResponseBuilder responseBuilder = new ResponseBuilder(mockSocket, testHttpRequestParameters);

        assertTrue(responseBuilder.generateResponseObject() instanceof GetEchoPostResponse);
    }

    @Test
    public void generatesPostEchoPostResponseObject() throws IOException {
        String testHeaders = "POST /echopost HTTP/1.1\nHost: localhost:4444";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(testHeaders.getBytes());

        RequestReader requestReader = new RequestReader(testInputStream);
        List<String> requestContent = requestReader.getRequestContent();

        RequestParser requestParser = new RequestParser(requestContent);
        requestParser.parseContent();
        String route = requestParser.getRoute();

        HashMap<String, String> testHttpRequestParameters = new HashMap<String, String>();
        testHttpRequestParameters.put("route", route);
        testHttpRequestParameters.put("method", requestParser.getMethod());
        testHttpRequestParameters.put("parsedRoute", requestParser.getParsedRoute());
        testHttpRequestParameters.put("body", requestParser.getBody());
        ResponseBuilder responseBuilder = new ResponseBuilder(mockSocket, testHttpRequestParameters);

        assertTrue(responseBuilder.generateResponseObject() instanceof PostEchoPostResponse);
    }

    @Test
    public void test() throws IOException {
        String testHeaders = "POST /echopost HTTP/1.1\nHost: localhost:4444";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(testHeaders.getBytes());

        RequestReader requestReader = new RequestReader(testInputStream);
        List<String> requestContent = requestReader.getRequestContent();

        RequestParser requestParser = new RequestParser(requestContent);
        requestParser.parseContent();
        String route = requestParser.getRoute();

        HashMap<String, String> testHttpRequestParameters = new HashMap<String, String>();
        testHttpRequestParameters.put("route", route);
        testHttpRequestParameters.put("method", requestParser.getMethod());
        testHttpRequestParameters.put("parsedRoute", requestParser.getParsedRoute());
        testHttpRequestParameters.put("body", requestParser.getBody());
        ResponseBuilder responseBuilder = new ResponseBuilder(mockSocket, testHttpRequestParameters);
        assertTrue(responseBuilder.getRoutesMap().get("time") instanceof TimeResponse);
    }

    //    @Test
//    public void canTellThatRouteIsTime() throws IOException {
//
//    }

//    @Test
//    public void canTellThatRouteIsEcho() throws IOException {
//
//    }

}