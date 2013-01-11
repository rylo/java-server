package server.responses;

public class Echo {

    public String get(String route) {
        System.out.println("route: " + route);
        String params[] = route.split("\\?");
        int n;
        String splitRoutes = "Query string parameters are: \n";
        for(n=1; n < params.length; n++) {
            splitRoutes += params[n];
            splitRoutes += "\n";
        }
        return splitRoutes;
    }

}
