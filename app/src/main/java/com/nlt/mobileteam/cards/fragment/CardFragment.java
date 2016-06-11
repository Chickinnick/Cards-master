package com.nlt.mobileteam.cards.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.nlt.mobileteam.cards.controller.BroadcastManager;
import com.nlt.mobileteam.cards.model.Action;
import com.nlt.mobileteam.cards.model.Card;

import java.io.File;

/**
 * Created by Nick on 07.05.2016.
 */
public class CardFragment extends Fragment {
    private static final String LOG_TAG = CardFragment.class.getSimpleName();
    protected TextView textView;
    protected EditText editText;
    protected static int position;
   /* private Card card;


    public void newInstance(Card card){
         this.card = new Card();
    }*/


    public void editText() {
        CharSequence text = textView.getText();
        textView.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);
        editText.setText(text);
    }

    public void saveText() {
        CharSequence text = null;
        if (editText != null) {
        text = editText.getText();
        editText.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        textView.setText(text);

            Card cardTosave = new Card();
        Bundle arguments = getArguments();
            String linkToFrontImage = arguments.getString(BackSideFragment.CARD_IMAGE_LINK_FRONT);
            if (!TextUtils.isEmpty(linkToFrontImage)) {
                cardTosave.setLinkToFrontImage(linkToFrontImage);
            }
            String linkToBackImage = arguments.getString(BackSideFragment.CARD_IMAGE_LINK_BACK);//FIX IT
            if (!TextUtils.isEmpty(linkToBackImage)) {
                cardTosave.setLinkToFrontImage(linkToBackImage);
            }

        if (this instanceof BackSideFragment) {
            Log.d(LOG_TAG, "class was BackSideFragment");
            Log.d(LOG_TAG, "class was FrontSideFragment");
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


    public void savePhoto(File imageFile, Context applicationContext) {

        Card cardTosave = new Card();

        Bundle arguments = getArguments();

        cardTosave.setFrontText(arguments.getString(BackSideFragment.CARD_KEY_FRONT_TEXT));//???todo why BackSideFragment
        cardTosave.setBackText(arguments.getString(BackSideFragment.CARD_KEY_BACK_TEXT));

        if (this instanceof BackSideFragment) {
            //CardDataController.getInstance().setBackText(textView.getText().toString());
            Log.d(LOG_TAG, "savePhoto was BackSideFragment");
            cardTosave.setLinkToBackImage(imageFile.getAbsolutePath());
            cardTosave.setLinkToFrontImage(arguments.getString(BackSideFragment.CARD_IMAGE_LINK_FRONT));

            ((BackSideFragment) this).showImage(imageFile, applicationContext);
        } else if (this instanceof FrontSideFragment) {
            Log.d(LOG_TAG, "savePhoto was FrontSideFragment");
            cardTosave.setLinkToFrontImage(imageFile.getAbsolutePath());
            cardTosave.setLinkToBackImage(arguments.getString(BackSideFragment.CARD_IMAGE_LINK_BACK));
            ((FrontSideFragment) this).showImage(imageFile, applicationContext);
            //CardDataController.getInstance().setFrontText(textView.getText().toString());

        }

        Log.d(LOG_TAG, "card" + cardTosave.toString());

        BroadcastManager.getInstance().sendBroadcastWithParcelable(Action.SAVE_STATE.name(), cardTosave);

    }
}
