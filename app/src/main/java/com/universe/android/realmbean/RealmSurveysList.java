package com.universe.android.realmbean;

import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class RealmSurveysList extends RealmObject {
    @PrimaryKey
    private int _id;
    private RealmSurveys survey;

    private RealmList<RealmWorkFlow> workflow;


    public RealmSurveys getSurvey() {
        return survey;
    }

    public void setSurvey(RealmSurveys survey) {
        this.survey = survey;
    }



    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }


    public RealmList<RealmWorkFlow> getWorkflow() {
        return workflow;
    }

    public void setWorkflow(RealmList<RealmWorkFlow> workflow) {
        this.workflow = workflow;
    }
}
