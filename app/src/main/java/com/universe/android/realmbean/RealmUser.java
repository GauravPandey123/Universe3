package com.universe.android.realmbean;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;


public class RealmUser extends RealmObject {
    private String status;

    private int designationLevel;

    private String reportingManager;

    private String designation;

    private String state;

    private String block;

    private String lockingPeriod;

    private String userType;

    private String country;

    private String username;

    private String village;

    private String createdBy;

    private String address;

    private String createdAt;

    @PrimaryKey
    private String _id;
    private String cd_code;
    private String name;
    private String employee_name;
    private String employee_code;
    private String email;
    private String password;
    private String mkt_territory_code;
    private String mobile;
    private String dob;
    private String doj;
    private String isActive;
    private String accessToken;
    private String lat;
    private String lng;
    private String type;
    private String location;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCd_code() {
        return cd_code;
    }

    public void setCd_code(String cd_code) {
        this.cd_code = cd_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getMkt_territory_code() {
        return mkt_territory_code;
    }

    public void setMkt_territory_code(String mkt_territory_code) {
        this.mkt_territory_code = mkt_territory_code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getEmployee_code() {
        return employee_code;
    }

    public void setEmployee_code(String employee_code) {
        this.employee_code = employee_code;
    }
}
