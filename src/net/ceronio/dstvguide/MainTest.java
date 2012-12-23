package net.ceronio.dstvguide;

import com.google.gson.Gson;
import net.ceronio.dstvguide.apiobjects.EventsByChannelList;
import net.ceronio.dstvguide.apiobjects.Genre;
import net.ceronio.dstvguide.apiobjects.Schedule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * User: macky
 * Date: 2012/12/21
 * Time: 1:59 PM
 */
public class MainTest {
    public static void main(String args[]) {
        (new MainTest()).startTest();
    }

    void startTest() {
        int[] channels = {101, 103};
        EventsByChannelList events;
        APIWrapper wrapper = APIWrapper.getInstance();
        events = wrapper.getEventsByChannelList((channels));
        Schedule[] schedules =events.getChannels()[0].getSchedules();
        System.out.println(schedules[0].getStartTime().toString());
        System.out.println(schedules[0].getFinishTime().toString());
        System.out.println(schedules[0].getTitle());
    }


}
