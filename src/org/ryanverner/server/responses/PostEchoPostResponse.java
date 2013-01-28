package server.responses;

public class PostEchoPostResponse extends ResponseObject {
    private final String body;

    public PostEchoPostResponse(String body) {
        this.body = body;
    }

    @Override
    public String getHeaders() {
        return "HTTP/1.1" + " 200 OK" + "\r\n" + "Content-Type: " + "text/plain" + "; charset=UTF-8\r\n\r\n";
    }

    @Override
    public String getBody(String route, String parsedRoute) {
        return body;
    }

}