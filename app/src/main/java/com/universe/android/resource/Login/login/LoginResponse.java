package com.universe.android.resource.Login.login;

import com.universe.android.web.BaseResponse;

import java.util.List;

/**
 * Created by gaurav.pandey on 06-02-2018.
 */

public class LoginResponse extends BaseResponse<LoginResponse> {

    /**
     * errorMsg :
     * statusCode : 200
     * response : {"report":{"report":"report","type":"rm"},"loginDetails":{"detail":{"_id":"5a8eb8b82741361f5827afb5","employee_code":10000297,"employee_name":"Bhajan Lal","mobile":123456789,"email":"BHAJAN.LAL@CRYSTALCROP.COM","isActive":1,"accessToken":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI1YThlYjhiODI3NDEzNjFmNTgyN2FmYjUiLCJkYXRlIjoxNTE5Mzk4MDQwMDcwfQ.y7iqid9WD5b-r3GPoioedh-8wDucKX9bokW24kBg8EQ","lat":"27","lng":"22","password":"pass123456","loc":[27,22],"cd_code":0,"type":"rsm","createdAt":"2018-02-22T14:02:03.809Z","location":"Kufra District, Libya","updatedAt":"2018-02-23T15:00:41.192Z","__v":5},"mapping":[{"_id":"5a8e81022741361f5827ae85","cd_code":1000,"name":"Parth Rawal","email":"parth@e2eprojects.com","password":"pass123456","mkt_territory_code":9501,"mobile":"NULL","dob":"NULL","doj":"NULL","isActive":1,"accessToken":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI1YThlODEwMjI3NDEzNjFmNTgyN2FlODUiLCJkYXRlIjoxNTE5NDU4MjMwMzc1fQ.tVi9BCMZMG-Wsp8ohbNXrZ9JTU4ZFrBBHDb5uC1VjmM","lat":"27","lng":"22","loc":[27,22],"__v":2,"createdAt":"2018-02-22T09:50:59.747Z","location":"Kufra District, Libya","updatedAt":"2018-02-24T07:43:51.427Z","type":"cd"},{"_id":"5a8eb8b82741361f5827afb6","employee_code":10002187,"employee_name":"Abhishek Kumar Rana","mobile":9997919143,"email":"Abhishek.LAL@CRYSTALCROP.COM","isActive":1,"accessToken":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI1YThlYjhiODI3NDEzNjFmNTgyN2FmYjYiLCJkYXRlIjoxNTE5Mzg3MzMzMjYwfQ.p8I9tHThO9Hulx6N-lBujNDySXhz5XonikkoiRmtb98","lat":"27","lng":"22","password":"pass123456","loc":[27,22],"cd_code":0,"type":"zsm","createdAt":"2018-02-22T15:13:36.613Z","location":"Kufra District, Libya","updatedAt":"2018-02-23T12:02:13.525Z","__v":1}]},"surveyDetails":[{"id":1,"pending":3,"details":{"_id":"5a86d4a9b69a800980dadd82","updatedAt":"2018-02-16T12:55:05.412Z","createdAt":"2018-02-16T12:55:05.412Z","responses":"{\"expiryDate\":\"16 Feb 2018\",\"title\":\"Distributor\",\"status\":10,\"isActive\":1,\"description\":\"\",\"category\":[{\"categoryName\":\"Distributor Retailer Linkage\",\"_id\":\"5a86cd5923deda0338a9de07\"}],\"categoryId\":\"[\\\"5a86cd5923deda0338a9de07\\\"]\"}","isActive":1,"expiryDate":"2018-02-16T00:00:00.000Z","createdBy":"5a799e932779e608b435a279","description":"","title":"Retailer","__v":0,"target":10,"categoryId":["5a86cd5923deda0338a9de07"]},"target":10,"submitted":0,"inprogress":0,"CrystalCustomer":0,"newRetailer":0,"doctorAssign":[{"_id":"5a8e81022741361f5827ae85","crystalDoctorName":"Parth Rawal","total":10,"completed":2}]},{"id":2,"pending":0,"details":{"_id":"5a86d4ccb69a800980dadd83","updatedAt":"2018-02-16T12:55:40.132Z","createdAt":"2018-02-16T12:55:40.132Z","responses":"{\"expiryDate\":\"16 Feb 2018\",\"title\":\"Retailer\",\"status\":10,\"isActive\":1,\"description\":\"\",\"category\":[{\"categoryName\":\"Distributer details\",\"_id\":\"5a86ccc423deda0338a9de02\"},{\"categoryName\":\"Owner details\",\"_id\":\"5a86ccd523deda0338a9de03\"},{\"categoryName\":\"distributer email\",\"_id\":\"5a86cd0423deda0338a9de04\"},{\"categoryName\":\"Turn over details\",\"_id\":\"5a86cd1623deda0338a9de05\"},{\"categoryName\":\"Others\",\"_id\":\"5a86cd2523deda0338a9de06\"}],\"categoryId\":\"[\\\"5a86ccc423deda0338a9de02\\\", \\\"5a86ccd523deda0338a9de03\\\", \\\"5a86cd0423deda0338a9de04\\\", \\\"5a86cd1623deda0338a9de05\\\", \\\"5a86cd2523deda0338a9de06\\\"]\"}","isActive":1,"expiryDate":"2018-02-16T00:00:00.000Z","createdBy":"5a799e932779e608b435a279","description":"","title":"Distributor","__v":0,"target":10,"categoryId":["5a86ccc423deda0338a9de02","5a86ccd523deda0338a9de03","5a86cd0423deda0338a9de04","5a86cd1623deda0338a9de05","5a86cd2523deda0338a9de06"]},"target":10,"submitted":0,"inprogress":0,"CrystalCustomer":0,"newRetailer":0,"doctorAssign":[{"_id":"5a8e81022741361f5827ae85","crystalDoctorName":"Parth Rawal","total":10,"completed":2}]}]}
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
         * report : {"report":"report","type":"rm"}
         * loginDetails : {"detail":{"_id":"5a8eb8b82741361f5827afb5","employee_code":10000297,"employee_name":"Bhajan Lal","mobile":123456789,"email":"BHAJAN.LAL@CRYSTALCROP.COM","isActive":1,"accessToken":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI1YThlYjhiODI3NDEzNjFmNTgyN2FmYjUiLCJkYXRlIjoxNTE5Mzk4MDQwMDcwfQ.y7iqid9WD5b-r3GPoioedh-8wDucKX9bokW24kBg8EQ","lat":"27","lng":"22","password":"pass123456","loc":[27,22],"cd_code":0,"type":"rsm","createdAt":"2018-02-22T14:02:03.809Z","location":"Kufra District, Libya","updatedAt":"2018-02-23T15:00:41.192Z","__v":5},"mapping":[{"_id":"5a8e81022741361f5827ae85","cd_code":1000,"name":"Parth Rawal","email":"parth@e2eprojects.com","password":"pass123456","mkt_territory_code":9501,"mobile":"NULL","dob":"NULL","doj":"NULL","isActive":1,"accessToken":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI1YThlODEwMjI3NDEzNjFmNTgyN2FlODUiLCJkYXRlIjoxNTE5NDU4MjMwMzc1fQ.tVi9BCMZMG-Wsp8ohbNXrZ9JTU4ZFrBBHDb5uC1VjmM","lat":"27","lng":"22","loc":[27,22],"__v":2,"createdAt":"2018-02-22T09:50:59.747Z","location":"Kufra District, Libya","updatedAt":"2018-02-24T07:43:51.427Z","type":"cd"},{"_id":"5a8eb8b82741361f5827afb6","employee_code":10002187,"employee_name":"Abhishek Kumar Rana","mobile":9997919143,"email":"Abhishek.LAL@CRYSTALCROP.COM","isActive":1,"accessToken":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI1YThlYjhiODI3NDEzNjFmNTgyN2FmYjYiLCJkYXRlIjoxNTE5Mzg3MzMzMjYwfQ.p8I9tHThO9Hulx6N-lBujNDySXhz5XonikkoiRmtb98","lat":"27","lng":"22","password":"pass123456","loc":[27,22],"cd_code":0,"type":"zsm","createdAt":"2018-02-22T15:13:36.613Z","location":"Kufra District, Libya","updatedAt":"2018-02-23T12:02:13.525Z","__v":1}]}
         * surveyDetails : [{"id":1,"pending":3,"details":{"_id":"5a86d4a9b69a800980dadd82","updatedAt":"2018-02-16T12:55:05.412Z","createdAt":"2018-02-16T12:55:05.412Z","responses":"{\"expiryDate\":\"16 Feb 2018\",\"title\":\"Distributor\",\"status\":10,\"isActive\":1,\"description\":\"\",\"category\":[{\"categoryName\":\"Distributor Retailer Linkage\",\"_id\":\"5a86cd5923deda0338a9de07\"}],\"categoryId\":\"[\\\"5a86cd5923deda0338a9de07\\\"]\"}","isActive":1,"expiryDate":"2018-02-16T00:00:00.000Z","createdBy":"5a799e932779e608b435a279","description":"","title":"Retailer","__v":0,"target":10,"categoryId":["5a86cd5923deda0338a9de07"]},"target":10,"submitted":0,"inprogress":0,"CrystalCustomer":0,"newRetailer":0,"doctorAssign":[{"_id":"5a8e81022741361f5827ae85","crystalDoctorName":"Parth Rawal","total":10,"completed":2}]},{"id":2,"pending":0,"details":{"_id":"5a86d4ccb69a800980dadd83","updatedAt":"2018-02-16T12:55:40.132Z","createdAt":"2018-02-16T12:55:40.132Z","responses":"{\"expiryDate\":\"16 Feb 2018\",\"title\":\"Retailer\",\"status\":10,\"isActive\":1,\"description\":\"\",\"category\":[{\"categoryName\":\"Distributer details\",\"_id\":\"5a86ccc423deda0338a9de02\"},{\"categoryName\":\"Owner details\",\"_id\":\"5a86ccd523deda0338a9de03\"},{\"categoryName\":\"distributer email\",\"_id\":\"5a86cd0423deda0338a9de04\"},{\"categoryName\":\"Turn over details\",\"_id\":\"5a86cd1623deda0338a9de05\"},{\"categoryName\":\"Others\",\"_id\":\"5a86cd2523deda0338a9de06\"}],\"categoryId\":\"[\\\"5a86ccc423deda0338a9de02\\\", \\\"5a86ccd523deda0338a9de03\\\", \\\"5a86cd0423deda0338a9de04\\\", \\\"5a86cd1623deda0338a9de05\\\", \\\"5a86cd2523deda0338a9de06\\\"]\"}","isActive":1,"expiryDate":"2018-02-16T00:00:00.000Z","createdBy":"5a799e932779e608b435a279","description":"","title":"Distributor","__v":0,"target":10,"categoryId":["5a86ccc423deda0338a9de02","5a86ccd523deda0338a9de03","5a86cd0423deda0338a9de04","5a86cd1623deda0338a9de05","5a86cd2523deda0338a9de06"]},"target":10,"submitted":0,"inprogress":0,"CrystalCustomer":0,"newRetailer":0,"doctorAssign":[{"_id":"5a8e81022741361f5827ae85","crystalDoctorName":"Parth Rawal","total":10,"completed":2}]}]
         */

