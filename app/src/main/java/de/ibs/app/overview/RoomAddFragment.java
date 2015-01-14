package de.ibs.app.overview;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import de.ibs.app.R;
import de.ibs.app.room.utils.RoomContract;

/**
 * Created by johnyso on 12.11.14.
 */
public class RoomAddFragment extends Fragment implements Button.OnClickListener{
    private Button button;
    private EditText height;
    private EditText width;
    private EditText length;
    private EditText name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.room_add_fragment, container, false);
        this.button = (Button) view.findViewById(R.id.button);
        this.height = (EditText) view.findViewById(R.id.editHeight);
        this.width = (EditText) view.findViewById(R.id.editWidth);
        this.length = (EditText) view.findViewById(R.id.editLength);
        this.name = (EditText) view.findViewById(R.id.editName);

        this.button.setOnClickListener(this);
        return view;

    }

    @Override
    public void onClick(View v) {
        if(this.height.getText().toString().matches("")
                || this.width.getText().toString().matches("")
                || this.length.getText().toString().matches("")
                || this.name.getText().toString().matches("")){
            Toast toast = Toast.makeText(getActivity(), "please Enter all Datas", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            ContentValues values = new ContentValues();
            values.put(RoomContract.Rooms.NAME, this.name.getText().toString());
            values.put(RoomContract.Rooms.HEIGHT, this.height.getText().toString());
            values.put(RoomContract.Rooms.WIDTH, this.width.getText().toString());
            values.put(RoomContract.Rooms.LENGTH, this.length.getText().toString());

            this.height.setText("");
            this.width.setText("");
            this.length.setText("");
            this.name.setText("");

            getActivity().getContentResolver().insert(Uri.withAppendedPath(RoomContract.CONTENT_URI,RoomContract.ROOMS),values);
            Toast toast = Toast.makeText(getActivity(), "Room added", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
