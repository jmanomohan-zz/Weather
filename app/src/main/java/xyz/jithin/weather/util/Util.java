package xyz.jithin.weather.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by JIM on 18/11/17.
 */

public class Util {

    public static String getWeek(String d) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = dateFormat.parse(d);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
            return dayFormat.format(calendar.getTime());
        } catch (ParseException e) {
            return d;
        }
    }

}