        private ReportBean report;
        private LoginDetailsBean loginDetails;
        private List<SurveyDetailsBean> surveyDetails;

        public ReportBean getReport() {
            return report;
        }

        public void setReport(ReportBean report) {
            this.report = report;
        }

        public LoginDetailsBean getLoginDetails() {
            return loginDetails;
        }

        public void setLoginDetails(LoginDetailsBean loginDetails) {
            this.loginDetails = loginDetails;
        }

        public List<SurveyDetailsBean> getSurveyDetails() {
            return surveyDetails;
        }

        public void setSurveyDetails(List<SurveyDetailsBean> surveyDetails) {
            this.surveyDetails = surveyDetails;
        }

        public static class ReportBean {
            /**
             * report : report
             * type : rm
             */

            private String report;
            private String type;

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
        }

        public static class LoginDetailsBean {
            /**
             * detail : {"_id":"5a8eb8b82741361f5827afb5","employee_code":10000297,"employee_name":"Bhajan Lal","mobile":123456789,"email":"BHAJAN.LAL@CRYSTALCROP.COM","isActive":1,"accessToken":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI1YThlYjhiODI3NDEzNjFmNTgyN2FmYjUiLCJkYXRlIjoxNTE5Mzk4MDQwMDcwfQ.y7iqid9WD5b-r3GPoioedh-8wDucKX9bokW24kBg8EQ","lat":"27","lng":"22","password":"pass123456","loc":[27,22],"cd_code":0,"type":"rsm","createdAt":"2018-02-22T14:02:03.809Z","location":"Kufra District, Libya","updatedAt":"2018-02-23T15:00:41.192Z","__v":5}
             * mapping : [{"_id":"5a8e81022741361f5827ae85","cd_code":1000,"name":"Parth Rawal","email":"parth@e2eprojects.com","password":"pass123456","mkt_territory_code":9501,"mobile":"NULL","dob":"NULL","doj":"NULL","isActive":1,"accessToken":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI1YThlODEwMjI3NDEzNjFmNTgyN2FlODUiLCJkYXRlIjoxNTE5NDU4MjMwMzc1fQ.tVi9BCMZMG-Wsp8ohbNXrZ9JTU4ZFrBBHDb5uC1VjmM","lat":"27","lng":"22","loc":[27,22],"__v":2,"createdAt":"2018-02-22T09:50:59.747Z","location":"Kufra District, Libya","updatedAt":"2018-02-24T07:43:51.427Z","type":"cd"},{"_id":"5a8eb8b82741361f5827afb6","employee_code":10002187,"employee_name":"Abhishek Kumar Rana","mobile":9997919143,"email":"Abhishek.LAL@CRYSTALCROP.COM","isActive":1,"accessToken":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI1YThlYjhiODI3NDEzNjFmNTgyN2FmYjYiLCJkYXRlIjoxNTE5Mzg3MzMzMjYwfQ.p8I9tHThO9Hulx6N-lBujNDySXhz5XonikkoiRmtb98","lat":"27","lng":"22","password":"pass123456","loc":[27,22],"cd_code":0,"type":"zsm","createdAt":"2018-02-22T15:13:36.613Z","location":"Kufra District, Libya","updatedAt":"2018-02-23T12:02:13.525Z","__v":1}]
             */

