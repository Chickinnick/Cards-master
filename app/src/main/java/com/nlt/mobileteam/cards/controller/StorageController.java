package com.nlt.mobileteam.cards.controller;

import com.nlt.mobileteam.cards.model.Card;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

/**
 * Created by Nick on 01.05.2016.
 */
public class StorageController {
    private static StorageController ourInstance = new StorageController();

    public static StorageController getInstance() {
        return ourInstance;
    }

    private StorageController() {
    }

    public static final String DATA_KEY = "datakey";

    public ArrayList<Card> getFromStorage() {

        ArrayList<Card> cards = Hawk.get(DATA_KEY, getDefaultCards());
        if (cards == null) {
            cards = getDefaultCards();
            Hawk.put(DATA_KEY, cards);
        }
        return cards;
    }

    private ArrayList<Card> getDefaultCards() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card("Type the Question here!", "Here is an Answer"));
        cards.add(new Card("What is the capital of Great Britan?", "London"));
        cards.add(new Card("Awesome question?", "answer"));
        return cards;
    }


}
