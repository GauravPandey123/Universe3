package com.universe.android.resource.Login.login;

import com.universe.android.web.BaseService;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by gaurav.pandey on 06-02-2018.
 */

public class LoginService extends BaseService<LoginService.LoginApi, LoginRequest, LoginResponse> {


    @Override
    public Call<LoginResponse> onExecute(LoginApi api, LoginRequest request) {
        return api.login(request);
    }

    @Override
    public Class<LoginApi> getAPI() {
        return LoginApi.class;
    }

    public interface LoginApi {
        @POST("/api/login")
        Call<LoginResponse> login(@Body LoginRequest loginRequest);

    }
}
