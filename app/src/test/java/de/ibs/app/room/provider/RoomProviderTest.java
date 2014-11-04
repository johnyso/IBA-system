package de.ibs.app.room.provider;

import android.app.Activity;
import de.ibs.app.room.RoomActivity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertTrue;

/**
 * Created by johnyso on 04.11.14.
 */
@Config(emulateSdk = 18, manifest = "app/src/main/AndroidManifest.xml")
@RunWith(RobolectricTestRunner.class)
public class RoomProviderTest {

    @Test
    public void testSomething() throws Exception {
        Activity activity = Robolectric.buildActivity(RoomActivity.class).create().get();
        assertTrue(activity != null);
    }
}