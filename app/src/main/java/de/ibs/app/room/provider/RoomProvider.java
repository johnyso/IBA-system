package de.ibs.app.room.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import de.ibs.app.room.utils.RoomContract;

import java.util.List;

import static de.ibs.app.room.utils.RoomContract.*;

public class RoomProvider extends ContentProvider {
    private RoomDatabaseHelper roomDatabaseHelper;

    @Override
    public boolean onCreate() {
        this.roomDatabaseHelper = new RoomDatabaseHelper(this.getContext());
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int uriType = URI_MATCHER.match(uri).getCode();
        long id;
        switch (uriType) {
            case TYPE_ROOMS:
                id = this.roomDatabaseHelper.insertRoom(values);
                getContext().getContentResolver().notifyChange(Uri.withAppendedPath(RoomContract.CONTENT_URI, RoomContract.ROOMS), null);
                return Uri.withAppendedPath(CONTENT_URI, ROOMS + "-" + id);
            case TYPE_SPEAKERS:
                List<String> list = URI_MATCHER.match(uri).getCapturings();
                values.put(Speakers.ROOM_ID, list.get(1));
                id = this.roomDatabaseHelper.insertSpeaker(values);
                return Uri.withAppendedPath(CONTENT_URI, ROOMS + "-" + list.get(1) + "/" + SPEAKERS + "-" + id);
            default:
                throw new IllegalArgumentException("WrongUri");
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final int uriType = URI_MATCHER.match(uri).getCode();
        List<String> list;
        switch (uriType) {
            case TYPE_ROOMS:
                return this.roomDatabaseHelper.getRooms();
            case TYPE_SPEAKERS:
                list = URI_MATCHER.match(uri).getCapturings();
                return this.roomDatabaseHelper.getSpeakers(list.get(1));
            case TYPE_ROOM:
                list = URI_MATCHER.match(uri).getCapturings();
                return this.roomDatabaseHelper.getRoom(list.get(1));
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        final int uriType = URI_MATCHER.match(uri).getCode();
        List<String> list;
        switch (uriType) {
            case TYPE_ROOM:
                list = URI_MATCHER.match(uri).getCapturings();
                return this.roomDatabaseHelper.updateRoom(values, list.get(1));
            case TYPE_SPEAKER:
                list = URI_MATCHER.match(uri).getCapturings();
                return this.roomDatabaseHelper.updateSpeaker(values, list.get(1), list.get(2));
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }
}
