package net.ceronio.dstvguide.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import net.ceronio.dstvguide.R;
import net.ceronio.dstvguide.data.Bouquet;
import net.ceronio.dstvguide.data.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * Shows a list of DStv bouquets; user can click on a bouquet to navigate to its channels
 */
public class BouquetListActivity extends GenericListActivity {

    private List<Bouquet> bouquetList;
    private Context context;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.bouquet_selection);

        pd = ProgressDialog.show(this, "Loading", "Fetching data...");
        new FetchBouquetTask().execute();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        // Get list of channels for the bouquet and save to app state
        Bouquet selectedBouquet = bouquetList.get(position);
//        List<Channel> selectedChannels = dataManager.getBouquetChannels(selectedBouquet);
//        Channel[] channels = wrapper.getChannelsByProduct(Integer.valueOf(selectedBouquet.getCode()));
//        state.setSelectedChannels(channels);
        state.setChannelListTitle(selectedBouquet.getName());
        pd = ProgressDialog.show(this, "Loading", "Fetching data...");
        new FetchBouqetChannelsTask().execute(selectedBouquet);

    }

    private class FetchBouqetChannelsTask extends AsyncTask<Bouquet, Void, List<Channel>> {
        private Exception exception;

        @Override
        protected List<Channel> doInBackground(Bouquet... bouquets) {
            try {
                return dataManager.getBouquetChannels(bouquets[0]);
            } catch (Exception e) {
                exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Channel> channels) {
            pd.dismiss();

            if (exception!= null) {
                Toast.makeText(context, exception.getMessage(), 3000).show();
                return;
            }

            state.setSelectedChannels(channels);
            // Navigate to channel list
            Intent intent = new Intent(getApplicationContext(), ChannelListActivity.class);
            startActivity(intent);
        }
    }

    private class FetchBouquetTask extends AsyncTask<Void, Void, Void> {
        private Exception exception;

        @Override
        protected void onPostExecute(Void aVoid) {
            if (exception!= null)
                Toast.makeText(context, exception.getMessage(), 3000).show();

            if (bouquetList == null) return;

            ArrayList<String> bouquets = new ArrayList<String>();
            for (Bouquet bouquet : bouquetList) {
                bouquets.add(bouquet.getName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_list_item_1, bouquets.toArray(new String[]{}));
            setListAdapter(adapter);
            pd.dismiss();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                bouquetList = dataManager.getBouquets();
            } catch (Exception e) {
                exception = e;
            }
            return null;
        }
    }
}
