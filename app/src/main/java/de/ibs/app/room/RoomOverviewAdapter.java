package de.ibs.app.room;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by johnyso on 11.11.14.
 */
public class RoomOverviewAdapter extends FragmentStatePagerAdapter{
    private static final int MAX_COUNT = 2;

    public RoomOverviewAdapter(FragmentManager fm, Context context) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new RoomListFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Test "+position;
    }

    @Override
    public int getCount() {
        return MAX_COUNT;
    }
}
