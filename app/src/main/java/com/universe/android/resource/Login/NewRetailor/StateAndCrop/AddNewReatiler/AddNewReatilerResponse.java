package com.universe.android.resource.Login.NewRetailor.StateAndCrop.AddNewReatiler;

import com.universe.android.web.BaseResponse;

/**
 * Created by gaurav.pandey on 04-03-2018.
 */

public class AddNewReatilerResponse extends BaseResponse<AddNewReatilerResponse> {

    /**
     * errorMsg :
     * statusCode : 200
     * response : {"__v":0,"updatedAt":"2018-03-04T10:49:06.937Z","createdAt":"2018-03-04T10:49:06.937Z","isActive":1,"totalSales":"2323","village_code":"234324","major_crop":"sg1hg2h121j2j","pincode":23423434,"mobile":234234342,"distributer_code":"wer3434","territory_code":"territorycode","state_code":"statecode","name":"test","_id":"5a9bcf228a7d5513989e2759"}
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
         * __v : 0
         * updatedAt : 2018-03-04T10:49:06.937Z
         * createdAt : 2018-03-04T10:49:06.937Z
         * isActive : 1
         * totalSales : 2323
         * village_code : 234324
         * major_crop : sg1hg2h121j2j
         * pincode : 23423434
         * mobile : 234234342
         * distributer_code : wer3434
         * territory_code : territorycode
         * state_code : statecode
         * name : test
         * _id : 5a9bcf228a7d5513989e2759
         */

        private int __v;
        private String updatedAt;
        private String createdAt;
        private int isActive;
        private String totalSales;
        private String village_code;
        private String major_crop;
        private int pincode;
        private int mobile;
        private String distributer_code;
        private String territory_code;
        private String state_code;
        private String name;
        private String _id;

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
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

        public String getTotalSales() {
            return totalSales;
        }

        public void setTotalSales(String totalSales) {
            this.totalSales = totalSales;
        }

        public String getVillage_code() {
            return village_code;
        }

        public void setVillage_code(String village_code) {
            this.village_code = village_code;
        }

        public String getMajor_crop() {
            return major_crop;
        }

        public void setMajor_crop(String major_crop) {
            this.major_crop = major_crop;
        }

        public int getPincode() {
            return pincode;
        }

        public void setPincode(int pincode) {
            this.pincode = pincode;
        }

        public int getMobile() {
            return mobile;
        }

        public void setMobile(int mobile) {
            this.mobile = mobile;
        }

        public String getDistributer_code() {
            return distributer_code;
        }

        public void setDistributer_code(String distributer_code) {
            this.distributer_code = distributer_code;
        }

        public String getTerritory_code() {
            return territory_code;
        }

        public void setTerritory_code(String territory_code) {
            this.territory_code = territory_code;
        }

        public String getState_code() {
            return state_code;
        }

        public void setState_code(String state_code) {
            this.state_code = state_code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }
    }
}
