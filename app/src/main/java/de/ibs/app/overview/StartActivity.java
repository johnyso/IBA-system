package de.ibs.app.overview;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import de.ibs.app.AppContract;
import de.ibs.app.R;
import de.ibs.app.room.provider.SampleRoomGenerator;

public class StartActivity extends FragmentActivity {
    private RoomOverview roomOverview;
    private LocalBroadcastManager localBroadcastManager;
    private BroadcastReceiver addRoomReceiver;
    private RoomAddFragment addRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        this.localBroadcastManager = LocalBroadcastManager.getInstance(this);
        this.addRoomReceiver = new AddRoomReceiver(this);
        // TODO: Remove sample Room generator
        SampleRoomGenerator.createRooms(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeFragments();
        this.localBroadcastManager.registerReceiver(this.addRoomReceiver, new IntentFilter(AppContract.BROADCAST_ACTION_ADD_ROOM));
    }

    @Override
    protected void onPause() {
        super.onPause();
        tearDownFragments();
        this.localBroadcastManager.unregisterReceiver(this.addRoomReceiver);
    }

    private void tearDownFragments() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.remove(this.roomOverview);
    }

    /**
     * initialize all Framgents in Activity
     */
    private void initializeFragments() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (this.roomOverview == null) {
            this.roomOverview = new RoomOverview();
            transaction.add(R.id.fragment_container, this.roomOverview, AppContract.ROOM_OVERVIEW_FRAGMENT);
        }
        if (this.addRoom == null) {
            this.addRoom = new RoomAddFragment();
            transaction.add(R.id.fragment_container, this.addRoom, AppContract.ROOM_ADD_FRAGMENT).hide(this.addRoom);
        }
        transaction.commit();
    }
    
    public void AddRoomFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(this.roomOverview)
                .show(this.addRoom)
                .addToBackStack("")
                .commit();
    }
}
