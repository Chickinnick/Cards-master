package com.nlt.mobileteam.cards.widget;

import com.nlt.mobileteam.cards.sticker.stickerdemo.view.BaseTextView;

public interface OperationListener {
    void onDeleteClick();

    void onEdit(BaseTextView bubbleTextView);

    void onClick(BaseTextView bubbleTextView);

    void onTop(BaseTextView bubbleTextView);

    void onDoubleTap(String mStr);

    void onEditStart(String currentText);
}