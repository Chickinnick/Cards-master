package com.nlt.mobileteam.cards.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

/**
 * Created by Nick on 28.04.2016.
 */
public class BackSideFragment extends CardFragment {


    public static final String CARD_KEY_FRONT_TEXT = "card_front_text";
    public static final String CARD_KEY_BACK_TEXT = "card_back";
    public static final String CARD_KEY_BACK_TEXT_SS = "card_back_ss";
    public static final String CARD_KEY = "card";
    public static final String LOG_TAG = FrontSideFragment.class.getSimpleName();

    public static final String CARD_IMAGE_LINK_FRONT = "link_front";
    public static final String CARD_IMAGE_LINK_BACK = "link_back";

    public static final String CARD_IMAGE_LINK_SS = "card_image_ss";
    private static final String CARD_KEY_TITLE = "title";
    public ImageView imageViewBack;
    public String path;


    public BackSideFragment() {
    }

    public static BackSideFragment newInstance(Card card) {
        Log.d(LOG_TAG, "onBack pos " + card.toString());

        BackSideFragment fragment = new BackSideFragment();
        Bundle args = new Bundle();
        args.putString(CARD_KEY_TITLE, card.getTitle());
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
        titleTextView = (TextView) rootView.findViewById(R.id.textview_title);
        titleEditText = (EditText) rootView.findViewById(R.id.edittext_title);

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


        String title = null;
        if (savedInstanceState == null) {
            title = args.getString(CARD_KEY_TITLE);
        } else {
            title = savedInstanceState.getString(CARD_KEY_TITLE);
        }
        titleTextView.setText(title);
        titleTextView.setOnClickListener(super.listener);


        imageViewBack = (ImageView) rootView.findViewById(R.id.imageview);
        path = null;
        if (savedInstanceState == null) {
            path = args.getString(CARD_IMAGE_LINK_BACK);
        } else {
            path = savedInstanceState.getString(CARD_IMAGE_LINK_SS);
        }

        Picasso.with(getActivity().getApplicationContext())
                .load(path)
                    .resize(300, 200)
                    .centerCrop()
                    .into(imageViewBack);

        hideKeyboard(textView);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        String back_txt = getArguments().getString(CARD_KEY_BACK_TEXT);
        outState.putString(CARD_KEY_BACK_TEXT_SS, back_txt);
        String back_img = getArguments().getString(CARD_IMAGE_LINK_BACK);
        outState.putString(CARD_IMAGE_LINK_SS, back_img);
        // Log.d(LOG_TAG, "saving state: back " + back_txt + "img:" + back_img);
        super.onSaveInstanceState(outState);
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showImage(String path, Context applicationContext) {
        Bundle args = super.getArguments();
        args.putString(CARD_IMAGE_LINK_BACK, path);
        args.putString(CARD_IMAGE_LINK_FRONT, args.getString(CARD_IMAGE_LINK_FRONT));
        //setArguments(args);

        Picasso.with(applicationContext)//todo MAYBE to child move method
                .load(path)
                .resize(300, 200)
                .centerCrop()
                .into((((BackSideFragment) this).imageViewBack));

    }
}
