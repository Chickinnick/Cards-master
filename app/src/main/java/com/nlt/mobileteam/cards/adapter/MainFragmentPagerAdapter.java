package com.nlt.mobileteam.cards.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.nlt.mobileteam.cards.activity.PlaceholderFragment;
import com.nlt.mobileteam.cards.model.Card;

import java.util.ArrayList;

/**
 * Created by user on 28.04.2016.
 */
public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Card> cards;
    private int size;


    public MainFragmentPagerAdapter(FragmentManager fm, ArrayList<Card> cards) {
        super(fm);
        this.cards = cards;
    }


    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position + 1, cards.get(position));
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return size;
    }

    private Fragment mCurrentFragment;

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            mCurrentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void removeCard(int currentItem) {
        cards.remove(currentItem);
        size = cards.size();
        notifyDataSetChanged();
    }


}
