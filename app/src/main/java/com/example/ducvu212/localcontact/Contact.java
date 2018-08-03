package com.example.ducvu212.localcontact;

public class Contact {

    private String mName;
    private String mPhone;
    private String mPhoto;
    private int mFavorite;

    public Contact(String name, String phone, String photo, int favorite) {
        mName = name;
        mPhone = phone;
        mPhoto = photo;
        mFavorite = favorite;
    }

    public int getFavorite() {
        return mFavorite;
    }

    public void setFavorite(int favorite) {
        mFavorite = favorite;
    }

    public Contact() {

    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
    }
}
