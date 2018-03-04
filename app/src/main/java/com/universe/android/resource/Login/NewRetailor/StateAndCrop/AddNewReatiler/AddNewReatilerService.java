package com.universe.android.resource.Login.NewRetailor.StateAndCrop.AddNewReatiler;

import com.universe.android.web.BaseService;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by gaurav.pandey on 04-03-2018.
 */

public class AddNewReatilerService extends BaseService<AddNewReatilerService.AddNewReatiler,AddNewReatilerRequest,AddNewReatilerResponse>
{

    @Override
    public Call<AddNewReatilerResponse> onExecute(AddNewReatiler api, AddNewReatilerRequest request) {
        return api.ADD_NEW_REATILER_RESPONSE_CALL(request);
    }

    @Override
    public Class<AddNewReatiler> getAPI() {
        return AddNewReatiler.class;
    }

    public interface AddNewReatiler
    {
        @POST("api/addRetailer")
        Call<AddNewReatilerResponse> ADD_NEW_REATILER_RESPONSE_CALL(@Body AddNewReatilerRequest addNewReatilerRequest);
    }
}
