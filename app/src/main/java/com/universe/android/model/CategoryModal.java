package com.universe.android.model;

import java.util.ArrayList;
import java.util.Date;


public class CategoryModal {

    private String id;
    private String categoryType;
    private String categoryName;
    private String categoryAnswered;
    private Date description;
    private String isViewByZM;
    private String isViewByRequester;
    private String isViewByApproval1;
    private String isViewByApproval2;
    private String isViewByApproval3;
    private String isViewByApproval4;
    private String isViewByApproval5;
    private String isViewByApproval6;



    private String contactNo;

    private String address;


    private String status;

    private boolean isActive;

    private Date expiryDate;
    private Date createdAt;
    private Date updatedAt;
    private String surveyId;
    private String clientId;
    private String customerId;
    private String questionCount;
    private String image;
    private ArrayList<Questions> questions;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Date getDescription() {
        return description;
    }

    public void setDescription(Date description) {
        this.description = description;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Questions> questions) {
        this.questions = questions;
    }

    public String getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(String questionCount) {
        this.questionCount = questionCount;
    }

    public String getCategoryAnswered() {
        return categoryAnswered;
    }

    public void setCategoryAnswered(String categoryAnswered) {
        this.categoryAnswered = categoryAnswered;
    }

    public String getIsViewByZM() {
        return isViewByZM;
    }

    public void setIsViewByZM(String isViewByZM) {
        this.isViewByZM = isViewByZM;
    }

    public String getIsViewByRequester() {
        return isViewByRequester;
    }

    public void setIsViewByRequester(String isViewByRequester) {
        this.isViewByRequester = isViewByRequester;
    }

    public String getIsViewByApproval1() {
        return isViewByApproval1;
    }

    public void setIsViewByApproval1(String isViewByApproval1) {
        this.isViewByApproval1 = isViewByApproval1;
    }

    public String getIsViewByApproval2() {
        return isViewByApproval2;
    }

    public void setIsViewByApproval2(String isViewByApproval2) {
        this.isViewByApproval2 = isViewByApproval2;
    }

    public String getIsViewByApproval3() {
        return isViewByApproval3;
    }

    public void setIsViewByApproval3(String isViewByApproval3) {
        this.isViewByApproval3 = isViewByApproval3;
    }

    public String getIsViewByApproval4() {
        return isViewByApproval4;
    }

    public void setIsViewByApproval4(String isViewByApproval4) {
        this.isViewByApproval4 = isViewByApproval4;
    }

    public String getIsViewByApproval5() {
        return isViewByApproval5;
    }

    public void setIsViewByApproval5(String isViewByApproval5) {
        this.isViewByApproval5 = isViewByApproval5;
    }

    public String getIsViewByApproval6() {
        return isViewByApproval6;
    }

    public void setIsViewByApproval6(String isViewByApproval6) {
        this.isViewByApproval6 = isViewByApproval6;
    }
}
