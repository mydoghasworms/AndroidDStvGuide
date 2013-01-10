package net.ceronio.dstvguide.guideapi;

import android.content.Context;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Wrapper for the DStv Guide ReST API
 * User: macky
 * Date: 2012/12/21
 * Time: 5:46 PM
 */
public class APIWrapper {

    /**
     * Known URL parameters:
     * apikey (required) = key to access API
     * c = country (two-letter ISO code e.g. ZA)
     * ch = comma-separated list of channels
     * ct = channel type (e.g. video / audio)
     * d = date (ISO date, YYYY-MM-DD e.g. 2012-03-01)
     * s = ???
     * u = ??? User? (Goes with API key)
     * p = Product (bouquet actually); integer ID of the bouquet
     */

    //public static String API_URL = "http://dstvapps.dstv.com/EPGRestService/api/json";
    public static String API_URL = "http://10.129.103.88:8000/EPGRestService/api/json";
    public static String API_KEY = "bda11d91-7ade-4da1-855d-24adfe39d174&u=3fb11b9b-6aea-475c-b149-26dd1224b390";
    public static String SERVICE_GET_BOUQUETS = "getBouquets";
    public static String SERVICE_GET_CHANNELS_BY_PRODUCT = "getChannelsByProduct";
    public static String SERVICE_GET_EVENTS_FOR_CHANNEL = "getEventsForChannel";
    public static String SERVICE_GET_GENRE_LIST_WITH_CHANNEL_NUMBERS = "GetGenreListWithChannelNumbers";
    public static String SERVICE_GET_EVENTS_BY_CHANNEL_LIST = "GetEventsByChannelList";
    public static String SERVICE_GET_SEARCH_RESULTS = "getSearchResults";
    public static String EVENT_URL = "http://www.dstv.com/guide/GuideAsyncHandler.ashx";
    public static String IMAGE_ROOT_URL = "http://cdn.dstv.com/www.dstv.com/DStvChannels/";

    private SimpleDateFormat dateFormat;

    private static APIWrapper wrapperInstance;

    /**
     * Singleton Method
     *
     * @return Instance of API wrapper class
     */
    public static APIWrapper getInstance() {
        if (wrapperInstance == null) wrapperInstance = new APIWrapper();
        return wrapperInstance;
    }

    private APIWrapper() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    public EventInfo getEventInfo(String eventID) throws Exception {
        EventInfo eventInfo = new EventInfo();
        //http://www.dstv.com/guide/GuideAsyncHandler.ashx?eventId=6015657a-3215-4b86-8e73-aa5a6bc50711&method=GetEventInfomation
        String uri = String.format("%s?eventId=%s&method=GetEventInfomation", EVENT_URL, eventID);
        URL url = new URL(uri);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        if (con.getResponseCode() > 201) throw new Exception("Error code: " + con.getResponseCode());
        String json = readStream(con.getInputStream());
        Gson gson = new Gson();
        String[] info = gson.fromJson(json, String[].class);
        eventInfo.code = info[0];
        eventInfo.description = info[1];
        eventInfo.rating = info[3];
        eventInfo.title = info[4];
        eventInfo.time = info[5];
        return eventInfo;
    }

//    /**
//     * Get program for list of channels for current date
//     *
//     * @param channelList
//     * @return Channel and EventDetailActivity information
//     */
//    public EventsByChannelList getEventsByChannelList(int[] channelList) {
//        return getEventsByChannelList(channelList, new Date());
//    }
//
//    public EventsByChannelList getEventsByChannelList(int[] channelList, Date date) {
//
//        String formattedDate = dateFormat.format(date);
//        String list = formatChannelList(channelList);
//
//        String uri2 = String.format("%s/%s?apikey=%s&ch=%s&c=ZA&ct=video&d=%s&s=false",
//                API_URL, SERVICE_GET_EVENTS_BY_CHANNEL_LIST, API_KEY, list, formattedDate);
//        String uri = API_URL + "/" + SERVICE_GET_EVENTS_BY_CHANNEL_LIST + "/?apikey=" + API_KEY
//                + "&ch=" + list
//                + "&c=ZA&ct=video&d=" + formattedDate;
//        EventsByChannelList eventsByChannelList = null;
//        try {
//            URL url = new URL(uri2);
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            if (con.getResponseCode() > 201) throw new Exception("Error code: " + con.getResponseCode());
//            String json = readStream(con.getInputStream());
//            Gson gson = new Gson();
//            eventsByChannelList = gson.fromJson(json, EventsByChannelList.class);
//        } catch (IOException e) {
//            // TODO provide better feedback by throwing exception
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return eventsByChannelList;
//    }

