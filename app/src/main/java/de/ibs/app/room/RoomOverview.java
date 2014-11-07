package de.ibs.app.room;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.ibs.app.R;

/**
 * Created by johnyso on 05.11.14.
 */
public class RoomOverview extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.room_overview_fragment, container, false);
    }

}
