package com.nlt.mobileteam.cards.sticker.stickerdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nick on 31.07.2016.
 */
public class SavableView implements Parcelable {

    protected static String type;

    public SavableView() {
    }


    public static final Creator<SavableView> CREATOR = new Creator<SavableView>() {
        @Override
        public SavableView createFromParcel(Parcel in) {
            String type = in.readString();
            SavableView view = null;
            switch (type) {
                case "BubblePropertyModel":
                    view = new BubblePropertyModel(in);
                    break;
                case "StickerPropertyModel":
                    view = new StickerPropertyModel(in);
                    break;
            }

            return view;
        }

        @Override
        public SavableView[] newArray(int size) {
            return new SavableView[size];
        }
    };

    public SavableView(String simpleName) {
        this.type = simpleName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public String getType() {
        return type;
    }
}
