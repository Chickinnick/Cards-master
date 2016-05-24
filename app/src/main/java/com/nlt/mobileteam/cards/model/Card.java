package com.nlt.mobileteam.cards.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Card implements Parcelable {

    int position;
    private String frontText;
    private String backText;
    private String linkToFrontImage;
    private String linkToBackImage;
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
        this.linkToBackImage = in.readString();
        this.linkToFrontImage = in.readString();
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
        dest.writeString(this.linkToFrontImage);
        dest.writeString(this.linkToBackImage);
    }

    public static final Parcelable.Creator<Card> CREATOR = new Parcelable.Creator<Card>() {
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    public String getLinkToFrontImage() {
        return linkToFrontImage;
    }

    public void setLinkToFrontImage(String linkToFrontImage) {
        this.linkToFrontImage = linkToFrontImage;
    }

    public String getLinkToBackImage() {
        return linkToBackImage;
    }

    public void setLinkToBackImage(String linkToBackImage) {
        this.linkToBackImage = linkToBackImage;
    }

    @Override
    public String toString() {
        return "Card{" +
                "position=" + position +
                ", frontText='" + frontText + '\'' +
                ", backText='" + backText + '\'' +
                '}';
    }
}
