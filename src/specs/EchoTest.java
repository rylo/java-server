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
    public void canParseMultipleKeyValuePairsInQueryParams() {
        String parsedString = new Echo().get("echo?abc=123&def=456");
        assertEquals(parsedString, "Query string parameters are: \nabc=123\ndef=456\n");
    }

}