package de.ibs.app.room.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import de.ibs.app.utils.DatabaseOpenHelper;

import static de.ibs.app.room.utils.RoomContract.*;

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
        String where = Speakers.ROOM_ID + "=?";
        String[] arg = {roomId};
        SQLiteDatabase database = this.databaseOpenHelper.getWritableDatabase();
        return database.query(Speakers.TABLE_NAME, null, where, arg, null, null, null, null);
    }

    public long insertSpeaker(ContentValues values) {
        SQLiteDatabase database = this.databaseOpenHelper.getWritableDatabase();
        return database.insert(Speakers.TABLE_NAME, null, values);
    }

    public Cursor getRoom(String roomId) {
        String where = Rooms._ID + "=?";
        String[] arg = {roomId};
        SQLiteDatabase database = this.databaseOpenHelper.getWritableDatabase();
        return database.query(Rooms.TABLE_NAME, null, where, arg, null, null, null, null);
    }

    public int updateRoom(ContentValues values, String roomId) {
        String where = Rooms._ID + "=?";
        String[] arg = {roomId};
        SQLiteDatabase database = this.databaseOpenHelper.getWritableDatabase();
        return database.update(Rooms.TABLE_NAME, values, where, arg);
    }

    public int updateSpeaker(ContentValues values, String roomId, String speakerId) {
        String where = Speakers.ROOM_ID + "=? AND " + Speakers._ID + "=?";
        String[] arg = {roomId, speakerId};
        SQLiteDatabase database = this.databaseOpenHelper.getWritableDatabase();
        return database.update(Speakers.TABLE_NAME, values, where, arg);
    }
}
