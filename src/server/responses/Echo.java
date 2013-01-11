package server.responses;

public class Echo {

    public String get(String route) {
        String params[] = route.split("\\?");
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
