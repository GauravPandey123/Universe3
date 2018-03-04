package com.universe.android.resource.Login.NewRetailor.StateAndCrop.DistributorAndVillage;

import com.universe.android.web.BaseResponse;

import java.util.List;

/**
 * Created by gaurav.pandey on 04-03-2018.
 */

public class DistributorResponse extends BaseResponse<DistributorResponse> {

    /**
     * errorMsg :
     * statusCode : 200
     * response : {"distributer":[{"_id":"5a9679f3d0a8c61f38259566","customer_code":"PC01000191","name":"GICE AGRO SCIENCES LTD.","territory_code":652},{"_id":"5a9679f3d0a8c61f38259567","customer_code":"PC01000243","name":"AGRI COMMODITIES AND FINANCE FZE","territory_code":652},{"_id":"5a9679f3d0a8c61f38259568","customer_code":"PC01000111","name":"CURE CHEM ZAMBIA LTD.","territory_code":652}],"village":[{"_id":"5a966fced0a8c61f3825952e","village_id":274,"village_name":"Aranpur"}]}
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
        private List<DistributerBean> distributer;
        private List<VillageBean> village;

        public List<DistributerBean> getDistributer() {
            return distributer;
        }

        public void setDistributer(List<DistributerBean> distributer) {
            this.distributer = distributer;
        }

        public List<VillageBean> getVillage() {
            return village;
        }

        public void setVillage(List<VillageBean> village) {
            this.village = village;
        }

        public static class DistributerBean {
            /**
             * _id : 5a9679f3d0a8c61f38259566
             * customer_code : PC01000191
             * name : GICE AGRO SCIENCES LTD.
             * territory_code : 652
             */

            private String _id;
            private String customer_code;
            private String name;
            private int territory_code;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getCustomer_code() {
                return customer_code;
            }

            public void setCustomer_code(String customer_code) {
                this.customer_code = customer_code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getTerritory_code() {
                return territory_code;
            }

            public void setTerritory_code(int territory_code) {
                this.territory_code = territory_code;
            }
        }

        public static class VillageBean {
            /**
             * _id : 5a966fced0a8c61f3825952e
             * village_id : 274
             * village_name : Aranpur
             */

            private String _id;
            private int village_id;
            private String village_name;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public int getVillage_id() {
                return village_id;
            }

            public void setVillage_id(int village_id) {
                this.village_id = village_id;
            }

            public String getVillage_name() {
                return village_name;
            }

            public void setVillage_name(String village_name) {
                this.village_name = village_name;
            }
        }
    }
}
