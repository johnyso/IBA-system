package de.ibs.app.room;

import android.net.Uri;
import de.ibs.app.utils.AdvancedUriMatcher;

/**
 * Created by johnyso on 04.11.14.
 */
public class RoomContract {
    public static final String AUTHORITY = "de.ibs.room";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String ROOMS = "rooms";

    public static final int TYPE_ROOMS = 1;

    public static final AdvancedUriMatcher URI_MATCHER = new AdvancedUriMatcher();

    static {
        URI_MATCHER.addURI(AUTHORITY, ROOMS, TYPE_ROOMS);
    }
}
