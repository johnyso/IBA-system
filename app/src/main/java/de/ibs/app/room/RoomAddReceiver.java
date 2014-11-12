package de.ibs.app.room;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by johnyso on 12.11.14.
 */
public class RoomAddReceiver extends BroadcastReceiver {
    private final RoomModel roomModel;

    public RoomAddReceiver(RoomModel roomModel) {
        this.roomModel = roomModel;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.roomModel.showAddRoom();
    }
}
