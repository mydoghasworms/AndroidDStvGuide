package net.ceronio.dstvguide;

import android.app.ListActivity;
import android.os.Bundle;
import net.ceronio.dstvguide.guideapi.APIWrapper;

/**
 * User: macky
 * Date: 2013/01/06
 * Time: 6:17 PM
 */
public class GenericListActivity extends ListActivity {

    protected APIWrapper wrapper;
    protected ApplicationState state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wrapper = APIWrapper.getInstance();
        state = ApplicationState.getInstance();
    }
}
