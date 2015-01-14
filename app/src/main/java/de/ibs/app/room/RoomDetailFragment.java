package de.ibs.app.room;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import de.ibs.app.AppContract;
import de.ibs.app.R;
import de.ibs.app.room.processor.Room;
import de.ibs.app.room.processor.RoomParser;
import de.ibs.app.room.utils.RoomContract;
import de.ibs.app.roomview.RoomView;
import de.ibs.app.speaker.SpeakerAcitvity;
import de.ibs.app.speaker.SpeakerConstants;
import de.ibs.app.speaker.processor.Speaker;
import de.ibs.app.speaker.processor.SpeakerParser;
import de.ibs.app.speaker.restmethod.SpeakerRequest;

import static de.ibs.app.room.utils.RoomContract.CONTENT_URI;
import static de.ibs.app.room.utils.RoomContract.ROOMS;


/**
 * Created by johnyso on 11.11.14.
 */
public class RoomDetailFragment extends Fragment implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    private Context context;
    private int currentId = 1;
    private RoomView roomView;
    private SeekBar seekBar;
    private Room room;
    private Speaker[] speakers;
    private LocalBroadcastManager localBroadcastManager;
    private RoomVerticalBroadcast roomVerticalBroadcast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        Cursor cursor = this.context.getContentResolver().query(Uri.withAppendedPath(RoomContract.CONTENT_URI, RoomContract.ROOMS + "-1/" + RoomContract.SPEAKERS), null, null, null, null);
        this.currentId = getArguments().getInt(RoomContract.Rooms._ID);
        this.localBroadcastManager = LocalBroadcastManager.getInstance(getActivity().getApplicationContext());
        this.roomVerticalBroadcast = new RoomVerticalBroadcast(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.room_detail_fragment, container, false);

        Cursor cursorRoom = getActivity().getContentResolver().query(Uri.withAppendedPath(RoomContract.CONTENT_URI, RoomContract.ROOMS + "-" + this.currentId), null, null, null, null);
        this.room = RoomParser.parseRoom(cursorRoom, 0);
        this.roomView = (RoomView) view.findViewById(R.id.roomView);
        this.roomView.setRoom(this.room);

        Cursor cursorSpeaker = getActivity().getContentResolver().query(Uri.withAppendedPath(RoomContract.CONTENT_URI, RoomContract.ROOMS + "-" + this.currentId + "/" + RoomContract.SPEAKERS), null, null, null, null);
        if (cursorSpeaker.getCount() > 0) {
            this.speakers = (SpeakerParser.parseSpeakers(cursorSpeaker));
            this.roomView.setSpeaker(this.speakers);
        }
        this.seekBar = (SeekBar) view.findViewById(R.id.roomHeightSeek);
        this.seekBar.setProgress(this.room.getPersonHeight());
        this.seekBar.setOnSeekBarChangeListener(this);

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.button_group);
        if (this.speakers != null) {
            for (Speaker speaker : this.speakers) {
                Button button = new Button(getActivity());
                button.setText("Wandhalterung " + speaker.getName());
                button.setTag(speaker.getId());
                button.setOnClickListener(this);
                layout.addView(button);
            }
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.localBroadcastManager.registerReceiver(this.roomVerticalBroadcast, new IntentFilter(AppContract.BROADCAST_UPDATE_VERTICAL));
    }

    @Override
    public void onPause() {
        super.onPause();
        this.localBroadcastManager.unregisterReceiver(this.roomVerticalBroadcast);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
//        Log.d("RoomDetail", "height: " + seekBar.getProgress());
        this.room.setPersonHeight(seekBar.getProgress());
        ContentValues values = new ContentValues();
        values.put(RoomContract.Rooms.PERSON_HEIGHT, seekBar.getProgress());
        context.getContentResolver().update(Uri.withAppendedPath(CONTENT_URI, ROOMS + "-" + this.room.getId()), values, null, null);
        updateVerticalAlignment();
    }

    public void updateVerticalAlignment() {
        if (this.speakers == null) {
            return;
        }
        float x = this.room.getPersonX() - this.room.getPaddingLeft();
        float y = this.room.getPersonY();
        ContentValues values = new ContentValues();

        int deg = 0;
        for (Speaker speaker : this.speakers) {
            double process = 100.0/(double) this.room.getPersonHeight();
            double height = speaker.getPositionHeight() / process;
            double distSpeaker = speaker.getPositionHeight() - height;

            switch (speaker.getAlignment()) {
                case RoomContract.Speakers.ALIGNMENT_TOP:
                    deg = (int) Math.toDegrees(Math.atan(distSpeaker / (this.room.getPersonY()-speaker.getPositionY())));
                    break;
                case RoomContract.Speakers.ALIGNMENT_RIGHT:
                    deg = (int) Math.toDegrees(Math.atan(distSpeaker / (speaker.getPositionX() - x) ));
                    break;
                case RoomContract.Speakers.ALIGNMENT_BOTTOM:
                    deg = (int) Math.toDegrees(Math.atan(distSpeaker / (speaker.getPositionY() - this.room.getPersonY())));
                    break;
                case RoomContract.Speakers.ALIGNMENT_LEFT:
                    deg = (int) Math.toDegrees(Math.atan(distSpeaker / (x - speaker.getPositionX())));
                    break;

            }
            Log.d("Positions: ", "Speaker: " + speaker.getAlignment() + " deg: " + deg);
            speaker.setHorizontal(deg);
            values.clear();
            values.put(RoomContract.Speakers.VERTICAL, deg);
            context.getContentResolver().update(RoomContract.getSpeakerPath(this.room.getId(), speaker.getId()), values, null, null);
            Intent intent = new Intent(context, SpeakerRequest.class);
            intent.putExtra(SpeakerConstants.REST_ID, AppContract.getRestPath(AppContract.VERTICAL, deg, speaker.getIp()));
            this.context.startService(intent);
        }
    }

    @Override
    public void onClick(View v) {
        int id = (Integer) v.getTag();
        Intent intent = new Intent(getActivity(),SpeakerAcitvity.class);
        intent.putExtra(RoomContract.Speakers._ID, id);
        intent.putExtra(RoomContract.Speakers.ROOM_ID, this.room.getId());
        startActivity(intent);
        //Broadcast change Fragment
    }
}
