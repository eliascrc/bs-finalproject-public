package cr.brainstation.bsfinalproject.utils.formatters;

import cr.brainstation.bsfinalproject.constants.CommonConstants;

import java.time.format.DateTimeFormatter;

/**
 * Returns a date time formatter with the standard date time format of the application.
 */
public class DateFormatterLoader {

    private static final DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern(CommonConstants.DATE_TIME_FORMAT);

    public static DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }
}
