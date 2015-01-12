package de.ibs.app.overview;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import de.ibs.app.R;

/**
 * Created by johnyso on 11.11.14.
 */
public class RoomOverviewAdapter extends FragmentStatePagerAdapter{
    private static final int MAX_COUNT = 2;
    private final Context context;

    public RoomOverviewAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new RoomListFragment();
        } else {
            return new FavoriteListFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return context.getString(R.string.ibstheme_tab_room_overview);
        } else {
            return context.getString(R.string.ibstheme_tab_room_favorit);
        }
    }

    @Override
    public int getCount() {
        return MAX_COUNT;
    }
}
