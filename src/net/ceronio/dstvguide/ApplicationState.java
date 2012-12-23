package net.ceronio.dstvguide;

import net.ceronio.dstvguide.apiobjects.Channel;
import net.ceronio.dstvguide.apiobjects.EventsByChannelList;
import net.ceronio.dstvguide.apiobjects.Schedule;

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

    private Channel selectedChannel;
    private EventsByChannelList selectedChannelList;
    private Schedule selectedSchedule;
    private Channel[] selectedChannels;

    public Channel[] getSelectedChannels() {
        return selectedChannels;
    }

    public void setSelectedChannels(Channel[] selectedChannels) {
        this.selectedChannels = selectedChannels;
    }

    public Schedule getSelectedSchedule() {
        return selectedSchedule;
    }

    public void setSelectedSchedule(Schedule selectedSchedule) {
        this.selectedSchedule = selectedSchedule;
    }
    // STATE METHODS

    public Channel getSelectedChannel() {
        return selectedChannel;
    }
    public void setSelectedChannel(Channel selectedChannel) {
        this.selectedChannel = selectedChannel;
    }

    public EventsByChannelList getSelectedChannelList() {
        return selectedChannelList;
    }

    public void setSelectedChannelList(EventsByChannelList selectedChannelList) {
        this.selectedChannelList = selectedChannelList;
    }

}