            private DetailBean detail;
            private List<MappingBean> mapping;

            public DetailBean getDetail() {
                return detail;
            }

            public void setDetail(DetailBean detail) {
                this.detail = detail;
            }

            public List<MappingBean> getMapping() {
                return mapping;
            }

            public void setMapping(List<MappingBean> mapping) {
                this.mapping = mapping;
            }

            public static class DetailBean {
                /**
                 * _id : 5a8eb8b82741361f5827afb5
                 * employee_code : 10000297
                 * employee_name : Bhajan Lal
                 * mobile : 123456789
                 * email : BHAJAN.LAL@CRYSTALCROP.COM
                 * isActive : 1
                 * accessToken : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI1YThlYjhiODI3NDEzNjFmNTgyN2FmYjUiLCJkYXRlIjoxNTE5Mzk4MDQwMDcwfQ.y7iqid9WD5b-r3GPoioedh-8wDucKX9bokW24kBg8EQ
                 * lat : 27
                 * lng : 22
                 * password : pass123456
                 * loc : [27,22]
                 * cd_code : 0
                 * type : rsm
                 * createdAt : 2018-02-22T14:02:03.809Z
                 * location : Kufra District, Libya
                 * updatedAt : 2018-02-23T15:00:41.192Z
                 * __v : 5
                 */

