package com.universe.android.realmbean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gaurav.pandey on 23-02-2018.
 */

public class RealmCrystalDoctor extends RealmObject {
    private String loginDetails;

    public String getDoctorAssign() {
        return doctorAssign;
    }

    public void setDoctorAssign(String doctorAssign) {
        this.doctorAssign = doctorAssign;
    }

    private String doctorAssign;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    private String details;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey
    private int id;

    public String getPending() {
        return pending;
    }

    public void setPending(String pending) {
        this.pending = pending;
    }

    private String pending;

    public void setLoginDetails(String loginDetails) {
        this.loginDetails = loginDetails;
    }

    public void setSurveyDetails(String surveyDetails) {
        this.surveyDetails = surveyDetails;
    }

    public String getSurveyDetails() {
        return surveyDetails;
    }

    private String surveyDetails;
}
