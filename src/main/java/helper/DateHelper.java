package helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 *
 * @author catty
 */
public class DateHelper {
// date/time processing

    static SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");

    public static Date toDate(String date, String... pattern) {
        try {
            if (pattern.length > 0) {

                formater.applyPattern(pattern[0]);

            }
            if (date == null) {
                return DateHelper.now();
            }
            Date converted = formater.parse(date);
            return addDays(converted, 0);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String toString(Date date, String... pattern) {
        if (pattern.length > 0) {
            formater.applyPattern(pattern[0]);
        }
        if (date == null) {
            date = DateHelper.now();
        }
        return formater.format(date);
    }

    public static Date addDays(Date date, long days) {
        date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
        return date;
    }

    public static Date add(int days) {
        Date now = DateHelper.now();
        now.setTime(now.getTime() + days * 24 * 60 * 60 * 1000);
        return now;
    }

    public static Date now() {
        return new Date();
    }

    public static boolean isValidFormat(String date) {
        try {
            Date chk = formater.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static String formartTime(LocalDateTime currentTime) {
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a"));
        return formattedTime;
    }

    public static String formatTime(String currentTime) {

  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  try {
    LocalDateTime time = LocalDateTime.parse(currentTime, formatter);

    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");

    return time.format(outputFormatter);

  } catch (DateTimeParseException e) {
    return "Invalid time format"; 
  }

}
}
