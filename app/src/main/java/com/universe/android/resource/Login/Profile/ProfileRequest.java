package com.universe.android.resource.Login.Profile;

import com.universe.android.web.BaseRequest;

/**
 * Created by gaurav.pandey on 07-02-2018.
 */

public class ProfileRequest extends BaseRequest {
    private String role;
    private String territory;
    private String LOB;
    private String userId;
    private String email;
    private String name;
    private String phone;
    private String address;
    private int isPicture;
    private String photo;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

    public String getLOB() {
        return LOB;
    }

    public void setLOB(String LOB) {
        this.LOB = LOB;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    @Override
    public boolean isValid(String Scenario) {
        return true;
    }
}
