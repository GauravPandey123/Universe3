package com.universe.android.model;

/**
 * Created by gaurav.pandey on 21-03-2018.
 */

public class SurveyReportModel {
    public String getTitleString() {
        return titleString;
    }

    public void setTitleString(String titleString) {
        this.titleString = titleString;
    }

    private String titleString;


    public SurveyReportModel(String title) {
        this.titleString = title;

    }
}
