package net.ceronio.dstvguide;

import android.R;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import net.ceronio.dstvguide.guideapi.Channel;
import net.ceronio.dstvguide.guideapi.ReSTAPIWrapper;
import net.ceronio.dstvguide.guideapi.Schedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * User: macky
 * Date: 2012/12/21
 * Time: 11:15 PM
 */
public class ChannelEventsActivity extends ListActivity  {

    private ReSTAPIWrapper wrapper;
    private ApplicationState state;
    private Schedule[] schedules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        wrapper = ReSTAPIWrapper.getInstance();
        state = ApplicationState.getInstance();
        super.onCreate(savedInstanceState);
        ArrayList<String> events = new ArrayList<String>();
        Channel channel = state.getSelectedChannel();
        schedules = channel.getSchedules();
        for (Schedule schedule : schedules) {
            events.add(simpleDateFormat.format(schedule.getStartTime()) + " " + schedule.getTitle());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.simple_list_item_1, events.toArray(new String[]{}));
        setListAdapter(adapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Channel channel = state.getSelectedChannel();


    }
}
