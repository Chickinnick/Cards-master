package com.nlt.mobileteam.cards.sticker.stickerdemo.view;

import com.nlt.mobileteam.cards.sticker.stickerdemo.model.SavableView;

/**
 * Created by user on 28.09.2016.
 */

public interface BaseTextView {

    String getText();

    int getmBgColor();

    void setmBgColor(int color);

    void setTempColor(int color);

    void setText(String text);

    void restoreViewState(SavableView savableView);

}
