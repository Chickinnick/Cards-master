package com.nlt.mobileteam.cards.controller;

import com.nlt.mobileteam.cards.model.Action;
import com.nlt.mobileteam.cards.model.Card;

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

    public void saveInStorageAndRemove(int position) {
        BroadcastManager.getInstance().sendBroadcastWithInt(Action.SAVE_STATE.name(), position);
        mCard = null;
        mCard = new Card();
    }


    public Card getCard() {
        return mCard;
    }
}
