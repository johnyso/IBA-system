package de.ibs.app.speaker.processor;

import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import de.ibs.app.room.utils.RoomContract;

/**
 * Created by johnyso on 26.11.14.
 */
public class SpeakerParser {

    public static Speaker[] parseSpeakers(Cursor cursor){
        Speaker.Builder builder = new Speaker.Builder();
        Speaker[] speakers = new Speaker[cursor.getCount()];
        if (cursor.moveToFirst()){
            do {
                Speaker speaker = builder.id(cursor.getInt(cursor.getColumnIndex(RoomContract.Speakers._ID)))
                        .ip(cursor.getString(cursor.getColumnIndex(RoomContract.Speakers.IP)))
                        .name(cursor.getString(cursor.getColumnIndex(RoomContract.Speakers.NAME)))
                        .positionX(cursor.getInt(cursor.getColumnIndex(RoomContract.Speakers.POSITION_X)))
                        .positionY(cursor.getInt(cursor.getColumnIndex(RoomContract.Speakers.POSITION_Y)))
                        .alignment(cursor.getInt(cursor.getColumnIndex(RoomContract.Speakers.ALIGNMENT)))
                        .positionHeight(cursor.getInt(cursor.getColumnIndex(RoomContract.Speakers.POSITION_HEIGHT)))
                        .horizontal(cursor.getInt(cursor.getColumnIndex(RoomContract.Speakers.HORIZONTAL)))
                        .vertical(cursor.getInt(cursor.getColumnIndex(RoomContract.Speakers.VERTICAL)))
                        .roomId(cursor.getInt(cursor.getColumnIndex(RoomContract.Speakers.ROOM_ID)))
                        .build();
                speakers[cursor.getPosition()] = speaker;
            } while (cursor.moveToNext());
            return speakers;
        } else {
            throw new CursorIndexOutOfBoundsException("no results");
        }
    }
}
