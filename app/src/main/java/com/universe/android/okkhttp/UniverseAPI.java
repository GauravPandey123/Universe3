package com.universe.android.okkhttp;


import okhttp3.MediaType;

/**
 * API Methods of HealthRise
 */
public interface UniverseAPI {
    String CONTENT_TYPE = "Content-Type";
    String APPLICATION_JSON = "application/json";
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    String WEB_SERVICE_BASE_URL = "http://13.127.101.233:3005/api/";
    String WEB_SERVICE_CREATE_SURVEY_METHOD = WEB_SERVICE_BASE_URL + "addForm";
    String WEB_SERVICE_LOGIN_METHOD = WEB_SERVICE_BASE_URL + "login";
    String WEB_SERVICE_CREATE_ANSWER_METHOD = WEB_SERVICE_BASE_URL + "answers";
    String WEB_SERVICE_CREATE_APPROVE_METHOD = WEB_SERVICE_BASE_URL + "approve";
    String WEB_SERVICE_CREATE_UPDATE_APPROVE_METHOD = WEB_SERVICE_BASE_URL + "updateApprove";
    String WEB_SERVICE_CREATE_UPDATE_METHOD = WEB_SERVICE_BASE_URL + "updateAnswers";
    String WEB_SERVICE_UPDATE_SURVEY_METHOD = WEB_SERVICE_BASE_URL + "updateForm";
    String WEB_SERVICE_ALLFORM_METHOD = WEB_SERVICE_BASE_URL + "allForms";
    String WEB_SERVICE_CREATE_ClIENT_METHOD = WEB_SERVICE_BASE_URL + "addClient";
    String WEB_SERVICE_CREATE_CUSTOMER_METHOD = WEB_SERVICE_BASE_URL + "addCustomer";

    //list
    String WEB_SERVICE_LIST_ADMIN_SURVEY_METHOD = WEB_SERVICE_BASE_URL + "adminSurveyList";
    String WEB_SERVICE_LIST_SURVEY_METHOD = WEB_SERVICE_BASE_URL + "surveyList";
    String WEB_SERVICE_LIST_CLIENT_METHOD = WEB_SERVICE_BASE_URL + "clientList";
    String WEB_SERVICE_LIST_CUSTOMER_METHOD = WEB_SERVICE_BASE_URL + "customerList";
    String WEB_SERVICE_LIST_CATEGORY_METHOD = WEB_SERVICE_BASE_URL + "categoryList";
    String WEB_SERVICE_LIST_QUESTION_METHOD = WEB_SERVICE_BASE_URL + "surveyQuestionList";



}
