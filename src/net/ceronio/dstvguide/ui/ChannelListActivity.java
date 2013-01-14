package net.ceronio.dstvguide.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import net.ceronio.dstvguide.data.Channel;
import net.ceronio.dstvguide.data.ChannelEvent;
import net.ceronio.dstvguide.guideapi.ChannelEvents;

import java.util.ArrayList;
import java.util.List;

/**
 * User: macky
 * Date: 2013/01/06
 * Time: 6:11 PM
 */
public class ChannelListActivity extends GenericListActivity {

    protected List<Channel> channels;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        channels = state.getSelectedChannels();

        ArrayList<String> channelList = new ArrayList<String>();
        for (Channel channel : channels) {
            channelList.add(String.format("%s %s", channel.getNumber(), channel.getShortDescription()));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, channelList.toArray(new String[]{}));
        setListAdapter(adapter);
        setTitle(state.getChannelListTitle());
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // Get list of channels for the bouquet and save to app state
        Channel selectedChannel = channels.get(position);
        state.setSelectedChannel(selectedChannel);
        pd = ProgressDialog.show(this, "Loading", "Fetching data...");
        new FetchChannelEventsTask().execute(selectedChannel);
    }

    private class FetchChannelEventsTask extends AsyncTask<Channel, Void, List<ChannelEvent>> {

        private Exception exception;

        @Override
        protected List<ChannelEvent> doInBackground(Channel... channels) {
            // Get list of events for given channel, on specific date (handled by data manager)
            try {
                return dataManager.getChannelEvents(channels[0], state.getSelectedDate());
            } catch (Exception e) {
                exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<ChannelEvent> channelEvents) {
            pd.dismiss();
            if (exception != null) {
                Toast.makeText(context, exception.getMessage(), 2000).show();
                return;
            }

            // Save selected channel events
            state.setSelectedChannelEvents(channelEvents);

            // Navigate to channel list
            Intent intent = new Intent(getApplicationContext(), ChannelEventListActivity.class);
            startActivity(intent);
        }
    }
}
