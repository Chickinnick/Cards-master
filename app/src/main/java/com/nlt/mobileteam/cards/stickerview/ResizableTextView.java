package com.nlt.mobileteam.cards.stickerview;


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.nlt.mobileteam.cards.sticker.stickerdemo.model.SavableView;
import com.nlt.mobileteam.cards.sticker.stickerdemo.model.TextPropertyModel;
import com.nlt.mobileteam.cards.stickerview.util.AutoResizeTextView;

import java.util.ArrayList;


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
        if (tv_main != null) {
            Log.d(TAG, "obtained existing");
            return tv_main;
        }

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
        Log.d(TAG, "obtained new");

        return tv_main;
    }

    public void setText(String text) {
        if (tv_main != null) {
            tv_main.setText(text);
        }
    }

    @Override
    public void setInEdit(boolean inEdit) {
        this.isInEdit = inEdit;

        ArrayList<View> views = new ArrayList<View>();
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            final Object tagObj = child.getTag();
            if (tagObj != null && (
                    tagObj.equals(IV_BORDER_TAG) ||
                            tagObj.equals(IV_SCALE_TAG) ||
                            tagObj.equals(IV_DELETE_TAG)
            )) {
                views.add(child);
            }

            for (View view :
                    views) {
                if (!inEdit) {
                    view.setVisibility(GONE);
                } else {
                    view.setVisibility(VISIBLE);
                }
            }

//        this.iv_border.setTag("iv_border");
//        this.iv_scale.setTag("iv_scale");
//        this.iv_delete.setTag("iv_delete");
//        this.iv_flip.setTag("iv_flip");

            invalidate();
        }
    }

    public String getText() {
        if (tv_main != null)
            return tv_main.getText().toString();

        return null;
    }

    @Override
    public int getmBgColor() {
        return 0;
    }

    @Override
    public void setmBgColor(int color) {

    }

    @Override
    public void setTempColor(int color) {

    }

    @Override
    public TextPropertyModel saveViewState() {
        TextPropertyModel textPropertyModel = super.saveViewState();
        textPropertyModel.setText(tv_main.getText().toString());
        return textPropertyModel;
    }


    @Override
    public void restoreViewState(SavableView savableView) {
        super.restoreViewState(savableView);
        this.tv_main.setText(((TextPropertyModel) savableView).getText());
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
