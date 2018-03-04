package com.universe.android.resource.Login.NewRetailor.StateAndCrop.Territory;

import com.universe.android.web.BaseResponse;

import java.util.List;

/**
 * Created by gaurav.pandey on 03-03-2018.
 */

public class TerroritryResponse extends BaseResponse<TerroritryResponse> {

    /**
     * errorMsg :
     * statusCode : 200
     * response : [{"territory_code":651,"name":"MS-NARAYANGAON"},{"territory_code":641,"name":"HP-SOLAN"},{"territory_code":652,"name":"MS-NASHIK WEST"}]
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
         * territory_code : 651
         * name : MS-NARAYANGAON
         */

        private int territory_code;
        private String name;

        public int getTerritory_code() {
            return territory_code;
        }

        public void setTerritory_code(int territory_code) {
            this.territory_code = territory_code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
