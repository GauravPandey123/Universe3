package com.universe.android.resource.Login.client;

import com.universe.android.web.BaseService;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by gaurav.pandey on 06-02-2018.
 */

public class ClientService extends BaseService<ClientService.SurveyApi, ClientRequest, ClientResponse> {


    @Override
    public Call<ClientResponse> onExecute(SurveyApi api, ClientRequest request) {
        return api.login(request);
    }

    @Override
    public Class<SurveyApi> getAPI() {
        return SurveyApi.class;
    }

    public interface SurveyApi {
        @POST("/api/clients")
        Call<ClientResponse> login(@Body ClientRequest loginRequest);

    }
}
