package com.nlt.mobileteam.cards.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.nlt.mobileteam.cards.R;

/**
 * Created by user on 23.08.2016.
 */

public class CardsViewPager extends ViewPager {
    public CardsViewPager(Context context) {
        super(context);
    }

    public CardsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean swipeable = true;


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return swipeable ? super.onInterceptTouchEvent(event) : false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return swipeable ? super.onTouchEvent(event) : false;
    }

    public boolean isSwipeable() {
        return swipeable;
    }

    public void setSwipeable(boolean swipeable) {
        Log.i("CardsViewPager" , "is swipable "  + swipeable );
        this.swipeable = swipeable;
    }
}
