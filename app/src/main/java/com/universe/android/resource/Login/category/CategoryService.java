package com.universe.android.resource.Login.category;

import com.universe.android.web.BaseService;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by gaurav.pandey on 06-02-2018.
 */

public class CategoryService extends BaseService<CategoryService.SurveyApi, CategoryRequest, CategoryResponse> {


    @Override
    public Call<CategoryResponse> onExecute(SurveyApi api, CategoryRequest request) {
        return api.login(request);
    }

    @Override
    public Class<SurveyApi> getAPI() {
        return SurveyApi.class;
    }

    public interface SurveyApi {
        @GET("/api/category")
        Call<CategoryResponse> login(@Body CategoryRequest loginRequest);

    }
}
