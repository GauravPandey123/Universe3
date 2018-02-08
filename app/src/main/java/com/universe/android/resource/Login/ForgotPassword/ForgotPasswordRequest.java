package com.universe.android.resource.Login.ForgotPassword;

import com.universe.android.web.BaseRequest;

/**
 * Created by gaurav.pandey on 07-02-2018.
 */

public class ForgotPasswordRequest extends BaseRequest {
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

    @Override
    public boolean isValid(String Scenario) {
        return true;
    }
}
