package specs;

import org.junit.Test;
import server.responses.Directory;

import java.io.File;

import static junit.framework.Assert.assertEquals;

public class DirectoryTest {

    @Test
    public void canGetDirectoryContents() {
        String requestedPath = System.getProperty("user.dir") + "/src";
        File[] allFiles = new File(requestedPath).listFiles();
        File[] directoryContents = new Directory().getDirectoryContents(new File(requestedPath));
        assertEquals(directoryContents[1], allFiles[1]);
    }
}