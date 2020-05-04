package com.ama.lax.models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String id;
    private String user;
    private String email;
    private String phone;
    private String img;
    private String id_num;
    private String birth_date;
    private String gender;
    private String height;
    private String weight;
    private String disease;
    private String score;

    public User() {
    }


    public User(String id, String user, String email, String phone, String img, String id_num, String birth_date, String gender, String height, String weight, String disease, String score) {
        this.id = id;
        this.user = user;
        this.email = email;
        this.phone = phone;
        this.img = img;
        this.id_num = id_num;
        this.birth_date = birth_date;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.disease = disease;
        this.score = score;
    }

    protected User(Parcel in) {
        id = in.readString();
        user = in.readString();
        email = in.readString();
        phone = in.readString();
        img = in.readString();
        id_num = in.readString();
        birth_date = in.readString();
        gender = in.readString();
        height = in.readString();
        weight = in.readString();
        disease = in.readString();
        score = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId_num() {
        return id_num;
    }

    public void setId_num(String id_num) {
        this.id_num = id_num;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(user);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(img);
        dest.writeString(id_num);
        dest.writeString(birth_date);
        dest.writeString(gender);
        dest.writeString(height);
        dest.writeString(weight);
        dest.writeString(disease);
        dest.writeString(score);
    }
}
