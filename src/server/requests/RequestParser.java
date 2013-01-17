package server.requests;

import java.util.HashMap;
import java.util.List;

public class RequestParser {
    private final List<String> requestContent;
    private String method;
    private String route;
    private String protocol;
    private String parsedRoute;
    private String body;

    public RequestParser(List<String> requestContent) {
        this.requestContent = requestContent;
    }

    public void parseContent() {
        String headerLines[];
        try {
            headerLines = requestContent.get(0).split("\\r?\\n");
            setMethod(headerLines[0].split(" ")[0]);
            setRoute(headerLines[0].split(" ")[1].substring(1));
            setProtocol(headerLines[0].split(" ")[2]);
            setParsedRoute(headerLines[0].split(" ")[1].substring(1));
            setBody(requestContent.get(1));
        } catch(ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, String> storeParsedContent() {
        HashMap<String, String> httpRequestContent = new HashMap<String, String>();
        httpRequestContent.put("method", getMethod());
        httpRequestContent.put("route", getRoute());
        httpRequestContent.put("protocol", getProtocol());
        httpRequestContent.put("parsedRoute", getParsedRoute());
        httpRequestContent.put("body", getBody());
        return httpRequestContent;
    }

    public String splitRouteAtQuestionMark(String route) {
        String splitRoutes[] =  route.split("\\?");
        int length = splitRoutes.length;
        if(length == 1) {
            return splitRouteAtAmpersand(splitRoutes[0], length);
        } else {
            return splitRouteAtAmpersand(splitRoutes[1], length);
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
        String splitRoutes[] =  route.split("\\?");
        this.route = splitRoutes[0];
    }

    private void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    private void setParsedRoute(String route) {
        this.parsedRoute = splitRouteAtQuestionMark(route);
    }

    private void setBody(String body) {
        this.body = body;
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

    public String getParsedRoute() {
        return parsedRoute;
    }

    public String getBody() {
        return body;
    }

}