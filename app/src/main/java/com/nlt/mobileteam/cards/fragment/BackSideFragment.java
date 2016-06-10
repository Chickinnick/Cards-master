package com.nlt.mobileteam.cards.fragment;

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
import android.widget.TextView;

import com.nlt.mobileteam.cards.R;
import com.nlt.mobileteam.cards.model.Card;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by Nick on 28.04.2016.
 */
public class BackSideFragment extends CardFragment {


    public static final String CARD_KEY = "card";
    public static final String CARD_KEY_BACK_TEXT = "card_back";
    public static final String CARD_IMAGE_LINK_BACK = "link_back";
    public static final String LOG_TAG = FrontSideFragment.class.getSimpleName();
    public static final String CARD_KEY_BACK_TEXT_SS = "card_back_ss";
    public static final String CARD_IMAGE_LINK_FRONT = "link_front";
    private static final String CARD_IMAGE_LINK_SSFR = "card_image_ssfr";

    public ImageView imageViewBack;
    public static final String CARD_KEY_FRONT_TEXT = "card_front_text";
    private static final String CARD_IMAGE_LINK_SS = "card_image_ss";

    public BackSideFragment() {
    }

    public static BackSideFragment newInstance(Card card) {
        Log.d(LOG_TAG, "onBack pos " + card.toString());

        BackSideFragment fragment = new BackSideFragment();
        Bundle args = new Bundle();
        args.putString(CARD_KEY_FRONT_TEXT, card.getFrontText());
        args.putString(CARD_KEY_BACK_TEXT, card.getBackText());
        args.putString(CARD_IMAGE_LINK_BACK, card.getLinkToBackImage());
        args.putString(CARD_IMAGE_LINK_FRONT, card.getLinkToFrontImage());
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.card_front, container, false);
        Bundle args = getArguments();

        String cardText = null;
        if (savedInstanceState == null) {
            cardText = args.getString(CARD_KEY_BACK_TEXT);
        } else {
            cardText = savedInstanceState.getString(CARD_KEY_BACK_TEXT_SS);
        }

        rootView.findViewById(R.id.bg).setBackground(getActivity().getResources().getDrawable(R.drawable.card_rev));
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

        imageViewBack = (ImageView) rootView.findViewById(R.id.imageview);
        String imageLink = null;
        if (savedInstanceState == null) {
            imageLink = args.getString(CARD_IMAGE_LINK_BACK);
        } else {
            imageLink = savedInstanceState.getString(CARD_IMAGE_LINK_SS);
        }

        String path = imageLink;
        File file;
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
                    .into(imageViewBack);
        }
        hideKeyboard(textView);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(CARD_KEY_BACK_TEXT_SS, getArguments().getString(CARD_KEY_BACK_TEXT));
        outState.putString(CARD_IMAGE_LINK_SS, getArguments().getString(CARD_IMAGE_LINK_BACK));
        outState.putString(CARD_IMAGE_LINK_SSFR, getArguments().getString(CARD_IMAGE_LINK_FRONT));
        super.onSaveInstanceState(outState);
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showImage(File imageFile, Context applicationContext) {

        Picasso.with(applicationContext)//todo MAYBE to child move method
                .load(imageFile)
                .resize(300, 200)
                .centerCrop()
                .into((((BackSideFragment) this).imageViewBack));

    }
}
