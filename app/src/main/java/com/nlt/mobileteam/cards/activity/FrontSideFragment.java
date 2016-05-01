package com.nlt.mobileteam.cards.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nlt.mobileteam.cards.R;
import com.nlt.mobileteam.cards.model.Card;

/**
 * Created by Nick on 28.04.2016.
 */
public class FrontSideFragment extends Fragment {

    private static final String CARD_KEY = "card";

    private RelativeLayout mCardLayout;

    Card card;
    private TextView editText;

    public FrontSideFragment() {
    }

    public static FrontSideFragment newInstance(Card card) {

        FrontSideFragment fragment = new FrontSideFragment();
        Bundle args = new Bundle();
        args.putParcelable(CARD_KEY, card);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        card = getArguments().getParcelable(CARD_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.card_front, container, false);
        rootView.requestFocus();
        editText = (TextView) rootView.findViewById(R.id.edittext);
        editText.setText(card.getFrontText());
        return rootView;
    }
}
