package com.universe.android.resource.Login.questions;

import com.universe.android.web.BaseService;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by gaurav.pandey on 06-02-2018.
 */

public class QuestionsService extends BaseService<QuestionsService.SurveyApi, QuestionsRequest, QuestionsResponse> {


    @Override
    public Call<QuestionsResponse> onExecute(SurveyApi api, QuestionsRequest request) {
        return api.login(request);
    }

    @Override
    public Class<SurveyApi> getAPI() {
        return SurveyApi.class;
    }

    public interface SurveyApi {
        @POST("/api/questions")
        Call<QuestionsResponse> login(@Body QuestionsRequest loginRequest);

    }
}
