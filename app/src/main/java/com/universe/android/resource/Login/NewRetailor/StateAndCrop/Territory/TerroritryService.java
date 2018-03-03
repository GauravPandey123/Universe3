package com.universe.android.resource.Login.NewRetailor.StateAndCrop.Territory;

import com.universe.android.web.BaseService;

import retrofit2.Call;
import retrofit2.http.Body;

/**
 * Created by gaurav.pandey on 03-03-2018.
 */

public class TerroritryService extends BaseService<TerroritryService.terroritryService,TerrorityRequest,TerroritryResponse> {


    @Override
    public Call<TerroritryResponse> onExecute(terroritryService api, TerrorityRequest request) {
        return api.TERRORITRY_RESPONSE_CALL(request);
    }

    @Override
    public Class<terroritryService> getAPI() {
        return terroritryService.class;
    }

    public interface terroritryService
    {
        Call<TerroritryResponse> TERRORITRY_RESPONSE_CALL(@Body TerrorityRequest terrorityRequest);
    }
}
