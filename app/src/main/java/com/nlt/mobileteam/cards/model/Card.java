package com.nlt.mobileteam.cards.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.nlt.mobileteam.cards.sticker.stickerdemo.model.SavableView;
import com.nlt.mobileteam.cards.sticker.stickerdemo.model.StickerPropertyModel;
import com.nlt.mobileteam.cards.sticker.stickerdemo.model.TextPropertyModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Card implements Parcelable {

    int position;
    private boolean isFavourite;
    private UUID identifier;
    private List<SavableView> frontSavedViewArray = new ArrayList<>();
    private List<SavableView> backSavedViewArray = new ArrayList<>();

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
        this.frontSavedViewArray = new ArrayList<>();
        in.readList(this.frontSavedViewArray, SavableView.class.getClassLoader());

        this.backSavedViewArray = new ArrayList<>();
        in.readList(this.backSavedViewArray, SavableView.class.getClassLoader());


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
        dest.writeList(frontSavedViewArray);
        dest.writeList(backSavedViewArray);
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
        String front = "";
        if (frontSavedViewArray != null && !frontSavedViewArray.isEmpty()) {
            for (SavableView savableView :
                    frontSavedViewArray) {
                String text = "";
                String url = "";

                if (savableView instanceof StickerPropertyModel) {
                    url = ((StickerPropertyModel) savableView).getStickerURL();
                } else if (savableView instanceof TextPropertyModel) {
                    text = ((TextPropertyModel) (savableView)).getText();
                }

                front = front.concat(!TextUtils.isEmpty(text) ? text : url);
            }
        }
        ;

        return "Card{front:"
                + front +
                ", backSavedViewArray=" + (backSavedViewArray != null && !backSavedViewArray.isEmpty()) +
                '}';
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public void setFrontSavedViewArray(ArrayList<SavableView> frontSavedViewArray) {
        this.frontSavedViewArray = frontSavedViewArray;
    }


    public List<SavableView> getBackSavedViewArray() {
        return backSavedViewArray;
    }

    public void setBackSavedViewArray(ArrayList<SavableView> backSavedViewArray) {
        this.backSavedViewArray = backSavedViewArray;
    }

    public List<SavableView> getFrontSavedViewArray() {
        return frontSavedViewArray;
    }
}
