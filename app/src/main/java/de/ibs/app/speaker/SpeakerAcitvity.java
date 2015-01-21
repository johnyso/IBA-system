package de.ibs.app.speaker;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import de.ibs.app.AppContract;
import de.ibs.app.R;
import de.ibs.app.room.utils.RoomContract;
import de.ibs.app.speaker.processor.SpeakerParser;

public class SpeakerAcitvity extends Activity implements View.OnClickListener {

    private static final int BUTTON_UP = 1;
    private static final int BUTTON_RIGTH = 2;
    private static final int BUTTON_DOWN = 3;
    private static final int BUTTON_LEFT = 4;
    private int id;
    private String ip;
    private int vertical;
    private int horizontal;
    private int roomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker_acitvity);
        Intent intent = getIntent();
        this.id = intent.getIntExtra(RoomContract.Speakers._ID, 0);
        this.roomId = intent.getIntExtra(RoomContract.Speakers.ROOM_ID, 0);

        Cursor cursor = this.getContentResolver().query(Uri.withAppendedPath(RoomContract.CONTENT_URI, RoomContract.ROOMS + "-" + this.roomId + "/" + RoomContract.SPEAKERS + "-" + id), null, null, null, null);
        if (cursor.moveToFirst()) {
            this.ip = cursor.getString(cursor.getColumnIndex(RoomContract.Speakers.IP));
            this.vertical = cursor.getInt(cursor.getColumnIndex(RoomContract.Speakers.VERTICAL));
            this.horizontal = cursor.getInt(cursor.getColumnIndex(RoomContract.Speakers.HORIZONTAL));
        }

        String vPath = AppContract.getRestPath(AppContract.VERTICAL, this.vertical, this.ip);
        String hPath = AppContract.getRestPath(AppContract.HORIZONTAL, this.horizontal, this.ip);

        Button buttonUp = (Button) findViewById(R.id.button_up);
        buttonUp.setTag(this.BUTTON_UP);
        buttonUp.setOnClickListener(this);

        Button buttonRight = (Button) findViewById(R.id.button_right);
        buttonUp.setTag(this.BUTTON_RIGTH);
        buttonRight.setOnClickListener(this);

        Button buttonDown = (Button) findViewById(R.id.button_down);
        buttonUp.setTag(this.BUTTON_DOWN);
        buttonDown.setOnClickListener(this);

        Button buttonLeft = (Button) findViewById(R.id.button_left);
        buttonUp.setTag(this.BUTTON_LEFT);
        buttonLeft.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        int direction = (Integer) v.getTag();
        int nVertical;
        int nHorizontal;
        switch (direction){
            case 1:
                nVertical = this.vertical + 5;
                break;
            case 2:
                nHorizontal = this.horizontal + 5;
                break;
            case 3:
                nVertical = this.vertical - 5;
                break;
            case 4:
                nHorizontal = this.horizontal - 5;
                break;

        }
    }
}
