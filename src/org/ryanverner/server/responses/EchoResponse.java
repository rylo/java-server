package server.responses;

public class EchoResponse extends ResponseObject {

    @Override
    public String getHeaders() {
        return "HTTP/1.1" + " 200 OK" + "\r\n" + "Content-Type: " + "text/plain" + "; charset=UTF-8\r\n\r\n";
    }

    @Override
    public String getBody(String route, String parsedRoute) {
        return "Query string parameters are: \n" + parsedRoute;
    }

}
