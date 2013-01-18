package specs;

import org.junit.Test;
import server.responses.FileResponse;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static junit.framework.Assert.assertEquals;

public class FileResponseTest {

    @Test
    public void canReadFile() {
        File requestedFile = new File("src/test_files/info.txt");
        String extension = "txt";
        String contentType = "text/plain";
        FileResponse fileResponse = new FileResponse(requestedFile, extension, contentType);
        assertEquals(fileResponse.getBody("src/test_files/info.txt"), "Something here\n" + "to read\n");
    }

    @Test
    public void canConvertAnImageFileToByteArrayToString() throws IOException {
        File requestedFile = new File("src/test_files/micah.jpg");
        String extension = "jpg";
        String contentType = "image/jpg";
        FileResponse fileResponse = new FileResponse(requestedFile, extension, contentType);
        assertEquals(fileResponse.getBody("src/test_files/micah.jpg"), Arrays.toString(fileResponse.getByteArray()));
    }

}


//FileReader reader = new FileReader();
//File file = new File(DIR + "test.jpg");
//byte[] imageContent = FileUtils.readFileToByteArray(file);
//byte[] readData = reader.getBinaryData(DIR + "test.jpg");
//
//for (int i = 0; i < imageContent.length; i++) {
//        assertEquals(imageContent[i], readData[i]);
//}