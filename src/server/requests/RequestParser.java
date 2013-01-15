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
        String lines[];
        try {
            lines = headers.split("\\r?\\n");
            setMethod(lines[0].split(" ")[0]);
            setRoute(lines[0].split(" ")[1].substring(1));
            setProtocol(lines[0].split(" ")[2]);
        } catch(ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        HashMap<String, String> httpRequestParameters = new HashMap<String, String>();
        httpRequestParameters.put("method", getMethod());
        httpRequestParameters.put("route", getRoute());
        httpRequestParameters.put("protocol", getProtocol());
        httpRequestParameters.put("parsedRoute", splitRouteAtQuestionMark(getRoute()));
        return httpRequestParameters;
    }

    public String splitRouteAtQuestionMark(String route) {
        String splitRoute[] =  route.split("\\?"); // String.split("[\r\n]+")
        int length = splitRoute.length;
        if(length == 1) {
            return splitRouteAtAmpersand(splitRoute[0], length);
        } else {
            return splitRouteAtAmpersand(splitRoute[1], length);
        }
    }

    public String splitRouteAtAmpersand(String route, int length) {
        String params[] = route.split("\\&");
        int n;
        String splitRoutes = "";
        for(n=0; n < params.length; n++) {
            if(length > 1) {
                splitRoutes += params[n];
                splitRoutes += "\n";
            }
        }
        return splitRoutes;
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