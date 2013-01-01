package net.ceronio.dstvguide;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import net.ceronio.dstvguide.guideapi.Bouquet;
import net.ceronio.dstvguide.guideapi.Channel;
import net.ceronio.dstvguide.guideapi.ReSTAPIWrapper;
import net.ceronio.dstvguide.guideapi.Schedule;

import java.util.ArrayList;

/**
 * User: macky
 * Date: 2012/12/23
 * Time: 11:43 AM
 */
public class BouquetsActivity extends ListActivity {

    ReSTAPIWrapper wrapper = ReSTAPIWrapper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bouquet_selection);
        Bouquet[] bouquetList = wrapper.getBouquets();

        ArrayList<String> bouquets = new ArrayList<String>();
        for (Bouquet bouquet : bouquetList) {
            bouquets.add(bouquet.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, bouquets.toArray(new String[]{}));
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

    }
}