    public ChannelEvents getChannelEvents(int channel, Date date) throws Exception {
        ChannelEvents channelEvents = new ChannelEvents();

        String formattedDate = dateFormat.format(date);
        String uri = String.format("%s/%s?apikey=%s&ch=%s&c=ZA&ct=video&d=%s",
                API_URL, SERVICE_GET_EVENTS_FOR_CHANNEL, API_KEY, String.valueOf(channel), formattedDate);

        URL url = new URL(uri);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        if (con.getResponseCode() > 201)
            throw new Exception(String.format("Web Service Error: %s (%s)", con.getResponseMessage(), con.getResponseCode())); // "Error code: " + con.getResponseCode() + con.getResponseMessage());
        String json = readStream(con.getInputStream());
        Gson gson = new Gson();
        channelEvents = gson.fromJson(json, ChannelEvents.class);

        return channelEvents;
    }

    public Bouquet[] getBouquets() throws Exception {
        Bouquet[] bouquets = new Bouquet[]{};
        String uri = API_URL + "/" + SERVICE_GET_BOUQUETS + "/?apikey=" + API_KEY + "&c=ZA";
        URL url = new URL(uri);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        String json = readStream(con.getInputStream());
        Gson gson = new Gson();
        String[][] bouquetList = gson.fromJson(json, String[][].class);
        ArrayList<Bouquet> bouquetArray = new ArrayList<Bouquet>();
        for (String[] bouquetDescription : bouquetList) {
            Bouquet bouquet = new Bouquet();
            if (bouquetDescription.length > 0)
                bouquet.setID(bouquetDescription[0]);
            if (bouquetDescription.length > 1)
                bouquet.setName(bouquetDescription[1]);
            if (bouquetDescription.length > 2)
                bouquet.setCode(bouquetDescription[2]);
            if (bouquetDescription.length > 3)
                bouquet.setDescription(bouquetDescription[3]);
            bouquetArray.add(bouquet);
        }
        bouquets = bouquetArray.toArray(bouquets);
        return bouquets;
    }

    /**
     * Retrieve list of genres with their respective channel numbers from DStv API
     *
     * @return array of Genre
     */
    public Genre[] getGenreListWithChannelNumbers() {
        Genre[] genres = null;
        try {
            String uri = API_URL + "/" + SERVICE_GET_GENRE_LIST_WITH_CHANNEL_NUMBERS + "/?apikey=" + API_KEY + "&c=ZA&ct=video";
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            String json = readStream(con.getInputStream());
            Gson gson = new Gson();
            genres = gson.fromJson(json, Genre[].class);
        } catch (IOException e) {
            // TODO provide better feedback by throwing exception
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return genres;
    }

    /**
     * Get list of channels for a given bouquet
     *
     * @return
     */
    public Channel[] getChannelsByProduct(int bouquetID) {
        Channel[] channels = new Channel[]{};
        try {
            String uri = API_URL + "/" + SERVICE_GET_CHANNELS_BY_PRODUCT + "?apikey=" + API_KEY + "&c=ZA&ct=video&p="
                    + String.valueOf(bouquetID);
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            String json = readStream(con.getInputStream());
            Gson gson = new Gson();
            String[][] channelList = gson.fromJson(json, String[][].class);
            ArrayList<Channel> channelArray = new ArrayList<Channel>();
            for (String[] channelDescription : channelList) {
                Channel channel = new Channel();
                if (channelDescription.length > 0)
                    channel.setNum(Integer.valueOf(channelDescription[0]));
                if (channelDescription.length > 1)
                    channel.setName(channelDescription[1]);
                if (channelDescription.length > 2)
                    channel.setID(Integer.valueOf(channelDescription[2]));
                if (channelDescription.length > 5)
                    channel.setImg(channelDescription[5]);
                channelArray.add(channel);
            }
            channels = channelArray.toArray(channels);
        } catch (IOException e) {
            // TODO provide better feedback by throwing exception
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return channels;
    }

    /**
     * Obtain String representation of JSON response from server
     *
     * @param in input stream from HTTP server response
     * @return JSON String
     */
    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     * Format list of channels as a comma-separated string
     *
     * @param channels
     * @return
     */
    private String formatChannelList(int[] channels) {
        String list = "";
        for (int i = 0; i < channels.length; i++) {
            if (i > 0) list += ",";
            list += channels[i];
        }
        return list;
    }


}
