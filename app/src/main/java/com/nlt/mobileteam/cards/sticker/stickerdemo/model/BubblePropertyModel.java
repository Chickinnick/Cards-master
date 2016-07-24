package com.nlt.mobileteam.cards.sticker.stickerdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Abner on 15/6/11.
 * QQ 230877476
 * Email nimengbo@gmail.com
 */
public class BubblePropertyModel implements Parcelable {
    private static final long serialVersionUID = 6339777989485920188L;
    //气泡id
    private long bubbleId;
    //文本
    private String text;
    //x坐标
    private float xLocation;
    //y坐标
    private float yLocation;
    //角度
    private float degree;
    //缩放值
    private float scaling;
    //气泡顺序
    private int order;

    public long getBubbleId() {
        return bubbleId;
    }

    public void setBubbleId(long bubbleId) {
        this.bubbleId = bubbleId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getxLocation() {
        return xLocation;
    }

    public void setxLocation(float xLocation) {
        this.xLocation = xLocation;
    }

    public float getyLocation() {
        return yLocation;
    }

    public void setyLocation(float yLocation) {
        this.yLocation = yLocation;
    }

    public float getDegree() {
        return degree;
    }

    public void setDegree(float degree) {
        this.degree = degree;
    }

    public float getScaling() {
        return scaling;
    }

    public void setScaling(float scaling) {
        this.scaling = scaling;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public BubblePropertyModel() {
    }

    protected BubblePropertyModel(Parcel in) {
        bubbleId = in.readLong();
        text = in.readString();
        xLocation = in.readFloat();
        yLocation = in.readFloat();
        degree = in.readFloat();
        scaling = in.readFloat();
        order = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(bubbleId);
        dest.writeString(text);
        dest.writeFloat(xLocation);
        dest.writeFloat(yLocation);
        dest.writeFloat(degree);
        dest.writeFloat(scaling);
        dest.writeInt(order);
    }

    @Override
    public String toString() {
        return "BubblePropertyModel{" +
                "bubbleId=" + bubbleId +
                ", text='" + text + '\'' +
                ", xLocation=" + xLocation +
                ", yLocation=" + yLocation +
                ", degree=" + degree +
                ", scaling=" + scaling +
                ", order=" + order +
                '}';
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<BubblePropertyModel> CREATOR = new Parcelable.Creator<BubblePropertyModel>() {
        @Override
        public BubblePropertyModel createFromParcel(Parcel in) {
            return new BubblePropertyModel(in);
        }

        @Override
        public BubblePropertyModel[] newArray(int size) {
            return new BubblePropertyModel[size];
        }
    };
}
