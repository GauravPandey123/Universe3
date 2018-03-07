package com.universe.android.resource.Login.surveyList;

import com.universe.android.web.BaseService;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by gaurav.pandey on 04-03-2018.
 */

public class SurveyListService extends BaseService<SurveyListService.surveyListApi, SurveyRequest, SurveyListResponse> {


    @Override
    public Call<SurveyListResponse> onExecute(surveyListApi api, SurveyRequest request) {

        return api.SURVEY_LIST_RESPONSE_CALL();
    }

    @Override
    public Class<surveyListApi> getAPI() {
        return surveyListApi.class;
    }

    public interface surveyListApi {
        @GET("api/surveyList")
        Call<SurveyListResponse> SURVEY_LIST_RESPONSE_CALL();

    }
}
