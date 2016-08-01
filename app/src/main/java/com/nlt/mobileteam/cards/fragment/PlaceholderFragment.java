package com.nlt.mobileteam.cards.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nlt.mobileteam.cards.R;
import com.nlt.mobileteam.cards.fragment.cards.BackCard;
import com.nlt.mobileteam.cards.fragment.cards.BaseCard;
import com.nlt.mobileteam.cards.fragment.cards.FrontCard;
import com.nlt.mobileteam.cards.model.Card;

public class PlaceholderFragment extends Fragment {

    private static final String LOG_TAG = PlaceholderFragment.class.getSimpleName();

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_CARD = "arg_card";
    public static final int SWIPED_UP = 1;
    public static final int SWIPED_DOWN = 2;
    //    private BaseCard fragmentFront;
//    private BaseCard fragmentBack;
    private BaseCard tempFragmentToReplace;
    private Card card;
    private ImageView imageView;
    int sectionNumber;
    public OnFragmentClickListener onFragmentClickListener;
    private boolean isFront;

    public void setOnFragmentClickListener(OnFragmentClickListener onFragmentClickListener) {
        this.onFragmentClickListener = onFragmentClickListener;
    }

    public void addText() {
        tempFragmentToReplace.addTextView();
        //tempFragmentToReplace.calculatePropertiesAndSave();
    }

    public interface OnFragmentClickListener {
        void onFragmentClick(View v);


    }

    public PlaceholderFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get back arguments

        card = getArguments().getParcelable(ARG_CARD);
        card.setPosition(getArguments().getInt(ARG_SECTION_NUMBER));
        sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        //    Log.w("PAGE", " onCreate  " + card.toString());

//        fragmentFront = BaseCard.newInstance(card, BaseCard.FRONT);
//        fragmentBack = BaseCard.newInstance(card, BaseCard.BACK);

    }


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment newInstance(int sectionNumber, Card card) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putParcelable(ARG_CARD, card);
        fragment.setArguments(args);
        //Log.w("PAGE", " newInstance  " + card.toString());
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main_activity_tabbed, container, false);
        rootView.requestFocus();
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFragmentClickListener.onFragmentClick(v);

            }
        });

        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        if (tempFragmentToReplace == null) {
            tempFragmentToReplace = FrontCard.newInstance(card, BaseCard.FRONT);
            isFront = true;
        }
        fragmentTransaction.replace(R.id.card_layout, tempFragmentToReplace);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


        //
//        Log.w("PAGE", "onCreateView" + card.toString());

        return rootView;
    }

    public void updateCard(Card newCard) {
//             Bundle args = new Bundle();
//                args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            args.putParcelable(ARG_CARD, newCard);
//            setArguments(args);
        card = newCard;

    }


    @Override
    public void onResume() {
        super.onResume();
        //card = ( (MainActivityTabbed) getActivity()).getCurrentCard();


    }

    public void toggleFragment(int swipe) {
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();

        if (tempFragmentToReplace == null) {
            tempFragmentToReplace = BackCard.newInstance(card, BaseCard.BACK);
            isFront = false;
        }

        if (!isFront) {
            tempFragmentToReplace = FrontCard.newInstance(card, BaseCard.FRONT);
            isFront = true;
        } else {
            tempFragmentToReplace = BackCard.newInstance(card, BaseCard.BACK);
            isFront = false;
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
//        fragmentTransaction.detach(tempFragmentToReplace);
//        fragmentTransaction.attach(tempFragmentToReplace);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }



    public Card getCard() {
        return card;
    }

    public void clearFocus() {
        tempFragmentToReplace.clearFocus();
    }

    public void enterEditTitleMode() {
        // tempFragmentToReplace.editTitle();
    }

    public void exitEditMode() {
        tempFragmentToReplace.calculatePropertiesAndSave();
    }

    public ImageView getImageView() {
        return imageView;
    }


    public void savePhotoInModel(String path) {
        tempFragmentToReplace.addStickerView(path);
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        fragmentTransaction.detach(tempFragmentToReplace);
        fragmentTransaction.attach(tempFragmentToReplace);
        fragmentTransaction.commit();
        //tempFragmentToReplace.calculatePropertiesAndSave();
    }
}
