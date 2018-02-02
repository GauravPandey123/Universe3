package com.universe.android.listneners;


import android.support.v7.widget.CardView;

public interface CardInterface {

    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}
