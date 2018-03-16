package com.universe.android.resource.Login.LocationUpdate;

import com.google.gson.annotations.SerializedName;
import com.universe.android.web.BaseResponse;

import java.util.List;

/**
 * Created by gaurav.pandey on 20-02-2018.
 */

public class UpDateLocationResponse extends BaseResponse<UpDateLocationResponse> {


    /**
     * errorMsg :
     * statusCode : 200
     * response : {"location":{"_id":"5a811ccfa6f7eb1200adcbd9","updatedAt":"2018-03-07T13:38:34.785Z","createdAt":"2018-02-12T04:49:19.490Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"https://s3.ap-south-1.amazonaws.com/images.prs.co.in/profile_pics/Photo_1520426934461.png","createdBy":"asdas","address":"asdsa","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Ganga Pesticides","__v":0,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer","locationSet":{"_id":"5a9feb5ae54f6d101c98e2e9","updatedAt":"2018-03-07T13:38:34.767Z","createdAt":"2018-03-07T13:38:34.767Z","location":"TECHNICAL PARADISE, SECTOR 56, GURGOAN, Basai Rd, Jacobpura, Sector 12, Gurugram, Haryana 122006, India","customerId":"5a811ccfa6f7eb1200adcbd9","type":"customer","loc":[28.4595,77.0266],"long":"77.0266","lat":"28.4595","user":"5a8eb8b82741361f5827afb5","__v":0}},"locationSet":true}
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
         * location : {"_id":"5a811ccfa6f7eb1200adcbd9","updatedAt":"2018-03-07T13:38:34.785Z","createdAt":"2018-02-12T04:49:19.490Z","isActive":0,"status":"0","clientId":"asdasd","surveyId":"asdas","image":"https://s3.ap-south-1.amazonaws.com/images.prs.co.in/profile_pics/Photo_1520426934461.png","createdBy":"asdas","address":"asdsa","contactNo":89999998765,"description":"asd","contactPerson":"asdsa","name":"Ganga Pesticides","__v":0,"state":"WB","territory":"Amtala","pincode":"9999","customer":"CrystalCustomer","locationSet":{"_id":"5a9feb5ae54f6d101c98e2e9","updatedAt":"2018-03-07T13:38:34.767Z","createdAt":"2018-03-07T13:38:34.767Z","location":"TECHNICAL PARADISE, SECTOR 56, GURGOAN, Basai Rd, Jacobpura, Sector 12, Gurugram, Haryana 122006, India","customerId":"5a811ccfa6f7eb1200adcbd9","type":"customer","loc":[28.4595,77.0266],"long":"77.0266","lat":"28.4595","user":"5a8eb8b82741361f5827afb5","__v":0}}
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
             * _id : 5a811ccfa6f7eb1200adcbd9
             * updatedAt : 2018-03-07T13:38:34.785Z
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
             * pincode : 9999
             * customer : CrystalCustomer
             * locationSet : {"_id":"5a9feb5ae54f6d101c98e2e9","updatedAt":"2018-03-07T13:38:34.767Z","createdAt":"2018-03-07T13:38:34.767Z","location":"TECHNICAL PARADISE, SECTOR 56, GURGOAN, Basai Rd, Jacobpura, Sector 12, Gurugram, Haryana 122006, India","customerId":"5a811ccfa6f7eb1200adcbd9","type":"customer","loc":[28.4595,77.0266],"long":"77.0266","lat":"28.4595","user":"5a8eb8b82741361f5827afb5","__v":0}
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
            private LocationSetBean locationSet;

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

            public LocationSetBean getLocationSet() {
                return locationSet;
            }

            public void setLocationSet(LocationSetBean locationSet) {
                this.locationSet = locationSet;
            }

            public static class LocationSetBean {
                /**
                 * _id : 5a9feb5ae54f6d101c98e2e9
                 * updatedAt : 2018-03-07T13:38:34.767Z
                 * createdAt : 2018-03-07T13:38:34.767Z
                 * location : TECHNICAL PARADISE, SECTOR 56, GURGOAN, Basai Rd, Jacobpura, Sector 12, Gurugram, Haryana 122006, India
                 * customerId : 5a811ccfa6f7eb1200adcbd9
                 * type : customer
                 * loc : [28.4595,77.0266]
                 * long : 77.0266
                 * lat : 28.4595
                 * user : 5a8eb8b82741361f5827afb5
                 * __v : 0
                 */

                private String _id;
                private String updatedAt;
                private String createdAt;
                private String location;
                private String customerId;
                private String type;
                @SerializedName("long")
                private String longX;
                private String lat;
                private String user;
                private int __v;
                private List<Double> loc;

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

                public int get__v() {
                    return __v;
                }

                public void set__v(int __v) {
                    this.__v = __v;
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
}
