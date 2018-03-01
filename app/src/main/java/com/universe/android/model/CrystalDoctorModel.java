package com.universe.android.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by gaurav.pandey on 23-02-2018.
 */

public class CrystalDoctorModel {
    private int id;
    private String name;
    private String contactPerson;
    private String total;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    private String completed;

    public ArrayList<String> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<String> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }

    private ArrayList<String> allItemsInSection;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    public String getInprogress() {
        return inprogress;
    }

    public void setInprogress(String inprogress) {
        this.inprogress = inprogress;
    }

    public String getCrystalCustomer() {
        return CrystalCustomer;
    }

    public void setCrystalCustomer(String crystalCustomer) {
        CrystalCustomer = crystalCustomer;
    }

    public String getNewRetailer() {
        return newRetailer;
    }

    public void setNewRetailer(String newRetailer) {
        this.newRetailer = newRetailer;
    }

    public String getDoctorAssign() {
        return doctorAssign;
    }

    public void setDoctorAssign(String doctorAssign) {
        this.doctorAssign = doctorAssign;
    }

    private String target;
    private String submitted;
    private String inprogress;
    private String CrystalCustomer;
    private String newRetailer;
    private String doctorAssign;


    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    private String expiryDate;

    private String createBy;

    private Date updatedAt;


    private Date createdAt;

    private String description;

    private String contactNo;
    private String address;

    private boolean isActive;
    private String image;
    private String surveyId;
    private String clientId;
    private String state;
    private String status;
    private String territory;
    private String pincode;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }


    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;
}
