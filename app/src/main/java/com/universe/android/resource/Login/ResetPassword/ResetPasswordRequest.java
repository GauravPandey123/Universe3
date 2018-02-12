package com.universe.android.resource.Login.ResetPassword;

import com.universe.android.web.BaseRequest;

/**
 * Created by gaurav.pandey on 07-02-2018.
 */

public class ResetPasswordRequest extends BaseRequest {
    private String userId;
    private String oldPassword;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    private String newPassword;

    @Override
    public boolean isValid(String Scenario) {
        return true;
    }
}
