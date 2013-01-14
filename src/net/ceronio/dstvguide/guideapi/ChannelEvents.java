package net.ceronio.dstvguide.guideapi;

import java.util.ArrayList;

public class ChannelEvents {
    String ChannelName;
    String Date;
    long DateTime;
    int Id;
    String Img;
    long Midnight;
    long Now;
    String Num;
    //Schedule[] Schedules;
    String[][] Schedules;

    public String[][] getSchedulesRaw() {
        return Schedules;
    }

    public Schedule[] getSchedules() {
        ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();
        for (String[] schedInfo : Schedules) {
            scheduleList.add(new Schedule(schedInfo));
        }
        return scheduleList.toArray(new Schedule[]{});
    }

    public String getChannelName() {
        return ChannelName;
    }

    public void setChannelName(String channelName) {
        ChannelName = channelName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public long getDateTime() {
        return DateTime;
    }

    public void setDateTime(long dateTime) {
        DateTime = dateTime;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public long getMidnight() {
        return Midnight;
    }

    public void setMidnight(long midnight) {
        Midnight = midnight;
    }

    public long getNow() {
        return Now;
    }

    public void setNow(long now) {
        Now = now;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }
}
