package com.universe.android.model;

import android.animation.TimeInterpolator;

public class ItemModel {
    public final String description;


    public final TimeInterpolator interpolator;

    public ItemModel(String description, TimeInterpolator interpolator) {
        this.description = description;
        this.interpolator = interpolator;
    }
}