package server;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

    public void parseContent() throws UnsupportedEncodingException {
        String requestContentLines[];
        try {
            requestContentLines = splitRequestContent();
            setMethod(requestContentLines[0].split(" ")[0]);
            setRoute(requestContentLines[0].split(" ")[1].substring(1));
            setProtocol(requestContentLines[0].split(" ")[2]);
            setParsedRoute(requestContentLines[0].split(" ")[1].substring(1));
            setBody(requestContent.get(1));
        } catch(ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private String[] splitRequestContent() {
        return requestContent.get(0).split("\\r?\\n");
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

    public String splitRouteAtQuestionMark(String route) throws UnsupportedEncodingException {
        String splitRoutes[] =  route.split("\\?");
        int length = splitRoutes.length;
        if(length == 1) {
            return URLDecoder.decode(splitRouteAtAmpersand(splitRoutes[0], length), "UTF-8");
        } else {
            return URLDecoder.decode(splitRouteAtAmpersand(splitRoutes[1], length), "UTF-8");
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
        return splitRoutes.replace("=", " = ");
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

    private void setParsedRoute(String route) throws UnsupportedEncodingException {
        this.parsedRoute = splitRouteAtQuestionMark(route);
    }

    private void setBody(String body) {
        String splitBody[] =  body.split("\\&");
        this.body = splitRouteAtAmpersand(body, splitBody.length);
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