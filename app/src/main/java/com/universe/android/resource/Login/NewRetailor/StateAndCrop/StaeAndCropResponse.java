package com.universe.android.resource.Login.NewRetailor.StateAndCrop;

import com.universe.android.web.BaseResponse;

import java.util.List;

/**
 * Created by gaurav.pandey on 03-03-2018.
 */

public class StaeAndCropResponse extends BaseResponse<StaeAndCropResponse> {
    /**
     * errorMsg :
     * statusCode : 200
     * response : {"state":[{"_id":"5a9653c1d0a8c61f382593f3","state_code":20015,"state_name":"J&K","zone_code":"30001","IsActive":1},{"_id":"5a9653c1d0a8c61f382593f4","state_code":277,"state_name":"DISCONTINUED CUSTOMERS","zone_code":"30001","IsActive":1},{"_id":"5a9653c1d0a8c61f382593f5","state_code":20016,"state_name":"Punjab","zone_code":"30001","IsActive":1}],"crop":[{"_id":"5a965ecfd0a8c61f382594aa","CROP_NAME":"Apple","CROP_INFO":"Apple"},{"_id":"5a965ecfd0a8c61f382594ab","CROP_NAME":"Bajra","CROP_INFO":"Bajra"},{"_id":"5a965ecfd0a8c61f382594ac","CROP_NAME":"Barley","CROP_INFO":"Barley"},{"_id":"5a965ecfd0a8c61f382594ad","CROP_NAME":"Ber","CROP_INFO":"Ber"},{"_id":"5a965ecfd0a8c61f382594ae","CROP_NAME":"Bhindi","CROP_INFO":"Bhindi"},{"_id":"5a965ecfd0a8c61f382594af","CROP_NAME":"Brinjal","CROP_INFO":"Brinjal"},{"_id":"5a965ecfd0a8c61f382594b0","CROP_NAME":"Cabbage","CROP_INFO":"Cabbage"},{"_id":"5a965ecfd0a8c61f382594b1","CROP_NAME":"Chilli","CROP_INFO":"Chilli"},{"_id":"5a965ecfd0a8c61f382594b2","CROP_NAME":"Chillies","CROP_INFO":"Chillies"},{"_id":"5a965ecfd0a8c61f382594b3","CROP_NAME":"Citrus","CROP_INFO":"Citrus"},{"_id":"5a965ecfd0a8c61f382594b4","CROP_NAME":"Cotton","CROP_INFO":"Cotton"}]}
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
        private List<StateBean> state;
        private List<CropBean> crop;

        public List<StateBean> getState() {
            return state;
        }

        public void setState(List<StateBean> state) {
            this.state = state;
        }

        public List<CropBean> getCrop() {
            return crop;
        }

        public void setCrop(List<CropBean> crop) {
            this.crop = crop;
        }

        public static class StateBean {
            /**
             * _id : 5a9653c1d0a8c61f382593f3
             * state_code : 20015
             * state_name : J&K
             * zone_code : 30001
             * IsActive : 1
             */

            private String _id;
            private int state_code;
            private String state_name;
            private String zone_code;
            private int IsActive;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public int getState_code() {
                return state_code;
            }

            public void setState_code(int state_code) {
                this.state_code = state_code;
            }

            public String getState_name() {
                return state_name;
            }

            public void setState_name(String state_name) {
                this.state_name = state_name;
            }

            public String getZone_code() {
                return zone_code;
            }

            public void setZone_code(String zone_code) {
                this.zone_code = zone_code;
            }

            public int getIsActive() {
                return IsActive;
            }

            public void setIsActive(int IsActive) {
                this.IsActive = IsActive;
            }
        }

        public static class CropBean {
            /**
             * _id : 5a965ecfd0a8c61f382594aa
             * CROP_NAME : Apple
             * CROP_INFO : Apple
             */

            private String _id;
            private String CROP_NAME;
            private String CROP_INFO;
            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }

            private boolean isSelected;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getCROP_NAME() {
                return CROP_NAME;
            }

            public void setCROP_NAME(String CROP_NAME) {
                this.CROP_NAME = CROP_NAME;
            }

            public String getCROP_INFO() {
                return CROP_INFO;
            }

            public void setCROP_INFO(String CROP_INFO) {
                this.CROP_INFO = CROP_INFO;
            }
        }
    }
}
