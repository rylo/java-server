package server.requests;

import java.util.HashMap;

public class RequestParser {
    private final String headers;
    private String method;
    private String route;
    private String protocol;

    public RequestParser(String headers) {
        this.headers = headers;
    }

    public HashMap<String, String> parseHeaders() {
        String lines[] = new String[0];
        try {
            lines = headers.split("\\r?\\n");
            setMethod(lines[0].split(" ")[0]);
            setRoute(lines[0].split(" ")[1]);
            setProtocol(lines[0].split(" ")[2]);
        } catch(ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        HashMap<String, String> httpRequestParameters = new HashMap<String, String>();
        String[] keys = new String[]{"method", "route", "protocol"};

        int num = 0;
        for(String key : keys) {
            while(!httpRequestParameters.containsKey(key) && num < 3) {
                httpRequestParameters.put(key, lines[0].split(" ")[num]);
                num++;
            }
        }
        return httpRequestParameters;
    }

    private void setMethod(String method) {
        this.method = method;
    }

    private void setRoute(String route) {
        this.route = route;
    }

    private void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getMethod() {
        return method;
    }

    public String getRoute() {
        return route;
    }

    public String getProtocol() {
        return protocol;
    }

}