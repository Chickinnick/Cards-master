package com.nlt.mobileteam.cards.activity;

import android.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nlt.mobileteam.cards.controller.CardDataController;
import com.nlt.mobileteam.cards.model.Card;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Nick on 07.05.2016.
 */
public class CardFragment extends Fragment {
    private static final String LOG_TAG = CardFragment.class.getSimpleName();
    protected TextView textView;
    protected EditText editText;
    protected ImageView imageView;
    protected static int position;


    public void editText() {
        CharSequence text = textView.getText();
        textView.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);
        editText.setText(text);
    }

    public void saveText() {
        CharSequence text = editText.getText();
        editText.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        textView.setText(text);
        if (this instanceof BackSideFragment) {
            CardDataController.getInstance().setBackText(textView.getText().toString());
            Log.d(LOG_TAG, "class was BackSideFragment");

        } else if (this instanceof FrontSideFragment) {
            Log.d(LOG_TAG, "class was FrontSideFragment");
            CardDataController.getInstance().setFrontText(textView.getText().toString());
        }
        CardDataController.getInstance().saveInStorageAndRemove();
    }


    public ImageView getImageView() {

        return imageView;
    }

    public void update(ArrayList<Card> cards) {

    }

    @Override
    public void onPause() {

        Log.w(LOG_TAG, "pauseView  !" + CardDataController.getInstance().getCard().toString());

        super.onPause();
    }


    @Override
    public void onDestroyView() {
        Log.d(LOG_TAG, "onDestroy View ");
        if (this instanceof BackSideFragment) {
            CardDataController.getInstance().setBackText(textView.getText().toString());
            Log.d(LOG_TAG, "class was BackSideFragment");

        } else if (this instanceof FrontSideFragment) {
            Log.d(LOG_TAG, "class was FrontSideFragment");
            CardDataController.getInstance().setFrontText(textView.getText().toString());
        }


        super.onDestroyView();
        // CardDataController.getInstance().saveInStorageAndRemove(position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    public void savePhoto(File imageFile) {
        if (this instanceof BackSideFragment) {
            CardDataController.getInstance().setBackImage(imageFile);
            Log.d(LOG_TAG, "class was BackSideFragment");

        } else if (this instanceof FrontSideFragment) {
            Log.d(LOG_TAG, "class was FrontSideFragment");
            CardDataController.getInstance().setFrontImage(imageFile);
        }
    }
}
