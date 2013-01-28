package server.responses;

public abstract class ResponseObject {

    public abstract String getBody(String route, String parsedRoute);
    public abstract String getHeaders();

}