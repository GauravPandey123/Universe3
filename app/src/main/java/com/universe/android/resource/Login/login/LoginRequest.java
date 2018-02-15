package com.universe.android.resource.Login.login;

import com.universe.android.web.BaseRequest;

/**
 * Created by gaurav.pandey on 06-02-2018.
 */

public class LoginRequest extends BaseRequest {
    private String email;
    private String password;
    private String appVersion;
    private String deviceToken;
    private String deviceType;
    private String deviceModel;
    private String deviceVersion;
    private String userDeviceActive;
    private String deviceIMEI;
    private String lat;

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


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public String getUserDeviceActive() {
        return userDeviceActive;
    }

    public void setUserDeviceActive(String userDeviceActive) {
        this.userDeviceActive = userDeviceActive;
    }

    public String getDeviceIMEI() {
        return deviceIMEI;
    }

    public void setDeviceIMEI(String deviceIMEI) {
        this.deviceIMEI = deviceIMEI;
    }

    @Override
    public boolean isValid(String Scenario) {
        return true;
    }
}
