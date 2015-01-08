package de.ibs.app.room;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SeekBar;
import de.ibs.app.AppContract;
import de.ibs.app.R;
import de.ibs.app.room.processor.RoomParser;
import de.ibs.app.roomview.RoomView;
import de.ibs.app.speaker.SpeakerAcitvity;
import de.ibs.app.speaker.SpeakerConstants;
import de.ibs.app.speaker.processor.SpeakerParser;
import de.ibs.app.speaker.restmethod.SpeakerRequest;

import static de.ibs.app.room.RoomDetailAdapter.ViewHolder;

/**
 * Created by johnyso on 11.11.14.
 */
public class RoomDetail extends Fragment implements AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor>, SeekBar.OnSeekBarChangeListener {
    private Context context;
    private RoomDetailAdapter adapter;
    private int currentId = 0;
    private RoomView roomView;
    private SeekBar seekBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        Cursor cursor = this.context.getContentResolver().query(Uri.withAppendedPath(RoomContract.CONTENT_URI, RoomContract.ROOMS + "-1/" + RoomContract.SPEAKERS), null, null, null, null);
        this.adapter = new RoomDetailAdapter(getActivity(), cursor, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.room_detail_fragment, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(this.adapter);
        listView.setOnItemClickListener(this);
        this.getLoaderManager().initLoader(0, null, this);
        this.roomView = (RoomView) view.findViewById(R.id.roomView);
        this.seekBar = (SeekBar) view.findViewById(R.id.roomHeightSeek);
        this.seekBar.setOnSeekBarChangeListener(this);
        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(getActivity(),
                Uri.withAppendedPath(RoomContract.CONTENT_URI, RoomContract.ROOMS + "-" + this.currentId + "/" + RoomContract.SPEAKERS), null, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.setNotificationUri(getActivity().getContentResolver(), Uri.withAppendedPath(RoomContract.CONTENT_URI, RoomContract.ROOMS + "-1/" + RoomContract.SPEAKERS));
        adapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.changeCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ViewHolder holder = (ViewHolder) view.getTag();
        Intent intent = new Intent(getActivity(), SpeakerAcitvity.class);
        intent.putExtra(RoomContract.Speakers._ID, holder.id);
        startActivity(intent);
    }

    public void resetList(int id) {
        Cursor cursor = getActivity().getContentResolver().query(Uri.withAppendedPath(RoomContract.CONTENT_URI, RoomContract.ROOMS + "-" + id +"/" + RoomContract.SPEAKERS), null, null, null, null);
        this.currentId = id;
        Cursor cursorRoom = getActivity().getContentResolver().query(Uri.withAppendedPath(RoomContract.CONTENT_URI, RoomContract.ROOMS + "-" + id), null, null, null, null);

        if(cursorRoom.moveToFirst()) {
            this.roomView.setRoom(RoomParser.parseRoom(cursorRoom,0));
        }

        if(cursor.moveToFirst()){
            this.roomView.setSpeaker(SpeakerParser.parseSpeakers(cursor));
        } else {
            this.roomView.setSpeaker(null);
        }

        adapter.swapCursor(cursor);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.d("RoomDetail", "height: " + seekBar.getProgress());
        Intent intent =  new Intent(context, SpeakerRequest.class);
        //TODO: inplement new one
        intent.putExtra(SpeakerConstants.REST_ID, AppContract.getRestPath(AppContract.VERTICAL, seekBar.getProgress(), "http://192.168.8.1/index.php/vertical-"));
        this.context.startService(intent);
    }
}
