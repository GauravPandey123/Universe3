package com.universe.android.model;

import com.universe.android.resource.Login.CrystalReport.CrystalReportResponse;

/**
 * Created by gaurav.pandey on 12-03-2018.
 */

public  class ListBean {
    /**
     * _id : 5aa24c5a0001850d1c9d5c3c
     * date : 2018-03-09T07:00:00.328Z
     * customerId : {"_id":"5a7da291f0249f1038c0b3f3","updatedAt":"2018-03-09T08:37:01.280Z","createdAt":"2018-02-09T13:30:57.529Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"https://s3.ap-south-1.amazonaws.com/images.prs.co.in/profile_pics/Photo_1520338063391.png","createdBy":"asdas","address":"udhyog vihar phase 4 gurgaon","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Chemical India","__v":0,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer","locationSet":"5aa247ad0001850d1c9d5c3a"}
     */

    private String _id;
    private String date;
    private CustomerIdBean customerId;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ListBean.CustomerIdBean getCustomerId() {
        return customerId;
    }

    public void setCustomerId(ListBean.CustomerIdBean customerId) {
        this.customerId = customerId;
    }

    public static class CustomerIdBean {
        /**
         * _id : 5a7da291f0249f1038c0b3f3
         * updatedAt : 2018-03-09T08:37:01.280Z
         * createdAt : 2018-02-09T13:30:57.529Z
         * isActive : 0
         * status : 0
         * clientId : asdasd
         * surveyId : asdas
         * image : https://s3.ap-south-1.amazonaws.com/images.prs.co.in/profile_pics/Photo_1520338063391.png
         * createdBy : asdas
         * address : udhyog vihar phase 4 gurgaon
         * contactNo : 89999998765
         * description : asd
         * contactPerson : asdsa
         * name : Chemical India
         * __v : 0
         * state : WB
         * territory : Amtala
         * pincode : 9999
         * customer : CrystalCustomer
         * locationSet : 5aa247ad0001850d1c9d5c3a
         */

        private String _id;
        private String updatedAt;
        private String createdAt;
        private int isActive;
        private String status;
        private String clientId;
        private String surveyId;
        private String image;
        private String createdBy;
        private String address;
        private long contactNo;
        private String description;
        private String contactPerson;
        private String name;
        private int __v;
        private String state;
        private String territory;
        private String pincode;
        private String customer;
        private String locationSet;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public int getIsActive() {
            return isActive;
        }

        public void setIsActive(int isActive) {
            this.isActive = isActive;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getSurveyId() {
            return surveyId;
        }

        public void setSurveyId(String surveyId) {
            this.surveyId = surveyId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public long getContactNo() {
            return contactNo;
        }

        public void setContactNo(long contactNo) {
            this.contactNo = contactNo;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getContactPerson() {
            return contactPerson;
        }

        public void setContactPerson(String contactPerson) {
            this.contactPerson = contactPerson;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
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

        public String getCustomer() {
            return customer;
        }

        public void setCustomer(String customer) {
            this.customer = customer;
        }

        public String getLocationSet() {
            return locationSet;
        }

        public void setLocationSet(String locationSet) {
            this.locationSet = locationSet;
        }
    }
}