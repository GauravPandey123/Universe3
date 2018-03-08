package com.universe.android.resource.Login.LocationUpdate;

import com.google.gson.annotations.SerializedName;
import com.universe.android.web.BaseResponse;

import java.util.List;

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
     * response : {"location":{"__v":0,"updatedAt":"2018-03-07T13:13:06.244Z","createdAt":"2018-03-07T13:13:06.244Z","location":"TECHNICAL PARADISE, SECTOR 56, GURGOAN, Basai Rd, Jacobpura, Sector 12, Gurugram, Haryana 122006, India","customerId":"5a82dbb7f08a61114407e578","type":"customer","loc":[28.4595,77.0266],"long":"77.0266","lat":"28.4595","user":"5a8eb8b82741361f5827afb5","_id":"5a9fe562e7b8041074e104bf"},"locationSet":true}
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
         * location : {"__v":0,"updatedAt":"2018-03-07T13:13:06.244Z","createdAt":"2018-03-07T13:13:06.244Z","location":"TECHNICAL PARADISE, SECTOR 56, GURGOAN, Basai Rd, Jacobpura, Sector 12, Gurugram, Haryana 122006, India","customerId":"5a82dbb7f08a61114407e578","type":"customer","loc":[28.4595,77.0266],"long":"77.0266","lat":"28.4595","user":"5a8eb8b82741361f5827afb5","_id":"5a9fe562e7b8041074e104bf"}
         * locationSet : true
         */

        private LocationBean location;
        private boolean locationSet;

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public boolean isLocationSet() {
            return locationSet;
        }

        public void setLocationSet(boolean locationSet) {
            this.locationSet = locationSet;
        }

        public static class LocationBean {
            /**
             * __v : 0
             * updatedAt : 2018-03-07T13:13:06.244Z
             * createdAt : 2018-03-07T13:13:06.244Z
             * location : TECHNICAL PARADISE, SECTOR 56, GURGOAN, Basai Rd, Jacobpura, Sector 12, Gurugram, Haryana 122006, India
             * customerId : 5a82dbb7f08a61114407e578
             * type : customer
             * loc : [28.4595,77.0266]
             * long : 77.0266
             * lat : 28.4595
             * user : 5a8eb8b82741361f5827afb5
             * _id : 5a9fe562e7b8041074e104bf
             */

            private int __v;
            private String updatedAt;
            private String createdAt;
            private String location;
            private String customerId;
            private String type;
            @SerializedName("long")
            private String longX;
            private String lat;
            private String user;
            private String _id;
            private List<Double> loc;

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

            public String getCustomerId() {
                return customerId;
            }

            public void setCustomerId(String customerId) {
                this.customerId = customerId;
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

            public List<Double> getLoc() {
                return loc;
            }

            public void setLoc(List<Double> loc) {
                this.loc = loc;
            }
        }
    }
}
