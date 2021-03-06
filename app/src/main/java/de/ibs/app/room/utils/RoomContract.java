package de.ibs.app.room.utils;

import android.net.Uri;
import android.provider.BaseColumns;
import de.ibs.app.utils.AdvancedUriMatcher;

/**
 * Created by johnyso on 04.11.14.
 */
public class RoomContract {
    public static final String AUTHORITY = "de.ibs.room";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String ROOMS = "rooms";
    public static final String ROOM = "rooms-#";
    public static final String SPEAKERS = "speakers";
    public static final String SPEAKER = "speakers-#";

    public static final int TYPE_ROOMS = 1;
    public static final int TYPE_ROOM = 2;
    public static final int TYPE_SPEAKERS = 10;
    public static final int TYPE_SPEAKER = 11;

    public static final AdvancedUriMatcher URI_MATCHER = new AdvancedUriMatcher();

    static {
        URI_MATCHER.addURI(AUTHORITY, ROOMS, TYPE_ROOMS);
        URI_MATCHER.addURI(AUTHORITY, ROOM, TYPE_ROOM);
        URI_MATCHER.addURI(AUTHORITY, ROOM + "/" + SPEAKERS, TYPE_SPEAKERS);
        URI_MATCHER.addURI(AUTHORITY, ROOM + "/" + SPEAKER, TYPE_SPEAKER);
    }

    public static final String CURRENT_ROOM = "currentRoom";

    public static Uri getSpeakerPath(int roomId, int speakerId) {
        return Uri.withAppendedPath(CONTENT_URI,ROOMS + "-" + roomId + "/" + SPEAKERS + "-" + speakerId);
    }

    public static Uri getAllSpeakerPath(int roomId){
        return Uri.withAppendedPath(CONTENT_URI,ROOMS + "-" + roomId + "/" + SPEAKERS);
    }

    public static abstract class Rooms implements BaseColumns {
        public static final String TABLE_NAME = "rooms";

        public static final String NAME = "name";
        public static final String WIDTH = "width";
        public static final String LENGTH = "length";
        public static final String HEIGHT = "height";
        public static final String PERSON_X = "person_x";
        public static final String PERSON_Y = "person_y";
        public static final String PERSON_HEIGHT = "person_heigth";

        public static final String TYPE_NAME = "TEXT";
        public static final String TYPE_WIDTH = "INTEGER";
        public static final String TYPE_LENGTH = "INTEGER";
        public static final String TYPE_HEIGHT = "INTEGER";
        public static final String TYPE_PERSON_X = "INTEGER";
        public static final String TYPE_PERSON_Y = "INTEGER";
        public static final String TYPE_PERSON_HEIGHT = "INTEGER";

        public static final String[] ALL_COLUMNS = {_ID, NAME, WIDTH, LENGTH, HEIGHT, PERSON_X, PERSON_Y, PERSON_HEIGHT};
    }

    public static abstract class Speakers implements BaseColumns {
        public static final String TABLE_NAME = "speakers";

        public static final String IP = "ip";
        public static final String NAME = "name";
        public static final String POSITION_X = "position_x";
        public static final String POSITION_Y = "position_y";
        public static final String POSITION_HEIGHT = "height";
        public static final String HORIZONTAL = "horizontal";
        public static final String VERTICAL = "vertical";
        public static final String ALIGNMENT = "alignment";

        public static final String ROOM_ID = "room_id";
        public static final String TYPE_IP = "TEXT";
        public static final String TYPE_NAME = "TEXT";
        public static final String TYPE_POSITION_X = "INTEGER";
        public static final String TYPE_POSITION_Y = "INTEGER";
        public static final String TYPE_ALIGNMENT = "INTEGER";
        public static final String TYPE_POSITION_HEIGHT = "INTEGER";
        public static final String TYPE_HORIZONTAL = "INTEGER";
        public static final String TYPE_VERTICAL = "INTEGER";
        public static final String TYPE_ROOM_ID = "INTEGER";

        public static final int ALIGNMENT_TOP = 1;
        public static final int ALIGNMENT_RIGHT = 2;
        public static final int ALIGNMENT_BOTTOM = 3;
        public static final int ALIGNMENT_LEFT = 4;

        public static final String[] ALL_COLUMNS = {_ID, IP, NAME, POSITION_X, POSITION_Y, ALIGNMENT, POSITION_HEIGHT, HORIZONTAL, VERTICAL, ROOM_ID};
    }
}
