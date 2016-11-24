package com.nlt.mobileteam.cards.widget;

import android.view.View;

import com.nlt.mobileteam.cards.sticker.stickerdemo.view.BaseTextView;
import com.nlt.mobileteam.cards.sticker.stickerdemo.view.BaseView;

public interface OperationListener {
    void onDeleteClick();

    void onEdit(BaseView mView);

    void onClick(BaseView mView);

    void onTop(BaseView mView);

    void onDoubleTap(String mStr);

    void onEditStart(String currentText);
}