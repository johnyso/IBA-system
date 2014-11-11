package de.ibs.app.room.provider;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import de.ibs.app.room.RoomActivity;
import de.ibs.app.room.RoomContract;
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
}