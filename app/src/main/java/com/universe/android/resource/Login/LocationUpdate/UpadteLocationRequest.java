package com.universe.android.resource.Login.LocationUpdate;

import com.universe.android.web.BaseRequest;

/**
 * Created by gaurav.pandey on 20-02-2018.
 */

public class UpadteLocationRequest extends BaseRequest {
    private String userId;
    private String lat;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    private String customerId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    private String lng;

    @Override
    public boolean isValid(String Scenario) {
        return true;
    }
}
