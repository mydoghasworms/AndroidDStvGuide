package net.ceronio.dstvguide;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import net.ceronio.dstvguide.apiobjects.Channel;
import net.ceronio.dstvguide.apiobjects.EventsByChannelList;
import net.ceronio.dstvguide.apiobjects.Genre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DStvGuideActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private APIWrapper wrapper;
    private ApplicationState state;

    protected ProgressDialog pd;

    private HashMap<String, Genre> genreMap;
    protected Genre[] genres;
    protected ArrayAdapter<String> dataAdapter;
    private Spinner spinner;
    private List<String> list;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        state = ApplicationState.getInstance();
        wrapper = APIWrapper.getInstance();
        genreMap = new HashMap<String, Genre>();

        pd = ProgressDialog.show(this, "Loading data...", "Loading", true);
        spinner = (Spinner) findViewById(R.id.genreSpinner);
        list = new ArrayList<String>();
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        // Retrieve list of genres from DStv Guide
        genres = wrapper.getGenreListWithChannelNumbers();

        for (Genre genre : genres) {
            genreMap.put(genre.getGenre(), genre);
        }

        // Populate spinner on main view with list of genres
        for (int i = 0; i < genres.length; i++) {
            dataAdapter.add(genres[i].getGenre());
        }

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


        spinner.setOnItemSelectedListener(this);

        pd.dismiss();

        ListView channelList = (ListView) findViewById(R.id.listView);
        channelList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventsByChannelList channelList = state.getSelectedChannelList();
                Channel selectedChannel = channelList.getChannels()[position];
                state.setSelectedChannel(selectedChannel);
                Intent intent = new Intent(getApplicationContext(), ChannelEventsActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayList<String> channelList = new ArrayList<String>();
        Genre genre = genreMap.get(parent.getItemAtPosition(position).toString());
        EventsByChannelList events = wrapper.getEventsByChannelList(genre.getChannelNumbers());
        state.setSelectedChannelList(events);
        Channel[] channels = events.getChannels();
        for (Channel channel : channels) {
            channelList.add(String.format("%s %s", channel.getNum(), channel.getName()));
        }
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, R.layout.channel_row, channelList);
        listView.setAdapter(listAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
