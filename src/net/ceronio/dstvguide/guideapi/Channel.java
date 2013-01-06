package net.ceronio.dstvguide.guideapi;

import java.util.ArrayList;

/**
 * User: macky
 * Date: 2012/12/21
 * Time: 6:15 PM
 */
public class Channel {
    int ID;
    String Img;
    String Name;
    int Num;
    String[][] Schedules;

    public Schedule[] getSchedules() {
        ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();
        for (String[] schedInfo : Schedules) {
            scheduleList.add(new Schedule(schedInfo));
        }
        return scheduleList.toArray(new Schedule[]{});
    }

    public int getID() {
        return ID;
    }

    public String getImg() {
        return Img;
    }

    public String getName() {
        return Name;
    }

    public int getNum() {
        return Num;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setImg(String img) {
        Img = img;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setNum(int num) {
        Num = num;
    }
}