                private String _id;
                private int employee_code;
                private String employee_name;
                private int mobile;
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

                public String getEmployee_name() {
                    return employee_name;
                }

                public void setEmployee_name(String employee_name) {
                    this.employee_name = employee_name;
                }

                public int getMobile() {
                    return mobile;
                }

                public void setMobile(int mobile) {
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

                public List<Integer> getLoc() {
                    return loc;
                }

                public void setLoc(List<Integer> loc) {
                    this.loc = loc;
                }
            }

            public static class MappingBean {
                /**
                 * _id : 5a8e81022741361f5827ae85
                 * cd_code : 1000
                 * name : Parth Rawal
                 * email : parth@e2eprojects.com
                 * password : pass123456
                 * mkt_territory_code : 9501
                 * mobile : NULL
                 * dob : NULL
                 * doj : NULL
                 * isActive : 1
                 * accessToken : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI1YThlODEwMjI3NDEzNjFmNTgyN2FlODUiLCJkYXRlIjoxNTE5NDU4MjMwMzc1fQ.tVi9BCMZMG-Wsp8ohbNXrZ9JTU4ZFrBBHDb5uC1VjmM
                 * lat : 27
                 * lng : 22
                 * loc : [27,22]
                 * __v : 2
                 * createdAt : 2018-02-22T09:50:59.747Z
                 * location : Kufra District, Libya
                 * updatedAt : 2018-02-24T07:43:51.427Z
                 * type : cd
                 * employee_code : 10002187
                 * employee_name : Abhishek Kumar Rana
                 */

