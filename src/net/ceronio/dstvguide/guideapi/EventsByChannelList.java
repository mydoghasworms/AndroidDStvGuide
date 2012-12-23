package net.ceronio.dstvguide.guideapi;

/**
 * User: macky
 * Date: 2012/12/21
 * Time: 7:01 PM
 */
public class EventsByChannelList {
    Channel[] Channels;
    String Date;
    Long DateTime;
    Long Midnight;
    Long Now;

    public Channel[] getChannels() {
        return Channels;
    }

    public String getDate() {
        return Date;
    }

    public Long getDateTime() {
        return DateTime;
    }

    public Long getMidnight() {
        return Midnight;
    }

    public Long getNow() {
        return Now;
    }
}
