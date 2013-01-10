package net.ceronio.dstvguide.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import net.ceronio.dstvguide.R;
import net.ceronio.dstvguide.guideapi.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: macky
 * Date: 2012/12/23
 * Time: 11:43 AM
 */
public class BouquetListActivity extends GenericListActivity {

    private HashMap<String, Genre> genreMap;
    private Bouquet[] bouquetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bouquet_selection);

        try {

        bouquetList = wrapper.getBouquets();

        ArrayList<String> bouquets = new ArrayList<String>();
        for (Bouquet bouquet : bouquetList) {
            bouquets.add(bouquet.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, bouquets.toArray(new String[]{}));
        setListAdapter(adapter);

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), 3000).show();
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayList<String> channelList = new ArrayList<String>();

        // Get list of channels for the bouquet and save to app state
        Bouquet selectedBouquet = bouquetList[position];
        Channel[] channels = wrapper.getChannelsByProduct(Integer.valueOf(selectedBouquet.getID()));
        state.setSelectedChannels(channels);
        state.setChannelListTitle(selectedBouquet.getName());

        // Navigate to channel list
        Intent intent = new Intent(getApplicationContext(), ChannelListActivity.class);
        startActivity(intent);

    }
}
