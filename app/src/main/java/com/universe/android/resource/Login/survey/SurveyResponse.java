package com.universe.android.resource.Login.survey;

import com.universe.android.web.BaseResponse;

import java.util.Date;

/**
 * Created by gaurav.pandey on 06-02-2018.
 */

public class SurveyResponse extends BaseResponse<SurveyResponse> {

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

        private String id;
        private String status;
        private String title;

        private Date expiryDate;

        private String createdBy;
        private Date createdAt;
        private Date updatedAt;
        private boolean isActive;
        private String description;
        private String image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Date getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(Date expiryDate) {
            this.expiryDate = expiryDate;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public Date getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
        }

        public Date getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
        }

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            isActive = active;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }


}
