package de.ibs.app.room;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import de.ibs.app.R;
import de.ibs.app.room.provider.SampleRoomGenerator;
import de.ibs.app.room.utils.RoomContract;
import de.ibs.app.speaker.NavDrawerItem;
import de.ibs.app.speaker.NavDrawerListAdapter;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import java.util.ArrayList;

public class RoomDetailActivity extends FragmentActivity {
    private int currentRoom = 1;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);
        Intent intent = getIntent();
        this.currentRoom = intent.getIntExtra(RoomContract.Rooms._ID, 0);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        displayView(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments

        RoomDetailFragment roomDetail = new RoomDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(RoomContract.Rooms._ID, this.currentRoom);
        roomDetail.setArguments(bundle);
        switch (position) {

            default:
                break;
        }

        if (roomDetail != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, roomDetail).commit();
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
}