                private String _id;
                private int cd_code;
                private String name;
                private String email;
                private String password;
                private int mkt_territory_code;
                private String mobile;
                private String dob;
                private String doj;
                private int isActive;
                private String accessToken;
                private String lat;
                private String lng;
                private int __v;
                private String createdAt;
                private String location;
                private String updatedAt;
                private String type;
                private int employee_code;
                private String employee_name;
                private List<Integer> loc;

                public String get_id() {
                    return _id;
                }

                public void set_id(String _id) {
                    this._id = _id;
                }

                public int getCd_code() {
                    return cd_code;
                }

                public void setCd_code(int cd_code) {
                    this.cd_code = cd_code;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }

                public String getPassword() {
                    return password;
                }

                public void setPassword(String password) {
                    this.password = password;
                }

                public int getMkt_territory_code() {
                    return mkt_territory_code;
                }

                public void setMkt_territory_code(int mkt_territory_code) {
                    this.mkt_territory_code = mkt_territory_code;
                }

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public String getDob() {
                    return dob;
                }

                public void setDob(String dob) {
                    this.dob = dob;
                }

                public String getDoj() {
                    return doj;
                }

                public void setDoj(String doj) {
                    this.doj = doj;
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

                public int get__v() {
                    return __v;
                }

                public void set__v(int __v) {
                    this.__v = __v;
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

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public int getEmployee_code() {
                    return employee_code;
                }

                public void setEmployee_code(int employee_code) {
                    this.employee_code = employee_code;
                }

                public String getEmployee_name() {
                    return employee_name;
                }

                public void setEmployee_name(String employee_name) {
                    this.employee_name = employee_name;
                }

                public List<Integer> getLoc() {
                    return loc;
                }

                public void setLoc(List<Integer> loc) {
                    this.loc = loc;
                }
            }
        }

        public static class SurveyDetailsBean {
            /**
             * id : 1
             * pending : 3
             * details : {"_id":"5a86d4a9b69a800980dadd82","updatedAt":"2018-02-16T12:55:05.412Z","createdAt":"2018-02-16T12:55:05.412Z","responses":"{\"expiryDate\":\"16 Feb 2018\",\"title\":\"Distributor\",\"status\":10,\"isActive\":1,\"description\":\"\",\"category\":[{\"categoryName\":\"Distributor Retailer Linkage\",\"_id\":\"5a86cd5923deda0338a9de07\"}],\"categoryId\":\"[\\\"5a86cd5923deda0338a9de07\\\"]\"}","isActive":1,"expiryDate":"2018-02-16T00:00:00.000Z","createdBy":"5a799e932779e608b435a279","description":"","title":"Retailer","__v":0,"target":10,"categoryId":["5a86cd5923deda0338a9de07"]}
             * target : 10
             * submitted : 0
             * inprogress : 0
             * CrystalCustomer : 0
             * newRetailer : 0
             * doctorAssign : [{"_id":"5a8e81022741361f5827ae85","crystalDoctorName":"Parth Rawal","total":10,"completed":2}]
             */

            private int id;
            private int pending;
            private DetailsBean details;
            private int target;
            private int submitted;
            private int inprogress;
            private int CrystalCustomer;
            private int newRetailer;
            private List<DoctorAssignBean> doctorAssign;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPending() {
                return pending;
            }

