package com.nlt.mobileteam.cards.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Card implements Parcelable {

    int position;
    private String frontText;
    private String backText;

    public Card() {
    }

    public Card(String frontText, String backText) {
        this.frontText = frontText;
        this.backText = backText;
    }

    public Card(int position, String frontText, String backText) {
        this.position = position;
        this.frontText = frontText;
        this.backText = backText;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public String getFrontText() {
        return frontText;
    }

    public void setFrontText(String frontText) {
        this.frontText = frontText;
    }

    public String getBackText() {
        return backText;
    }

    public void setBackText(String backText) {
        this.backText = backText;
    }

    public Card(Parcel in) {
        this.frontText = in.readString();
        this.backText = in.readString();
        this.position = in.readInt();
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(
                this.frontText);
        dest.writeString(
                this.backText);
        dest.writeInt(this.position);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        public Card[] newArray(int size) {
            return new Card[size];
        }
    };
}
