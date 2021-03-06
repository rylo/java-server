package server.responses;

public class FailureResponse extends ResponseObject {

    @Override
    public String getHeaders() {
        return "HTTP/1.1" + " 404 Not Found" + "\r\n" + "Content-Type: " + "text/html" + "; charset=UTF-8\r\n\r\n";
    }

    @Override
    public String getBody(String route, String parsedRoute) {
        return "<h1>404: File or Directory Not Found error</h1>";
    }

}