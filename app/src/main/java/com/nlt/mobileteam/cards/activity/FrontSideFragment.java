package com.nlt.mobileteam.cards.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.nlt.mobileteam.cards.controller.CardDataController;
import com.nlt.mobileteam.cards.model.Card;
import com.squareup.picasso.Picasso;

/**
 * Created by Nick on 28.04.2016.
 */
public class FrontSideFragment extends CardFragment {

    private static final String CARD_KEY = "card";
    private static final String LOG_TAG = FrontSideFragment.class.getSimpleName();
    private static final String CARD_KEY_FRONT_TEXT = "card_front_text";

    private static final String CARD_KEY_BACK_TEXT = "card_back";


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
        fragment.setArguments(args);
        CardDataController.getInstance().setPosition(position);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String cardText = getArguments().getString(CARD_KEY_FRONT_TEXT);

        String text = getArguments().getString(CARD_KEY_BACK_TEXT);
        CardDataController.getInstance().setBackText(text);
        CardDataController.getInstance().setFrontText(cardText);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.card_front, container, false);
        rootView.requestFocus();
        textView = (TextView) rootView.findViewById(R.id.textview);
        editText = (EditText) rootView.findViewById(R.id.edittext);
        textView.setText(CardDataController.getInstance().getFrontText());
        Log.d(LOG_TAG, "onCrerateFront");
        textView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        hideKeyboard(textView);
        imageView = (ImageView) rootView.findViewById(R.id.imageview);
        Picasso.with(getActivity())
                .load(CardDataController.getInstance().getFrontImage())
                .resize(300, 200)
                .centerCrop()
                .into(imageView);
        return rootView;
    }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
