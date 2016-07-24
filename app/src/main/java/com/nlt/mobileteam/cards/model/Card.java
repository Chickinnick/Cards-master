package com.nlt.mobileteam.cards.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.nlt.mobileteam.cards.sticker.stickerdemo.model.BubblePropertyModel;
import com.nlt.mobileteam.cards.sticker.stickerdemo.model.StickerPropertyModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Card implements Parcelable {

    int position;
    private boolean isFavourite;
    private UUID identifier;
    private List<BubblePropertyModel> backTextArray = new ArrayList<>();
    private List<StickerPropertyModel> backImageArray = new ArrayList<>();
    private List<BubblePropertyModel> frontTextArray = new ArrayList<>();
    private List<StickerPropertyModel> frontImageArray = new ArrayList<>();

    public Card() {
        identifier = UUID.randomUUID();
    }




    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public UUID getIdentifier() {
        return identifier;
    }


    public Card(Parcel in) {
        this.identifier = (UUID) in.readSerializable();
        this.frontTextArray = new ArrayList<>();
        in.readList(this.frontTextArray, BubblePropertyModel.class.getClassLoader());

        this.backTextArray = new ArrayList<>();
        in.readList(this.backTextArray, BubblePropertyModel.class.getClassLoader());

        this.frontImageArray = new ArrayList<>();
        in.readList(this.frontImageArray, StickerPropertyModel.class.getClassLoader());


        this.backImageArray = new ArrayList<>();
        in.readList(this.backImageArray, StickerPropertyModel.class.getClassLoader());
        this.position = in.readInt();

        boolean[] arr = new boolean[1];
        in.readBooleanArray(arr);
        this.isFavourite = arr[0];
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(identifier);
        dest.writeList(frontTextArray);
        dest.writeList(backTextArray);
        dest.writeList(frontImageArray);
        dest.writeList(backImageArray);
        dest.writeInt(this.position);
        dest.writeBooleanArray(new boolean[]{this.isFavourite});
    }

    public static final Parcelable.Creator<Card> CREATOR = new Parcelable.Creator<Card>() {
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        public Card[] newArray(int size) {
            return new Card[size];
        }
    };


    @Override
    public String toString() {
        return "Card{" +
                "position=" + position +
                ", isFavourite=" + isFavourite +
                ", identifier=" + identifier +
                ", backTextArray=" + backTextArray +
                ", backImageArray=" + backImageArray +
                ", frontTextArray=" + frontTextArray +
                ", frontImageArray=" + frontImageArray +
                '}';
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public void setBackTextArray(List<BubblePropertyModel> backTextArray) {
        this.backTextArray = backTextArray;
    }

    public void setBackImageArray(List<StickerPropertyModel> backImageArray) {
        this.backImageArray = backImageArray;
    }

    public void setFrontTextArray(List<BubblePropertyModel> frontTextArray) {
        this.frontTextArray = frontTextArray;
    }

    public void setFrontImageArray(List<StickerPropertyModel> frontImageArray) {
        this.frontImageArray = frontImageArray;
    }

    public List<StickerPropertyModel> getFrontImageArray() {
        return frontImageArray;
    }

    public List<BubblePropertyModel> getFrontTextArray() {
        return frontTextArray;
    }

    public List<StickerPropertyModel> getBackImageArray() {
        return backImageArray;
    }

    public List<BubblePropertyModel> getBackTextArray() {
        return backTextArray;
    }
}
