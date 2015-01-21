package de.ibs.app.overview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import de.ibs.app.room.utils.RoomContract;

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
        Bundle extra = intent.getExtras();
        int speakerId = extra.getInt(RoomContract.Speakers._ID,0);
        int roomId = extra.getInt(RoomContract.Speakers.ROOM_ID,0);

        this.activity.changeToAddSpeaker(roomId, speakerId);
    }
}
