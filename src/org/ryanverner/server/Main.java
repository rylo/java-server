package server;

import server.responses.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws IOException {
        ServerBuilder serverBuilder            = new ServerBuilder(100);
        HashMap<String, ResponseObject> routes = new HashMap<String, ResponseObject>(){
            {
                put("time",            new TimeResponse());
                put("echo",            new EchoResponse());
                put("some-script-url", new EchoResponse());
                put("echopost",        new GetEchoPostResponse());
                put("form",            new GetEchoPostResponse());
                put("redirect",        new RedirectResponse());
            }
        };
        serverBuilder.setRoutes(routes);
        serverBuilder.setServerSocket(new ServerSocket(5000));
        serverBuilder.begin();
    }

}