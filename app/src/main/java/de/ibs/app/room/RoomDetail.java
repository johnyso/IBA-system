package de.ibs.app.room;

import android.content.Context;
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
import de.ibs.app.R;

/**
 * Created by johnyso on 11.11.14.
 */
public class RoomDetail extends Fragment implements AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    private Context context;
    private RoomDetailAdapter adapter;

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
        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(getActivity(),
                Uri.withAppendedPath(RoomContract.CONTENT_URI, RoomContract.ROOMS + "-1/" + RoomContract.SPEAKERS), null, null, null, null);
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
        Log.d("RoomDetail", "OnClick " + position);
    }
}
