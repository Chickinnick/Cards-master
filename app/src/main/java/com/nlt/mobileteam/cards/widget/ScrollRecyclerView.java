package com.nlt.mobileteam.cards.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Nick on 05.06.2016.
 */
public class ScrollRecyclerView extends RecyclerView {
    public ScrollRecyclerView(Context context) {
        super(context);
    }

    public ScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public boolean fling(int velocityX, int velocityY) {
        velocityY *= 0.7;
        return super.fling(velocityX, velocityY);
    }
}
