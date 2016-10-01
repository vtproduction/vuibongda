package ats.abongda.helper;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by NienLe on 12-Jul-16.
 */
public class DateTimeUlti {
    public static DateTime stringToDatetime(String dateTimeString){
        DateTime dateTime;
        DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            dateTime = df.parseDateTime(dateTimeString.replace('T',' ').substring(0, dateTimeString.length() - 1));
        }catch (Exception e){
            LogUtil.e(e.getMessage());
            dateTime = DateTime.now();
        }
        return dateTime;
    }
}
