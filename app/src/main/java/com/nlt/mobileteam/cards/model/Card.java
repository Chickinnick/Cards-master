package com.nlt.mobileteam.cards.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.nlt.mobileteam.cards.sticker.stickerdemo.model.BaseViewModel;
import com.nlt.mobileteam.cards.sticker.stickerdemo.model.BubblePropertyModel;
import com.nlt.mobileteam.cards.sticker.stickerdemo.model.StickerPropertyModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Card implements Parcelable {

    int position;
    private boolean isFavourite;
    private UUID identifier;
    private List<BaseViewModel> frontSavedViewArray = new ArrayList<>();
    private List<BaseViewModel> backSavedViewArray = new ArrayList<>();

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
        in.readList(this.frontSavedViewArray, BaseViewModel.class.getClassLoader());

        this.backSavedViewArray = new ArrayList<>();
        in.readList(this.backSavedViewArray, BaseViewModel.class.getClassLoader());


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
        String fronmt = "";
        if (frontSavedViewArray != null && !frontSavedViewArray.isEmpty()) {
            for (BaseViewModel baseViewModel :
                    frontSavedViewArray) {
                String text = "";
                String url = "";

                if (baseViewModel instanceof StickerPropertyModel) {
                    url = ((StickerPropertyModel) baseViewModel).getStickerURL();
                } else {
                    text = ((BubblePropertyModel) (baseViewModel)).getText();
                }

                fronmt = fronmt.concat(" " + url + "/" + text + " ");
            }
        }
        ;

        return "Card{front:"
                + fronmt +
                ", backSavedViewArray=" + (backSavedViewArray != null && !backSavedViewArray.isEmpty()) +
                '}';
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public void setFrontSavedViewArray(ArrayList<BaseViewModel> frontSavedViewArray) {
        this.frontSavedViewArray = frontSavedViewArray;
    }


    public List<BaseViewModel> getBackSavedViewArray() {
        return backSavedViewArray;
    }

    public void setBackSavedViewArray(ArrayList<BaseViewModel> backSavedViewArray) {
        this.backSavedViewArray = backSavedViewArray;
    }

    public List<BaseViewModel> getFrontSavedViewArray() {
        return frontSavedViewArray;
    }
}
