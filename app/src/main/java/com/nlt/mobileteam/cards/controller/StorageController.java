package com.nlt.mobileteam.cards.controller;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.nlt.mobileteam.cards.model.Card;
import com.nlt.mobileteam.cards.model.Folder;
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
    public static final String FOLDERS_DATA_KEY = "folders_key";


    private ArrayList<Card> getDefaultCards() {
        ArrayList<Card> cards = new ArrayList<>();
        //cards.add(new Card("Type the Question here!", "Here is an Answer"));
        //cards.add(new Card("What is the capital of Great Britan?", "London"));
        //cards.add(new Card("Awesome question?", "answer"));
        cards.add(new Card("A ", "AA"));
        cards.add(new Card("B ", "BB"));
        cards.add(new Card("C", "CC"));
        cards.add(new Card("D", "DD"));
        cards.add(new Card("E", "EE"));
        cards.add(new Card("F", "FF"));
        cards.add(new Card("G", "GG"));
        cards.add(new Card("H", "HH"));
        cards.add(new Card("I", "II"));
        return cards;
    }


    public ArrayList<Folder> getFolderFromStorage() {
        return Hawk.get(FOLDERS_DATA_KEY, getDefaultFoldersList());
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
        Hawk.put(FOLDERS_DATA_KEY, foldersArrayList);
    }
}
