package com.universe.android.resource.Login.SurveyDetails;

import com.universe.android.web.BaseRequest;

/**
 * Created by gaurav.pandey on 05-03-2018.
 */

public class SurveyDeatailRequest extends BaseRequest {

    private String surveyId;

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getEmployee_code() {
        return employee_code;
    }

    public void setEmployee_code(String employee_code) {
        this.employee_code = employee_code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    private String employee_code;
    private String type;
    private String filter;
    private String employeeId;
    private String startDate;
    private String endDate;

    @Override
    public boolean isValid(String Scenario) {
        return true;
    }
}

