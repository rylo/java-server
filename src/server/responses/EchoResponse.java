package server.responses;

import server.ResponseObject;

public class EchoResponse extends ResponseObject {
    private final String parsedRoute;

    public EchoResponse(String parsedRoute) {
        super();
        this.parsedRoute = parsedRoute;
    }

    @Override
    public String getHeaders() {
        return "HTTP/1.1" + " 200 OK" + "\r\n" + "Content-Type: " + "text/plain" + "; charset=UTF-8\r\n\r\n";
    }

    @Override
    public String getBody(String route) {
        return "Query string parameters are: \n" + parsedRoute;
    }

}