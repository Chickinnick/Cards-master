package com.nlt.mobileteam.cards.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nlt.mobileteam.cards.R;
import com.nlt.mobileteam.cards.model.Card;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by Nick on 28.04.2016.
 */
public class FrontSideFragment extends CardFragment {

    public static final String CARD_KEY = "card";
    public static final String LOG_TAG = FrontSideFragment.class.getSimpleName();
    public static final String CARD_IMAGE_LINK_FRONT = "link_front";

    public static final String CARD_KEY_BACK_TEXT = "card_back";

    public static final String CARD_KEY_FRONT_TEXT = "card_front_text";
    public static final String CARD_KEY_FRONT_TEXT_SS = "card_front_text_ss";
    private static final String CARD_IMAGE_LINK_SSFRONT = "link_front_ss";

    public ImageView imageViewFront;

    private RelativeLayout mCardLayout;


    public FrontSideFragment() {
    }

    public static FrontSideFragment newInstance(Card card) {
        position = card.getPosition();
        Log.d(LOG_TAG, "on Front pos " + card.toString());
        FrontSideFragment fragment = new FrontSideFragment();
        Bundle args = new Bundle();
        args.putString(CARD_KEY_FRONT_TEXT, card.getFrontText());
        args.putString(CARD_KEY_BACK_TEXT, card.getBackText());
        args.putString(CARD_IMAGE_LINK_FRONT, card.getLinkToFrontImage());
        fragment.setArguments(args);
        //CardDataController.getInstance().setPosition(position);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.card_front, container, false);
        rootView.requestFocus();
        Bundle args = getArguments();


        String cardText = null;
        if (savedInstanceState == null) {
            cardText = args.getString(CARD_KEY_FRONT_TEXT);
        } else {
            cardText = savedInstanceState.getString(CARD_KEY_FRONT_TEXT_SS);
        }
        String imageLink = null;

        textView = (TextView) rootView.findViewById(R.id.textview);
        editText = (EditText) rootView.findViewById(R.id.edittext);
        textView.setText(cardText);
        textView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        hideKeyboard(textView);
        imageViewFront = (ImageView) rootView.findViewById(R.id.imageview);

        String path = null;

        if (savedInstanceState == null) {
            path = args.getString(CARD_IMAGE_LINK_FRONT);
        } else {
            path = savedInstanceState.getString(CARD_IMAGE_LINK_SSFRONT);
        }

        File file = null;
        if (!TextUtils.isEmpty(path)) {
            file = new File(path);
        } else {
            file = null;
        }
        if (file != null) {
            Picasso.with(getActivity().getApplicationContext())
                    .load(file)
                    .resize(300, 200)
                    .centerCrop()
                    .into(imageViewFront);
        }
        return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CARD_KEY_FRONT_TEXT_SS, getArguments().getString(CARD_KEY_FRONT_TEXT));
        outState.putString(CARD_IMAGE_LINK_SSFRONT, getArguments().getString(CARD_IMAGE_LINK_FRONT));
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showImage(File imageFile, Context applicationContext) {
        Picasso.with(applicationContext)
                .load(imageFile)
                .resize(300, 200)
                .centerCrop()
                .into((((FrontSideFragment) this).imageViewFront));
    }
}
