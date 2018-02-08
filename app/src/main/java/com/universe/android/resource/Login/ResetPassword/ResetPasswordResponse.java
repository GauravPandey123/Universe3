package com.universe.android.resource.Login.ResetPassword;

import com.universe.android.web.BaseResponse;

/**
 * Created by gaurav.pandey on 07-02-2018.
 */

public class ResetPasswordResponse extends BaseResponse<ResetPasswordResponse> {


    /**
     * errorMsg :
     * statusCode : 200
     * response : password change successfully
     */

    private String errorMsg;
    private int statusCode;
    private String response;

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

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
