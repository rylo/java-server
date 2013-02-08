package server.responses;

public class RedirectResponse extends ResponseObject {

    @Override
    public String getHeaders() {
        return "HTTP/1.1" + " 301 Moved Permanently" + "\r\n" + "Location: http://localhost:5000/";
    }

    @Override
    public String getBody(String route, String parsedRoute) {
        return "";
    }

}