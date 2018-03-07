package com.universe.android.resource.Login.LocationUpdate;

import com.google.gson.annotations.SerializedName;
import com.universe.android.web.BaseResponse;

/**
 * Created by gaurav.pandey on 20-02-2018.
 */

public class UpDateLocationResponse extends BaseResponse<UpDateLocationResponse> {


    @Override
    public boolean isValid(String condition, boolean isStrict) {
        return true;
    }

    /**
     * errorMsg :
     * statusCode : 200
     * response : {"_id":"5a811ccfa6f7eb1200adcbd9","updatedAt":"2018-03-07T12:48:54.793Z","createdAt":"2018-02-12T04:49:19.490Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"https://s3.ap-south-1.amazonaws.com/images.prs.co.in/profile_pics/Photo_1520426934461.png","createdBy":"asdas","address":"asdsa","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Ganga Pesticides","__v":0,"state":"WB","territory":"Amtala","pinco de":"9999","customer":"CrystalCustomer","locationSet":"5a9fdda58ccccf04bcf7f1ac"}
     */

    private String errorMsg;
    private int statusCode;
    private ResponseBean response;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * _id : 5a811ccfa6f7eb1200adcbd9
         * updatedAt : 2018-03-07T12:48:54.793Z
         * createdAt : 2018-02-12T04:49:19.490Z
         * isActive : 0
         * status : 0
         * clientId : asdasd
         * surveyId : asdas
         * image : https://s3.ap-south-1.amazonaws.com/images.prs.co.in/profile_pics/Photo_1520426934461.png
         * createdBy : asdas
         * address : asdsa
         * contactNo : 89999998765
         * description : asd
         * contactPerson : asdsa
         * name : Ganga Pesticides
         * __v : 0
         * state : WB
         * territory : Amtala
         * pinco de : 9999
         * customer : CrystalCustomer
         * locationSet : 5a9fdda58ccccf04bcf7f1ac
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
        @SerializedName("pinco de")
        private String _$PincoDe190; // FIXME check this code
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

        public String get_$PincoDe190() {
            return _$PincoDe190;
        }

        public void set_$PincoDe190(String _$PincoDe190) {
            this._$PincoDe190 = _$PincoDe190;
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
