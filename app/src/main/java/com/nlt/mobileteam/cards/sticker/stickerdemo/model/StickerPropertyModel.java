package com.nlt.mobileteam.cards.sticker.stickerdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Abner on 15/6/11.
 * QQ 230877476
 * Email nimengbo@gmail.com
 */
public class StickerPropertyModel extends SavableView {
    private static final long serialVersionUID = 3800737478616389410L;

    //贴纸id
    private long stickerId;
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

    //水平镜像 1镜像 2未镜像
    private int horizonMirror;

    //贴纸PNG URL
    private String stickerURL;
    private float[] matrixValues;

    public int getHorizonMirror() {
        return horizonMirror;
    }

    public void setHorizonMirror(int horizonMirror) {
        this.horizonMirror = horizonMirror;
    }

    public String getStickerURL() {
        return stickerURL;
    }

    public void setStickerURL(String stickerURL) {
        this.stickerURL = stickerURL;
    }

    public long getStickerId() {
        return stickerId;
    }

    public void setStickerId(long stickerId) {
        this.stickerId = stickerId;
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


    public StickerPropertyModel() {
    }

    protected StickerPropertyModel(Parcel in) {
        stickerId = in.readLong();
        text = in.readString();
        xLocation = in.readFloat();
        yLocation = in.readFloat();
        degree = in.readFloat();
        scaling = in.readFloat();
        order = in.readInt();
        horizonMirror = in.readInt();
        stickerURL = in.readString();
        matrixValues = new float[9];
        in.readFloatArray(matrixValues);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getType());
        dest.writeLong(stickerId);
        dest.writeString(text);
        dest.writeFloat(xLocation);
        dest.writeFloat(yLocation);
        dest.writeFloat(degree);
        dest.writeFloat(scaling);
        dest.writeInt(order);
        dest.writeInt(horizonMirror);
        dest.writeString(stickerURL);
        dest.writeFloatArray(matrixValues);
    }

    @Override
    public String toString() {
        return "StickerPropertyModel{" +
                "stickerId=" + stickerId +
                ", text='" + text + '\'' +
                ", xLocation=" + xLocation +
                ", yLocation=" + yLocation +
                ", degree=" + degree +
                ", scaling=" + scaling +
                ", order=" + order +
                ", horizonMirror=" + horizonMirror +
                ", stickerURL='" + stickerURL + '\'' +
                '}';
    }

    public static final Parcelable.Creator<StickerPropertyModel> CREATOR = new Parcelable.Creator<StickerPropertyModel>() {
        @Override
        public StickerPropertyModel createFromParcel(Parcel in) {
            type = in.readString();

            return new StickerPropertyModel(in);
        }

        @Override
        public StickerPropertyModel[] newArray(int size) {
            return new StickerPropertyModel[size];
        }
    };

    public void setMatrixValues(float[] matrixValues) {
        this.matrixValues = matrixValues;
    }

    public float[] getMatrixValues() {
        return matrixValues;
    }
}