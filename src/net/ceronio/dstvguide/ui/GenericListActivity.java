package net.ceronio.dstvguide.ui;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import net.ceronio.dstvguide.ApplicationState;
import net.ceronio.dstvguide.DataManager;
import net.ceronio.dstvguide.guideapi.APIWrapper;

/**
 * User: macky
 * Date: 2013/01/06
 * Time: 6:17 PM
 */
public class GenericListActivity extends ListActivity {

    protected APIWrapper wrapper;
    protected ApplicationState state;
    protected DataManager dataManager;
    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wrapper = APIWrapper.getInstance();
        state = ApplicationState.getInstance();
        dataManager = DataManager.getInstance(this);
        context = this;
    }
}
