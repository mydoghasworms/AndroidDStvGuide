package net.ceronio.dstvguide.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import net.ceronio.dstvguide.R;

/**
 * User: macky
 * Date: 2012/12/23
 * Time: 12:00 PM
 */
public class SearchActivity extends Activity {

    ImageButton search_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        setup();

    }

    private void setup() {
        search_button = (ImageButton) findViewById(R.id.search_button);
        search_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText search_term = (EditText) findViewById(R.id.search_term);
                Log.i("GUIDE", String.format("Searched for %s", search_term.getText()));
                Toast.makeText(view.getContext(), String.format("Searched for %s", search_term.getText()), 3000).show();
            }
        });
    }
}
