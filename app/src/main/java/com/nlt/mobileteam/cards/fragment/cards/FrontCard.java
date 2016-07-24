package com.nlt.mobileteam.cards.fragment.cards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nlt.mobileteam.cards.model.Card;
import com.nlt.mobileteam.cards.sticker.stickerdemo.model.BubblePropertyModel;
import com.nlt.mobileteam.cards.sticker.stickerdemo.model.StickerPropertyModel;

import java.util.List;

/**
 * Created by Nick on 10.07.2016.
 */
public class FrontCard extends BaseCard {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        Bundle args = getArguments();
        Card card = args.getParcelable(CARD_INSTANCE);
        onRestoreViews(card);
        //textView.setText(card.getFrontText());
        return view;
    }

    @Override
    public void onRestoreViews(Card card) {

        List<StickerPropertyModel> frontImageArray = card.getFrontImageArray();
        List<BubblePropertyModel> frontTextArray = card.getFrontTextArray();
        if (frontImageArray != null) {
            for (StickerPropertyModel stickerPropertyModel :
                    frontImageArray) {
                addStickerView(stickerPropertyModel);
            }
        }

    }
}
