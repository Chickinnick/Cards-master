package com.nlt.mobileteam.cards.sticker.stickerdemo.view;

/**
 * Created by user on 28.09.2016.
 */

public interface BaseTextView {
    void setInEdit(boolean inEdit);

    String getText();

    int getmBgColor();

    void setmBgColor(int color);

    void setTempColor(int color);

    void setText(String text);
}
