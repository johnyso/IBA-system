package de.ibs.app.overview;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import de.ibs.app.AppContract;
import de.ibs.app.R;
import de.ibs.app.room.utils.RoomContract;

/**
 * Created by johnyso on 12.11.14.
 */
public class RoomAddFragment extends Fragment implements Button.OnClickListener{
    private static final int SAVE = 1;
    private static final int ADD = 2;
    private Button button;
    private EditText height;
    private EditText width;
    private EditText length;
    private EditText name;
    private Button addSpeakerButton;
    private TextView id;
    private ListView listView;
    private SpeakerListAdapter adapter;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.room_add_fragment, container, false);
        this.button = (Button) view.findViewById(R.id.button);
        this.button.setTag(this.SAVE);
        this.height = (EditText) view.findViewById(R.id.editHeight);
        this.width = (EditText) view.findViewById(R.id.editWidth);
        this.length = (EditText) view.findViewById(R.id.editLength);
        this.name = (EditText) view.findViewById(R.id.editName);
        this.id = (TextView) view.findViewById(R.id.id);
        this.listView = (ListView) view.findViewById(R.id.listView);
        this.addSpeakerButton = (Button) view.findViewById(R.id.addSpeakerButton);
        this.addSpeakerButton.setTag(this.ADD);
        this.button.setOnClickListener(this);
        this.addSpeakerButton.setOnClickListener(this);
        return view;

    }

    @Override
    public void onClick(View v) {
        int tag = (Integer) v.getTag();
        if (tag == this.SAVE) {
            if (this.height.getText().toString().matches("")
                    || this.width.getText().toString().matches("")
                    || this.length.getText().toString().matches("")
                    || this.name.getText().toString().matches("")) {
                Toast toast = Toast.makeText(getActivity(), "please Enter all Datas", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                ContentValues values = new ContentValues();
                values.put(RoomContract.Rooms.NAME, this.name.getText().toString());
                values.put(RoomContract.Rooms.HEIGHT, this.height.getText().toString());
                values.put(RoomContract.Rooms.WIDTH, this.width.getText().toString());
                values.put(RoomContract.Rooms.LENGTH, this.length.getText().toString());
                Uri uri;
                if (this.id.getText().equals("")) {
                    uri = getActivity().getContentResolver().insert(Uri.withAppendedPath(RoomContract.CONTENT_URI, RoomContract.ROOMS), values);
                    setRoom(uri);
                    Toast toast = Toast.makeText(getActivity(), "Room added", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    int roomId = Integer.parseInt(this.id.getText().toString());
                    getActivity().getContentResolver().update(Uri.withAppendedPath(RoomContract.CONTENT_URI, RoomContract.ROOMS + "-" + roomId), values, null, null);
                    setRoom(roomId);
                    Toast toast = Toast.makeText(getActivity(), "Raumänderung gespeichert", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        } else if(tag == this.ADD){
            Intent intent = new Intent(AppContract.BROADCAST_ACTION_ADD_SPEAKER);
            LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
        }
    }

    public void setRoom(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri,null,null,null,null);
        int roomId = 0;
        if(cursor.moveToFirst()) {
            roomId = cursor.getInt(cursor.getColumnIndex(RoomContract.Rooms._ID));
            this.id.setText(Integer.toString(roomId));
            this.name.setText(cursor.getString(cursor.getColumnIndex(RoomContract.Rooms.NAME)));
            this.height.setText(cursor.getString(cursor.getColumnIndex(RoomContract.Rooms.HEIGHT)));
            this.width.setText(cursor.getString(cursor.getColumnIndex(RoomContract.Rooms.WIDTH)));
            this.length.setText(cursor.getString(cursor.getColumnIndex(RoomContract.Rooms.LENGTH)));
        }
        cursor.close();

        cursor = this.context.getContentResolver().query(RoomContract.getAllSpeakerPath(roomId),null,null,null,null);
        this.adapter = new SpeakerListAdapter(getActivity(),cursor,0);
        this.listView.setAdapter(this.adapter);
        cursor.close();

    }
    public void setRoom(int id) {
        Uri uri = Uri.withAppendedPath(RoomContract.CONTENT_URI, RoomContract.ROOMS + "-" + id);
        setRoom(uri);
    }

    public void resetRoom() {
        this.id.setText("");
        this.name.setText("");
        this.height.setText("");
        this.width.setText("");
        this.length.setText("");
    }
}
