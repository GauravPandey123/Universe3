package com.universe.android.resource.Login.login;

import com.universe.android.web.BaseResponse;

import java.util.List;

/**
 * Created by gaurav.pandey on 06-02-2018.
 */

public class LoginResponse extends BaseResponse<LoginResponse> {

    @Override
    public boolean isValid(String condition, boolean isStrict) {
        return true;
    }

    /**
     * errorMsg :
     * statusCode : 200
     * response : {"report":{"report":"report","type":"zm","detail":{"_id":"5a8eb8b82741361f5827afb6","employee_code":10002187,"name":"Abhishek","mobile":9997919143,"email":"Abhishek.LAL@CRYSTALCROP.COM","isActive":1,"accessToken":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI1YThlYjhiODI3NDEzNjFmNTgyN2FmYjYiLCJkYXRlIjoxNTE5NDY5OTc3MDYxfQ.47CHXUmRo0swezAWRGc-bJO_W13ylCXSnhk-KdnIIgw","lat":"27","lng":"22","password":"pass123456","loc":[27,22],"cd_code":0,"type":"zsm","createdAt":"2018-02-22T15:13:36.613Z","location":"Kufra District, Libya","updatedAt":"2018-02-24T10:59:37.930Z","__v":1,"phone":666666}},"loginDetails":[{"member":{"_id":"5a8eb8b82741361f5827afb5","name":"Bhajan Lal"}},{"member":{"_id":"5a9bb621f1698218a076324c","name":"Ravindra Babarao Ingale"}},{"member":{"_id":"5a9bb621f1698218a0763241","name":"Mayur Jarkad"}}]}
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
         * report : {"report":"report","type":"zm","detail":{"_id":"5a8eb8b82741361f5827afb6","employee_code":10002187,"name":"Abhishek","mobile":9997919143,"email":"Abhishek.LAL@CRYSTALCROP.COM","isActive":1,"accessToken":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI1YThlYjhiODI3NDEzNjFmNTgyN2FmYjYiLCJkYXRlIjoxNTE5NDY5OTc3MDYxfQ.47CHXUmRo0swezAWRGc-bJO_W13ylCXSnhk-KdnIIgw","lat":"27","lng":"22","password":"pass123456","loc":[27,22],"cd_code":0,"type":"zsm","createdAt":"2018-02-22T15:13:36.613Z","location":"Kufra District, Libya","updatedAt":"2018-02-24T10:59:37.930Z","__v":1,"phone":666666}}
         * loginDetails : [{"member":{"_id":"5a8eb8b82741361f5827afb5","name":"Bhajan Lal"}},{"member":{"_id":"5a9bb621f1698218a076324c","name":"Ravindra Babarao Ingale"}},{"member":{"_id":"5a9bb621f1698218a0763241","name":"Mayur Jarkad"}}]
         */

        private ReportBean report;
        private List<LoginDetailsBean> loginDetails;

        public ReportBean getReport() {
            return report;
        }

        public void setReport(ReportBean report) {
            this.report = report;
        }

        public List<LoginDetailsBean> getLoginDetails() {
            return loginDetails;
        }

        public void setLoginDetails(List<LoginDetailsBean> loginDetails) {
            this.loginDetails = loginDetails;
        }

        public static class ReportBean {
            /**
             * report : report
             * type : zm
             * detail : {"_id":"5a8eb8b82741361f5827afb6","employee_code":10002187,"name":"Abhishek","mobile":9997919143,"email":"Abhishek.LAL@CRYSTALCROP.COM","isActive":1,"accessToken":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI1YThlYjhiODI3NDEzNjFmNTgyN2FmYjYiLCJkYXRlIjoxNTE5NDY5OTc3MDYxfQ.47CHXUmRo0swezAWRGc-bJO_W13ylCXSnhk-KdnIIgw","lat":"27","lng":"22","password":"pass123456","loc":[27,22],"cd_code":0,"type":"zsm","createdAt":"2018-02-22T15:13:36.613Z","location":"Kufra District, Libya","updatedAt":"2018-02-24T10:59:37.930Z","__v":1,"phone":666666}
             */

