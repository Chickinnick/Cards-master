package com.nlt.mobileteam.cards.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nlt.mobileteam.cards.JazzyViewPager;
import com.nlt.mobileteam.cards.OutlineContainer;
import com.nlt.mobileteam.cards.R;

public class CardsAdapter extends PagerAdapter {

    Context context;
    private JazzyViewPager mJazzy;

    public CardsAdapter(Context context) {
        this.context = context;
    }

    private void setupJazziness(JazzyViewPager.TransitionEffect effect) {
        mJazzy = (JazzyViewPager) ((Activity) context).findViewById(R.id.jazzy_pager);
        mJazzy.setTransitionEffect(effect);
        mJazzy.setAdapter(new MainAdapter(context, mJazzy));
        // mJazzy.setPageMargin(30);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View page = inflater.inflate(R.layout.card_item, container, false);
        mJazzy = (JazzyViewPager) page.findViewById(R.id.jazzy_pager_item);
        mJazzy.setTransitionEffect(JazzyViewPager.TransitionEffect.Stack);
        mJazzy.setAdapter(new MainAdapter(context, mJazzy));
        // mJazzy.setPageMargin(30);
        container.addView(mJazzy, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        mJazzy.setObjectForPosition(page, position);
        return page;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object obj) {
        container.removeView(mJazzy.findViewFromObject(position));
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
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