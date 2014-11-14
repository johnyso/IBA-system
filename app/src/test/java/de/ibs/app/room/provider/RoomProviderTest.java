package de.ibs.app.room.provider;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import de.ibs.app.room.RoomActivity;
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
    private static final int TEST_SPEAKER_WIDTH = 2;
    private static final int TEST_SPEAKER_HEIGHT = 2;
    private static final int TEST_SPEAKER_VERTICAL = 3;
    private static final int TEST_SPEAKER_HORIZONTAL = 4;
    private static final int TEST_SPEAKER_ROOM = 1;

    private static final String TEST_INSERT_URI = "content://de.ibs.room/rooms-1";
    private static final String TEST_INSERT_URI_SPEAKER = "content://de.ibs.room/rooms-1/speakers-1";

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
            assertThat(result.getInt(result.getColumnIndex(Rooms._ID)),equalTo(1));
            assertThat(result.getString(result.getColumnIndex(Rooms.NAME)),equalTo(TEST_NAME));
            assertThat(result.getInt(result.getColumnIndex(Rooms.HEIGHT)),equalTo(TEST_HEIGHT));
            assertThat(result.getInt(result.getColumnIndex(Rooms.LENGTH)),equalTo(TEST_LENGTH));
            assertThat(result.getInt(result.getColumnIndex(Rooms.WIDTH)),equalTo(TEST_WIDTH));

        }
    }

    @Test
    public void checkCursorOneRoom(){
        this.shadowContentResolver.insert(Uri.withAppendedPath(CONTENT_URI, ROOMS), exampleValues());
        this.shadowContentResolver.insert(Uri.withAppendedPath(CONTENT_URI, ROOMS), exampleValues());
        Cursor result = this.shadowContentResolver.query(Uri.withAppendedPath(CONTENT_URI, ROOMS + "-1"), null, null, null, null);
        assertThat(result.getCount(),equalTo(1));
        if(result.moveToFirst()){
            assertThat(result.getInt(result.getColumnIndex(Rooms._ID)),equalTo(1));
            assertThat(result.getString(result.getColumnIndex(Rooms.NAME)),equalTo(TEST_NAME));
            assertThat(result.getInt(result.getColumnIndex(Rooms.HEIGHT)),equalTo(TEST_HEIGHT));
            assertThat(result.getInt(result.getColumnIndex(Rooms.LENGTH)),equalTo(TEST_LENGTH));
            assertThat(result.getInt(result.getColumnIndex(Rooms.WIDTH)),equalTo(TEST_WIDTH));

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
            assertThat(result.getInt(result.getColumnIndex(Speakers._ID)),equalTo(1));
            assertThat(result.getString(result.getColumnIndex(Speakers.IP)),equalTo(TEST_SPEAKER_IP));
            assertThat(result.getInt(result.getColumnIndex(Speakers.HEIGHT)),equalTo(TEST_SPEAKER_HEIGHT));
            assertThat(result.getInt(result.getColumnIndex(Speakers.WIDTH)),equalTo(TEST_SPEAKER_WIDTH));
            assertThat(result.getInt(result.getColumnIndex(Speakers.VERTICAL)),equalTo(TEST_SPEAKER_VERTICAL));
            assertThat(result.getInt(result.getColumnIndex(Speakers.HORIZONTAL)),equalTo(TEST_SPEAKER_HORIZONTAL));
            assertThat(result.getInt(result.getColumnIndex(Speakers.ROOM_ID)),equalTo(1));
        }
    }

    private ContentValues exampleValuesSpeaker() {
        ContentValues value = new ContentValues();
        value.put(Speakers.IP, TEST_SPEAKER_IP);
        value.put(Speakers.WIDTH, TEST_SPEAKER_WIDTH);
        value.put(Speakers.HEIGHT, TEST_SPEAKER_HEIGHT);
        value.put(Speakers.HORIZONTAL, TEST_SPEAKER_HORIZONTAL);
        value.put(Speakers.VERTICAL, TEST_SPEAKER_VERTICAL);
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
        return value;
    }

}