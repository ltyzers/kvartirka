package com.example.kvartirka;

class Model {

    private String [] mStringArray;
    private String mImageUrl;
    private String mFlatAddress;
    private int mDayPrice;
    private String mTitle;

    Model(String imageUrl, String flatAddress, int dayPrice, String[] stringArray, String title) {
        mImageUrl = imageUrl;
        mFlatAddress = flatAddress;
        mDayPrice = dayPrice;
        mStringArray = stringArray;
        mTitle = title;
    }

    String getImageUrl() {
        return mImageUrl;
    }

    String getFlatAddress() {
        return mFlatAddress;
    }

    int getDayPrice() {
        return mDayPrice;
    }

    String[] getStringArray() {
        return mStringArray;
    }

    String getTitle() {
        return mTitle;
    }
}