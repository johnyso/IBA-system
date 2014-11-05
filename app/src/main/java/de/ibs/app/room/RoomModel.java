package de.ibs.app.room;

import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import de.ibs.app.AppContract;
import de.ibs.app.R;

/**
 * Created by johnyso on 05.11.14.
 */
public class RoomModel extends Fragment {
    private RoomOverview roomOverview;

    @Override
    public void onResume() {
        super.onResume();
        this.initializeFragments();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.tearDownFragments();
    }

    private void tearDownFragments() {
        FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
        transaction.remove(this.roomOverview);
    }

    private void initializeFragments() {
        FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
        if (this.roomOverview == null){
            this.roomOverview = new RoomOverview();
            transaction.add(R.id.fragment_container,roomOverview, AppContract.ROOM_OVERVIEW_FRAGMENT).commit();
        }
    }
}
