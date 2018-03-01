package com.universe.android.resource.Login.CrystalReport;

import com.universe.android.web.BaseRequest;

/**
 * Created by gaurav.pandey on 22-02-2018.
 */

public class CrystalReportRequest extends BaseRequest {

    public String getCdId() {
        return cdId;
    }

    public void setCdId(String cdId) {
        this.cdId = cdId;
    }

    private String cdId;

    @Override
    public boolean isValid(String Scenario) {
        return true;
    }
}
