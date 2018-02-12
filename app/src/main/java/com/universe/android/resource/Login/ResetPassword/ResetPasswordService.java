package com.universe.android.resource.Login.ResetPassword;

import com.universe.android.web.BaseService;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by gaurav.pandey on 07-02-2018.
 */

public class ResetPasswordService extends BaseService<ResetPasswordService.ResetPasswordApi, ResetPasswordRequest, ResetPasswordResponse> {


    @Override
    public Call<ResetPasswordResponse> onExecute(ResetPasswordApi api, ResetPasswordRequest request) {
        return api.resetPasswordResponse(request);
    }

    @Override
    public Class<ResetPasswordApi> getAPI() {
        return ResetPasswordApi.class;
    }

    public interface ResetPasswordApi {
        @POST("/api/changePassword")
        Call<ResetPasswordResponse> resetPasswordResponse(@Body ResetPasswordRequest resetPasswordRequest);
    }
}
