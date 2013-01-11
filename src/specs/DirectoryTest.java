package specs;

import org.junit.Test;

import server.requests.RequestParser;
import server.requests.RequestReader;
import server.responses.Directory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class DirectoryTest {

    @Test
    public void canGetDirectoryContents() {
        String requestedPath = System.getProperty("user.dir") + "/src";
        File[] allFiles = new File(requestedPath).listFiles();
        HashMap<String, String> testHttpRequestParameters = new HashMap<String, String>();
        testHttpRequestParameters.put("protocol", "HTTP/1.1");
        testHttpRequestParameters.put("route", "/hellomate");
        testHttpRequestParameters.put("method", "GET");
        File[] directoryContents = new Directory(testHttpRequestParameters).getDirectoryContents(new File(requestedPath));
        assertEquals(directoryContents[1], allFiles[1]);
    }

    @Test
    public void canGetTheRequestedDirectory() {
        String route = "/src";
        HashMap<String, String> testHttpRequestParameters = new HashMap<String, String>();
        testHttpRequestParameters.put("protocol", "HTTP/1.1");
        testHttpRequestParameters.put("route", "/hellomate");
        testHttpRequestParameters.put("method", "GET");
        File requestedDirectory = new Directory(testHttpRequestParameters).getRequestedDirectory(route);
        File testDirectory = new File(System.getProperty("user.dir") + route);
        assertEquals(requestedDirectory, testDirectory);
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
        HashMap<String, String> testHttpRequestParameters = new HashMap<String, String>();
        testHttpRequestParameters.put("protocol", "HTTP/1.1");
        testHttpRequestParameters.put("route", "/blah");
        testHttpRequestParameters.put("method", "GET");
        assertTrue(new Directory(testHttpRequestParameters).routeIsDirectory(route));
    }

    @Test
    public void canGetDirectoryResponse() {
        HashMap<String, String> testHttpRequestParameters = new HashMap<String, String>();
        testHttpRequestParameters.put("protocol", "HTTP/1.1");
        testHttpRequestParameters.put("route", "/server");
        testHttpRequestParameters.put("method", "GET");
        assertTrue(new Directory(testHttpRequestParameters).getDirectoryResponse("src").contains("200 OK"));
    }

}