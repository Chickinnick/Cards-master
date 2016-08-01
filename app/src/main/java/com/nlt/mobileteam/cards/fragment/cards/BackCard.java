package com.nlt.mobileteam.cards.fragment.cards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nlt.mobileteam.cards.R;
import com.nlt.mobileteam.cards.model.Card;
import com.nlt.mobileteam.cards.sticker.stickerdemo.model.BaseViewModel;
import com.nlt.mobileteam.cards.sticker.stickerdemo.model.BubblePropertyModel;
import com.nlt.mobileteam.cards.sticker.stickerdemo.model.StickerPropertyModel;

import java.util.List;

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
        Log.d("Back:", card.toString());
        onRestoreViews(card);

        view.findViewById(R.id.bg).setBackground(getActivity().getResources().getDrawable(R.drawable.card_rev));
        return view;
    }

    @Override
    public void onRestoreViews(Card card) {
        List<BaseViewModel> backArray = card.getBackSavedViewArray();

        if (null != backArray) {
            for (BaseViewModel bubblePropertyModel :
                    backArray) {
                if (bubblePropertyModel instanceof BubblePropertyModel) {
                    addTextView((BubblePropertyModel) bubblePropertyModel);
                } else if (bubblePropertyModel instanceof StickerPropertyModel) {
                    addStickerView((StickerPropertyModel) bubblePropertyModel);
                }
            }
        }
    }
}
