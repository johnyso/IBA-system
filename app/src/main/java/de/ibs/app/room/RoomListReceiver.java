package de.ibs.app.room;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by johnyso on 11.11.14.
 */
public class RoomListReceiver extends BroadcastReceiver{

    private final RoomModel roomModel;

    public RoomListReceiver(RoomModel roomModel) {
        this.roomModel = roomModel;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        int id = bundle.getInt(RoomContract.Rooms._ID);
        this.roomModel.showRoomDetail(id);
    }
}
