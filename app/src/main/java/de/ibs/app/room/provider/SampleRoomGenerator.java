package de.ibs.app.room.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import de.ibs.app.room.RoomContract;

/**
 * Created by johnyso on 11.11.14.
 */
public class SampleRoomGenerator {

    public static void createRooms(Context context){
        Cursor rooms = context.getContentResolver().query(Uri.withAppendedPath(RoomContract.CONTENT_URI,RoomContract.ROOMS),null,null,null,null);
        if (rooms.getCount() == 0) {
            Uri uri1 = context.getContentResolver().insert(Uri.withAppendedPath(RoomContract.CONTENT_URI,RoomContract.ROOMS),roomValues1());
            context.getContentResolver().insert(Uri.withAppendedPath(RoomContract.CONTENT_URI,RoomContract.ROOMS),roomValues2());

            context.getContentResolver().insert(Uri.parse(uri1+"/"+RoomContract.SPEAKERS),speakerValue1());
            context.getContentResolver().insert(Uri.parse(uri1+"/"+RoomContract.SPEAKERS),speakerValue2());

        }
    }

    private static ContentValues speakerValue1() {
        ContentValues value = new ContentValues();
        value.put(RoomContract.Speakers.IP, "141.62.110.97");
        value.put(RoomContract.Speakers.POSITION_X, 0);
        value.put(RoomContract.Speakers.POSITION_Y, 0);
        value.put(RoomContract.Speakers.HORIZONTAL, 0);
        value.put(RoomContract.Speakers.VERTICAL, 0);
        return value;
    }

    private static ContentValues speakerValue2() {
        ContentValues value = new ContentValues();
        value.put(RoomContract.Speakers.IP, "141.62.110.97");
        value.put(RoomContract.Speakers.POSITION_X, 200);
        value.put(RoomContract.Speakers.POSITION_Y, 200);
        value.put(RoomContract.Speakers.HORIZONTAL, 0);
        value.put(RoomContract.Speakers.VERTICAL, 0);
        return value;
    }

    private static ContentValues roomValues1(){
        ContentValues values = new ContentValues();
        values.put(RoomContract.Rooms.NAME, "Room 1");
        values.put(RoomContract.Rooms.HEIGHT, "2");
        values.put(RoomContract.Rooms.WIDTH, "6");
        values.put(RoomContract.Rooms.LENGTH, "3");
        return values;
    }

    private static ContentValues roomValues2(){
        ContentValues values = new ContentValues();
        values.put(RoomContract.Rooms.NAME, "Room 2");
        values.put(RoomContract.Rooms.HEIGHT, "2");
        values.put(RoomContract.Rooms.WIDTH, "3");
        values.put(RoomContract.Rooms.LENGTH, "2");
        return values;
    }
}
