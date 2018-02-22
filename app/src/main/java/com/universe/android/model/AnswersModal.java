package com.universe.android.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class AnswersModal {
    @PrimaryKey
    private String _id;
    private String answers;
    private String state;
    private String status;
    private String territory;
    private String pincode;
    private String title;
    private String submitbyCD;
    private String submitbyRM;
    private int _v;
    private String responses;
    private String submitbyZM;
    private boolean pendingforCD;
    private boolean inprocessforCD;
    private boolean rejectedforCD;
    private boolean completedforCD;
    private boolean pendingforRM;
    private boolean inprocessforRM;
    private boolean rejectedforRM;
    private boolean completedforRM;
    private boolean pendingforZM;
    private boolean inprocessforZM;
    private boolean rejectedforZM;
    private boolean completedforZM;
    private boolean isSync=true;
    private boolean isUpdate=true;
    private String date;
    private Date createdAt;
    private Date updatedAt;
    private String surveyId;
    private String customerId;
    private String workflow;
    private String contactNo;


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

    public boolean isPendingforCD() {
        return pendingforCD;
    }

    public void setPendingforCD(boolean pendingforCD) {
        this.pendingforCD = pendingforCD;
    }

    public boolean isInprocessforCD() {
        return inprocessforCD;
    }

    public void setInprocessforCD(boolean inprocessforCD) {
        this.inprocessforCD = inprocessforCD;
    }

    public boolean isRejectedforCD() {
        return rejectedforCD;
    }

    public void setRejectedforCD(boolean rejectedforCD) {
        this.rejectedforCD = rejectedforCD;
    }

    public boolean isCompletedforCD() {
        return completedforCD;
    }

    public void setCompletedforCD(boolean completedforCD) {
        this.completedforCD = completedforCD;
    }

    public boolean isPendingforRM() {
        return pendingforRM;
    }

    public void setPendingforRM(boolean pendingforRM) {
        this.pendingforRM = pendingforRM;
    }

    public boolean isInprocessforRM() {
        return inprocessforRM;
    }

    public void setInprocessforRM(boolean inprocessforRM) {
        this.inprocessforRM = inprocessforRM;
    }

    public boolean isRejectedforRM() {
        return rejectedforRM;
    }

    public void setRejectedforRM(boolean rejectedforRM) {
        this.rejectedforRM = rejectedforRM;
    }

    public boolean isCompletedforRM() {
        return completedforRM;
    }

    public void setCompletedforRM(boolean completedforRM) {
        this.completedforRM = completedforRM;
    }

    public boolean isPendingforZM() {
        return pendingforZM;
    }

    public void setPendingforZM(boolean pendingforZM) {
        this.pendingforZM = pendingforZM;
    }

    public boolean isInprocessforZM() {
        return inprocessforZM;
    }

    public void setInprocessforZM(boolean inprocessforZM) {
        this.inprocessforZM = inprocessforZM;
    }

    public boolean isRejectedforZM() {
        return rejectedforZM;
    }

    public void setRejectedforZM(boolean rejectedforZM) {
        this.rejectedforZM = rejectedforZM;
    }

    public boolean isCompletedforZM() {
        return completedforZM;
    }

    public void setCompletedforZM(boolean completedforZM) {
        this.completedforZM = completedforZM;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public String getWorkflow() {
        return workflow;
    }

    public void setWorkflow(String workflow) {
        this.workflow = workflow;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
}
