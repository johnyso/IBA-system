package de.ibs.app.room.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import de.ibs.app.room.RoomContract;
import de.ibs.app.utils.DatabaseOpenHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowContentResolver;
import org.robolectric.shadows.ShadowLog;

import static de.ibs.app.room.RoomContract.*;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by johnyso on 04.11.14.
 */
@Config(emulateSdk = 18, manifest = "app/src/main/AndroidManifest.xml")
@RunWith(RobolectricTestRunner.class)
public class RoomProviderTest {
    private static final String TEST_NAME = "Room1";
    private static final int TEST_WIDTH = 10;
    private static final int TEST_HEIGHT = 2;
    private static final int TEST_LENGTH = 10;
    private static final String TEST_SPEAKER_IP = "141.62.110.97";
    private static final int TEST_SPEAKER_POSITION_X = 2;
    private static final int TEST_SPEAKER_POSITION_Y = 2;
    private static final int TEST_SPEAKER_VERTICAL = 3;
    private static final int TEST_SPEAKER_HORIZONTAL = 4;
    private static final int TEST_SPEAKER_ROOM = 1;

    private static final String TEST_INSERT_URI = "content://de.ibs.room/rooms-1";
    private static final String TEST_INSERT_URI_SPEAKER = "content://de.ibs.room/rooms-1/speakers-1";
    private static final Integer TEST_PERSON_X = 200;
    private static final Integer TEST_PERSON_Y = 100;
    private static final int TEST_SPEAKER_POSITION_HEIGHT = 2;
    private static final Integer TEST_PERSON_HEIGHT = 1;
    private static final String TEST_SPEAKER_NAME = "FR";

