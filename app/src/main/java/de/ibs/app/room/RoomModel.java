package de.ibs.app.room;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.ibs.app.AppContract;
import de.ibs.app.R;

/**
 * Created by johnyso on 05.11.14.
 */
public class RoomModel extends Fragment {
    private RoomOverview roomOverview;
    private LocalBroadcastManager localBroadcastManager;
    private RoomListReceiver roomListReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.roomListReceiver = new RoomListReceiver();
        this.localBroadcastManager = LocalBroadcastManager.getInstance(getActivity().getApplicationContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        this.initializeFragments();
        this.localBroadcastManager.registerReceiver(this.roomListReceiver, new IntentFilter(AppContract.BROADCAST_ACTION_ROOM));
    }

    @Override
    public void onPause() {
        super.onPause();
        this.tearDownFragments();
        this.localBroadcastManager.unregisterReceiver(this.roomListReceiver);
    }

    private void tearDownFragments() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.remove(this.roomOverview).commit();
    }

    private void initializeFragments() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (this.roomOverview == null) {
            this.roomOverview = new RoomOverview();
            transaction.add(R.id.fragment_container, this.roomOverview, AppContract.ROOM_OVERVIEW_FRAGMENT).commit();
        }
    }
}