            public void setPending(int pending) {
                this.pending = pending;
            }

            public DetailsBean getDetails() {
                return details;
            }

            public void setDetails(DetailsBean details) {
                this.details = details;
            }

            public int getTarget() {
                return target;
            }

            public void setTarget(int target) {
                this.target = target;
            }

            public int getSubmitted() {
                return submitted;
            }

            public void setSubmitted(int submitted) {
                this.submitted = submitted;
            }

            public int getInprogress() {
                return inprogress;
            }

            public void setInprogress(int inprogress) {
                this.inprogress = inprogress;
            }

            public int getCrystalCustomer() {
                return CrystalCustomer;
            }

            public void setCrystalCustomer(int CrystalCustomer) {
                this.CrystalCustomer = CrystalCustomer;
            }

            public int getNewRetailer() {
                return newRetailer;
            }

            public void setNewRetailer(int newRetailer) {
                this.newRetailer = newRetailer;
            }

            public List<DoctorAssignBean> getDoctorAssign() {
                return doctorAssign;
            }

            public void setDoctorAssign(List<DoctorAssignBean> doctorAssign) {
                this.doctorAssign = doctorAssign;
            }

            public static class DetailsBean {
                /**
                 * _id : 5a86d4a9b69a800980dadd82
                 * updatedAt : 2018-02-16T12:55:05.412Z
                 * createdAt : 2018-02-16T12:55:05.412Z
                 * responses : {"expiryDate":"16 Feb 2018","title":"Distributor","status":10,"isActive":1,"description":"","category":[{"categoryName":"Distributor Retailer Linkage","_id":"5a86cd5923deda0338a9de07"}],"categoryId":"[\"5a86cd5923deda0338a9de07\"]"}
                 * isActive : 1
                 * expiryDate : 2018-02-16T00:00:00.000Z
                 * createdBy : 5a799e932779e608b435a279
                 * description :
                 * title : Retailer
                 * __v : 0
                 * target : 10
                 * categoryId : ["5a86cd5923deda0338a9de07"]
                 */

                private String _id;
                private String updatedAt;
                private String createdAt;
                private String responses;
                private int isActive;
                private String expiryDate;
                private String createdBy;
                private String description;
                private String title;
                private int __v;
                private int target;
                private List<String> categoryId;

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

                public String getResponses() {
                    return responses;
                }

                public void setResponses(String responses) {
                    this.responses = responses;
                }

                public int getIsActive() {
                    return isActive;
                }

                public void setIsActive(int isActive) {
                    this.isActive = isActive;
                }

                public String getExpiryDate() {
                    return expiryDate;
                }

                public void setExpiryDate(String expiryDate) {
                    this.expiryDate = expiryDate;
                }

                public String getCreatedBy() {
                    return createdBy;
                }

                public void setCreatedBy(String createdBy) {
                    this.createdBy = createdBy;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public int get__v() {
                    return __v;
                }

                public void set__v(int __v) {
                    this.__v = __v;
                }

                public int getTarget() {
                    return target;
                }

                public void setTarget(int target) {
                    this.target = target;
                }

                public List<String> getCategoryId() {
                    return categoryId;
                }

                public void setCategoryId(List<String> categoryId) {
                    this.categoryId = categoryId;
                }
            }

            public static class DoctorAssignBean {
                /**
                 * _id : 5a8e81022741361f5827ae85
                 * crystalDoctorName : Parth Rawal
                 * total : 10
                 * completed : 2
                 */

                private String _id;
                private String crystalDoctorName;
                private int total;
                private int completed;

                public String get_id() {
                    return _id;
                }

                public void set_id(String _id) {
                    this._id = _id;
                }

                public String getCrystalDoctorName() {
                    return crystalDoctorName;
                }

                public void setCrystalDoctorName(String crystalDoctorName) {
                    this.crystalDoctorName = crystalDoctorName;
                }

                public int getTotal() {
                    return total;
                }

                public void setTotal(int total) {
                    this.total = total;
                }

                public int getCompleted() {
                    return completed;
                }

                public void setCompleted(int completed) {
                    this.completed = completed;
                }
            }
        }
    }
}
