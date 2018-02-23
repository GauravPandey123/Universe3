package com.universe.android.resource.Login.LocationUpdate;

import com.universe.android.web.BaseRequest;

/**
 * Created by gaurav.pandey on 20-02-2018.
 */

public class UpadteLocationRequest extends BaseRequest {
    String userId;
    String lat;

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

    String lng;

    @Override
    public boolean isValid(String Scenario) {
        return true;
    }
}
