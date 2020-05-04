package com.ama.lax.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Exercise implements Parcelable {

    private String id;
    private String name;
    private int img;

    public Exercise() {
    }

    public Exercise(String id, String name, int img) {
        this.id = id;
        this.name = name;
        this.img = img;
    }

    protected Exercise(Parcel in) {
        id = in.readString();
        name = in.readString();
        img = in.readInt();
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(img);
    }
}
