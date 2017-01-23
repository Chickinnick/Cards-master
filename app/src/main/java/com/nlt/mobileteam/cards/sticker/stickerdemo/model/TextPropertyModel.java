package com.nlt.mobileteam.cards.sticker.stickerdemo.model;

import android.os.Parcel;

public class TextPropertyModel extends SavableView {

    private long bubbleId;

    private String text;

    private float xLocation;

    private float yLocation;

    private float degree;

    private float scaling;

    private int order;

    private int bgColor;

    private float angle;

    private float scaleWidth;
    private float scaleHeight;

    public int getBgColor() {
        return bgColor;
    }

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


    public float getScaleHeight() {
        return scaleHeight;
    }

    public float getScaleWidth() {
        return scaleWidth;
    }

    public float getAngle() {
        return angle;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public TextPropertyModel() {
        super(TextPropertyModel.class.getSimpleName());
    }

    protected TextPropertyModel(Parcel in) {
        bubbleId = in.readLong();
        text = in.readString();
        xLocation = in.readFloat();
        yLocation = in.readFloat();
        degree = in.readFloat();
        angle = in.readFloat();
        scaling = in.readFloat();
        scaleWidth = in.readFloat();
        scaleHeight = in.readFloat();
        order = in.readInt();
        bgColor = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getType());
        dest.writeLong(bubbleId);
        dest.writeString(text);
        dest.writeFloat(xLocation);
        dest.writeFloat(yLocation);

        dest.writeFloat(degree);
        dest.writeFloat(angle);
        dest.writeFloat(scaling);
        dest.writeFloat(scaleWidth);
        dest.writeFloat(scaleHeight);
        dest.writeInt(order);
        dest.writeInt(bgColor);
    }

    @Override
    public String toString() {
        return "BubblePropertyModel{" +
                "bubbleId=" + bubbleId +
                ", text='" + text +
                ", bg color: " + bgColor +
                '}';
    }

    public static Creator<TextPropertyModel> CREATOR = new Creator<TextPropertyModel>() {
        @Override
        public TextPropertyModel createFromParcel(Parcel in) {
            type = in.readString();
            return new TextPropertyModel(in);
        }

        @Override
        public TextPropertyModel[] newArray(int size) {
            return new TextPropertyModel[size];
        }
    };


    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }


    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setScaleWidth(float XScaling) {
        this.scaleWidth = XScaling;
    }

    public void setScaleHeight(float scaleHeight) {
        this.scaleHeight = scaleHeight;
    }
}
