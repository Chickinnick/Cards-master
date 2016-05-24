package com.nlt.mobileteam.cards.controller;

import android.util.Log;

import com.nlt.mobileteam.cards.model.Action;
import com.nlt.mobileteam.cards.model.Card;

import java.io.File;

/**
 * Created by Nick on 15.05.2016.
 */
public class CardDataController {
    private static CardDataController ourInstance = new CardDataController();
    private Card mCard;

    public static CardDataController getInstance() {
        return ourInstance;
    }

    private CardDataController() {
        mCard = new Card();
    }


    public void setBackText(String text) {
        mCard.setBackText(text);
    }

    public void setFrontText(String text) {
        mCard.setFrontText(text);
    }


    public String getFrontText() {
        return mCard.getFrontText();
    }

    public String getBackText() {
        return mCard.getBackText();
    }

    public void saveInStorageAndRemove() {
        BroadcastManager.getInstance().sendBroadcast(Action.SAVE_STATE.name());
        Log.d("CARDDATA:", "save state " + mCard.toString());
    /*    mCard = null;
        mCard = new Card();*/
    }


    public Card getCard() {
        return mCard;
    }

    public void setPosition(int position) {
        mCard.setPosition(position);
    }

    public void setCard(Card card) {
        this.mCard = card;
    }

    public void clear() {
        mCard = null;
        mCard = new Card();
    }

    public void setBackImage(File imageFile) {
        mCard.setLinkToBackImage(imageFile.getAbsolutePath());
    }

    public void setFrontImage(File imageFile) {
        mCard.setLinkToFrontImage(imageFile.getAbsolutePath());

    }

    public String getFrontImage() {
        return mCard.getLinkToFrontImage();
    }

    public String getBackImage() {
        return mCard.getLinkToBackImage();
    }
}
