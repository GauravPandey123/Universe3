package com.universe.android.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.universe.android.R;
import com.universe.android.activity.admin.FormQuestionActivity;
import com.universe.android.enums.FormEnum;
import com.universe.android.helper.FontClass;
import com.universe.android.helper.GPSTracker;
import com.universe.android.okkhttp.APIClient;
import com.universe.android.okkhttp.UniverseAPI;
import com.universe.android.realmbean.RealmController;
import com.universe.android.resource.Login.login.LoginRequest;
import com.universe.android.resource.Login.login.LoginResponse;
import com.universe.android.resource.Login.login.LoginService;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;
import com.universe.android.web.BaseApiCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import in.editsoft.api.exception.APIException;
import in.editsoft.api.util.App;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private EditText editTextInputEmail, editTextInputPassword;
    private AppCompatButton textViewLogin;
    private TextView textViewForgotPassword;
    private TextInputLayout textInputLayoutEmailLogin, textInputLayoutPassword;
    private GPSTracker gpsTracker;

    private double latitude;
    private double longitude;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialization();
        setUpElements();
    }

    private void initialization() {
        textViewLogin = findViewById(R.id.btn_login);
        editTextInputEmail = findViewById(R.id.input_email);
        editTextInputPassword = findViewById(R.id.input_password);
        textViewForgotPassword = findViewById(R.id.link_forgot_password);
        textInputLayoutEmailLogin = findViewById(R.id.textInputLayoutEmailLogin);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);

        editTextInputEmail.setTypeface(FontClass.openSansLight(mContext));
        editTextInputPassword.setTypeface(FontClass.openSansLight(mContext));
        textViewLogin.setTypeface(FontClass.openSansRegular(mContext));
        textViewForgotPassword.setTypeface(FontClass.openSansRegular(mContext));
        textInputLayoutEmailLogin.setTypeface(FontClass.openSansRegular(mContext));
        textInputLayoutPassword.setTypeface(FontClass.openSansRegular(mContext));

        gpsTracker = new GPSTracker(LoginActivity.this);
        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();
    }


    private void setUpElements() {
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.animateView(v);
                login();
                // getSurveyResponse();
            }
        });
        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, ForgotPasswordActivity.class));
                finish();
                // overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        final String email = editTextInputEmail.getText().toString();
        final String pass = editTextInputPassword.getText().toString();
        if (validateDetails(email, pass)) {
            if (!Utility.isConnected()) {
                Utility.showToast(R.string.msg_disconnected);
            } else {

                submitLoginRequest(email, pass);
            }
        }

    }

    private boolean validateDetails(String email, String pass) {
        // Utility.hideSoftKeyboard(mA);
        if (!Utility.validateString(email)) {
            Utility.showToast(R.string.msg_enter_email);
            return false;
        } else if (!Utility.validateEmail(email)) {
            Utility.showToast(R.string.msg_email_error);
            return false;
        } else if (!Utility.validateString(pass)) {
            Utility.showToast(R.string.msg_enter_pass1);
            return false;
        } else if (pass.length() < 1) {
            Utility.showToast(R.string.msg_pass_error);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }


    public void submitLoginRequest(String email, String password) {
        showProgress(R.string.msg_load_default);
        final JSONObject jsonSubmitReq = new JSONObject();
        try {
            jsonSubmitReq.put(AppConstants.EMAIL, email.toLowerCase());
            jsonSubmitReq.put(AppConstants.PASSWORD, password);
            jsonSubmitReq.put(AppConstants.LAT, latitude);
            jsonSubmitReq.put(AppConstants.LNG, longitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpClient okHttpClient = APIClient.getHttpClient();
        RequestBody requestBody = RequestBody.create(UniverseAPI.JSON, jsonSubmitReq.toString());
        String url = UniverseAPI.WEB_SERVICE_LOGIN_METHOD;

        final Request request = APIClient.getPostRequest(this, url, requestBody);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                errorExceptionHandle(e.getMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                try {
                    if (response != null && response.isSuccessful()) {
                        String responseData = response.body().string();
                        if (responseData != null) {
                            JSONObject jsonResponse = new JSONObject(responseData);
                            JSONObject jsonObject = jsonResponse.getJSONObject(AppConstants.RESPONSE);
                            Prefs.putStringPrefs(AppConstants.TYPE, jsonObject.optString("type"));
                            Prefs.putStringPrefs(AppConstants.ROLE, jsonObject.optString("role"));
                            JSONObject jsonObject1 = jsonObject.getJSONObject(AppConstants.DETAIL);
                            Prefs.putStringPrefs(AppConstants.employee_name, jsonObject1.optString("name"));
                            Prefs.putStringPrefs(AppConstants.picture, jsonObject1.optString("picture"));
                            Prefs.putStringPrefs(AppConstants.email, jsonObject1.optString("email").toLowerCase());
                            Prefs.putStringPrefs(AppConstants.phone, jsonObject1.optString(AppConstants.phone));
                            Prefs.putStringPrefs(AppConstants.employee_code, jsonObject1.optString("employee_code"));
                            Prefs.putStringPrefs(AppConstants.password, jsonObject1.optString("password"));
                            Prefs.putStringPrefs(AppConstants.accessToken, jsonObject1.optString("accessToken"));
                            Prefs.putStringPrefs(AppConstants.lat, jsonObject1.optString("lat"));
                            Prefs.putStringPrefs(AppConstants.lng, jsonObject1.optString("lng"));
                            Prefs.putStringPrefs(AppConstants.location, jsonObject1.optString("location"));
                            Prefs.putStringPrefs(AppConstants.UserId, jsonObject1.optString(AppConstants.ID));
                            jsonObject.put(AppConstants.ID,"00");
                            new RealmController().saveUserDetail(jsonObject.toString());

                        }

                        if (Prefs.getStringPrefs(AppConstants.TYPE).equalsIgnoreCase(AppConstants.ADMIN)){

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getAdminSurveyResponse();
                                }
                            });

                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getSurveyResponse();
                                }
                            });

                        }


                    } else {
                        errorHandle(response);


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    errorExceptionHandle(e.getMessage());


                }
            }
        });
    }

    private void errorHandle(final Response response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                assert response != null;
                Utility.showToast("Unexpected code - "+response.code()+" "+response.message());
                dismissProgress();
            }
        });
    }
    private void errorExceptionHandle(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                assert response != null;
                Utility.showToast(response);
                dismissProgress();
            }
        });
    }



    private void getQuestionsResponse() {
        OkHttpClient okHttpClient = APIClient.getHttpClient();
        String url = UniverseAPI.WEB_SERVICE_ALLFORM_METHOD;
        Request request = APIClient.getRequest(mContext, url);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                errorExceptionHandle(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {

                    if (response != null && response.isSuccessful()) {
                        String responseData = response.body().string();
                        if (responseData != null) {
                            JSONObject jsonResponse = new JSONObject(responseData);
                            JSONArray array = jsonResponse.getJSONArray(AppConstants.RESPONSE);
                            new RealmController().saveQuestions(array.toString());
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getSurveyQuestionsResponse();
                            }
                        });

                    } else {
                        errorHandle(response);
                    }

                } catch (Exception e) {

                    e.printStackTrace();
                    errorExceptionHandle(e.getMessage());
                } finally {
                }

            }
        });

    }
    private void getAdminSurveyResponse() {
        JSONObject jsonSubmitReq=new JSONObject();
        try {
            jsonSubmitReq.put(AppConstants.UserId,Prefs.getStringPrefs(AppConstants.UserId));
            if (Prefs.getStringPrefs(AppConstants.TYPE).equalsIgnoreCase(AppConstants.ADMIN))
                jsonSubmitReq.put(AppConstants.TYPE,Prefs.getStringPrefs(AppConstants.TYPE));
            else {
                jsonSubmitReq.put(AppConstants.TYPE,"");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpClient okHttpClient = APIClient.getHttpClient();


        RequestBody requestBody = RequestBody.create(UniverseAPI.JSON, jsonSubmitReq.toString());
        String url = UniverseAPI.WEB_SERVICE_LIST_ADMIN_SURVEY_METHOD;
        Request request = APIClient.getPostRequest(this, url, requestBody);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
               errorExceptionHandle(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {

                    if (response != null && response.isSuccessful()) {
                        String responseData = response.body().string();
                        if (responseData != null) {
                            JSONObject jsonResponse = new JSONObject(responseData);
                            JSONArray array = jsonResponse.getJSONArray(AppConstants.RESPONSE);
                            new RealmController().saveSurveysResponse(array.toString());
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getClientResponse();
                            }
                        });


                    } else {
                     errorHandle(response);
                    }

                } catch (Exception e) {
                    errorExceptionHandle(e.getMessage());
                    e.printStackTrace();
                } finally {
                }

            }
        });

    }

    private void getSurveyResponse() {
        JSONObject jsonSubmitReq=new JSONObject();
        try {
            jsonSubmitReq.put(AppConstants.UserId,Prefs.getStringPrefs(AppConstants.UserId));
            if (Prefs.getStringPrefs(AppConstants.TYPE).equalsIgnoreCase(AppConstants.ADMIN))
            jsonSubmitReq.put(AppConstants.TYPE,Prefs.getStringPrefs(AppConstants.TYPE));
            else {
                jsonSubmitReq.put(AppConstants.TYPE,"");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpClient okHttpClient = APIClient.getHttpClient();


        RequestBody requestBody = RequestBody.create(UniverseAPI.JSON, jsonSubmitReq.toString());
        String url = UniverseAPI.WEB_SERVICE_LIST_ADMIN_SURVEY_METHOD;
        Request request = APIClient.getPostRequest(this, url, requestBody);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                errorExceptionHandle(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {

                    if (response != null && response.isSuccessful()) {
                        String responseData = response.body().string();
                        if (responseData != null) {
                            JSONObject jsonResponse = new JSONObject(responseData);
                            JSONArray array = jsonResponse.getJSONArray(AppConstants.RESPONSE);
                            new RealmController().saveSurveysListResponse(array.toString());
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getClientResponse();
                            }
                        });


                    } else {
                     errorHandle(response);
                    }

                } catch (Exception e) {
                    errorExceptionHandle(e.getMessage());
                    e.printStackTrace();
                } finally {
                }

            }
        });

    }


    private void getClientResponse() {
        OkHttpClient okHttpClient = APIClient.getHttpClient();
        String url = UniverseAPI.WEB_SERVICE_LIST_CLIENT_METHOD;
        Request request = APIClient.getRequest(mContext, url);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                errorExceptionHandle(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {

                    if (response != null && response.isSuccessful()) {
                        String responseData = response.body().string();
                        if (responseData != null) {
                            JSONObject jsonResponse = new JSONObject(responseData);
                            JSONArray array = jsonResponse.getJSONArray(AppConstants.RESPONSE);
                            new RealmController().saveClientsResponse(array.toString());
                        }


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getCustomerResponse();
                            }
                        });


                    } else {
                       errorHandle(response);
                    }

                } catch (Exception e) {
                    errorExceptionHandle(e.getMessage());
                    e.printStackTrace();
                } finally {
                }

            }
        });

    }


    private void getCustomerResponse() {


        OkHttpClient okHttpClient = APIClient.getHttpClient();
        String url = UniverseAPI.WEB_SERVICE_LIST_CUSTOMER_METHOD;


        Request request = APIClient.getRequest(mContext, url);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                errorExceptionHandle(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {

                    if (response != null && response.isSuccessful()) {
                        String responseData = response.body().string();
                        if (responseData != null) {
                            JSONObject jsonResponse = new JSONObject(responseData);
                            JSONArray array = jsonResponse.getJSONArray(AppConstants.RESPONSE);
                            new RealmController().saveCustomersResponse(array.toString());
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getCategoryResponse();
                            }
                        });


                    } else {
                     errorHandle(response);
                    }

                } catch (Exception e) {
                    errorExceptionHandle(e.getMessage());
                    e.printStackTrace();
                } finally {
                }

            }
        });

    }


    private void getCategoryResponse() {
        OkHttpClient okHttpClient = APIClient.getHttpClient();
        String url = UniverseAPI.WEB_SERVICE_LIST_CATEGORY_METHOD;
        Request request = APIClient.getRequest(mContext, url);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                errorExceptionHandle(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {

                    if (response != null && response.isSuccessful()) {
                        String responseData = response.body().string();
                        if (responseData != null) {
                            JSONObject jsonResponse = new JSONObject(responseData);
                            JSONArray array = jsonResponse.getJSONArray(AppConstants.RESPONSE);
                            new RealmController().saveCategoryResponse(array.toString());
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getQuestionsResponse();
                            }
                        });


                    } else {
                      errorHandle(response);
                    }

                } catch (Exception e) {
                    errorExceptionHandle(e.getMessage());
                    e.printStackTrace();
                } finally {
                }

            }
        });

    }

    private void getSurveyQuestionsResponse() {
        OkHttpClient okHttpClient = APIClient.getHttpClient();
        String url = UniverseAPI.WEB_SERVICE_LIST_QUESTION_METHOD;
        Request request = APIClient.getRequest(mContext, url);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                errorExceptionHandle(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {

                    if (response != null && response.isSuccessful()) {
                        String responseData = response.body().string();
                        if (responseData != null) {
                            JSONObject jsonResponse = new JSONObject(responseData);
                            JSONArray array = jsonResponse.getJSONArray(AppConstants.RESPONSE);
                            new RealmController().saveSurveyQuestions(array.toString());
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getRetailerResponse();
                            }
                        });

                    } else {
                      errorHandle(response);
                    }

                } catch (Exception e) {
                    errorExceptionHandle(e.getMessage());
                    e.printStackTrace();
                } finally {
                }

            }
        });

    }

    private void getAnswersResponse() {
        OkHttpClient okHttpClient = APIClient.getHttpClient();
        String url = UniverseAPI.WEB_SERVICE_LIST_ANSWERS_METHOD;
        Request request = APIClient.getRequest(mContext, url);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                errorExceptionHandle(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {

                    if (response != null && response.isSuccessful()) {
                        String responseData = response.body().string();
                        if (responseData != null) {
                            JSONObject jsonResponse = new JSONObject(responseData);
                            JSONArray array = jsonResponse.getJSONArray(AppConstants.RESPONSE);
                            new RealmController().saveAnswers(array.toString());
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissProgress();
                                goToMain();
                            }
                        });

                    } else {
                       errorHandle(response);
                    }
                } catch (Exception e) {
                    errorExceptionHandle(e.getMessage());
                    e.printStackTrace();
                } finally {
                }

            }
        });

    }

    private void getRetailerResponse() {


        OkHttpClient okHttpClient = APIClient.getHttpClient();
        String url = UniverseAPI.WEB_SERVICE_LIST_RETAILER_METHOD;


        Request request = APIClient.getRequest(mContext, url);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                errorExceptionHandle(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {

                    if (response != null && response.isSuccessful()) {
                        String responseData = response.body().string();
                        if (responseData != null) {
                            JSONObject jsonResponse = new JSONObject(responseData);
                            JSONArray array = jsonResponse.getJSONArray(AppConstants.RESPONSE);
                            new RealmController().saveCustomersResponse(array.toString());
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getAnswersResponse();
                            }
                        });


                    } else {
                        errorHandle(response);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    errorExceptionHandle(e.getMessage());
                } finally {
                }

            }
        });

    }


    private void goToMain() {
        Prefs.putBooleanPrefs(AppConstants.Login_Status, true);
        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        finish();
    }
}

