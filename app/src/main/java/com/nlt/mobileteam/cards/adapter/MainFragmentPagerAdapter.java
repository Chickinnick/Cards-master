package com.nlt.mobileteam.cards.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.nlt.mobileteam.cards.activity.MainActivityTabbed;
import com.nlt.mobileteam.cards.fragment.PlaceholderFragment;
import com.nlt.mobileteam.cards.model.Card;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by user on 28.04.2016.
 */
public class MainFragmentPagerAdapter extends FixedFragmentStatePagerAdapter {

    private Context context;
    private FragmentManager fragmentManager;
    ArrayList<Card> cards;
    private int size;
    private int randomItem;

    private Fragment mCurrentFragment;

    public MainFragmentPagerAdapter(FragmentManager fm, ArrayList<Card> cards, Context context) {
        super(fm);
        this.context = context;
        this.fragmentManager = fm;
        this.cards = cards;
    }

    private int getRandomUniquePage() {
        int temp = getRandomItem();
        if (temp != randomItem) {
            randomItem = temp;
            return randomItem;
        } else {
            getRandomUniquePage();
        }
        return randomItem;
    }

    private int getRandomItem() {
        return new Random().nextInt(cards.size() + 1);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        //return
        Log.i("PAGERADAPTER", cards.get(position).toString());
        int pos;
      /*   if(MainActivityTabbed.isShuffleMode){
            pos = getRandomUniquePage();
        } else{*/
        pos = position + 1;
        //}
        return PlaceholderFragment.newInstance(pos, cards.get(position));
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


    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            mCurrentFragment = ((Fragment) object);
            if (mCurrentFragment != null) {
                ((PlaceholderFragment) mCurrentFragment).setOnFragmentClickListener((MainActivityTabbed) context);
            }
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
        if (cards == null || cards.isEmpty()) {
            return new Card();
        }
        return cards.get(currentItemIndex);
    }


}
