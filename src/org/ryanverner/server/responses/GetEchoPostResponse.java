package server.responses;

public class GetEchoPostResponse extends ResponseObject {

    @Override
    public String getHeaders() {
        return "HTTP/1.1" + " 200 OK" + "\r\n" + "Content-Type: " + "text/html" + "; charset=UTF-8\r\n\r\n";
    }

    @Override
    public String getBody(String route, String parsedRoute) {
        return "<form method=\"post\">\n" +
               "A: <input type=\"text\" name=\"A\"><br>\n" +
               "B: <input type=\"text\" name=\"B\"><br>\n" +
               "C: <input type=\"text\" name=\"C\"><br>\n" +
               "<input type=\"submit\" value=\"Submit\">\n" +
               "</form>";
    }

}