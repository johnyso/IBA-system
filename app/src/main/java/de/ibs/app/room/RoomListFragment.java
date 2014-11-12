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
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import de.ibs.app.AppContract;
import de.ibs.app.R;

import static de.ibs.app.room.RoomListAdapter.*;

/**
 * Created by johnyso on 11.11.14.
 */
public class RoomListFragment extends Fragment implements AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor>, Button.OnClickListener{

    private Context context;
    private RoomListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        Cursor cursor = this.context.getContentResolver().query(Uri.withAppendedPath(RoomContract.CONTENT_URI,RoomContract.ROOMS),null,null,null,null);
        this.adapter = new RoomListAdapter(context,cursor,0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.room_list,container,false);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(this);
        this.getLoaderManager().initLoader(0,null,this);
        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(getActivity(),
                Uri.withAppendedPath(RoomContract.CONTENT_URI,RoomContract.ROOMS),null,null,null,null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.setNotificationUri(getActivity().getContentResolver(),Uri.withAppendedPath(RoomContract.CONTENT_URI,RoomContract.ROOMS));
        adapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.changeCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO: get ID from viewHolder
        ViewHolder holder = (ViewHolder) view.getTag();
        Intent intent = new Intent(AppContract.BROADCAST_ACTION_ROOM);
        intent.putExtra(RoomContract.Rooms._ID, holder.id);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

    @Override
    public void onClick(View v) {
        Log.d("RoomListFragment", "OnClickListener Add new Room");
    }
}
