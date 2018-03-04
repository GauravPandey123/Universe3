package com.universe.android.resource.Login.NewRetailor.StateAndCrop.DistributorAndVillage;

import com.universe.android.web.BaseService;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by gaurav.pandey on 04-03-2018.
 */

public class DistributorService extends BaseService<DistributorService.DistriButorService, DistributorRequest, DistributorResponse> {


    @Override
    public Call<DistributorResponse> onExecute(DistriButorService api, DistributorRequest request) {
        return api.DISTRIBUTOR_RESPONSE_CALL(request);
    }

    @Override
    public Class<DistriButorService> getAPI() {
        return DistriButorService.class;
    }

    public interface DistriButorService {
        @POST("api/distributerWithVillage")
        Call<DistributorResponse> DISTRIBUTOR_RESPONSE_CALL(@Body DistributorRequest distributorRequest);
    }
}
