package net.ceronio.dstvguide;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import net.ceronio.dstvguide.guideapi.Bouquet;
import net.ceronio.dstvguide.guideapi.Channel;
import net.ceronio.dstvguide.guideapi.ReSTAPIWrapper;

import java.util.ArrayList;

/**
 * User: macky
 * Date: 2013/01/06
 * Time: 6:11 PM
 */
public class ChannelListActivity extends GenericListActivity {

    protected Channel[] channels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        channels = state.getSelectedChannels();

        ArrayList<String> channelList = new ArrayList<String>();
        for (Channel channel : channels) {
            channelList.add(String.format("%s %s", channel.getNum(), channel.getName()));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, channelList.toArray(new String[]{}));
        setListAdapter(adapter);
        setTitle(state.getChannelListTitle());
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }
}
