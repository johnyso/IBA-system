package de.ibs.app.room;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import de.ibs.app.AppContract;
import de.ibs.app.R;
import de.ibs.app.room.provider.SampleRoomGenerator;

public class StartActivity extends FragmentActivity {
    private RoomOverview roomOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);

        // TODO: Remove sample Room generator
        SampleRoomGenerator.createRooms(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeFragments();
    }

    @Override
    protected void onPause() {
        super.onPause();
        tearDownFragments();
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
        transaction.commit();
    }
}
