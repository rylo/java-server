package server;

import server.responses.EchoResponse;
import server.responses.GetEchoPostResponse;
import server.responses.ResponseObject;
import server.responses.TimeResponse;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws IOException {
        ServerBuilder serverBuilder = new ServerBuilder(100);
        HashMap<String, ResponseObject> routes = new HashMap<String, ResponseObject>(){
            {
                put("time", new TimeResponse());
                put("echo", new EchoResponse());
                put("echopost", new GetEchoPostResponse());
            }
        };
        serverBuilder.createRoutes(routes);
        serverBuilder.setServerSocket(new ServerSocket(4444));
        serverBuilder.begin();
    }

}