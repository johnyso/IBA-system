package de.ibs.app.overview;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import de.ibs.app.R;
import de.ibs.app.room.utils.RoomContract;

public class AddSpeaker extends Fragment implements Button.OnClickListener {
    private Context context;
    private Button button;
    private EditText height;
    private EditText width;
    private EditText length;
    private EditText name;
    private EditText ip;
    private TextView id;

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
        this.width = (EditText) view.findViewById(R.id.editWidth);
        this.length = (EditText) view.findViewById(R.id.editLength);
        this.name = (EditText) view.findViewById(R.id.editName);
        this.ip = (EditText) view.findViewById(R.id.editIp);
        this.id = (TextView) view.findViewById(R.id.id);
        this.button.setOnClickListener(this);
        return view;

    }

    @Override
    public void onClick(View v) {
        if (this.height.getText().toString().matches("")
                || this.width.getText().toString().matches("")
                || this.length.getText().toString().matches("")
                || this.name.getText().toString().matches("")
                || this.ip.getText().toString().matches("")
                ) {
            Toast toast = Toast.makeText(getActivity(), "please Enter all Datas", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            ContentValues values = new ContentValues();
            values.put(RoomContract.Rooms.NAME, this.name.getText().toString());
            Uri uri;
            if (this.id.getText().equals("")) {
                //  uri = getActivity().getContentResolver().insert(Uri.withAppendedPath(RoomContract.CONTENT_URI, RoomContract.ROOMS), values);
                Toast toast = Toast.makeText(getActivity(), "Room added", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                // int roomId = Integer.parseInt(this.id.getText().toString());
                // getActivity().getContentResolver().update(Uri.withAppendedPath(RoomContract.CONTENT_URI, RoomContract.ROOMS + "-" + roomId), values, null, null);

                Toast toast = Toast.makeText(getActivity(), "Raumänderung gespeichert", Toast.LENGTH_SHORT);
                toast.show();
            }

        }
    }
}
