package net.ceronio.dstvguide.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import net.ceronio.dstvguide.ApplicationState;
import net.ceronio.dstvguide.R;
import net.ceronio.dstvguide.guideapi.Channel;
import net.ceronio.dstvguide.guideapi.Schedule;

import java.text.SimpleDateFormat;

/**
 * User: macky
 * Date: 2012/12/22
 * Time: 4:50 PM
 */
public class EventDetailActivity extends Activity {

    private ApplicationState state;

    protected SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail);
        state = ApplicationState.getInstance();
        setup();
    }

    private void setup() {
//        Schedule schedule = state.getSelectedSchedule();
//        Channel channel = state.getSelectedChannel();
//        TextView channelDetail = (TextView) findViewById(R.id.channelDetail);
//        channelDetail.setText(channel.getName());
//        TextView eventDescription = (TextView) findViewById(R.id.eventDescription);
//        eventDescription.setText(schedule.getDescription());
//        TextView timeDetail = (TextView) findViewById(R.id.timeDetail);
//        timeDetail.setText(String.format("%s - %s", simpleTimeFormat.format(schedule.getStartTime()),
//                simpleTimeFormat.format(schedule.getFinishTime())));
    }


}
