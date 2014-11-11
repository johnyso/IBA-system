package de.ibs.app.room.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import de.ibs.app.room.RoomContract;
import de.ibs.app.utils.DatabaseOpenHelper;

import static de.ibs.app.room.RoomContract.*;

/**
 * Created by johnyso on 11.11.14.
 */
public class RoomDatabaseHelper {
    private DatabaseOpenHelper databaseOpenHelper;
    public RoomDatabaseHelper(Context context) {
        this.databaseOpenHelper = DatabaseOpenHelper.getInstance(context);
    }


    public long insertRoom(ContentValues values) {
        SQLiteDatabase database = this.databaseOpenHelper.getWritableDatabase();
        return database.insert(Rooms.TABLE_NAME, null, values);
    }

    public Cursor getRooms() {
        SQLiteDatabase database = this.databaseOpenHelper.getWritableDatabase();
        return database.query(Rooms.TABLE_NAME, null, null, null, null, null, null, null);
    }

    public Cursor getSpeakers(String roomId) {
        String where = "Room_id=?";
        String[] arg = {roomId};
        SQLiteDatabase database = this.databaseOpenHelper.getWritableDatabase();
        return database.query(Speakers.TABLE_NAME, null, where, arg, null, null, null, null);
    }
}
