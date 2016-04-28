package com.nlt.mobileteam.cards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by Nick on 28.04.2016.
 */
public class FrontSideFragment extends Fragment {

    private RelativeLayout mCardLayout;

    public FrontSideFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.card_front, container, false);
      /*  mCardLayout = (RelativeLayout) rootView.findViewById(R.id.card_front);*/

        return rootView;
    }
}
