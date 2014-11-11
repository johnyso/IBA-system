package de.ibs.app.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by johnyso on 11.11.14.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseOpenHelper";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ibs";
    private  static DatabaseOpenHelper instance;

    private static final String SQL_DROP_TABLE = "Drop table if exists "+DATABASE_NAME+".";
    private static final String SQL_DELETE_ALL_FROM_TABLE = "DELETE FROM ";
/*    public static final String SQL_CREATE_ROOM = "Create table if not exists "
            + Cameras.TABLE_NAME + "( "
            + Cameras._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Cameras.NAME + " " + Cameras.TYPE_NAME + " , "
            + Cameras.HTTP_PORT + " " + Cameras.TYPE_HTTP_PORT + " , "
            + Cameras.IP + " " + Cameras.TYPE_IP_INTERN + " , "
            + Cameras.RTSP_PORT + " " + Cameras.TYPE_RTSP_PORT + " , "
            + Cameras.USER + " " + Cameras.TYPE_USER + " , "
            + Cameras.PASSWORD + " " + Cameras.TYPE_PASSWORD + " , "
            + Cameras.TYPE + " " + Cameras.TYPE_TYPE + " DEFAULT " + CameraContract.MODEL_TVIP41550 + ", "
            + Cameras.SCREENSHOT + " " + Cameras.TYPE_SCREENSHOT + ", "
            + Cameras.STREAM + " " + Cameras.TYPE_STREAM + ", "
            + Cameras.LAST_UPDATE + " " + Cameras.TYPE_LAST_UPDATE + ", "
            + Cameras.CAMERA_EXT_ID + " " + Cameras.TYPE_CAMERA_EXT_ID + "); ";
    */

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
        //db.execSQL(SQL_CREATE_ROOM);
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
