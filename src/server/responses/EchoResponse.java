package server.responses;

import server.ResponseObject;

public class EchoResponse extends ResponseObject {

    @Override
    public String getHeaders() {
        return "HTTP/1.1" + " 200 OK" + "\r\n" + "Content-Type: " + "text/plain" + "; charset=UTF-8\r\n\r\n";
    }

    // shouldn't be any parsing here

    @Override
    public String getBody(String route) {
        String params[] =  route.split("\\?");
        int length = params.length;
        if(length == 1) {
            return routeSplitAtAnd(params[0], length);
        } else {
            return routeSplitAtAnd(params[1], length);
        }
    }

    public String routeSplitAtAnd(String route, int length) {
        String params[] = route.split("\\&");
        int n;
        String splitRoutes = "Query string parameters are: \n";
        for(n=0; n < params.length; n++) {
            if(length > 1) {
                splitRoutes += params[n];
                splitRoutes += "\n";
            }
        }
        return splitRoutes;
    }

}
