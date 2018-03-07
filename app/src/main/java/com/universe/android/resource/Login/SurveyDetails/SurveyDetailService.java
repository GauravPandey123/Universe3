package com.universe.android.resource.Login.SurveyDetails;

import com.universe.android.web.BaseService;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by gaurav.pandey on 05-03-2018.
 */

public class SurveyDetailService extends BaseService<SurveyDetailService.surveyDeatil, SurveyDeatailRequest, SurverDetailResponse> {


    @Override
    public Call<SurverDetailResponse> onExecute(surveyDeatil api, SurveyDeatailRequest request) {
        return api.surveyDeatilResponse(request);
    }

    @Override
    public Class<surveyDeatil> getAPI() {
        return surveyDeatil.class;
    }

    public interface surveyDeatil {
        @POST("api/surveyDetails")
        Call<SurverDetailResponse> surveyDeatilResponse(@Body SurveyDeatailRequest surveyDeatailRequest);
    }
}
