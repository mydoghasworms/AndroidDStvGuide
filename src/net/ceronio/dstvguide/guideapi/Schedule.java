package net.ceronio.dstvguide.guideapi;

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
        return (new Date(Long.parseLong(schedInfo[1]) * 1000));
        //return schedInfo[1];
    }

    public long getStartTimeRaw() {
        return Long.parseLong(schedInfo[1]);
    }

    public Date getFinishTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getStartTime());
        cal.add(Calendar.SECOND, Integer.parseInt(schedInfo[2]));
        return cal.getTime();
    }

    public long getFinishTimeRaw() {
        return Long.parseLong(schedInfo[1]) + Integer.parseInt(schedInfo[2]);
    }

    public String getTitle() {
        return schedInfo[3];
    }

    public String getRating() {
        if (schedInfo.length > 4)
            return schedInfo[4];
        else
            return "";
    }

    public String getDescription() {
        if (schedInfo.length > 5)
            return schedInfo[5];
        else
            return "";
    }

    public String getCategory() {
        if (schedInfo.length > 6)
            return schedInfo[6];
        else
            return "";
    }


}
