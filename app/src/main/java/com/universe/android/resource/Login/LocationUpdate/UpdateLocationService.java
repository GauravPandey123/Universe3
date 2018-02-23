package com.universe.android.resource.Login.LocationUpdate;

import com.universe.android.web.BaseService;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by gaurav.pandey on 20-02-2018.
 */

public class UpdateLocationService extends BaseService<UpdateLocationService.UpDateLocationServiceApi, UpadteLocationRequest, UpDateLocationResponse> {


    @Override
    public Call<UpDateLocationResponse> onExecute(UpDateLocationServiceApi api, UpadteLocationRequest request) {
        return api.upDateLocationResponse(request);
    }

    @Override
    public Class<UpDateLocationServiceApi> getAPI() {
        return UpDateLocationServiceApi.class;
    }

    public interface UpDateLocationServiceApi {
        @POST("/api/setLocation")
        Call<UpDateLocationResponse> upDateLocationResponse(@Body UpadteLocationRequest upadteLocationRequest);
    }
}
