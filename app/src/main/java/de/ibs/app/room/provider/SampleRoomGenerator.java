package de.ibs.app.room.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import de.ibs.app.room.utils.RoomContract;

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
            context.getContentResolver().insert(Uri.parse(uri1+"/"+RoomContract.SPEAKERS),speakerValue3());
            context.getContentResolver().insert(Uri.parse(uri1+"/"+RoomContract.SPEAKERS),speakerValue4());

        }
    }

    private static ContentValues speakerValue1() {
        ContentValues value = new ContentValues();
        value.put(RoomContract.Speakers.IP, "192.168.1.34:8080");
        value.put(RoomContract.Speakers.POSITION_X, 170);
        value.put(RoomContract.Speakers.POSITION_Y, 0);
        value.put(RoomContract.Speakers.POSITION_HEIGHT, 150);
        value.put(RoomContract.Speakers.ALIGNMENT, RoomContract.Speakers.ALIGNMENT_TOP);
        value.put(RoomContract.Speakers.HORIZONTAL, 0);
        value.put(RoomContract.Speakers.VERTICAL, 0);
        return value;
    }

    private static ContentValues speakerValue2() {
        ContentValues value = new ContentValues();
        value.put(RoomContract.Speakers.IP, "192.168.1.35:8080");
        value.put(RoomContract.Speakers.POSITION_X, 320);
        value.put(RoomContract.Speakers.POSITION_Y, 200);
        value.put(RoomContract.Speakers.POSITION_HEIGHT, 150);
        value.put(RoomContract.Speakers.ALIGNMENT, RoomContract.Speakers.ALIGNMENT_RIGHT);
        value.put(RoomContract.Speakers.HORIZONTAL, 0);
        value.put(RoomContract.Speakers.VERTICAL, 0);
        return value;
    }

    private static ContentValues speakerValue3() {
        ContentValues value = new ContentValues();
        value.put(RoomContract.Speakers.IP, "192.168.1.37:8080");
        value.put(RoomContract.Speakers.POSITION_X, 200);
        value.put(RoomContract.Speakers.POSITION_Y, 455);
        value.put(RoomContract.Speakers.POSITION_HEIGHT, 150);
        value.put(RoomContract.Speakers.ALIGNMENT, RoomContract.Speakers.ALIGNMENT_BOTTOM);
        value.put(RoomContract.Speakers.HORIZONTAL, 0);
        value.put(RoomContract.Speakers.VERTICAL, 0);
        return value;
    }

    private static ContentValues speakerValue4() {
        ContentValues value = new ContentValues();
        value.put(RoomContract.Speakers.IP, "192.168.1.38:8080");
        value.put(RoomContract.Speakers.POSITION_X, 0);
        value.put(RoomContract.Speakers.POSITION_Y, 200);
        value.put(RoomContract.Speakers.POSITION_HEIGHT, 150);
        value.put(RoomContract.Speakers.ALIGNMENT, RoomContract.Speakers.ALIGNMENT_LEFT);
        value.put(RoomContract.Speakers.HORIZONTAL, 0);
        value.put(RoomContract.Speakers.VERTICAL, 0);
        return value;
    }

//    private static ContentValues speakerValue3() {
//        ContentValues value = new ContentValues();
//        value.put(RoomContract.Speakers.IP, "141.62.110.97");
//        value.put(RoomContract.Speakers.POSITION_X, 300);
//        value.put(RoomContract.Speakers.POSITION_Y, 0);
//        value.put(RoomContract.Speakers.HORIZONTAL, 0);
//        value.put(RoomContract.Speakers.VERTICAL, 0);
//        return value;
//    }
//
//    private static ContentValues speakerValue4() {
//        ContentValues value = new ContentValues();
//        value.put(RoomContract.Speakers.IP, "141.62.110.97");
//        value.put(RoomContract.Speakers.POSITION_X, 300);
//        value.put(RoomContract.Speakers.POSITION_Y, 400);
//        value.put(RoomContract.Speakers.HORIZONTAL, 0);
//        value.put(RoomContract.Speakers.VERTICAL, 0);
//        return value;
//    }

    private static ContentValues roomValues1(){
        ContentValues values = new ContentValues();
        values.put(RoomContract.Rooms.NAME, "Room 1");
        values.put(RoomContract.Rooms.HEIGHT, "200");
        values.put(RoomContract.Rooms.WIDTH, "600");
        values.put(RoomContract.Rooms.LENGTH, "300");
        return values;
    }

    private static ContentValues roomValues2(){
        ContentValues values = new ContentValues();
        values.put(RoomContract.Rooms.NAME, "Room 2");
        values.put(RoomContract.Rooms.HEIGHT, "200");
        values.put(RoomContract.Rooms.WIDTH, "300");
        values.put(RoomContract.Rooms.LENGTH, "200");
        return values;
    }
}
