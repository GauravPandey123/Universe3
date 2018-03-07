package com.universe.android.resource.Login.surveyList;

import com.universe.android.web.BaseResponse;

import java.util.List;

/**
 * Created by gaurav.pandey on 04-03-2018.
 */

public class SurveyListResponse extends BaseResponse<SurveyListResponse> {

    /**
     * errorMsg :
     * statusCode : 200
     * response : [{"pending":3,"surveyDetails":{"_id":"5a86d4a9b69a800980dadd82","updatedAt":"2018-02-16T12:55:05.412Z","createdAt":"2018-02-16T12:55:05.412Z","responses":"{\"expiryDate\":\"16 Feb 2018\",\"title\":\"Distributor\",\"status\":10,\"isActive\":1,\"description\":\"\",\"category\":[{\"categoryName\":\"Distributor Retailer Linkage\",\"_id\":\"5a86cd5923deda0338a9de07\"}],\"categoryId\":\"[\\\"5a86cd5923deda0338a9de07\\\"]\"}","isActive":1,"expiryDate":"2018-02-16T00:00:00.000Z","createdBy":"5a799e932779e608b435a279","description":"","title":"Retailer","__v":0,"target":20,"categoryId":["5a86cd5923deda0338a9de07"]}},{"pending":0,"surveyDetails":{"_id":"5a86d4ccb69a800980dadd83","updatedAt":"2018-02-16T12:55:40.132Z","createdAt":"2018-02-16T12:55:40.132Z","responses":"{\"expiryDate\":\"16 Feb 2018\",\"title\":\"Retailer\",\"status\":10,\"isActive\":1,\"description\":\"\",\"category\":[{\"categoryName\":\"Distributer details\",\"_id\":\"5a86ccc423deda0338a9de02\"},{\"categoryName\":\"Owner details\",\"_id\":\"5a86ccd523deda0338a9de03\"},{\"categoryName\":\"distributer email\",\"_id\":\"5a86cd0423deda0338a9de04\"},{\"categoryName\":\"Turn over details\",\"_id\":\"5a86cd1623deda0338a9de05\"},{\"categoryName\":\"Others\",\"_id\":\"5a86cd2523deda0338a9de06\"}],\"categoryId\":\"[\\\"5a86ccc423deda0338a9de02\\\", \\\"5a86ccd523deda0338a9de03\\\", \\\"5a86cd0423deda0338a9de04\\\", \\\"5a86cd1623deda0338a9de05\\\", \\\"5a86cd2523deda0338a9de06\\\"]\"}","isActive":1,"expiryDate":"2018-02-16T00:00:00.000Z","createdBy":"5a799e932779e608b435a279","description":"","title":"Distributor","__v":0,"target":10,"categoryId":["5a86ccc423deda0338a9de02","5a86ccd523deda0338a9de03","5a86cd0423deda0338a9de04","5a86cd1623deda0338a9de05","5a86cd2523deda0338a9de06"]}}]
     */

    private String errorMsg;
    private int statusCode;
    private List<ResponseBean> response;

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

    public List<ResponseBean> getResponse() {
        return response;
    }

    public void setResponse(List<ResponseBean> response) {
        this.response = response;
    }


    public static class ResponseBean {
        /**
         * pending : 3
         * surveyDetails : {"_id":"5a86d4a9b69a800980dadd82","updatedAt":"2018-02-16T12:55:05.412Z","createdAt":"2018-02-16T12:55:05.412Z","responses":"{\"expiryDate\":\"16 Feb 2018\",\"title\":\"Distributor\",\"status\":10,\"isActive\":1,\"description\":\"\",\"category\":[{\"categoryName\":\"Distributor Retailer Linkage\",\"_id\":\"5a86cd5923deda0338a9de07\"}],\"categoryId\":\"[\\\"5a86cd5923deda0338a9de07\\\"]\"}","isActive":1,"expiryDate":"2018-02-16T00:00:00.000Z","createdBy":"5a799e932779e608b435a279","description":"","title":"Retailer","__v":0,"target":20,"categoryId":["5a86cd5923deda0338a9de07"]}
         */

        private int pending;
        private SurveyDetailsBean surveyDetails;

        public int getPending() {
            return pending;
        }

        public void setPending(int pending) {
            this.pending = pending;
        }

        public SurveyDetailsBean getSurveyDetails() {
            return surveyDetails;
        }

        public void setSurveyDetails(SurveyDetailsBean surveyDetails) {
            this.surveyDetails = surveyDetails;
        }

        public static class SurveyDetailsBean {
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
             * target : 20
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
    }
}
