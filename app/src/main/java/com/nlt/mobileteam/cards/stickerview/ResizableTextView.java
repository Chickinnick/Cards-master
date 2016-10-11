package com.nlt.mobileteam.cards.stickerview;


import android.content.Context;
import android.graphics.Color;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.nlt.mobileteam.cards.stickerview.util.AutoResizeTextView;


/**
 * Created by cheungchingai on 6/15/15.
 */
public class ResizableTextView extends ResizableView {
    private AutoResizeTextView tv_main;

    public ResizableTextView(Context context) {
        super(context);
    }

    public ResizableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ResizableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public View getMainView() {
        if (tv_main != null)
            return tv_main;

        tv_main = new AutoResizeTextView(getContext());
        //tv_main.setTextSize(22);
        tv_main.setTextColor(
                Color.WHITE);
        tv_main.setGravity(
                Gravity.CENTER);
        tv_main.setTextSize(400);
        tv_main.setShadowLayer(4, 0, 0, Color.BLACK);
        //    tv_main.setSingleLine(true);
        //tv_main.setMaxLines(1);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        tv_main.setLayoutParams(params);
        if (getImageViewFlip() != null)
            getImageViewFlip().setVisibility(View.GONE);
        return tv_main;
    }

    public void setText(String text) {
        if (tv_main != null) {
            tv_main.setText(text);
        }
    }

    public String getText() {
        if (tv_main != null)
            return tv_main.getText().toString();

        return null;
    }

    public void setLines(int lines) {
        if (tv_main != null) {
            tv_main.setLines(lines);
        }
    }


    public static float pixelsToSp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px / scaledDensity;
    }

    @Override
    protected void onScaling(boolean scaleUp) {
        super.onScaling(scaleUp);
    }

    public void incrementLines() {
        // if (tv_main != null) {
        //     int count = tv_main.getLineCount();
        //     tv_main.setLines(++count);
        // }
    }
}
