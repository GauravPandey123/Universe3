package com.universe.android.resource.Login.CrystalReport;

import com.universe.android.web.BaseRequest;

/**
 * Created by gaurav.pandey on 22-02-2018.
 */

public class CrystalReportRequest extends BaseRequest {
    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    private String surveyId;

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
