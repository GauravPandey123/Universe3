package com.universe.android.resource.Login;

import com.universe.android.web.BaseService;

/**
 * Created by gaurav.pandey on 06-02-2018.
 */
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

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
