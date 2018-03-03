package com.universe.android.resource.Login.NewRetailor.StateAndCrop.Territory;

import com.universe.android.web.BaseRequest;

/**
 * Created by gaurav.pandey on 03-03-2018.
 */

public class TerrorityRequest extends BaseRequest {

    public int getState_code() {
        return state_code;
    }

    public void setState_code(int state_code) {
        this.state_code = state_code;
    }

    private int state_code;
    @Override
    public boolean isValid(String Scenario) {
        return true;
    }
}
