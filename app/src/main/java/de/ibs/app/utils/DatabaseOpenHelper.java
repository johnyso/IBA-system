package de.ibs.app.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static de.ibs.app.room.RoomContract.*;

/**
 * Created by johnyso on 11.11.14.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseOpenHelper";
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "ibs";
    private  static DatabaseOpenHelper instance;

    private static final String SQL_DROP_TABLE = "Drop table if exists "+DATABASE_NAME+".";
    private static final String SQL_DELETE_ALL_FROM_TABLE = "DELETE FROM ";

    public static final String SQL_CREATE_ROOM = "Create table if not exists "
            + Rooms.TABLE_NAME + "( "
            + Rooms._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Rooms.NAME + " " + Rooms.TYPE_NAME + " , "
            + Rooms.HEIGHT + " " + Rooms.TYPE_HEIGHT + " , "
            + Rooms.WIDTH + " " + Rooms.TYPE_WIDTH + " , "
            + Rooms.LENGTH + " " + Rooms.TYPE_LENGTH + " , "
            + Rooms.PERSON_X + " " + Rooms.TYPE_PERSON_X + " DEFAULT 1, "
            + Rooms.PERSON_Y + " " + Rooms.TYPE_PERSON_Y + " DEFAULT 1, "
            + Rooms.PERSON_HEIGHT + " " + Rooms.TYPE_PERSON_HEIGHT + " DEFAULT 1); ";

    public static final String SQL_CREATE_SPEAKER = "Create table if not exists "
            + Speakers.TABLE_NAME + "( "
            + Speakers._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Speakers.IP + " " + Speakers.TYPE_IP + " , "
            + Speakers.NAME + " " + Speakers.TYPE_NAME + " , "
            + Speakers.POSITION_Y + " " + Speakers.TYPE_HEIGHT + " , "
            + Speakers.POSITION_X + " " + Speakers.TYPE_WIDTH + " , "
            + Speakers.HORIZONTAL + " " + Speakers.TYPE_HORIZONTAL + " , "
            + Speakers.VERTICAL + " " + Speakers.TYPE_VERTICAL + " , "
            + Speakers.ROOM_ID + " " + Speakers.TYPE_ROOM_ID + " , "
            + "FOREIGN KEY(" + Speakers.ROOM_ID + ") REFERENCES " + Rooms.TABLE_NAME + "(" + Rooms._ID + ")); ";


    public static DatabaseOpenHelper getInstance(Context context){
        if (instance == null) {
            instance = new DatabaseOpenHelper(context);
        }
        return instance;
    }

    private DatabaseOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ROOM);
        db.execSQL(SQL_CREATE_SPEAKER);
    }

    public void dropTable(String table){
        String sql = SQL_DROP_TABLE+table;
        Log.d(TAG, "dropTable(): " + sql);
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(sql);
    }

    public void deleteAll(String table){
        String sql = SQL_DELETE_ALL_FROM_TABLE +table;
        Log.d(TAG, "deleteAll(): " + sql);
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
