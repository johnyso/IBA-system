package de.ibs.app.overview;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import de.ibs.app.R;
import de.ibs.app.room.utils.RoomContract;

/**
 * Created by johnyso on 11.11.14.
 */
public class RoomListAdapter extends CursorAdapter {

    private LayoutInflater inflater;

    static class ViewHolder {
        public TextView name;
        public int id;
    }

    public RoomListAdapter(Context context, Cursor cursor, int i) {
        super(context, cursor, i);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View rowView = inflater.inflate(R.layout.room_overview_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.name = (TextView) rowView.findViewById(R.id.textView);
        rowView.setTag(viewHolder);

        return rowView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.name.setText(cursor.getString(cursor.getColumnIndex(RoomContract.Rooms.NAME)));
        holder.id = cursor.getInt(cursor.getColumnIndex(RoomContract.Rooms._ID));
    }
}
