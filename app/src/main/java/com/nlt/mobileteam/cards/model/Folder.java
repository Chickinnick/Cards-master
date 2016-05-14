package com.nlt.mobileteam.cards.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.UUID;

public class Folder implements Parcelable {

    private String name;
    private UUID identifier;
    private int color;

    private ArrayList<Card> cards = new ArrayList<>();

    public Folder(String todoBody) {
        name = todoBody;
        color = 1677725;
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


    public Folder(Parcel in) {
        this.identifier = (UUID) in.readSerializable();
        this.color = in.readInt();
        ArrayList<Card> cardsTemp = new ArrayList<>();
        in.readTypedList(this.cards, Card.CREATOR);

        this.name = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(identifier);
        dest.writeInt(color);
        dest.writeTypedList(cards);
        dest.writeString(name);
    }

    public static final Parcelable.Creator<Folder> CREATOR = new Parcelable.Creator<Folder>() {
        public Folder createFromParcel(Parcel in) {
            return new Folder(in);
        }

        public Folder[] newArray(int size) {
            return new Folder[size];
        }
    };
}

