package com.nlt.mobileteam.cards.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.nlt.mobileteam.cards.R;

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
        rootView.requestFocus();
      /*  mCardLayout = (RelativeLa.yout) rootView.findViewById(R.id.card_front);*/

        return rootView;
    }
}
