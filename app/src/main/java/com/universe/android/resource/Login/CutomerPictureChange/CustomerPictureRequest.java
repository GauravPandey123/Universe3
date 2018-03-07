package com.universe.android.resource.Login.CutomerPictureChange;

import com.universe.android.web.BaseRequest;

/**
 * Created by gaurav.pandey on 06-03-2018.
 */

public class CustomerPictureRequest extends BaseRequest {
    String customerId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getIsPicture() {
        return isPicture;
    }

    public void setIsPicture(int isPicture) {
        this.isPicture = isPicture;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    int isPicture;
    String photo;

    @Override
    public boolean isValid(String Scenario) {
        return true;
    }
}
