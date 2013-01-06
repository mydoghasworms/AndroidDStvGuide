package net.ceronio.dstvguide;

import android.app.ListActivity;
import android.os.Bundle;
import net.ceronio.dstvguide.guideapi.ReSTAPIWrapper;

/**
 * User: macky
 * Date: 2013/01/06
 * Time: 6:17 PM
 */
public class GenericListActivity extends ListActivity {

    protected ReSTAPIWrapper wrapper;
    protected ApplicationState state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wrapper = ReSTAPIWrapper.getInstance();
        state = ApplicationState.getInstance();
    }
}
