package de.ibs.app.room;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by johnyso on 11.11.14.
 */
public class RoomListReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("RoomListReceiver","clicked");
    }
}
