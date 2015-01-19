package de.ibs.app.overview;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import de.ibs.app.AppContract;
import de.ibs.app.R;
import de.ibs.app.room.utils.RoomContract;

/**
 * Created by johnyso on 11.11.14.
 */
public class RoomListAdapter extends CursorAdapter implements View.OnClickListener {
    private LayoutInflater inflater;
    private Context context;

    static class ViewHolder {
        public TextView name;
        public int id;
        public TextView height;
        public TextView width;
        public TextView length;
        public ImageButton imageButton;
    }

    public RoomListAdapter(Context context, Cursor cursor, int i) {
        super(context, cursor, i);
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View rowView = inflater.inflate(R.layout.room_overview_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.name = (TextView) rowView.findViewById(R.id.textView);
        viewHolder.height = (TextView) rowView.findViewById(R.id.textViewHeightVariable);
        viewHolder.width = (TextView) rowView.findViewById(R.id.textViewWidthVariable);
        viewHolder.length = (TextView) rowView.findViewById(R.id.textViewLengthVariable);
        viewHolder.imageButton = (ImageButton) rowView.findViewById(R.id.image_button);
        rowView.setTag(viewHolder);

        return rowView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.name.setText(cursor.getString(cursor.getColumnIndex(RoomContract.Rooms.NAME)));
        holder.id = cursor.getInt(cursor.getColumnIndex(RoomContract.Rooms._ID));
        holder.length.setText(cursor.getString(cursor.getColumnIndex(RoomContract.Rooms.LENGTH)));
        holder.width.setText(cursor.getString(cursor.getColumnIndex(RoomContract.Rooms.WIDTH)));
        holder.height.setText(cursor.getString(cursor.getColumnIndex(RoomContract.Rooms.HEIGHT)));
        holder.imageButton.setTag(cursor.getString(cursor.getColumnIndex(RoomContract.Rooms._ID)));
        holder.imageButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = Integer.parseInt((String) v.getTag());
        Intent intent = new Intent(AppContract.BROADCAST_ACTION_ROOM_SETTING);
        intent.putExtra(RoomContract.Rooms._ID,id);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
