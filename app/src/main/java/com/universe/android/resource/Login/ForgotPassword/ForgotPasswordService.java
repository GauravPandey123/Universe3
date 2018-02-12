package com.universe.android.resource.Login.ForgotPassword;

import com.universe.android.web.BaseService;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by gaurav.pandey on 07-02-2018.
 */

public class ForgotPasswordService extends BaseService<ForgotPasswordService.forgotPasswordService, ForgotPasswordRequest, ForgotPasswordResponse> {


    @Override
    public Call<ForgotPasswordResponse> onExecute(forgotPasswordService api, ForgotPasswordRequest request) {
        return api.forgotPasswordResponse(request);
    }

    @Override
    public Class<forgotPasswordService> getAPI() {
        return forgotPasswordService.class;
    }

    public interface forgotPasswordService {
        @POST("/api/forgotPassword")
        Call<ForgotPasswordResponse> forgotPasswordResponse(@Body ForgotPasswordRequest forgotPasswordRequest);
    }
}
