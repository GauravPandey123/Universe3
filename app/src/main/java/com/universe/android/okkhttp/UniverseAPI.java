package com.universe.android.okkhttp;


import okhttp3.MediaType;

/**
 * API Methods of HealthRise
 */
public interface UniverseAPI {
    String CONTENT_TYPE = "Content-Type";
    String APPLICATION_JSON = "application/json";
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    String WEB_SERVICE_BASE_URL = "http://103.11.84.144:3006/api/";
    String WEB_SERVICE_CREATE_SURVEY_METHOD = WEB_SERVICE_BASE_URL + "surveys";


}
