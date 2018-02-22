package com.universe.android.realmbean;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class RealmCategoryAnswers extends RealmObject {
    @PrimaryKey
    private String _id;
    private String answers;
    private String submitbyCD;
    private String submitbyRM;
    private int _v;
    private String responses;
    private String submitbyZM;
    private boolean isSync=true;
    private boolean isUpdate=true;
    private Date date;
    private Date createdAt;
    private Date updatedAt;
    private String surveyId;
    private String customerId;
    private String categoryId;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public String getSubmitbyCD() {
        return submitbyCD;
    }

    public void setSubmitbyCD(String submitbyCD) {
        this.submitbyCD = submitbyCD;
    }

    public String getSubmitbyRM() {
        return submitbyRM;
    }

    public void setSubmitbyRM(String submitbyRM) {
        this.submitbyRM = submitbyRM;
    }

    public int get_v() {
        return _v;
    }

    public void set_v(int _v) {
        this._v = _v;
    }

    public String getResponses() {
        return responses;
    }

    public void setResponses(String responses) {
        this.responses = responses;
    }

    public String getSubmitbyZM() {
        return submitbyZM;
    }

    public void setSubmitbyZM(String submitbyZM) {
        this.submitbyZM = submitbyZM;
    }

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
