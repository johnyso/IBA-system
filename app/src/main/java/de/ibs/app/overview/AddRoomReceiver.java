package de.ibs.app.overview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by johnyso on 14.01.15.
 */
public class AddRoomReceiver extends BroadcastReceiver {
    private final StartActivity startActivity;

    public AddRoomReceiver(StartActivity startActivity) {
        this.startActivity = startActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.startActivity.AddRoomFragment();
    }
}
