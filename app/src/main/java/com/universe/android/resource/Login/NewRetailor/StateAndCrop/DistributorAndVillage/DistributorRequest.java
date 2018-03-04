package com.universe.android.resource.Login.NewRetailor.StateAndCrop.DistributorAndVillage;

import com.universe.android.web.BaseRequest;

/**
 * Created by gaurav.pandey on 04-03-2018.
 */

public class DistributorRequest extends BaseRequest {
    public int getTerritory_code() {
        return territory_code;
    }

    public void setTerritory_code(int territory_code) {
        this.territory_code = territory_code;
    }

    private int territory_code;

    @Override
    public boolean isValid(String Scenario) {
        return true;
    }
}
