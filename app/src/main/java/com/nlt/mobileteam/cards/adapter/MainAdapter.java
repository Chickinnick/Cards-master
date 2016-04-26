package com.nlt.mobileteam.cards.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nlt.mobileteam.cards.JazzyViewPager;
import com.nlt.mobileteam.cards.OutlineContainer;

public class MainAdapter extends PagerAdapter {
    Context context;
    private JazzyViewPager mJazzy;
    private ViewGroup savedViews[] = new ViewGroup[3];

    public MainAdapter() {
    }

    public MainAdapter(Context context) {
        this.context = context;
    }

    public MainAdapter(Context context, JazzyViewPager mJazzy) {
        this.context = context;
        this.mJazzy = mJazzy;
    }

    private int pos = 0;


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        TextView text = new TextView(context);
        text.setGravity(Gravity.CENTER);
        text.setTextSize(30);
        text.setTextColor(Color.WHITE);
        text.setText("Page " + position);
        text.setPadding(30, 30, 30, 30);
        int bg = Color.rgb((int) Math.floor(Math.random() * 128) + 64,
                (int) Math.floor(Math.random() * 128) + 64,
                (int) Math.floor(Math.random() * 128) + 64);
        text.setBackgroundColor(bg);

        mJazzy.setObjectForPosition(text, position);
        container.addView(text, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    /*    if (pos >= savedViews.length - 1)
            pos = 0;
        else
            ++pos;

        if (savedViews[pos] == null) {

            savedViews[pos] = container;
        } else {
            return savedViews[pos];
        }
*/
        return text;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object obj) {
        container.removeView(mJazzy.findViewFromObject(position));
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        if (view instanceof OutlineContainer) {
            return ((OutlineContainer) view).getChildAt(0) == obj;
        } else {
            return view == obj;
        }
    }
}