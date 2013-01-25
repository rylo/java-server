package specs;

import org.junit.Test;
import server.responses.DirectoryResponse;

import java.io.File;

import static junit.framework.Assert.assertEquals;

public class DirectoryResponseTest {

    @Test
    public void canGetDirectoryContents() {
        String route = "src/test_files";
        String requestedPath = System.getProperty("user.dir") + "/" + route;
        File directory = new File(requestedPath);
        File[] listOfFiles = directory.listFiles();
        DirectoryResponse directoryResponse = new DirectoryResponse(directory);
        assertEquals(directoryResponse.linkFiles(route, listOfFiles),
                "<p><a href=http://localhost:4444/src/test_files/baseball_rules.pdf>baseball_rules.pdf</a></p>\n" +
                "<p><a href=http://localhost:4444/src/test_files/dave.jpg>dave.jpg</a></p>\n" +
                "<p><a href=http://localhost:4444/src/test_files/info.txt>info.txt</a></p>\n" +
                "<p><a href=http://localhost:4444/src/test_files/micah.jpg>micah.jpg</a></p>\n" +
                "<p><a href=http://localhost:4444/src/test_files/monster.jpeg>monster.jpeg</a></p>\n" +
                "<p><a href=http://localhost:4444/src/test_files/paul.png>paul.png</a></p>\n" +
                "<p><a href=http://localhost:4444/src/test_files/william.jpg>william.jpg</a></p>\n");
    }

}