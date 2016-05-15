package com.nlt.mobileteam.cards.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nlt.mobileteam.cards.R;
import com.nlt.mobileteam.cards.controller.CardDataController;
import com.nlt.mobileteam.cards.model.Card;

/**
 * Created by Nick on 28.04.2016.
 */
public class BackSideFragment extends CardFragment {


    private static final String CARD_KEY = "card";
    private static final String CARD_KEY_BACK_TEXT = "card_back";


    public BackSideFragment() {
    }

    public static BackSideFragment newInstance(Card card) {

        BackSideFragment fragment = new BackSideFragment();
        Bundle args = new Bundle();
        args.putString(CARD_KEY_BACK_TEXT, card.getBackText());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        String text = getArguments().getString(CARD_KEY_BACK_TEXT);
        CardDataController.getInstance().setBackText(text);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.card_back, container, false);
        textView = (TextView) rootView.findViewById(R.id.textview);
        editText = (EditText) rootView.findViewById(R.id.edittext);
        textView.setText(CardDataController.getInstance().getBackText());
        textView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        imageView = (ImageView) rootView.findViewById(R.id.imageview);
        hideKeyboard(textView);
        return rootView;
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
