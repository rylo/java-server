package specs;

import org.junit.Test;
import server.responses.Echo;

import static junit.framework.Assert.assertEquals;

public class EchoTest {

    @Test
    public void canParseOneKeyValuePairInQueryParams() {
        String parsedString = new Echo().get("echo?abc=123");
        assertEquals(parsedString, "Query string parameters are: \nabc=123\n");
    }

    @Test
    public void canParseTwoKeyValuePairsInQueryParams() {
        String parsedString = new Echo().get("echo?abc=123&def=456");
        assertEquals(parsedString, "Query string parameters are: \nabc=123\ndef=456\n");
    }

    @Test
    public void canParseFiveKeyValuePairsInQueryParams() {
        String parsedString = new Echo().get("echo?abc=123&def=456&ghi=789&jkl=101&mno=999");
        assertEquals(parsedString, "Query string parameters are: \nabc=123\ndef=456\nghi=789\njkl=101\nmno=999\n");
    }

    @Test
    public void canHandleNoQueryString() {
        String parsedString = new Echo().get("echo");
        assertEquals(parsedString, "Query string parameters are: \n");
    }

}