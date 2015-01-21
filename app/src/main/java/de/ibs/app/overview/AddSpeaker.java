package de.ibs.app.overview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import de.ibs.app.R;
import de.ibs.app.room.utils.RoomContract;
import de.ibs.app.speaker.processor.Speaker;
import de.ibs.app.speaker.processor.SpeakerParser;

import java.util.ResourceBundle;

public class AddSpeaker extends Fragment implements Button.OnClickListener {
    private Context context;
    private Button button;
    private EditText height;
    private EditText x;
    private EditText y;
    private EditText name;
    private EditText ip;
    private TextView id;
    private int speakerId = 0;
    private int roomId = 0;
    private RadioGroup radioGroup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.speaker_add_fragment, container, false);
        this.button = (Button) view.findViewById(R.id.button);
        this.height = (EditText) view.findViewById(R.id.editHeight);
        this.x = (EditText) view.findViewById(R.id.editWidth);
        this.y = (EditText) view.findViewById(R.id.editLength);
        this.name = (EditText) view.findViewById(R.id.editName);
        this.ip = (EditText) view.findViewById(R.id.editIp);
        this.id = (TextView) view.findViewById(R.id.id);
        this.radioGroup = (RadioGroup) view.findViewById(R.id.button_group);
        this.button.setOnClickListener(this);
        return view;

    }

    @Override
    public void onClick(View v) {
        if (this.height.getText().toString().matches("")
                || this.x.getText().toString().matches("")
                || this.y.getText().toString().matches("")
                || this.name.getText().toString().matches("")
                || this.ip.getText().toString().matches("")
                ) {
            Toast toast = Toast.makeText(getActivity(), "Bitte alle Daten angeben", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            int alignment = 0;
            int checkedId = this.radioGroup.getCheckedRadioButtonId();
            switch (checkedId){
                case R.id.radio_top:
                    alignment = RoomContract.Speakers.ALIGNMENT_TOP;
                    break;
                case R.id.radio_right:
                    alignment = RoomContract.Speakers.ALIGNMENT_RIGHT;
                    break;
                case R.id.radio_bottom:
                    alignment = RoomContract.Speakers.ALIGNMENT_BOTTOM;
                    break;
                case R.id.radio_left:
                    alignment = RoomContract.Speakers.ALIGNMENT_LEFT;
                    break;
            }
            ContentValues values = new ContentValues();
            values.put(RoomContract.Speakers.NAME, this.name.getText().toString());
            values.put(RoomContract.Speakers.POSITION_X, this.x.getText().toString());
            values.put(RoomContract.Speakers.POSITION_Y, this.y.getText().toString());
            values.put(RoomContract.Speakers.IP, this.ip.getText().toString());
            values.put(RoomContract.Speakers.POSITION_HEIGHT, this.height.getText().toString());
            values.put(RoomContract.Speakers.ALIGNMENT, alignment);

            Uri uri;
            if (this.id.getText().equals("")) {
                uri = getActivity().getContentResolver().insert(RoomContract.getAllSpeakerPath(this.roomId), values);
                Toast toast = Toast.makeText(getActivity(), "Wandhalterung hinzugefügt", Toast.LENGTH_SHORT);
                toast.show();
                initSpeaker(uri);
            } else {
                getActivity().getContentResolver().update(RoomContract.getSpeakerPath(this.roomId, this.speakerId), values, null, null);
                Toast toast = Toast.makeText(getActivity(), "Wandhalterung Änderung gespeichert", Toast.LENGTH_SHORT);
                toast.show();
            }

        }
    }

    public void init(int roomId, int speakerId) {
        this.roomId = roomId;
        this.speakerId = speakerId;
        if (speakerId != 0) {
            this.id.setText(Integer.toString(speakerId));
            initSpeaker();
        } else {
            reset();
        }
    }

    private void reset() {
        this.name.setText("");
        this.ip.setText("");
        this.x.setText("");
        this.y.setText("");
        this.height.setText("");
    }

    private void initSpeaker() {
        Uri uri = RoomContract.getSpeakerPath(this.roomId, this.speakerId);
        initSpeaker(uri);
    }

    private void initSpeaker(Uri uri) {
        Cursor cursor = this.getActivity().getContentResolver().query(uri, null, null, null, null);
        if (cursor.moveToFirst()) {
            this.name.setText(cursor.getString(cursor.getColumnIndex(RoomContract.Speakers.NAME)));
            this.ip.setText(cursor.getString(cursor.getColumnIndex(RoomContract.Speakers.IP)));
            this.x.setText(cursor.getString(cursor.getColumnIndex(RoomContract.Speakers.POSITION_X)));
            this.y.setText(cursor.getString(cursor.getColumnIndex(RoomContract.Speakers.POSITION_Y)));
            this.height.setText(cursor.getString(cursor.getColumnIndex(RoomContract.Speakers.POSITION_HEIGHT)));
        }
    }
}
