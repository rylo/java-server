package server.responses;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Time {

    public String get() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        return "The time is " + timeFormatter.format(currentTime) + " (hour:minute:second)";
    }
}