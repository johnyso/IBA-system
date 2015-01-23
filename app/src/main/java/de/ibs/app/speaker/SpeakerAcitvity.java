package de.ibs.app.speaker;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import de.ibs.app.AppContract;
import de.ibs.app.R;
import de.ibs.app.room.utils.RoomContract;
import de.ibs.app.speaker.processor.SpeakerParser;
import de.ibs.app.speaker.restmethod.SpeakerRequest;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          

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

        ImageButton buttonUp = (ImageButton) findViewById(R.id.button_up);
        buttonUp.setOnClickListener(this);

        ImageButton buttonRight = (ImageButton) findViewById(R.id.button_right);
        buttonRight.setOnClickListener(this);

        ImageButton buttonDown = (ImageButton) findViewById(R.id.button_down);
        buttonDown.setOnClickListener(this);

        ImageButton buttonLeft = (ImageButton) findViewById(R.id.button_left);
        buttonLeft.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        int stepsH = 10;
        int stepsV = 2;
        int nVertical = 0;
        int nHorizontal = 0;
        int id = v.getId();
        ContentValues values = new ContentValues();
        Intent intent = new Intent(this, SpeakerRequest.class);
        switch (id){
            case R.id.button_up:
                nVertical = this.vertical + stepsV;
                this.vertical = nVertical;
                values.put(RoomContract.Speakers.VERTICAL, nVertical);
                intent.putExtra(SpeakerConstants.REST_ID, AppContract.getRestPath(AppContract.VERTICAL, nVertical, this.ip));
                break;
            case R.id.button_right:
                nHorizontal = this.horizontal + stepsH;
                this.horizontal = nHorizontal;
                intent.putExtra(SpeakerConstants.REST_ID, AppContract.getRestPath(AppContract.HORIZONTAL, nHorizontal, this.ip));
                values.put(RoomContract.Speakers.HORIZONTAL, nHorizontal);
                break;
            case R.id.button_down:
                nVertical = this.vertical - stepsV;
                this.vertical = nVertical;
                values.put(RoomContract.Speakers.VERTICAL, nVertical);
                intent.putExtra(SpeakerConstants.REST_ID, AppContract.getRestPath(AppContract.VERTICAL, nVertical, this.ip));
                break;
            case R.id.button_left:
                nHorizontal = this.horizontal - stepsH;
                this.horizontal = nHorizontal;
                intent.putExtra(SpeakerConstants.REST_ID, AppContract.getRestPath(AppContract.HORIZONTAL, nHorizontal, this.ip));
                values.put(RoomContract.Speakers.HORIZONTAL, nHorizontal);
                break;
        }
        getContentResolver().update(RoomContract.getSpeakerPath(this.roomId, this.id), values, null, null);
        this.startService(intent);
    }
}
