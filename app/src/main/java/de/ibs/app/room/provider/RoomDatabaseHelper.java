package de.ibs.app.room.provider;

import android.content.Context;
import de.ibs.app.utils.DatabaseOpenHelper;

/**
 * Created by johnyso on 11.11.14.
 */
public class RoomDatabaseHelper {
    private DatabaseOpenHelper databaseOpenHelper;
    public RoomDatabaseHelper(Context context) {
        this.databaseOpenHelper = DatabaseOpenHelper.getInstance(context);
    }

}
