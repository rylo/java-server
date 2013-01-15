package specs;

import org.junit.Test;
import server.requests.RequestParser;
import server.requests.RequestReader;
import server.responses.EchoResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;

public class EchoTest {

    @Test
    public void correctlyDisplaysTheQueryParams() throws IOException {
        String testHeaders = "GET /echo?key=value&name=Tom&sport=basketball HTTP/1.1\nHost: localhost:4444";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(testHeaders.getBytes());

        RequestReader requestReader = new RequestReader(testInputStream);
        String headers = requestReader.getHeaders();

        RequestParser requestParser = new RequestParser(headers);
        requestParser.parseHeaders();
        HashMap<String, String> httpRequestHeaders = requestParser.storeParsedHeaders();

        EchoResponse echoResponse = new EchoResponse(httpRequestHeaders.get("parsedRoute"));
        assertEquals(echoResponse.getBody(requestParser.getRoute()), "Query string parameters are: \nkey=value\nname=Tom\nsport=basketball\n");
    }

}