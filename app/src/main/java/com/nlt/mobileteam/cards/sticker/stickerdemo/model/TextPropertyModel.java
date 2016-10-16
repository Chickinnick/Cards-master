package com.nlt.mobileteam.cards.sticker.stickerdemo.model;

import android.os.Parcel;

public class TextPropertyModel extends SavableView {
    private static final long serialVersionUID = 6339777989485920188L;

    private long bubbleId;

    private String text;

    private float xLocation;
    //y坐标
    private float yLocation;
    //角度
    private float degree;
    //缩放值
    private float scaling;
    //气泡顺序
    private int order;
    private float[] matrixValues;
    private int bgColor;
    private float XDegree;
    private float YDegree;
    private float XScaling;
    private float YScaling;

    public int getBgColor() {
        return bgColor;
    }

    public long getBubbleId() {
        return bubbleId;
    }

    public void setBubbleId(long bubbleId) {
        this.bubbleId = bubbleId;
    }

    public float[] getMatrixValues() {
        return matrixValues;
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


    public float getYScaling() {
        return YScaling;
    }

    public float getXScaling() {
        return XScaling;
    }

    public float getYDegree() {
        return YDegree;
    }

    public float getXDegree() {
        return XDegree;
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
        XDegree = in.readFloat();
        YDegree = in.readFloat();
        scaling = in.readFloat();
        XScaling = in.readFloat();
        YScaling = in.readFloat();
        order = in.readInt();
        matrixValues = new float[9];
        in.readFloatArray(matrixValues);
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
        dest.writeFloat(XDegree);
        dest.writeFloat(YDegree);
        dest.writeFloat(scaling);
        dest.writeFloat(XScaling);
        dest.writeFloat(YScaling);
        dest.writeInt(order);
        dest.writeFloatArray(matrixValues);
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

    public void setMatrixValues(float[] matrixValues) {
        this.matrixValues = matrixValues;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }


    public void setXDegree(float XDegree) {
        this.XDegree = XDegree;
    }

    public void setYDegree(float YDegree) {
        this.YDegree = YDegree;
    }

    public void setXScaling(float XScaling) {
        this.XScaling = XScaling;
    }

    public void setYScaling(float YScaling) {
        this.YScaling = YScaling;
    }
}