    private RoomProvider roomProvider;
    private ShadowContentResolver shadowContentResolver;

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
        this.roomProvider = new RoomProvider();
        ContentResolver contentResolver = Robolectric.application.getContentResolver();
        this.shadowContentResolver = Robolectric.shadowOf(contentResolver);
        this.roomProvider.onCreate();
        ShadowContentResolver.registerProvider(AUTHORITY, this.roomProvider);
    }

    /**
     * TEST ROOM
     */
    @Test
    public void testInitialQueryIsEmpty() {
        Cursor cursor = shadowContentResolver.query(Uri.withAppendedPath(CONTENT_URI, ROOMS), null, null, null, null);
        assertThat(cursor.getCount(), equalTo(0));
    }

    /*
    * Test insert
    */
    @Test(expected = IllegalArgumentException.class)
    public void testInsert() {
        this.shadowContentResolver.insert(Uri.withAppendedPath(CONTENT_URI, "WrongUri"), new ContentValues());
    }

    @Test
    public void insertRoom() {
        Uri uri = this.shadowContentResolver.insert(Uri.withAppendedPath(CONTENT_URI, ROOMS), exampleValues());
        assertThat(uri.toString(),equalTo(TEST_INSERT_URI));
        Cursor result = this.shadowContentResolver.query(Uri.withAppendedPath(CONTENT_URI, ROOMS), null, null, null, null);
        assertThat(result.getCount(), equalTo(1));
    }

    @Test
    public void checkCursorRoom(){
        this.shadowContentResolver.insert(Uri.withAppendedPath(CONTENT_URI, ROOMS), exampleValues());
        Cursor result = this.shadowContentResolver.query(Uri.withAppendedPath(CONTENT_URI, ROOMS), null, null, null, null);
        assertThat(result.getCount(),equalTo(1));
        if(result.moveToFirst()){
            checkCursorRoomResults(result);

        }
    }

    @Test
    public void checkCursorOneRoom(){
        this.shadowContentResolver.insert(Uri.withAppendedPath(CONTENT_URI, ROOMS), exampleValues());
        this.shadowContentResolver.insert(Uri.withAppendedPath(CONTENT_URI, ROOMS), exampleValues());
        Cursor result = this.shadowContentResolver.query(Uri.withAppendedPath(CONTENT_URI, ROOMS + "-1"), null, null, null, null);
        assertThat(result.getCount(),equalTo(1));
        if(result.moveToFirst()){
            checkCursorRoomResults(result);

        }
    }

    @Test
    public void checkUpdateRoom(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Rooms.PERSON_X,200);
        contentValues.put(Rooms.PERSON_Y,200);
        contentValues.put(Rooms.PERSON_HEIGHT,100);
        this.shadowContentResolver.insert(Uri.withAppendedPath(CONTENT_URI, ROOMS), exampleValues());

        int changes = this.shadowContentResolver.update(Uri.withAppendedPath(CONTENT_URI, ROOMS + "-1"), contentValues, null, null);
        assertThat(changes, equalTo(1));

        Cursor result = this.shadowContentResolver.query(Uri.withAppendedPath(CONTENT_URI, ROOMS), null, null, null, null);
        assertThat(result.getCount(),equalTo(1));
        if(result.moveToFirst()){
            assertThat(result.getInt(result.getColumnIndex(Rooms.PERSON_X)), equalTo(200));
            assertThat(result.getInt(result.getColumnIndex(Rooms.PERSON_Y)), equalTo(200));
            assertThat(result.getInt(result.getColumnIndex(Rooms.PERSON_HEIGHT)), equalTo(100));
        }
    }

    /**
     * TEST SPEAKER
     */
    @Test
    public void testInitialSpeakerQueryIsEmpty() {
        Cursor cursor = shadowContentResolver.query(Uri.withAppendedPath(CONTENT_URI, ROOMS + "-1/" + SPEAKERS), null, null, null, null);
        assertThat(cursor.getCount(), equalTo(0));
    }

    /*
   * Test insert
   */
    @Test(expected = IllegalArgumentException.class)
    public void testInsertSpeaker() {
        this.shadowContentResolver.insert(Uri.withAppendedPath(CONTENT_URI, "WrongUri"), new ContentValues());
    }

    @Test
    public void insertSpeaker() {
        this.shadowContentResolver.insert(Uri.withAppendedPath(CONTENT_URI, ROOMS), exampleValues());
        Uri uri = this.shadowContentResolver.insert(Uri.withAppendedPath(CONTENT_URI, ROOMS + "-1/" + SPEAKERS), exampleValuesSpeaker());
        assertThat(uri.toString(),equalTo(TEST_INSERT_URI_SPEAKER));
        Cursor result = this.shadowContentResolver.query(Uri.withAppendedPath(CONTENT_URI, ROOMS + "-1/" + SPEAKERS), null, null, null, null);
        assertThat(result.getCount(), equalTo(1));
    }

    @Test
    public void checkCursorSpeaker(){
        this.shadowContentResolver.insert(Uri.withAppendedPath(CONTENT_URI, ROOMS), exampleValues());
        Uri uri = this.shadowContentResolver.insert(Uri.withAppendedPath(CONTENT_URI, ROOMS + "-1/" + SPEAKERS), exampleValuesSpeaker());
        Cursor result = this.shadowContentResolver.query(Uri.withAppendedPath(CONTENT_URI, ROOMS + "-1/" + SPEAKERS), null, null, null, null);
        assertThat(result.getCount(),equalTo(1));
        if(result.moveToFirst()){
            checkSpeakerCursor(result);
        }
    }

    private void checkSpeakerCursor(Cursor result) {
        assertThat(result.getInt(result.getColumnIndex(Speakers._ID)),equalTo(1));
        assertThat(result.getString(result.getColumnIndex(Speakers.NAME)),equalTo(TEST_SPEAKER_NAME));
        assertThat(result.getString(result.getColumnIndex(Speakers.IP)),equalTo(TEST_SPEAKER_IP));
        assertThat(result.getInt(result.getColumnIndex(Speakers.POSITION_Y)),equalTo(TEST_SPEAKER_POSITION_Y));
        assertThat(result.getInt(result.getColumnIndex(Speakers.POSITION_X)),equalTo(TEST_SPEAKER_POSITION_X));
        assertThat(result.getInt(result.getColumnIndex(Speakers.POSITION_HEIGHT)),equalTo(TEST_SPEAKER_POSITION_X));
        assertThat(result.getInt(result.getColumnIndex(Speakers.VERTICAL)),equalTo(TEST_SPEAKER_VERTICAL));
        assertThat(result.getInt(result.getColumnIndex(Speakers.HORIZONTAL)),equalTo(TEST_SPEAKER_HORIZONTAL));
        assertThat(result.getInt(result.getColumnIndex(Speakers.ROOM_ID)),equalTo(1));
    }

    private ContentValues exampleValuesSpeaker() {
        ContentValues value = new ContentValues();
        value.put(Speakers.NAME, TEST_SPEAKER_NAME);
        value.put(Speakers.IP, TEST_SPEAKER_IP);
        value.put(Speakers.POSITION_X, TEST_SPEAKER_POSITION_X);
        value.put(Speakers.POSITION_Y, TEST_SPEAKER_POSITION_Y);
        value.put(Speakers.POSITION_HEIGHT, TEST_SPEAKER_POSITION_HEIGHT);
        value.put(Speakers.HORIZONTAL, TEST_SPEAKER_HORIZONTAL);
        value.put(Speakers.VERTICAL, TEST_SPEAKER_VERTICAL);
        value.put(Speakers.ROOM_ID, TEST_SPEAKER_ROOM);
        return value;
    }


    @After
    public void tearDown() {
        DatabaseOpenHelper.getInstance(Robolectric.application).getWritableDatabase().close();
    }


    private ContentValues exampleValues() {
            ContentValues value = new ContentValues();
            value.put(Rooms.NAME, TEST_NAME);
            value.put(Rooms.WIDTH, TEST_WIDTH);
            value.put(Rooms.HEIGHT, TEST_HEIGHT);
            value.put(Rooms.LENGTH, TEST_LENGTH);
            value.put(Rooms.PERSON_X, TEST_PERSON_X);
            value.put(Rooms.PERSON_Y, TEST_PERSON_Y);
            value.put(Rooms.PERSON_HEIGHT, TEST_PERSON_HEIGHT);
        return value;
    }


    private void checkCursorRoomResults(Cursor result) {
        assertThat(result.getInt(result.getColumnIndex(Rooms._ID)),equalTo(1));
        assertThat(result.getString(result.getColumnIndex(Rooms.NAME)), equalTo(TEST_NAME));
        assertThat(result.getInt(result.getColumnIndex(Rooms.HEIGHT)), equalTo(TEST_HEIGHT));
        assertThat(result.getInt(result.getColumnIndex(Rooms.LENGTH)), equalTo(TEST_LENGTH));
        assertThat(result.getInt(result.getColumnIndex(Rooms.WIDTH)), equalTo(TEST_WIDTH));
        assertThat(result.getInt(result.getColumnIndex(Rooms.PERSON_X)), equalTo(TEST_PERSON_X));
        assertThat(result.getInt(result.getColumnIndex(Rooms.PERSON_Y)), equalTo(TEST_PERSON_Y));
        assertThat(result.getInt(result.getColumnIndex(Rooms.PERSON_HEIGHT)), equalTo(TEST_PERSON_HEIGHT));
    }

}