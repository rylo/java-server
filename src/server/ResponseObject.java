package server;

public abstract class ResponseObject {

    public abstract String getBody(String route);
    public abstract String getHeaders();

}