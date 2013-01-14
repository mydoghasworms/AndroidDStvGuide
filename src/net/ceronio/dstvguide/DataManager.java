package net.ceronio.dstvguide;

import android.content.Context;
import net.ceronio.dstvguide.data.Bouquet;
import net.ceronio.dstvguide.data.Channel;
import net.ceronio.dstvguide.data.ChannelEvent;
import net.ceronio.dstvguide.data.DatabaseHandler;
import net.ceronio.dstvguide.guideapi.APIWrapper;
import net.ceronio.dstvguide.guideapi.ChannelEvents;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Handles the interactions with the database and ReST API transparently for the user
 */
public class DataManager {

    DatabaseHandler dbh;
    APIWrapper wrapper;
    private static DataManager dataManager;

    public List<Bouquet> getBouquets() throws Exception {
        List<Bouquet> bouquets;
        // First try read bouquets from DB
        bouquets = dbh.getBouquets();
        // If nothing on DB, read from API, add on DB
        if (bouquets.isEmpty()) {
            String[][] rawBouquets = wrapper.getBouquets();
            for (String[] bouquetDescription : rawBouquets) {
                Bouquet bouquet = new Bouquet();
                if (bouquetDescription.length > 0)
                    bouquet.setCode(Integer.parseInt(bouquetDescription[0]));
                if (bouquetDescription.length > 1)
                    bouquet.setName(bouquetDescription[1]);
                if (bouquetDescription.length > 2)
                    bouquet.setId(bouquetDescription[2]);
                bouquets.add(bouquet);
            }
            dbh.addBouquets(bouquets);
        }
        return bouquets;
    }

    public List<Channel> getBouquetChannels(Bouquet bouquet) throws Exception {
        List<Channel> channels;
        channels = dbh.getBouquetChannels(bouquet);
        if (channels.isEmpty()) {
            String[][] rawBouquetChannels = wrapper.getBouquetChannels(bouquet.getCode());
            for (String[] bouquetChannel : rawBouquetChannels) {
                Channel channel = makeChannelFromRawData(bouquetChannel);
                channels.add(channel);
                dbh.addChannel(channel);
            }
            dbh.addBouquetChannels(bouquet, channels);
        }
        return channels;
    }

    public List<ChannelEvent> getChannelEvents(Channel channel, Date date) throws Exception {
        // TODO: Add persistence for channel events
        List<ChannelEvent> channelEvents = new ArrayList<ChannelEvent>();
        ChannelEvents ce = wrapper.getChannelEvents(channel.getNumber(), date);
        for (String[] event : ce.getSchedulesRaw()) {
            ChannelEvent channelEvent = new ChannelEvent();
            channelEvent.setChannel(channel.getNumber());
            channelEvent.setDate(date);
            if (event.length>0)
                channelEvent.setId(event[0]);
            if (event.length>1)
                channelEvent.setStartTimeRaw(Long.parseLong(event[1]));
            if (event.length>2)
                channelEvent.setDurationRaw(Long.parseLong(event[2]));
            if (event.length>3)
                channelEvent.setDescription(event[3]);
            if (event.length>4)
                channelEvent.setRating(event[4]);
            if (event.length>5)
                channelEvent.setLongDescription(event[5]);
            channelEvents.add(channelEvent);
        }
        return channelEvents;
    }

    /**
     * Create a channel definition from a string of arrays received from the getChannelsByProduct API method
     * @param rawChannelData Array String
     * @return Channel
     */
    private Channel makeChannelFromRawData(String[] rawChannelData) {
        Channel channel = new Channel();
        if (rawChannelData.length>0)
            channel.setNumber(Integer.parseInt(rawChannelData[0]));
        if (rawChannelData.length>1)
            channel.setShortDescription(rawChannelData[1]);
        if (rawChannelData.length>2)
            channel.setId(Integer.parseInt(rawChannelData[2]));
        if (rawChannelData.length>7)
            channel.setLongDescription(rawChannelData[7]);
        return channel;
    }

    /**
     * Singleton getInstance()
     * @return Singleton instance of data manager
     */
    public static DataManager getInstance(Context context) {
        if (dataManager == null) {
            dataManager = new DataManager(context);
        }
        return dataManager;
    }

    private DataManager(Context context) {
        dbh = new DatabaseHandler(context);
        wrapper = APIWrapper.getInstance();
    }

    /**
     * Deletes and recreates channel list through call to data API
     */
    public void refreshChannels() throws DataManagerException {
        dbh.deleteChannels();
    }

    public void refreshBouquets() throws  DataManagerException {

    }
}
