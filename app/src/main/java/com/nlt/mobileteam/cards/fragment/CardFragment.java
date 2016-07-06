package com.nlt.mobileteam.cards.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nlt.mobileteam.cards.activity.MainActivityTabbed;
import com.nlt.mobileteam.cards.controller.BroadcastManager;
import com.nlt.mobileteam.cards.model.Action;
import com.nlt.mobileteam.cards.model.Card;
import com.nlt.mobileteam.cards.sticker.stickerdemo.view.BubbleInputDialog;
import com.nlt.mobileteam.cards.sticker.stickerdemo.view.BubbleTextView;
import com.nlt.mobileteam.cards.sticker.stickerdemo.view.StickerView;

import java.util.ArrayList;

/**
 * Created by Nick on 07.05.2016.
 */
public class CardFragment extends Fragment implements View.OnClickListener {
    private static final String LOG_TAG = CardFragment.class.getSimpleName();
    protected TextView textView;
    protected EditText editText;

    protected TextView titleTextView;
    protected EditText titleEditText;

    protected static int position;
    public View.OnClickListener listener = this;
    /* private Card card;

    public void newInstance(Card card){
         this.card = new Card();
    }*/


    public  BubbleInputDialog mBubbleInputDialog;

    public  StickerView mCurrentView;

    public  BubbleTextView mCurrentEditTextView;

    public  ArrayList<View> mViews;

    public  RelativeLayout mContentRootView;


    public void editText() {
        CharSequence text = textView.getText();
        textView.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);
        editText.setText(text);
    }

    public void editTitle() {
        CharSequence text = titleTextView.getText();
        titleTextView.setVisibility(View.GONE);
        titleEditText.setVisibility(View.VISIBLE);
        titleEditText.setText(text);
    }

    public void saveText() {
        CharSequence text = null;
        if (editText != null) {
            text = editText.getText();
            editText.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            if (!titleEditText.isActivated() && !TextUtils.isEmpty(text)) {
                textView.setText(text);
            }
        }

        CharSequence title = null;
        if (titleEditText != null) {
            title = titleEditText.getText();
            titleEditText.setVisibility(View.GONE);
            titleTextView.setVisibility(View.VISIBLE);
            if (/*!editText.isActivated() &&*/ !TextUtils.isEmpty(title)) {
                titleTextView.setText(title);
            }


            }
        Card cardTosave = ((MainActivityTabbed) getActivity()).getCurrentCard();
        Bundle arguments = getArguments();


        cardTosave.setTitle(title.toString());
        String linkToFrontImage = arguments.getString(FrontSideFragment.CARD_IMAGE_LINK_FRONT);
        if (!TextUtils.isEmpty(linkToFrontImage)) {
            cardTosave.setLinkToFrontImage(linkToFrontImage);
            Log.e(LOG_TAG, "setLinkToFrontImage" + linkToFrontImage);

        }
        String linkToBackImage = arguments.getString(BackSideFragment.CARD_IMAGE_LINK_BACK);//FIX IT
        if (!TextUtils.isEmpty(linkToBackImage)) {
            cardTosave.setLinkToBackImage(linkToBackImage);
            Log.e(LOG_TAG, "linkToBackImage" + linkToBackImage);

        }

        if (this instanceof BackSideFragment) {
            Log.d(LOG_TAG, "class was BackSideFragment");
            cardTosave.setBackText(text.toString());
            cardTosave.setFrontText(arguments.getString(BackSideFragment.CARD_KEY_FRONT_TEXT));//???todo why BackSideFragment

        } else if (this instanceof FrontSideFragment) {
            Log.d(LOG_TAG, "class was FrontSideFragment");
            cardTosave.setFrontText(text.toString());
            cardTosave.setBackText(arguments.getString(BackSideFragment.CARD_KEY_BACK_TEXT));
        }
        Log.d(LOG_TAG, "card" + cardTosave.toString());

        //  CardDataController.getInstance().saveInStorageAndRemove();
        BroadcastManager.getInstance().sendBroadcastWithParcelable(Action.SAVE_STATE.name(), cardTosave);


    }



    @Override
    public void onDestroyView() {
        Log.d(LOG_TAG, "onDestroy View ");
        if (this instanceof BackSideFragment) {
            //CardDataController.getInstance().setBackText(textView.getText().toString());
            Log.d(LOG_TAG, "class was BackSideFragment");

        } else if (this instanceof FrontSideFragment) {
            Log.d(LOG_TAG, "class was FrontSideFragment");
            //CardDataController.getInstance().setFrontText(textView.getText().toString());
        }


        super.onDestroyView();
        // CardDataController.getInstance().saveInStorageAndRemove(position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    public void savePhoto(String path, Context applicationContext) {

//        Card cardTosave = new Card();
        Card cardTosave = ((MainActivityTabbed) getActivity()).getCurrentCard();

        Bundle arguments = getArguments();

        cardTosave.setFrontText(arguments.getString(BackSideFragment.CARD_KEY_FRONT_TEXT));//???todo why BackSideFragment
        cardTosave.setBackText(arguments.getString(BackSideFragment.CARD_KEY_BACK_TEXT));

        if (this instanceof BackSideFragment) {
            //CardDataController.getInstance().setBackText(textView.getText().toString());
            Log.d(LOG_TAG, "savePhoto was BackSideFragment");
            //    cardTosave.setLinkToBackImage(arguments.getString(BackSideFragment.CARD_IMAGE_LINK_BACK));
            cardTosave.setLinkToBackImage(path);
            Log.e(LOG_TAG, "path" + path);

            ((BackSideFragment) this).showImage(path, applicationContext);
            cardTosave.setLinkToFrontImage(arguments.getString(FrontSideFragment.CARD_IMAGE_LINK_FRONT));
        } else if (this instanceof FrontSideFragment) {
            Log.d(LOG_TAG, "savePhoto was FrontSideFragment");
            cardTosave.setLinkToFrontImage(path);
            //     cardTosave.setLinkToFrontImage(arguments.getString(FrontSideFragment.CARD_IMAGE_LINK_FRONT));
            Log.e(LOG_TAG, "path" + path);

            ((FrontSideFragment) this).showImage(path, applicationContext);
            cardTosave.setLinkToBackImage(arguments.getString(BackSideFragment.CARD_IMAGE_LINK_BACK));
            //CardDataController.getInstance().setFrontText(textView.getText().toString());

        }

        Log.d(LOG_TAG, "card img" + cardTosave.toString());

        BroadcastManager.getInstance().sendBroadcastWithParcelable(Action.SAVE_STATE.name(), cardTosave);

    }


    @Override
    public void onClick(View v) {
        Log.d(LOG_TAG, "ON title click!");
        ((PlaceholderFragment) getParentFragment()).onFragmentClickListener.onTitleClick(v);
    }
}
