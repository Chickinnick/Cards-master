package com.nlt.mobileteam.cards.fragment.cards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nlt.mobileteam.cards.R;
import com.nlt.mobileteam.cards.model.Card;

/**
 * Created by Nick on 10.07.2016.
 */
public class BackCard extends BaseCard {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        Bundle args = getArguments();
        Card card = args.getParcelable(CARD_INSTANCE);
        onRestoreViews(card);

        view.findViewById(R.id.bg).setBackground(getActivity().getResources().getDrawable(R.drawable.card_rev));
        return view;
    }

    @Override
    public void onRestoreViews(Card card) {

    }
}
