package de.ibs.app.room.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import de.ibs.app.room.RoomContract;

import static de.ibs.app.room.RoomContract.*;

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
        switch (uriType) {
            case TYPE_ROOMS:
                long id = this.roomDatabaseHelper.insertRoom(values);
                return Uri.withAppendedPath(CONTENT_URI, ROOMS + "-" + id);
            default:
                throw new IllegalArgumentException("WrongUri");
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final int uriType = URI_MATCHER.match(uri).getCode();
        switch (uriType) {
            case TYPE_ROOMS:
                return this.roomDatabaseHelper.getRooms();
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}