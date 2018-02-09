package com.universe.android.resource.Login.questions;

import com.universe.android.web.BaseResponse;

import java.util.Date;

/**
 * Created by gaurav.pandey on 06-02-2018.
 */

public class QuestionsResponse extends BaseResponse<QuestionsResponse> {

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
        private String title;
        private String longTitle;

        private String inputType;

        private String type;

        private boolean required;


        private int maxValue;

        private boolean isActive;

        private int minValue;
        private String maxLength;
        private boolean visibility;
        private String surveyId;
        private String clientId;
        private String customerId;
        private int displayOrder;
        private String optionValues;
        private String optionTitle;
        private String orientation;
        private Date createdAt;
        private Date updatedAt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLongTitle() {
            return longTitle;
        }

        public void setLongTitle(String longTitle) {
            this.longTitle = longTitle;
        }

        public String getInputType() {
            return inputType;
        }

        public void setInputType(String inputType) {
            this.inputType = inputType;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isRequired() {
            return required;
        }

        public void setRequired(boolean required) {
            this.required = required;
        }

        public int getMaxValue() {
            return maxValue;
        }

        public void setMaxValue(int maxValue) {
            this.maxValue = maxValue;
        }

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            isActive = active;
        }

        public int getMinValue() {
            return minValue;
        }

        public void setMinValue(int minValue) {
            this.minValue = minValue;
        }

        public String getMaxLength() {
            return maxLength;
        }

        public void setMaxLength(String maxLength) {
            this.maxLength = maxLength;
        }

        public boolean isVisibility() {
            return visibility;
        }

        public void setVisibility(boolean visibility) {
            this.visibility = visibility;
        }

        public String getSurveyId() {
            return surveyId;
        }

        public void setSurveyId(String surveyId) {
            this.surveyId = surveyId;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public int getDisplayOrder() {
            return displayOrder;
        }

        public void setDisplayOrder(int displayOrder) {
            this.displayOrder = displayOrder;
        }

        public String getOptionValues() {
            return optionValues;
        }

        public void setOptionValues(String optionValues) {
            this.optionValues = optionValues;
        }

        public String getOptionTitle() {
            return optionTitle;
        }

        public void setOptionTitle(String optionTitle) {
            this.optionTitle = optionTitle;
        }

        public String getOrientation() {
            return orientation;
        }

        public void setOrientation(String orientation) {
            this.orientation = orientation;
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
    }


}
