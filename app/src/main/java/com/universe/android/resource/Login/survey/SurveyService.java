package com.universe.android.resource.Login.survey;

import com.universe.android.web.BaseService;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

/**
 * Created by gaurav.pandey on 06-02-2018.
 */

public class SurveyService extends BaseService<SurveyService.SurveyApi, SurveyRequest, SurveyResponse> {


    @Override
    public Call<SurveyResponse> onExecute(SurveyApi api, SurveyRequest request) {
        return api.login(request);
    }

    @Override
    public Class<SurveyApi> getAPI() {
        return SurveyApi.class;
    }

    public interface SurveyApi {
        @GET("/api/surveys")
        Call<SurveyResponse> login(@Body SurveyRequest loginRequest);

    }
}
