package net.ceronio.dstvguide;

import android.app.Activity;
import android.os.Bundle;
import net.ceronio.dstvguide.guideapi.Schedule;

/**
 * User: macky
 * Date: 2012/12/22
 * Time: 4:50 PM
 */
public class EventDetailActivity extends Activity {

    private ApplicationState state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail);
        state = ApplicationState.getInstance();

    }

    private void setup() {
        Schedule schedule = state.getSelectedSchedule();
        findViewById(R.id.eventDescription);

    }


}
