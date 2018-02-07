package com.universe.android.resource.Login;

import com.google.gson.annotations.SerializedName;
import com.universe.android.web.BaseResponse;

import java.util.List;

/**
 * Created by gaurav.pandey on 06-02-2018.
 */

public class LoginResponse extends BaseResponse <LoginResponse>{

    /**
     * errorMsg :
     * statusCode : 200
     * response : {"_id":"5a799e932779e608b435a279","updatedAt":"2018-02-06T12:25:19.739Z","createdAt":"2018-02-06T12:24:51.458Z","designation":"RM","address":"","loc":[21,77],"long":"21","lat":"77","isActive":1,"picture":"","designationLevel":"3","phone":1234567,"password":"12345","email":"gaurav.pandey@quayintech.com","name":"gaurav","__v":0,"accessToken":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI1YTc5OWU5MzI3NzllNjA4YjQzNWEyNzkiLCJkYXRlIjoxNTE3OTE5OTE5NzM3fQ.ZW2U3rVBy8Prv8erIr4xpZijHabmjKpOCYodEIT0LiU"}
     */

    private String errorMsg;
    private int statusCode;
    private ResponseBean response;

    @Override
    public boolean isValid(String condition, boolean isStrict) {
        return true;
    }

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
         * _id : 5a799e932779e608b435a279
         * updatedAt : 2018-02-06T12:25:19.739Z
         * createdAt : 2018-02-06T12:24:51.458Z
         * designation : RM
         * address :
         * loc : [21,77]
         * long : 21
         * lat : 77
         * isActive : 1
         * picture :
         * designationLevel : 3
         * phone : 1234567
         * password : 12345
         * email : gaurav.pandey@quayintech.com
         * name : gaurav
         * __v : 0
         * accessToken : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI1YTc5OWU5MzI3NzllNjA4YjQzNWEyNzkiLCJkYXRlIjoxNTE3OTE5OTE5NzM3fQ.ZW2U3rVBy8Prv8erIr4xpZijHabmjKpOCYodEIT0LiU
         */

        private String _id;
        private String updatedAt;
        private String createdAt;
        private String designation;
        private String address;
        @SerializedName("long")
        private String longX;
        private String lat;
        private int isActive;
        private String picture;
        private String designationLevel;
        private int phone;
        private String password;
        private String email;
        private String name;
        private int __v;
        private String accessToken;
        private List<Integer> loc;

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

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLongX() {
            return longX;
        }

        public void setLongX(String longX) {
            this.longX = longX;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public int getIsActive() {
            return isActive;
        }

        public void setIsActive(int isActive) {
            this.isActive = isActive;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getDesignationLevel() {
            return designationLevel;
        }

        public void setDesignationLevel(String designationLevel) {
            this.designationLevel = designationLevel;
        }

        public int getPhone() {
            return phone;
        }

        public void setPhone(int phone) {
            this.phone = phone;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public List<Integer> getLoc() {
            return loc;
        }

        public void setLoc(List<Integer> loc) {
            this.loc = loc;
        }
    }
}
