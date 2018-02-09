package com.universe.android.resource.Login.customer;

import com.universe.android.web.BaseService;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by gaurav.pandey on 06-02-2018.
 */

public class CustomerService extends BaseService<CustomerService.SurveyApi, CustomerRequest, CustomerResponse> {


    @Override
    public Call<CustomerResponse> onExecute(SurveyApi api, CustomerRequest request) {
        return api.login(request);
    }

    @Override
    public Class<SurveyApi> getAPI() {
        return SurveyApi.class;
    }

    public interface SurveyApi {
        @POST("/api/customers")
        Call<CustomerResponse> login(@Body CustomerRequest loginRequest);

    }
}
