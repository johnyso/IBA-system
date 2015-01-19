package de.ibs.app.overview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import de.ibs.app.room.utils.RoomContract;

/**
 * Created by johnyso on 19.01.15.
 */
public class RoomSettingReceiver extends BroadcastReceiver {
    private final StartActivity activity;

    public RoomSettingReceiver(StartActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra(RoomContract.Rooms._ID,0);
        activity.changeToSetting(id);
    }
}
