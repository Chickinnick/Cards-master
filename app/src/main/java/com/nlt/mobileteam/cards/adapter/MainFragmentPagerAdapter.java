package com.nlt.mobileteam.cards.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.nlt.mobileteam.cards.fragment.PlaceholderFragment;
import com.nlt.mobileteam.cards.model.Card;

import java.util.ArrayList;

/**
 * Created by user on 28.04.2016.
 */
public class MainFragmentPagerAdapter extends FixedFragmentStatePagerAdapter {

    private Context context;
    private FragmentManager fragmentManager;
    ArrayList<Card> cards;
    private int size;


    public MainFragmentPagerAdapter(FragmentManager fm, ArrayList<Card> cards, Context context) {
        super(fm);
        this.context = context;
        this.fragmentManager = fm;
        this.cards = cards;
    }


    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        //return
        Log.i("PAGERADAPTER", cards.get(position).toString());
        return PlaceholderFragment.newInstance(position + 1, cards.get(position));
    }


    @Override
    public String getTag(int position) {
        return String.valueOf(cards.get(position).getPosition());
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return size;
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
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


    public void setCards(ArrayList<Card> cards) {
        size = cards.size();

        //   for (int i = 0; i < cards.size(); i++) {
        //       fragmentManager.beginTransaction().remove(mCurrentFragment).commit();
        //   }
        this.cards = cards;
        notifyDataSetChanged();
    }

    public Card getCard(int currentItemIndex) {
        return cards.get(currentItemIndex);
    }
}
