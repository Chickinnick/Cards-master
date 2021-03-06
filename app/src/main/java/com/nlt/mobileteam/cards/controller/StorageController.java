package com.nlt.mobileteam.cards.controller;

import android.content.Context;
import android.util.Log;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.nlt.mobileteam.cards.R;
import com.nlt.mobileteam.cards.model.Card;
import com.nlt.mobileteam.cards.model.Folder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import io.paperdb.Paper;

/**
 * Created by Nick on 01.05.2016.
 */
public class StorageController {
    private static final String FAV_DATA_KEY = "favourite";
    private static StorageController ourInstance = new StorageController();

    public static StorageController getInstance() {
        return ourInstance;
    }

    private StorageController() {
    }

    public static final String DATA_KEY = "datakey";
    public static final String FOLDERS_DATA_KEY = "folders_key";


    private ArrayList<Card> getDefaultCards() {
        ArrayList<Card> cards = new ArrayList<>();
        //cards.add(new Card("Type the Question here!", "Here is an Answer"));
        //cards.add(new Card("What is the capital of Great Britan?", "London"));
        //cards.add(new Card("Awesome question?", "answer"));
        cards.add(new Card());
        cards.add(new Card());
        Log.d("Storage", "getDefaultCards  ");
        return cards;
    }


    public ArrayList<Folder> getFolderFromStorage() {
        if (Paper.book().read(FOLDERS_DATA_KEY) == null) {
            Log.d("StorageController", "FOLDERS_ null");

            saveFolders(getDefaultFoldersList());
            return getDefaultFoldersList();
        } else {
            Log.d("StorageController", "FOLDERS_ from storage");

            Log.d("Storage", "getFolderFromStorage  " + Paper.book().read(FOLDERS_DATA_KEY));
            return Paper.book().read(FOLDERS_DATA_KEY);
        }
    }


    public ArrayList<Folder> getDefaultFoldersList() {
        ArrayList<Folder> folders = new ArrayList<>();
        Folder defaultFolder = new Folder("DefaultFolder");
        defaultFolder.setColor(ColorGenerator.MATERIAL.getRandomColor());
        defaultFolder.setCards(getDefaultCards());
        folders.add(defaultFolder);
        return folders;
    }

    public void saveFolders(ArrayList<Folder> foldersArrayList) {

        if(foldersArrayList== null){
            Log.d("Storage", "saveFolders null ");
            return;
        }
        Log.d("Storage", "saveFolders  " + foldersArrayList.toString());
        //  boolean result = Hawk.put(FOLDERS_DATA_KEY, foldersArrayList);
        Paper.book().write(FOLDERS_DATA_KEY, foldersArrayList);
        ;
        // Log.d("Storage", "saveFolders  " + result);
    }


    public void saveInFavourites(Card card, Context context) {
        Log.d("Storage", "fav:" + card.toString());
        Folder fav = getFavourite(context);
        ArrayList<Card> cards = fav.getCards();
        Set<UUID> uuids = new HashSet<>(cards.size());

        for (Card item : cards) {
            uuids.add(item.getIdentifier());
        }

        if (!uuids.contains(card.getIdentifier())) {
            cards.add(card);
        }
        Paper.book().write(FAV_DATA_KEY, fav);
    }

    public Folder getFavourite(Context context) {
        return Paper.book().read(FAV_DATA_KEY, getEmptyFavoutite(context));
    }

    private Folder getEmptyFavoutite(Context context) {
        Folder fav = new Folder(context.getResources().getString(R.string.favourite));
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card());
        fav.setCards(cards);
        return fav;
    }

    public void removeFromFavourites(Card card, Context context) {
        Log.d("Storage", "fav:" + card.toString());
        Folder fav = getFavourite(context);
        ArrayList<Card> cards = fav.getCards();
        cards.remove(card);
        //  Hawk.put(FAV_DATA_KEY, fav);
        Paper.book().write(FAV_DATA_KEY, fav);
    }
}
