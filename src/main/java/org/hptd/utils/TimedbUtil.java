package org.hptd.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * the db name is a string with datetime
 *
 * @author ford
 */
public class TimedbUtil {
    public static final DateTimeFormatter FORMAT = DateTimeFormat.forPattern("YYYY-MM-DD HH:mm:ss");
    public static final DateTimeFormatter MONTH_FORMAT = DateTimeFormat.forPattern("YYYY-MM");
    public static final DateTimeFormatter YEAR_FORMAT = DateTimeFormat.forPattern("YYYY");

    public static String getRawdbName(long datetime) {
        return MONTH_FORMAT.print(datetime);
    }

    public static String getRawdbName(DateTime dateTime) {
        return getRawdbName(dateTime.getMillis());
    }

    public static String getRawdbName(Date date) {
        return getRawdbName(date.getTime());
    }

    public static String getHourStatdbName(long datetime) {
        return YEAR_FORMAT.print(datetime);
    }

    public static String getHourStatdbName(DateTime dateTime) {
        return getHourStatdbName(dateTime.getMillis());
    }

    public static String getHourStatdbName(Date date) {
        return getHourStatdbName(date.getTime());
    }
}