            private String report;
            private String type;
            private DetailBean detail;

            public String getReport() {
                return report;
            }

            public void setReport(String report) {
                this.report = report;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public DetailBean getDetail() {
                return detail;
            }

            public void setDetail(DetailBean detail) {
                this.detail = detail;
            }

            public static class DetailBean {
                /**
                 * _id : 5a8eb8b82741361f5827afb6
                 * employee_code : 10002187
                 * name : Abhishek
                 * mobile : 9997919143
                 * email : Abhishek.LAL@CRYSTALCROP.COM
                 * isActive : 1
                 * accessToken : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI1YThlYjhiODI3NDEzNjFmNTgyN2FmYjYiLCJkYXRlIjoxNTE5NDY5OTc3MDYxfQ.47CHXUmRo0swezAWRGc-bJO_W13ylCXSnhk-KdnIIgw
                 * lat : 27
                 * lng : 22
                 * password : pass123456
                 * loc : [27,22]
                 * cd_code : 0
                 * type : zsm
                 * createdAt : 2018-02-22T15:13:36.613Z
                 * location : Kufra District, Libya
                 * updatedAt : 2018-02-24T10:59:37.930Z
                 * __v : 1
                 * phone : 666666
                 */

                private String _id;
                private int employee_code;
                private String name;
                private long mobile;
                private String email;
                private int isActive;
                private String accessToken;
                private String lat;
                private String lng;
                private String password;
                private int cd_code;
                private String type;
                private String createdAt;
                private String location;
                private String updatedAt;
                private int __v;
                private int phone;
                private List<Integer> loc;

                public String get_id() {
                    return _id;
                }

                public void set_id(String _id) {
                    this._id = _id;
                }

                public int getEmployee_code() {
                    return employee_code;
                }

                public void setEmployee_code(int employee_code) {
                    this.employee_code = employee_code;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public long getMobile() {
                    return mobile;
                }

                public void setMobile(long mobile) {
                    this.mobile = mobile;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }

                public int getIsActive() {
                    return isActive;
                }

                public void setIsActive(int isActive) {
                    this.isActive = isActive;
                }

                public String getAccessToken() {
                    return accessToken;
                }

                public void setAccessToken(String accessToken) {
                    this.accessToken = accessToken;
                }

                public String getLat() {
                    return lat;
                }

                public void setLat(String lat) {
                    this.lat = lat;
                }

                public String getLng() {
                    return lng;
                }

                public void setLng(String lng) {
                    this.lng = lng;
                }

                public String getPassword() {
                    return password;
                }

                public void setPassword(String password) {
                    this.password = password;
                }

                public int getCd_code() {
                    return cd_code;
                }

                public void setCd_code(int cd_code) {
                    this.cd_code = cd_code;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
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

                public String getUpdatedAt() {
                    return updatedAt;
                }

                public void setUpdatedAt(String updatedAt) {
                    this.updatedAt = updatedAt;
                }

                public int get__v() {
                    return __v;
                }

                public void set__v(int __v) {
                    this.__v = __v;
                }

                public int getPhone() {
                    return phone;
                }

                public void setPhone(int phone) {
                    this.phone = phone;
                }

                public List<Integer> getLoc() {
                    return loc;
                }

                public void setLoc(List<Integer> loc) {
                    this.loc = loc;
                }
            }
        }

        public static class LoginDetailsBean {

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }

            private boolean isSelected;
            /**
             * member : {"_id":"5a8eb8b82741361f5827afb5","name":"Bhajan Lal"}
             */

            private MemberBean member;

            public MemberBean getMember() {
                return member;
            }

            public void setMember(MemberBean member) {
                this.member = member;
            }

            public static class MemberBean {

                /**
                 * _id : 5a8eb8b82741361f5827afb5
                 * name : Bhajan Lal
                 */

                private String _id;
                private String name;

                public String get_id() {
                    return _id;
                }

                public void set_id(String _id) {
                    this._id = _id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }
}
