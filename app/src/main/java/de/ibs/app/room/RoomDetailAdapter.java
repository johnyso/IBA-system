package de.ibs.app.room;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.ibs.app.R;

/**
 * Created by johnyso on 11.11.14.
 */
public class RoomDetailAdapter extends CursorAdapter {
    private LayoutInflater inflater;

    public RoomDetailAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder {
        public TextView name;
        public int id;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View rowView = inflater.inflate(R.layout.room_detail_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.name = (TextView) rowView.findViewById(R.id.textView);
        rowView.setTag(viewHolder);

        return rowView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.name.setText(cursor.getString(cursor.getColumnIndex(RoomContract.Speakers.IP)));
        holder.id = cursor.getInt(cursor.getColumnIndex(RoomContract.Speakers._ID));
    }
}
