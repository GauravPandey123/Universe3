package com.universe.android.resource.Login.NewRetailor.StateAndCrop.AddNewReatiler;

import com.universe.android.web.BaseRequest;

/**
 * Created by gaurav.pandey on 04-03-2018.
 */

public class AddNewReatilerRequest extends BaseRequest {
    public String getRetailerName() {
        return retailerName;
    }

    public void setRetailerName(String retailerName) {
        this.retailerName = retailerName;
    }

    public int getState_code() {
        return state_code;
    }

    public void setState_code(int state_code) {
        this.state_code = state_code;
    }

    public int getTerritory_code() {
        return territory_code;
    }

    public void setTerritory_code(int territory_code) {
        this.territory_code = territory_code;
    }

    public String getDistributer_code() {
        return distributer_code;
    }

    public void setDistributer_code(String distributer_code) {
        this.distributer_code = distributer_code;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCropId() {
        return cropId;
    }

    public void setCropId(String cropId) {
        this.cropId = cropId;
    }

    public String getVillage_code() {
        return village_code;
    }

    public void setVillage_code(String village_code) {
        this.village_code = village_code;
    }

    public String getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(String totalSales) {
        this.totalSales = totalSales;
    }

    private String retailerName;
    private int state_code;
    private int territory_code;
    private String distributer_code;

    private String mobileNumber;
    private String pincode;
    private String cropId;
    private String village_code;
    private String totalSales;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    private String Address;

    @Override
    public boolean isValid(String Scenario) {
        return true;
    }
}
