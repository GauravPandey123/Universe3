package com.universe.android.resource.Login.NewRetailor.StateAndCrop;

import com.universe.android.web.BaseService;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by gaurav.pandey on 03-03-2018.
 */

public class StateAndCropService extends BaseService<StateAndCropService.StateandCrop,StateAndCropRequest,StaeAndCropResponse> {


    @Override
    public Call<StaeAndCropResponse> onExecute(StateandCrop api, StateAndCropRequest request) {
        return api.statecrop();
    }

    @Override
    public Class<StateandCrop> getAPI() {
        return StateandCrop.class;
    }

    public interface StateandCrop {
        @GET("api/stateAndcropList")
        Call<StaeAndCropResponse> statecrop();
    }
}
