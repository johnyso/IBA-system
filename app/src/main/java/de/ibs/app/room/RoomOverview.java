package de.ibs.app.room;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.viewpagerindicator.TabPageIndicator;
import de.ibs.app.R;

/**
 * Created by johnyso on 05.11.14.
 */
public class RoomOverview extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.room_overview_fragment, container, false);

        RoomOverviewAdapter adapter = new RoomOverviewAdapter(getFragmentManager(),getActivity());

        ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
        indicator.setViewPager(pager);

        return view;
    }

}
