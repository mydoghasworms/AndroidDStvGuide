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
     * Information about API obtained from
     * http://dstvapps.dstv.com/epgrestservice/api/json/help?apikey=bda11d91-7ade-4da1-855d-24adfe39d174&u=3fb11b9b-6aea-475c-b149-26dd1224b390
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
    public static String API_URL = "http://10.129.103.216:8000/EPGRestService/api/json";
    public static String API_KEY = "bda11d91-7ade-4da1-855d-24adfe39d174&u=3fb11b9b-6aea-475c-b149-26dd1224b390";
    public static String KEY = "bda11d91-7ade-4da1-855d-24adfe39d174&u=3fb11b9b-6aea-475c-b149-26dd1224b390";
    public static String USER = "3fb11b9b-6aea-475c-b149-26dd1224b390";
    public static String SERVICE_GET_BOUQUETS = "getBouquets";
    public static String SERVICE_GET_CHANNELS_BY_PRODUCT = "getChannelsByProduct";
    public static String SERVICE_GET_EVENTS_FOR_CHANNEL = "getEventsForChannel";
    public static String SERVICE_GET_GENRE_LIST_WITH_CHANNEL_NUMBERS = "GetGenreListWithChannelNumbers";
    //public static String SERVICE_GET_EVENTS_BY_CHANNEL_LIST = "GetEventsByChannelList";
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

    public String[][] getBouquets() throws Exception {
        String uri = API_URL + "/" + SERVICE_GET_BOUQUETS + "/?apikey=" + API_KEY + "&c=ZA";
        URL url = new URL(uri);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        if (con.getResponseCode() > 201) throw new Exception("Error code: " + con.getResponseCode());
        String json = readStream(con.getInputStream());
        Gson gson = new Gson();
        return gson.fromJson(json, String[][].class);
    }

    public String[][] getBouquetChannels(int bouquetCode) throws Exception {
        //String uri = API_URL + "/" + SERVICE_GET_BOUQUETS + "/?apikey=" + API_KEY + "&c=ZA";
        //http://dstvapps.dstv.com/epgrestservice/api/json/getChannelsByProduct?u=3fb11b9b-6aea-475c-b149-26dd1224b390&ct=video&c=ZA&p=1&apikey=bda11d91-7ade-4da1-855d-24adfe39d174
        String uri = API_URL + "/" + SERVICE_GET_CHANNELS_BY_PRODUCT + "?u=" + USER + "&c=ZA&p=" + bouquetCode + "&apikey=" + KEY;
        URL url = new URL(uri);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        if (con.getResponseCode() > 201) throw new Exception("Error code: " + con.getResponseCode());
        String json = readStream(con.getInputStream());
        Gson gson = new Gson();
        return gson.fromJson(json, String[][].class);
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
