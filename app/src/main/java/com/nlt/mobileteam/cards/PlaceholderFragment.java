package com.nlt.mobileteam.cards;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class PlaceholderFragment extends Fragment {

    private static final String LOG_TAG = PlaceholderFragment.class.getSimpleName();
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int mFocusedPage;
    private RelativeLayout mCardLayout;
    private Fragment fragmentFront;
    private Fragment fragmentBack;

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    private JazzyViewPager mJazzy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main_activity_tabbed, container, false);
        mCardLayout = (RelativeLayout) rootView.findViewById(R.id.card_layout);


        fragmentFront = new FrontSideFragment();
        fragmentBack = new BackSideFragment();
        mCardBackLayout = rootView.findViewById(R.id.card_back);
        mCardFrontLayout = rootView.findViewById(R.id.card_front);
        loadAnimations();
        changeCameraDistance();

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


    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetRightOutDown;
    private AnimatorSet mSetLeftInDown;
    private AnimatorSet mSetLeftIn;
    private boolean mIsBackVisible = false;
    private View mCardFrontLayout;
    private View mCardBackLayout;


    private void changeCameraDistance() {
        int distance = 18000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mCardFrontLayout.setCameraDistance(scale);
        mCardBackLayout.setCameraDistance(scale);
    }

    private void loadAnimations() {
        // mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.out_animation);
        // mSetRightOutDown = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.out_animation_down);
        // mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.in_animation);
        // mSetLeftInDown = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.in_animation_down);
    }


    public void flipFragmentToBack() {
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                R.animator.card_flip_left_in, R.animator.card_flip_left_out

        );
        fragmentTransaction.replace(R.id.card_layout, fragmentBack, "fragmentBack");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void flipFragmentToFront() {
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                R.animator.card_flip_left_in, R.animator.card_flip_left_out

        );
        fragmentTransaction.replace(R.id.card_layout, fragmentFront, "fragmentFront");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    private void findViews() {
    }

    public void flipCardUp() {
        if (!mIsBackVisible) {
            mSetRightOut.setTarget(mCardFrontLayout);
            mSetLeftIn.setTarget(mCardBackLayout);
            mSetLeftIn.start();
            mSetRightOut.start();
            mIsBackVisible = true;
        } else {
            mSetRightOut.setTarget(mCardBackLayout);
            mSetLeftIn.setTarget(mCardFrontLayout);
            mSetLeftIn.start();
            mSetRightOut.start();
            mIsBackVisible = false;
        }
    }

    public void flipCardDown() {
        if (!mIsBackVisible) {
            mSetRightOutDown.setTarget(mCardFrontLayout);
            mSetLeftInDown.setTarget(mCardBackLayout);
            mSetLeftInDown.start();
            mSetRightOutDown.start();
            mIsBackVisible = true;
        } else {
            mSetRightOutDown.setTarget(mCardBackLayout);
            mSetLeftInDown.setTarget(mCardFrontLayout);
            mSetLeftInDown.start();
            mSetRightOutDown.start();
            mIsBackVisible = false;
        }
    }

}
