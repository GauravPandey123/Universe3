package com.universe.android.resource.Login.LocationUpdate;

import com.google.gson.annotations.SerializedName;
import com.universe.android.web.BaseResponse;

/**
 * Created by gaurav.pandey on 20-02-2018.
 */

public class UpDateLocationResponse extends BaseResponse<UpDateLocationResponse> {

    /**
     * errorMsg :
     * statusCode : 200
     * response : {"__v":0,"updatedAt":"2018-02-21T05:46:36.462Z","createdAt":"2018-02-21T05:46:36.462Z","location":"Unnamed Road, Chikal Pat, Madhya Pradesh 461228, India","type":"shop","loc":[22,77],"long":"77","lat":"22","user":"1234567","_id":"5a8d07bcfa4c1512c856ca33"}
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
         * updatedAt : 2018-02-21T05:46:36.462Z
         * createdAt : 2018-02-21T05:46:36.462Z
         * location : Unnamed Road, Chikal Pat, Madhya Pradesh 461228, India
         * type : shop
         * loc : [22,77]
         * long : 77
         * lat : 22
         * user : 1234567
         * _id : 5a8d07bcfa4c1512c856ca33
         */

        private int __v;
        private String updatedAt;
        private String createdAt;
        private String location;
        private String type;
        @SerializedName("long")
        private String longX;
        private String lat;
        private String user;
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

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }


    }
}
