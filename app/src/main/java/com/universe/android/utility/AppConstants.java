package com.universe.android.utility;

import java.text.SimpleDateFormat;

/**
 * Created by gaurav.pandey on 23-01-2018.
 */

public class AppConstants {
    public static final String STR_TITLE = "strTitle";
    public static final String DATE_FORMAT = "dd MMM yyyy";
    public static final SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
    public static final SimpleDateFormat format2 = new SimpleDateFormat(DATE_FORMAT);
    public static final String format8 = "MMM dd, yyyy hh:mm:ss";
    //    2018-12-05T00:00:00.000Z
    public static final SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00.000'Z'");
    public static final SimpleDateFormat format4 = new SimpleDateFormat("yyyy-MM-dd");
    public static final String utc_format = "yyyy-MM-dd'T'HH:00:00.SSS'Z'";
    public static final String utc_format1 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String format6 = "yyyy-MM-dd";
    public static final String format5 = "MMM dd, yyyy hh:mm:ss a";
    public static final SimpleDateFormat format10 = new SimpleDateFormat(AppConstants.format5);
    public static final String format = "dd/MMM/yyyy";

    public static final int DashBoard = 0;
    public static final int SurveyReport = 1;
    public static final int SyncResponse = 2;
    public static final int Questionaire = 3;
    public static final int Analytics = 4;
    public static final int ManageTeams = 5;
    public static final int Help = 6;
    public static final int UpdateApplication = 7;
    public static final int LogOut = 8;
    public static final String POSITION = "position";
    public static final String CATEGORYID = "categoryId";
    public static final String CLIENTID = "clientId";
    public static final String CUSTOMERID = "customerId";
    public static final String SURVEYID = "surveyId";
    public static final String UPDATEDAT = "updatedAt";
    public static final String EXPIRYDATE ="expiryDate" ;
    public static final String PROPERTIES = "properties";
    public static final String STRING = "string";
    public static final String NUMBER = "number";
    public static final String FLOAT = "float";
    public static final String FORM_ANS_ID = "formAnsId";
    public static final String RESPONSES = "responses";
    public static final String SUBMITBY = "submitBy";
    public static final String RADIO = "radio";
    public static final String CHECKBOX = "checkbox";
    public static final String SELECT = "select";
    public static final String TEXTBOX = "textbox";
    public static final String TEXTAREA = "textarea";
    public static final String SECTION = "section";
    public static final String DATE = "date";
    public static final String CREATEDAT = "createdAt";
    public static final String ASTERIK_SIGN = "<font color='#03A9F4'> *</font>";
    public static final String FORM_ID = "formId";
    public static final String ID = "id";

    public static final String ISUPDATE = "isUpdate";
    public static final String ISSYNC ="isSync" ;
    public static final String LONG = "long";
}
