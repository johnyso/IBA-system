package de.ibs.app.speaker.processor;

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
 * Created by johnyso on 26.11.14.
 */

@Config(manifest = "app/src/main/AndroidManifest.xml")
@RunWith(RobolectricTestRunner.class)
public class SpeakerParserTest {
    private int TEST_ID_1 = 1;
    private String TEST_NAME_1 = "sp1";
    private String TEST_IP_1 = "192.168.0.10";
    private int TEST_POSITION_X_1 = 150;
    private int TEST_POSITION_Y_1 = 100;
    private int TEST_POSITION_HEIGHT_1 = 10;
    private int TEST_HORIZONTAL_1 = 100;
    private int TEST_VERTICAL_1 = 10;
    private int TEST_ROOM_ID_1 = 1;

    private int TEST_ID_2 = 1;
    private String TEST_NAME_2 = "sp1";
    private String TEST_IP_2 = "192.168.0.10";
    private int TEST_POSITION_X_2 = 150;
    private int TEST_POSITION_Y_2 = 100;
    private int TEST_POSITION_HEIGHT_2 = 10;
    private int TEST_HORIZONTAL_2 = 100;
    private int TEST_VERTICAL_2 = 10;
    private int TEST_ROOM_ID_2 = 1;


    @Test
    public void testParserCursor(){
        Speaker[] speakers = SpeakerParser.parseSpeakers(getCursor());
        assertThat(speakers.length, equalTo(2));
        assertThat(speakers[0].getId(), equalTo(TEST_ID_1));
        assertThat(speakers[0].getIp(), equalTo(TEST_IP_1));
        assertThat(speakers[0].getName(), equalTo(TEST_NAME_1));
        assertThat(speakers[0].getPositionX(), equalTo(TEST_POSITION_X_1));
        assertThat(speakers[0].getPositionY(), equalTo(TEST_POSITION_Y_1));
        assertThat(speakers[0].getPositionHeight(), equalTo(TEST_POSITION_HEIGHT_1));
        assertThat(speakers[0].getVertical(), equalTo(TEST_VERTICAL_1));
        assertThat(speakers[0].getHorizontal(), equalTo(TEST_HORIZONTAL_1));
        assertThat(speakers[0].getRoomId(), equalTo(TEST_ROOM_ID_1));
    }

    private Cursor getCursor() {
        MatrixCursor cursor = new MatrixCursor(RoomContract.Speakers.ALL_COLUMNS);
        cursor.addRow(new String[]{
                String.valueOf(TEST_ID_1),
                TEST_IP_1,
                TEST_NAME_1,
                String.valueOf(TEST_POSITION_X_1),
                String.valueOf(TEST_POSITION_Y_1),
                String.valueOf(TEST_POSITION_HEIGHT_1),
                String.valueOf(TEST_HORIZONTAL_1),
                String.valueOf(TEST_VERTICAL_1),
                String.valueOf(TEST_ROOM_ID_1)
        });
        cursor.addRow(new String[]{
                String.valueOf(TEST_ID_2),
                TEST_IP_2,
                TEST_NAME_2,
                String.valueOf(TEST_POSITION_X_2),
                String.valueOf(TEST_POSITION_Y_2),
                String.valueOf(TEST_POSITION_HEIGHT_2),
                String.valueOf(TEST_HORIZONTAL_2),
                String.valueOf(TEST_VERTICAL_2),
                String.valueOf(TEST_ROOM_ID_2)
        });
        return cursor;
    }
}
