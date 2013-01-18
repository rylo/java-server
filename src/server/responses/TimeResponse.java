package server.responses;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeResponse extends ResponseObject {

    @Override
    public String getHeaders() {
        return "HTTP/1.1" + " 200 OK" + "\r\n" + "Content-Type: " + "text/html" + "; charset=UTF-8\r\n\r\n";
    }

    @Override
    public String getBody(String route) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        return "The time is " + timeFormatter.format(currentTime) + " (hour:minute:second)";
    }

}