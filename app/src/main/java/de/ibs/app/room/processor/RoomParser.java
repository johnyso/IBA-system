package de.ibs.app.room.processor;

import android.database.Cursor;
import de.ibs.app.room.utils.RoomContract;

import java.util.NoSuchElementException;

/**
 * Created by johnyso on 25.11.14.
 */
public class RoomParser {
    public static Room parseRoom(Cursor cursor, int position){
        if (cursor.moveToPosition(position)) {
            Room.Builder builder = new Room.Builder();
            return builder.id(cursor.getInt(cursor.getColumnIndex(RoomContract.Rooms._ID)))
                    .name(cursor.getString(cursor.getColumnIndex(RoomContract.Rooms.NAME)))
                    .width(cursor.getInt(cursor.getColumnIndex(RoomContract.Rooms.WIDTH)))
                    .length(cursor.getInt(cursor.getColumnIndex(RoomContract.Rooms.LENGTH)))
                    .height(cursor.getInt(cursor.getColumnIndex(RoomContract.Rooms.HEIGHT)))
                    .personX(cursor.getInt(cursor.getColumnIndex(RoomContract.Rooms.PERSON_X)))
                    .personY(cursor.getInt(cursor.getColumnIndex(RoomContract.Rooms.PERSON_Y)))
                    .personHeight(cursor.getInt(cursor.getColumnIndex(RoomContract.Rooms.PERSON_HEIGHT)))
                    .build();
        } else {
            throw new NoSuchElementException("no Item at position: "+position);
        }
    }
}
