package de.ibs.app.overview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by johnyso on 19.01.15.
 */
public class AddSpeakerReceiver extends BroadcastReceiver {

    private final StartActivity activity;

    public AddSpeakerReceiver(StartActivity activity){
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.activity.changeToAddSpeaker();
    }
}
