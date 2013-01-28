package specs;

import org.junit.Test;
import server.RequestParser;
import server.RequestReader;
import server.responses.EchoResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class EchoResponseTest {

    @Test
    public void correctlyDisplaysTheQueryParams() throws IOException {
        String testHeaders = "GET /echo?key=value&name=Tom&sport=basketball HTTP/1.1\nHost: localhost:4444";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(testHeaders.getBytes());

        RequestReader requestReader = new RequestReader(testInputStream);
        List<String> headers = requestReader.getRequestContent();

        RequestParser requestParser = new RequestParser(headers);
        requestParser.parseContent();
        HashMap<String, String> httpRequestHeaders = requestParser.storeParsedContent();

        EchoResponse echoResponse = new EchoResponse();
        assertEquals(echoResponse.getBody(requestParser.getRoute(), requestParser.getParsedRoute()), "Query string parameters are: \nkey=value\nname=Tom\nsport=basketball\n");
    }

}