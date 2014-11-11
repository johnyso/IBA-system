package de.ibs.app.room;

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
    public static final String ROOM = "room";

    public static final int TYPE_ROOMS = 1;
    public static final int TYPE_ROOM = 1;

    public static final AdvancedUriMatcher URI_MATCHER = new AdvancedUriMatcher();

    static {
        URI_MATCHER.addURI(AUTHORITY, ROOMS, TYPE_ROOMS);
        URI_MATCHER.addURI(AUTHORITY, ROOM, TYPE_ROOM);
    }

    public static abstract class Rooms implements BaseColumns{
        public static final String TABLE_NAME = "rooms";

        public static final String NAME = "name";
        public static final String WIDTH = "width";
        public static final String LENGTH = "length";
        public static final String HEIGHT = "height";

        public static final String TYPE_NAME = "TEXT";
        public static final String TYPE_WIDTH = "INTEGER";
        public static final String TYPE_LENGTH = "INTEGER";
        public static final String TYPE_HEIGHT = "INTEGER";
    }
}
