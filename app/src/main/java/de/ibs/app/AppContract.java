package de.ibs.app;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * Created by johnyso on 05.11.14.
 */
public class AppContract {
    public static final String ROOM_MODEL_FRAGMENT = "fragment_room_model";
    public static final String ROOM_DETAIL_FRAGMENT = "fragment_room_detail";
    public static final String ROOM_OVERVIEW_FRAGMENT = "fragment_overview_model";
    public static final String ROOM_ADD_FRAGMENT = "fragment_room_add";

    public static final String BROADCAST_ACTION_ROOM = "broadcast_action_room";
    public static final String BROADCAST_ACTION_ADD_ROOM = "broadcast_add_room";

    public static final String ROOM_DETAIL_BACKSTACK = "backstack_room_detail";
    public static final String ROOM_ADD_BACKSTACK = "backstack_room_add";
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final String BROADCAST_UPDATE_VERTICAL = "de.ibs.broadcast.vertical";
    public static final String BROADCAST_ACTION_ADD_SPEAKER = "de.ibs.broadcast.add.speaker";
    public static final String SPEAKER_ADD_FRAGMENT = "speaker_add_fragment";
    public static final String BROADCAST_ACTION_ROOM_SETTING = "de.ibs.broadcast.room.setting";
    public static final double hFactor = 0.5;

    public static String getRestPath(int direction, int deg, String ip) {


        if (direction == HORIZONTAL){
            return "http://" + ip + "/index.php/horizontal-" + Math.abs((int) (deg * hFactor));
        } else {
            return "http://" + ip + "/index.php/vertical-" + deg ;
        }
    }
}
