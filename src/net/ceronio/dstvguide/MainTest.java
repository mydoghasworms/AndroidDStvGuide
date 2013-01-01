package net.ceronio.dstvguide;

import net.ceronio.dstvguide.guideapi.Bouquet;
import net.ceronio.dstvguide.guideapi.EventsByChannelList;
import net.ceronio.dstvguide.guideapi.ReSTAPIWrapper;
import net.ceronio.dstvguide.guideapi.Schedule;

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
        ReSTAPIWrapper wrapper = ReSTAPIWrapper.getInstance();
        EventsByChannelList events;
        events = wrapper.getEventsByChannelList((channels));
        Bouquet[] bouquets = wrapper.getBouquets();
        for (Bouquet bouquet : bouquets) {
            System.out.println(String.format("%s %s %s %s", bouquet.getID(), bouquet.getName(), bouquet.getCode(), bouquet.getDescription()));
        }
        Schedule[] schedules =events.getChannels()[0].getSchedules();
        System.out.println(schedules[0].getStartTime().toString());
        System.out.println(schedules[0].getFinishTime().toString());
        System.out.println(schedules[0].getTitle());
    }


}
