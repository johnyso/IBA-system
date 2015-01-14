package de.ibs.app.room.processor;

import android.database.Cursor;
import android.database.MatrixCursor;
import de.ibs.app.room.utils.RoomContract;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by johnyso on 25.11.14.
 */

@Config(manifest = "app/src/main/AndroidManifest.xml")
@RunWith(RobolectricTestRunner.class)
public class RoomParserTest {
    private int TEST_ID_1 = 1;
    private int TEST_ID_2 = 2;
    private String TEST_NAME_1 = "Room1";
    private String TEST_NAME_2 = "Room2";
    private static final int TEST_WIDTH_1 = 150;
    private static final int TEST_WIDTH_2 = 160;
    private static final int TEST_LENGTH_1 = 220;
    private static final int TEST_LENGTH_2 = 230;
    private static final int TEST_HEIGHT_1 = 100;
    private static final int TEST_HEIGHT_2 = 120;
    private static final Integer TEST_PERSON_X = 200;
    private static final Integer TEST_PERSON_Y = 100;
    private static final Integer TEST_PERSON_HEIGHT = 1;

    @Test
    public void testRoomParseSingleCursor() {
        Room room = RoomParser.parseRoom(this.getTestCursor(), 0);
        assertThat(room.getId(), equalTo(TEST_ID_1));
        assertThat(room.getName(), equalTo(TEST_NAME_1));
        assertThat(room.getWidth(), equalTo(TEST_WIDTH_1));
        assertThat(room.getLength(), equalTo(TEST_LENGTH_1));
        assertThat(room.getHeight(), equalTo(TEST_HEIGHT_1));
        assertThat(room.getPersonX(), equalTo(TEST_PERSON_X));
        assertThat(room.getPersonY(), equalTo(TEST_PERSON_Y));
        assertThat(room.getPersonHeight(), equalTo(TEST_PERSON_HEIGHT));

    }

    private Cursor getTestCursor() {
        MatrixCursor cursor = new MatrixCursor(RoomContract.Rooms.ALL_COLUMNS);
        cursor.addRow(new String[]{
                String.valueOf(TEST_ID_1),
                TEST_NAME_1,
                String.valueOf(TEST_WIDTH_1),
                String.valueOf(TEST_LENGTH_1),
                String.valueOf(TEST_HEIGHT_1),
                String.valueOf(TEST_PERSON_X),
                String.valueOf(TEST_PERSON_Y),
                String.valueOf(TEST_PERSON_HEIGHT)
        });
        return cursor;
    }

}
