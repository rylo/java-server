package specs;

import org.junit.Test;
import server.RequestParser;
import server.RequestReader;
import server.ResponseBuilder;
import server.responses.ResponseObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class RequestParserTest {

    @Test
    public void canParseHeadersThatHaveBeenRead() throws IOException {
        String testHeaders = "GET /hellomate HTTP/1.1\nHost: localhost:4444";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(testHeaders.getBytes());

        RequestReader requestReader = new RequestReader(testInputStream);
        List<String> requestContent = requestReader.getRequestContent();

        RequestParser requestParser = new RequestParser(requestContent);
        requestParser.parseContent();
        HashMap<String, String> httpRequestHeaders = requestParser.storeParsedContent();

        HashMap<String, String> testHttpRequestHeaders = new HashMap<String, String>();
        testHttpRequestHeaders.put("body", "");
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
        List<String> requestContent = requestReader.getRequestContent();

        RequestParser requestParser = new RequestParser(requestContent);
        requestParser.parseContent();

        assertEquals("GET", requestParser.getMethod());
    }

    @Test
    public void canRetrieveTheRoute() throws IOException {
        String headers = "GET /aswellroute HTTP/1.1\nHost: localhost:4444";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(headers.getBytes());

        RequestReader requestReader = new RequestReader(testInputStream);
        List<String> requestContent = requestReader.getRequestContent();

        RequestParser requestParser = new RequestParser(requestContent);
        requestParser.parseContent();

        assertEquals("aswellroute", requestParser.getRoute());
    }

    @Test
    public void canRetrieveTheProtocol() throws IOException {
        String headers = "GET /hellomate HTTP/1.1\nHost: localhost:4444";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(headers.getBytes());

        RequestReader requestReader = new RequestReader(testInputStream);
        List<String> requestContent = requestReader.getRequestContent();

        RequestParser requestParser = new RequestParser(requestContent);
        requestParser.parseContent();

        assertEquals("HTTP/1.1", requestParser.getProtocol());
    }

    @Test
    public void canSplitRouteWithOneKeyValuePair() throws IOException {
        String headers = "GET /echo?abc=123 HTTP/1.1\nHost: localhost:4444";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(headers.getBytes());

        RequestReader requestReader = new RequestReader(testInputStream);
        List<String> requestContent = requestReader.getRequestContent();

        RequestParser requestParser = new RequestParser(requestContent);
        requestParser.parseContent();

        assertEquals(requestParser.getParsedRoute(), "abc=123\n");
    }

    @Test
    public void canSplitRouteWithThreeKeyValuePairs() throws IOException {
        String headers = "GET /echo?abc=123&name=Ryan&hair=red HTTP/1.1\nHost: localhost:4444";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(headers.getBytes());

        RequestReader requestReader = new RequestReader(testInputStream);
        List<String> requestContent = requestReader.getRequestContent();

        RequestParser requestParser = new RequestParser(requestContent);
        requestParser.parseContent();

        assertEquals(requestParser.getParsedRoute(), "abc=123\nname=Ryan\nhair=red\n");
    }

    @Test
    public void canSplitRouteWithNoKeyValuePairs() throws IOException {
        String headers = "GET /echo HTTP/1.1\nHost: localhost:4444";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(headers.getBytes());

        RequestReader requestReader = new RequestReader(testInputStream);
        List<String> requestContent = requestReader.getRequestContent();

        RequestParser requestParser = new RequestParser(requestContent);
        requestParser.parseContent();
        String parsedRoute = requestParser.splitRouteAtQuestionMark(requestParser.getRoute());

        assertEquals(parsedRoute, "");
    }

    @Test
    public void canCorrectlyRecognizeBodyContentAndRetrieveIt() throws IOException {
        String content = "POST /echopost HTTP/1.1\nHost: localhost:4444\nContent-Length: 11\n\nA=1&B=2&C=3";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(content.getBytes());
        List<String> requestContent = new RequestReader(testInputStream).getRequestContent();
        RequestParser requestParser = new RequestParser(requestContent);
        requestParser.parseContent();
        requestParser.storeParsedContent();
        assertEquals(requestParser.getBody(), "A=1\nB=2\nC=3\n");
    }

}