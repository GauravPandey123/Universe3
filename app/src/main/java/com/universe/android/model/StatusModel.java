package com.universe.android.model;


/**
 * Created by gaurav.pandey on 30-01-2018.
 */

public class StatusModel {
    private String statusString;

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public int getImageViewId() {
        return imageViewId;
    }

    public void setImageViewId(int imageViewId) {
        this.imageViewId = imageViewId;
    }

    private int imageViewId;

    public StatusModel(String statusString, int imageViewId) {
        this.statusString = statusString;
        this.imageViewId = imageViewId;
    }

}
