package net.ceronio.dstvguide;

import android.R;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import net.ceronio.dstvguide.guideapi.APIWrapper;
import net.ceronio.dstvguide.guideapi.Channel;
import net.ceronio.dstvguide.guideapi.ChannelEvents;
import net.ceronio.dstvguide.guideapi.Schedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * User: macky
 * Date: 2012/12/21
 * Time: 11:15 PM
 */
public class ChannelEventListActivity extends GenericListActivity  {

    private Schedule[] schedules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        ArrayList<String> events = new ArrayList<String>();
        ChannelEvents channelEvents = state.getSelectedChannelEvents();
        schedules = channelEvents.getSchedules();

        int scrollPosition = 0;
        int position = 0; // Why aren't we just using classic for loop
        for (Schedule schedule : schedules) {
            events.add(simpleDateFormat.format(schedule.getStartTime()) + " " + schedule.getTitle());

            // Keep track of current time so we know where to scroll to
            if (schedule.getStartTimeRaw() <= channelEvents.getNow() &&
                channelEvents.getNow() <= schedule.getFinishTimeRaw())
                scrollPosition = position;
            position += 1;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.simple_list_item_1, events.toArray(new String[]{}));
        setListAdapter(adapter);
        setTitle(state.getEventListTitle());

        // Scroll to currently showing program
        setSelection(scrollPosition);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        // Get selected event and save to app state
        Schedule schedule = schedules[position];
        state.setSelectedSchedule(schedule);

        // Navigate to channel list
        Intent intent = new Intent(getApplicationContext(), EventDetailActivity.class);
        startActivity(intent);


    }
}
