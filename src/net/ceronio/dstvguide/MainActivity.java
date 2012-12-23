package net.ceronio.dstvguide;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * User: macky
 * Date: 2012/12/23
 * Time: 11:01 AM
 */
public class MainActivity extends TabActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrypoint);
    }

    private void setup() {
        Intent intentBouquet = new Intent().setClass(this, Activity.class);
        TabHost tabhost = (TabHost) findViewById(R.id.selectionTabHost);


    }
}
