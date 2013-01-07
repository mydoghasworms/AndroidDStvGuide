package net.ceronio.dstvguide;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import java.util.Date;

/**
 * User: macky
 * Date: 2012/12/23
 * Time: 11:01 AM
 */
public class MainActivity extends TabActivity {

    private ApplicationState state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrypoint);
        state = ApplicationState.getInstance();
        state.setSelectedDate(new Date()); // TODO: This must be selectable somewhere
        setup();
    }

    private void setup() {
        Intent intentBouquet = new Intent().setClass(this, Activity.class);
        //TabHost tabhost = (TabHost) findViewById(R.id.selectionTabHost);
        TabHost tabhost = getTabHost();

        // Bouquet Tab
        TabHost.TabSpec bouquetSpec = tabhost.newTabSpec("Bouquets");
        bouquetSpec.setIndicator("Bouquets");
        Intent bouquetsIntent = new Intent(this, BouquetListActivity.class);
        bouquetSpec.setContent(bouquetsIntent);

        // Search Tab
        TabHost.TabSpec searchSpec = tabhost.newTabSpec("Search");
        searchSpec.setIndicator("Search");
        Intent searchIntent = new Intent(this, SearchActivity.class);
        searchSpec.setContent(searchIntent);

        // FavoritesListActivity Tab
        TabHost.TabSpec favoritesSpec = tabhost.newTabSpec("Favorites");
        favoritesSpec.setIndicator("Favorites");
        Intent favoritesIntent = new Intent(this, FavoritesListActivity.class);
        favoritesSpec.setContent(favoritesIntent);

        tabhost.addTab(bouquetSpec);
        tabhost.addTab(searchSpec);
        tabhost.addTab(favoritesSpec);

    }
}
