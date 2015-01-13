package de.ibs.app.room;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import de.ibs.app.R;
import de.ibs.app.room.processor.Room;
import de.ibs.app.room.processor.RoomParser;
import de.ibs.app.room.utils.RoomContract;
import de.ibs.app.roomview.RoomView;
import de.ibs.app.speaker.SpeakerConstants;
import de.ibs.app.speaker.processor.Speaker;
import de.ibs.app.speaker.processor.SpeakerParser;
import de.ibs.app.speaker.restmethod.SpeakerRequest;


/**
 * Created by johnyso on 11.11.14.
 */
public class RoomDetailFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    private Context context;
    private int currentId = 1;
    private RoomView roomView;
    private SeekBar seekBar;
    private Room room;
    private Speaker[] speakers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        Cursor cursor = this.context.getContentResolver().query(Uri.withAppendedPath(RoomContract.CONTENT_URI, RoomContract.ROOMS + "-1/" + RoomContract.SPEAKERS), null, null, null, null);
        this.currentId = getArguments().getInt(RoomContract.Rooms._ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.room_detail_fragment, container, false);

        Cursor cursorRoom = getActivity().getContentResolver().query(Uri.withAppendedPath(RoomContract.CONTENT_URI, RoomContract.ROOMS + "-" + this.currentId), null, null, null, null);
        this.room = RoomParser.parseRoom(cursorRoom, 0);
        this.roomView = (RoomView) view.findViewById(R.id.roomView);
        this.roomView.setRoom(this.room);

        Cursor cursorSpeaker = getActivity().getContentResolver().query(Uri.withAppendedPath(RoomContract.CONTENT_URI, RoomContract.ROOMS + "-" + this.currentId + "/" + RoomContract.SPEAKERS), null, null, null, null);
        this.speakers = (SpeakerParser.parseSpeakers(cursorSpeaker));
        if (cursorSpeaker.getCount() > 0) {
            this.roomView.setSpeaker(this.speakers);
        }
        this.seekBar = (SeekBar) view.findViewById(R.id.roomHeightSeek);
        this.seekBar.setOnSeekBarChangeListener(this);
        return view;
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
        float x = this.room.getPersonX() - this.room.getPaddingLeft();
        float y = this.room.getPersonY();

        Log.d("Positions: ", "SeekBar: " + seekBar.getProgress());

        int deg = 0;
        for (Speaker speaker : this.speakers) {
            double process = 100.0/(double) seekBar.getProgress();
            double height = speaker.getPositionHeight() / process;
            double distSpeaker = speaker.getPositionHeight() - height;

            switch (speaker.getAlignment()) {
                case RoomContract.Speakers.ALIGNMENT_TOP:
                    deg = (int) Math.toDegrees(Math.atan(distSpeaker / y));
                    break;
                case RoomContract.Speakers.ALIGNMENT_RIGHT:
                    deg = (int) Math.toDegrees(Math.atan(distSpeaker / x));
                    break;
                case RoomContract.Speakers.ALIGNMENT_BOTTOM:
                    deg = (int) Math.toDegrees(Math.atan(distSpeaker / y));
                    break;
                case RoomContract.Speakers.ALIGNMENT_LEFT:
                    deg = (int) Math.toDegrees(Math.atan(distSpeaker / x));
                    break;

            }
            Log.d("Positions: ", "Speaker: " + speaker.getAlignment() + " deg: " + deg);
        }


        Intent intent = new Intent(context, SpeakerRequest.class);
//        //TODO: inplement new one
        intent.putExtra(SpeakerConstants.REST_ID, "http://192.168.1.34:8080/index.php/vertical-" + seekBar.getProgress());
        //  this.context.startService(intent);
    }
}
