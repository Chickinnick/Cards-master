package com.nlt.mobileteam.cards.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Folder implements Serializable {

    private String name;
    private UUID identifier;
    private int color;

    private ArrayList<Card> cards;

    public Folder(String todoBody) {
        name = todoBody;
        color = 1677725;
        cards = new ArrayList<>();
        identifier = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    public UUID getIdentifier() {
        return identifier;
    }

}

