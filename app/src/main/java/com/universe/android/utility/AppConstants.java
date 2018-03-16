package com.universe.android.utility;

import java.text.SimpleDateFormat;

/**
 * Created by gaurav.pandey on 23-01-2018.
 */

public class AppConstants {
    public static final int SUCCESS = 200;
    public static final String STR_TITLE = "strTitle";
    public static final String DATE_FORMAT = "dd MMM yyyy";
    public static final SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat format11 = new SimpleDateFormat("dd-MM-yyyy");
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
    public static final String EXPIRYDATE = "expiryDate";
    public static final String PROPERTIES = "properties";
    public static final String STRING = "string";
    public static final String NUMBER = "number";
    public static final String FLOAT = "float";
    public static final String FORM_ANS_ID = "formAnsId";
    public static final String RESPONSES = "responses";
    public static final String RESPONSE = "response";
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
    public static final String ID = "_id";

    public static final String ISUPDATE = "isUpdate";
    public static final String ISSYNC = "isSync";
    public static final String LONG = "long";

    //for login Module
    public static final String TOKEN_ID = "token_id";
    public static final String TOKEN_KEY = "Authorization";
    public static final String ACCESS_TOKEN = "accessToken";

    public static final String DESIGNATION = "designation";
    public static final String UserId = "user_id";
    public static final String isActive = "isActive";
    public static final String designationLevel = "designationLevel";
    public static final String phone = "phone";
    public static final String password = "password";
    public static final String email = "email";
    public static final String name = "employee_name";
    public static final String picture = "picture";
    public static final String location = "location";
    public static final String lat = "lat";
    public static final String lng = "lat";
    public static final String NAME = "name";

    public static final String Login_Status = "login_status";
    public static final String AUTHORIZATION = "Authorization";
    public static final String LAST_SYNC_DATE = "LastSyncDate";
    public static final String WORKFLOWS = "workflows";
    public static final String SURVEYREPORT = "surveyReport";
    public static final String CREATED_BY = "createdBy";
    public static final String TITLE = "title";
    public static final String MINUS_ONE = "minus";
    public static final String ISACTIVE = "isActive";
    public static final String YES = "Yes";
    public static final String CATEGORY_NAME = "categoryName";
    public static final String CATEGORY = "category";
    public static final String LATTITUDE = "lattitude";
    public static final String LONGITUDE = "longitude";
    public static final String PROFILE_CHECK = "ProfileCheck";

    //survey status
    public static final int PENDING = 0;
    public static final int INPROCESS = 1;
    public static final int SUBMITTED = 2;
    public static final int COMPLETED = 3;
    public static final int REJECT = 4;
    public static final String ANSWERS = "answers";
    public static final String SUBMITBY_CD = "submitbyCD";
    public static final String SUBMITBY_RM = "submitbyRM";
    public static final String SUBMITBY_ZM = "submitbyZM";
    public static final String CD_STATUS = "cd_Status";
    public static final String RM_STATUS = "rm_Status";
    public static final String ZM_STATUS = "zm_Status";
    public static final String USERNAME = "userName";
    public static final String STATUS = "status";
    public static final String QUESTIONS = "questions";
    public static final String QUESTIONID = "questionId";
    public static final String ANSWER = "answer";
    public static final String REQUIRED = "required";
    public static final String ISVIEW = "isView";
    public static final String WORKFLOW = "workflow";
    public static final String REPORT = "report";

    public static final String MULTISELECT = "multiselect";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String LAT = "lat";
    public static final String LNG = "lng";
    public static final String DETAIL = "detail";
    public static final String TYPE = "type";
    public static final String TODATE ="toDate" ;
    public static final String CUSTOMER = "customer";
    public static final String PNG_FILE_TYPE = "pngFile";
    public static final String EXCEL_FILE_TYPE = "excelFile";
    public static final String DIR_UNIVERSE = "/Universe";
    public static final String NEW = "retailer";
    public static final String ISVIEWBYZM ="isViewZM" ;
    public static final String MULTIEDITTEXT = "multiedittext";
    public static final String ROLE = "role";

    public static String surveyDetails = "surveyDetails";
    public static String crystalDoctorName = "crystalDoctorName";
    public static String total = "total";
    public static String completed = "completed";

    public static String target = "target";
    public static String submitted = "submitted";
    public static String inprogress = "inprogress";
    public static String CrystalCustomer = "CrystalCustomer";
    public static String newRetailer = "newRetailer";


    public static final String UPDATEID = "updateId";
    public static final String MAPPING = "mapping";
    public static final String DETAILS = "details";
    public static final String EMPLOYEE_NAME = "employee_name";

    public static final String customer = "customer";
    public static final String employee = "employee";
    public static final String ME ="Me" ;
    public static final String REASON = "reason";
    public static final String VISIBLITY = "visiblity";
    public static final String GROUP_POSITION = "groupPosition";


    public static String Percent = "percent";
    public static String CrystaDoctorName="CrystaDoctorName";
    public static String LoginDetail="loginDetails";
    public static String detail="detail";


    public static String CDID="cdid";
    public static String FROMDATE="formdate";
    public static String STATECODE="statecode";
    public static String TerroitryCode="TerroitryCode";
    public static String CROPID="cropid";
    public static String Distributor_Id="distributor_id";


    public static String VillageId="villageId";
    public static String employee_name="employee_name";


    public static String TeamSurveyId="TeamSurveyId";
    public static String employee_code="employee_code";


    public static String retailerName="retailerName";
    public static String state_code="state_code";
    public static String territory_code="territory_code";
    public static String distributer_code="distributer_code";
    public static String mobile="mobile";
    public static String mobileNumber="mobileNumber";
    public static String pincode="pincode";
    public static String cropId="cropId";
    public static String village_code="village_code";
    public static String Address="Address";
    public static String totalSales="totalSales";


    public static String CUSTOMERIMAGE="CUSTOMERIMAGE";
    public static String LocationUpdate="LocationUpdate";
    public static String FilterData="FilterData";
    public static String TeamSurveyname="TeamSurveyname";
    public static String accessToken="accessToken";
    public static String requester="requester";
    public static String lob_name="lob_name";
    public static String VillageData="villageData";
}

