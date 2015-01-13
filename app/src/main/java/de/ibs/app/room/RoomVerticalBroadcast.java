package de.ibs.app.room;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by johnyso on 13.01.15.
 */
public class RoomVerticalBroadcast extends BroadcastReceiver {
    RoomDetailFragment fragment;

    public RoomVerticalBroadcast(RoomDetailFragment fragment){
        this.fragment = fragment;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.fragment.updateVerticalAlignment();
    }
}
