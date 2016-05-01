package com.nlt.mobileteam.cards.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nlt.mobileteam.cards.R;
import com.nlt.mobileteam.cards.model.Card;
import com.nlt.mobileteam.cards.widget.JazzyViewPager;

public class PlaceholderFragment extends Fragment {

    private static final String LOG_TAG = PlaceholderFragment.class.getSimpleName();

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_CARD = "arg_card";
    public static final int SWIPED_UP = 1;
    public static final int SWIPED_DOWN = 2;
    private Fragment fragmentFront;
    private Fragment fragmentBack;
    private Fragment tempFragmentToReplace;
    private Card card;


    public PlaceholderFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get back arguments
        card = getArguments().getParcelable(ARG_CARD);
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber, Card card) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putParcelable(ARG_CARD, card);
        fragment.setArguments(args);

        return fragment;
    }

    private JazzyViewPager mJazzy;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main_activity_tabbed, container, false);
        rootView.requestFocus();
        fragmentFront = FrontSideFragment.newInstance(card);
        fragmentBack = BackSideFragment.newInstance(card);

        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        if (tempFragmentToReplace == null) {
            tempFragmentToReplace = fragmentFront;
        }
        fragmentTransaction.replace(R.id.card_layout, tempFragmentToReplace);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        //    toggleFragment(SWIPED_UP);

        // mJazzy = (JazzyViewPager) rootView.findViewById(R.id.jazzy_pager_item);
        // mJazzy.setTransitionEffect(JazzyViewPager.TransitionEffect.CubeOut);
        // MainAdapter pagerAdapter =  new MainAdapter(getContext(), mJazzy);
        // PagerAdapter adapter = new InfinitePagerAdapter(pagerAdapter);
        // mJazzy.setAdapter(adapter);
           /* mJazzy.setCurrentItem(2);*/
        // mJazzy.setPageMargin(30);


           /* TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));*/
        return rootView;
    }


    public void toggleFragment(int swipe) {
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();


        if (tempFragmentToReplace == null) {
            tempFragmentToReplace = fragmentBack;
        }

        if (tempFragmentToReplace.equals(fragmentBack)) {
            tempFragmentToReplace = fragmentFront;

        } else if (tempFragmentToReplace.equals(fragmentFront)) {
            tempFragmentToReplace = fragmentBack;
        }

        if (swipe == SWIPED_DOWN) {
            fragmentTransaction.setCustomAnimations(
                    R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                    R.animator.card_flip_left_in, R.animator.card_flip_left_out

            );
        } else if (swipe == SWIPED_UP) {
            fragmentTransaction.setCustomAnimations(
                    R.animator.card_flip_right_in_reverse, R.animator.card_flip_right_out_reverse/*,
                    R.animator.card_flip_left_in_reverse, R.animator.card_flip_left_out_reverse*/

            );
        }

        fragmentTransaction.replace(R.id.card_layout, tempFragmentToReplace);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}
