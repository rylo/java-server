package specs;

import org.junit.Test;
import server.responses.DirectoryResponse;

import java.io.File;

import static junit.framework.Assert.assertEquals;

public class DirectoryResponseTest {

    @Test
    public void canGetDirectoryContents() {
        String requestedPath = System.getProperty("user.dir") + "/" + "src";
        File directory = new File(requestedPath);
        File[] listOfFiles = directory.listFiles();
        DirectoryResponse directoryResponse = new DirectoryResponse(directory);
        assertEquals(directoryResponse.linkFiles("src", listOfFiles),
                "<p><a href=http://localhost:4444/src/Main.class>Main.class</a></p>\n" +
                "<p><a href=http://localhost:4444/src/Main.java>Main.java</a></p>\n" +
                "<p><a href=http://localhost:4444/src/server>server</a></p>\n" +
                "<p><a href=http://localhost:4444/src/specs>specs</a></p>\n");
    }

}