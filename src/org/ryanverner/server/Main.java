package server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ServerBuilder serverBuilder = new ServerBuilder(100);
        serverBuilder.begin();
    }

}