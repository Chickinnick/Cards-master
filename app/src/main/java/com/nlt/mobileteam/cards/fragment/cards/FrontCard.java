package com.nlt.mobileteam.cards.fragment.cards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nlt.mobileteam.cards.model.Card;
import com.nlt.mobileteam.cards.sticker.stickerdemo.model.BubblePropertyModel;
import com.nlt.mobileteam.cards.sticker.stickerdemo.model.SavableView;
import com.nlt.mobileteam.cards.sticker.stickerdemo.model.StickerPropertyModel;

import java.util.List;

/**
 * Created by Nick on 10.07.2016.
 */
public class FrontCard extends BaseCard {


    public static final String SAVED_ARRAY_KEY = "saved_v_array_key";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        Bundle args = getArguments();
        Card card = args.getParcelable(CARD_INSTANCE);
        Log.d("Front:", card.toString());

        //  Log.d("Front:",  card.toString());

        onRestoreViews(card);
        //textView.setText(card.getFrontText());
        return view;
    }

    @Override
    public void onRestoreViews(Card card) {

        List<SavableView> frontArray = card.getFrontSavedViewArray();

        if (null != frontArray) {
            for (SavableView bubblePropertyModel :
                    frontArray) {
                if (bubblePropertyModel instanceof BubblePropertyModel) {
                    addTextView((BubblePropertyModel) bubblePropertyModel);
                } else if (bubblePropertyModel instanceof StickerPropertyModel) {
                    addStickerView((StickerPropertyModel) bubblePropertyModel);
                }
            }
        }


    }
}
