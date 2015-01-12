package de.ibs.app.room;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import de.ibs.app.R;
import de.ibs.app.room.processor.RoomParser;
import de.ibs.app.room.utils.RoomContract;
import de.ibs.app.roomview.RoomView;
import de.ibs.app.speaker.processor.SpeakerParser;


/**
 * Created by johnyso on 11.11.14.
 */
public class RoomDetailFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    private Context context;
    private int currentId = 1;
    private RoomView roomView;
    private SeekBar seekBar;

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

        this.roomView = (RoomView) view.findViewById(R.id.roomView);
        this.roomView.setRoom(RoomParser.parseRoom(cursorRoom,0));

        Cursor cursorSpeaker = getActivity().getContentResolver().query(Uri.withAppendedPath(RoomContract.CONTENT_URI, RoomContract.ROOMS + "-" + this.currentId +"/" + RoomContract.SPEAKERS), null, null, null, null);
        if(cursorSpeaker.getCount() > 0) {
            this.roomView.setSpeaker(SpeakerParser.parseSpeakers(cursorSpeaker));
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
//        Intent intent =  new Intent(context, SpeakerRequest.class);
//        //TODO: inplement new one
//        intent.putExtra(SpeakerConstants.REST_ID,"http://192.168.8.1:8080/index.php/vertical-" + seekBar.getProgress());
//        this.context.startService(intent);
    }
}
