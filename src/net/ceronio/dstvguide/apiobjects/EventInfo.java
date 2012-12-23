package net.ceronio.dstvguide.apiobjects;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * User: macky
 * Date: 2012/12/21
 * Time: 9:37 PM
 */
public class EventInfo {
    public String code;
    public String description;
    public String rating;
    public String title;

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    public String time;
}
