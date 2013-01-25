package server.responses;

import java.io.*;
import java.util.Arrays;

public class FileResponse extends ResponseObject {
    private final File requestedFile;
    private final String extension;
    private final String contentType;

    public FileResponse(File requestedFile, String extension, String contentType) {
        this.requestedFile = requestedFile;
        this.extension = extension;
        this.contentType = contentType;
    }

    @Override
    public String getHeaders() {
        return "HTTP/1.1" + " 200 OK" + "\r\n" + "Content-Type: " + contentType + "; charset=UTF-8\r\n\r\n";
    }

    @Override
    public String getBody(String route) {
        String response = "";
        if(extension.matches("^.*?(jpeg|jpg|png).*$")) {
            try {
                response = readImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(extension.equals("pdf")) {
            try {
                response = readPDF();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            response = readFile();
        }
        return response;
    }

    private String readPDF() throws IOException {
        InputStream fileInputStream = new FileInputStream(requestedFile);
        fileInputStream.read();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int data;
        while( (data = fileInputStream.read()) >= 0 ) {
            outputStream.write(data);
        }
        fileInputStream.close();
        return Arrays.toString(outputStream.toByteArray());
    }

    private String readFile() {
        BufferedReader reader;
        String response = "";
        try {
            FileReader fileReader = new FileReader(requestedFile);
            reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
                response += line + "\n";
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public String readImage() throws IOException {
        byte[] byteArray = getByteArray();
        return Arrays.toString(byteArray);
    }

    public byte[] getByteArray() throws IOException {
        byte[] byteArray = new byte[(int) requestedFile.length()];
        InputStream inputStream;
        String fileName = String.valueOf(requestedFile);
        inputStream = new BufferedInputStream(new FileInputStream(fileName));
        int bytesRead = 0;
        while (bytesRead < byteArray.length) {
            int bytesRemaining = byteArray.length - bytesRead;
            int read = inputStream.read(byteArray, bytesRead, bytesRemaining);
            if (read > 0) {
                bytesRead += read;
            }
        }
        inputStream.close();
        return byteArray;
    }

}