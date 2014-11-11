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
    private RoomDetail roomDetail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.roomListReceiver = new RoomListReceiver(this);
        this.localBroadcastManager = LocalBroadcastManager.getInstance(getActivity().getApplicationContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        this.initializeFragments();
        this.showFragment();
        this.localBroadcastManager.registerReceiver(this.roomListReceiver, new IntentFilter(AppContract.BROADCAST_ACTION_ROOM));
    }

    @Override
    public void onPause() {
        super.onPause();
        this.tearDownFragments();
        this.localBroadcastManager.unregisterReceiver(this.roomListReceiver);
    }

    private void showFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.show(this.roomOverview).commit();
    }

    private void tearDownFragments() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.remove(this.roomOverview)
                .remove(this.roomDetail).commit();
    }

    private void initializeFragments() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (this.roomOverview == null) {
            this.roomOverview = new RoomOverview();
            transaction.add(R.id.fragment_container, this.roomOverview, AppContract.ROOM_OVERVIEW_FRAGMENT)
                    .hide(this.roomOverview);
        }
        if (this.roomDetail == null) {
            this.roomDetail = new RoomDetail();
            transaction.add(R.id.fragment_container, this.roomDetail, AppContract.ROOM_DETAIL_FRAGMENT)
                    .hide(this.roomDetail);
        }
        transaction.commit();
    }

    /**
     * ShowRoomDetail will change the Fragment from Overview to Detail
     * @param id int the Room id
     */
    public void showRoomDetail(int id) {
        this.roomDetail.resetList(id);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.hide(this.roomOverview)
                .show(this.roomDetail)
                .addToBackStack(AppContract.ROOM_DETAIL_BACKSTACK)
                .commit();
    }
}
