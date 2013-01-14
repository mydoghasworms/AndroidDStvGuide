package net.ceronio.dstvguide;

import net.ceronio.dstvguide.data.Channel;
import net.ceronio.dstvguide.data.ChannelEvent;

import java.util.Date;
import java.util.List;

/**
 * User: macky
 * Date: 2012/12/22
 * Time: 4:45 PM
 */
public class ApplicationState {

    private static ApplicationState state;

    public static ApplicationState getInstance() {
        if (state==null) {
            state = new ApplicationState();
        }
        return state;
    }

    private ApplicationState(){

    }

//    private Channel selectedChannel;
//    private Schedule selectedSchedule;

    private String channelListTitle;
    private String eventListTitle;
    private Date selectedDate;
    private Channel selectedChannel;
    private List<Channel> selectedChannels;
    private List<ChannelEvent> selectedChannelEvents;

    public List<ChannelEvent> getSelectedChannelEvents() {
        return selectedChannelEvents;
    }

    public void setSelectedChannelEvents(List<ChannelEvent> selectedChannelEvents) {
        this.selectedChannelEvents = selectedChannelEvents;
    }

    public Channel getSelectedChannel() {
        return selectedChannel;
    }

    public void setSelectedChannel(Channel selectedChannel) {
        this.selectedChannel = selectedChannel;
    }

    public List<Channel> getSelectedChannels() {
        return selectedChannels;
    }

    public void setSelectedChannels(List<Channel> selectedChannels) {
        this.selectedChannels = selectedChannels;
    }

    public String getChannelListTitle() {
        return channelListTitle;
    }

    public void setChannelListTitle(String channelListTitle) {
        this.channelListTitle = channelListTitle;
    }

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    public String getEventListTitle() {
        return eventListTitle;
    }

    public void setEventListTitle(String eventListTitle) {
        this.eventListTitle = eventListTitle;
    }
}
