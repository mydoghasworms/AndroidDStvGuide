package net.ceronio.dstvguide.apiobjects;

import java.util.Calendar;
import java.util.Date;

/**
 * User: macky
 * Date: 2012/12/21
 * Time: 6:17 PM
 */
public class Schedule {
    private String[] schedInfo;
    public Schedule(String[] schedInfo) {
        this.schedInfo = schedInfo;
    }

    public String getEventID() {
        return schedInfo[0];
    }

    public Date getStartTime() {
        return (new Date(Long.parseLong(schedInfo[1])*1000));
        //return schedInfo[1];
    }

    public Date getFinishTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getStartTime());
        cal.add(Calendar.SECOND, Integer.parseInt(schedInfo[2]));
        return cal.getTime();
    }

    public String getTitle() {
        return schedInfo[3];
    }

}
