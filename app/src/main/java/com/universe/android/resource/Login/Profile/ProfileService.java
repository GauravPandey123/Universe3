package com.universe.android.resource.Login.Profile;

import com.universe.android.web.BaseService;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by gaurav.pandey on 07-02-2018.
 */

public class ProfileService extends BaseService<ProfileService.ProfileInterface, ProfileRequest, ProfileResponse> {

    @Override
    public Call<ProfileResponse> onExecute(ProfileInterface api, ProfileRequest request) {
        return api.profileResponse(request);
    }

    @Override
    public Class<ProfileInterface> getAPI() {
        return ProfileInterface.class;
    }

    public interface ProfileInterface {
        @POST("/api/updateProfile")
        Call<ProfileResponse> profileResponse(@Body ProfileRequest profileRequest);


    }
}
