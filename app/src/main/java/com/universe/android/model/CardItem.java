package com.universe.android.model;


public class CardItem {

    private int mTextResource;
    private int mTitleResource;

    public CardItem( int text) {

        mTextResource = text;
    }

    public int getText() {
        return mTextResource;
    }

    public int getTitle() {
        return mTitleResource;
    }
}
