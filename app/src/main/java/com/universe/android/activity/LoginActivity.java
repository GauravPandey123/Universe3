package com.universe.android.activity;


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
import com.universe.android.helper.FontClass;
import com.universe.android.realmbean.RealmController;
import com.universe.android.resource.Login.category.CategoryRequest;
import com.universe.android.resource.Login.category.CategoryResponse;
import com.universe.android.resource.Login.category.CategoryService;
import com.universe.android.resource.Login.client.ClientRequest;
import com.universe.android.resource.Login.client.ClientResponse;
import com.universe.android.resource.Login.client.ClientService;
import com.universe.android.resource.Login.customer.CustomerRequest;
import com.universe.android.resource.Login.customer.CustomerResponse;
import com.universe.android.resource.Login.customer.CustomerService;
import com.universe.android.resource.Login.login.LoginRequest;
import com.universe.android.resource.Login.login.LoginResponse;
import com.universe.android.resource.Login.login.LoginService;
import com.universe.android.resource.Login.questions.QuestionsRequest;
import com.universe.android.resource.Login.questions.QuestionsResponse;
import com.universe.android.resource.Login.questions.QuestionsService;
import com.universe.android.resource.Login.survey.SurveyRequest;
import com.universe.android.resource.Login.survey.SurveyResponse;
import com.universe.android.resource.Login.survey.SurveyService;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;
import com.universe.android.web.BaseApiCallback;

import in.editsoft.api.exception.APIException;


public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private EditText editTextInputEmail, editTextInputPassword;
    private AppCompatButton textViewLogin;
    private TextView textViewForgotPassword;
    private TextInputLayout textInputLayoutEmailLogin, textInputLayoutPassword;

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
    }


    private void setUpElements() {
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, ForgotPasswordActivity.class));
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        String email = editTextInputEmail.getText().toString();
        String pass = editTextInputPassword.getText().toString();
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
        } else if (pass.length() < 5) {
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


    //hit web service here
    public void submitLoginRequest(String email, String password) {
        showProgress(R.string.msg_load_default);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);
        LoginService loginService = new LoginService();
        loginService.executeService(loginRequest, new BaseApiCallback<LoginResponse>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(@NonNull LoginResponse response) {
                super.onSuccess(response);
                LoginResponse.ResponseBean responseBean = response.getResponse();
                Prefs.putStringPrefs(AppConstants.TOKEN_ID, responseBean.getAccessToken());
                Prefs.putStringPrefs(AppConstants.UserId, responseBean.get_id());
                Prefs.putStringPrefs(AppConstants.password, responseBean.getPassword());
                Prefs.putStringPrefs(AppConstants.email, responseBean.getEmail());
                Prefs.putStringPrefs(AppConstants.name, responseBean.getName());
                Prefs.putIntegerPrefs(AppConstants.phone, responseBean.getPhone());
                Prefs.putStringPrefs(AppConstants.designationLevel, responseBean.getDesignationLevel());
                Prefs.putStringPrefs(AppConstants.DESIGNATION, responseBean.getDesignation());
                Prefs.putStringPrefs(AppConstants.picture, responseBean.getPicture());


                getSurveyResponse();

            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
                Utility.showToast(e.getData());
            }
        });

    }

    //hit web service here
    public void getSurveyResponse() {
        showProgress(R.string.loading_survey);
        SurveyRequest surveyRequest = new SurveyRequest();
        SurveyService surveyService = new SurveyService();
        surveyService.executeService(surveyRequest, new BaseApiCallback<SurveyResponse>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(@NonNull SurveyResponse response) {
                super.onSuccess(response);
                if (response != null && response.getStatusCode() == 200) {
                    SurveyResponse.ResponseBean responseBean = response.getResponse();
                    String responseData = new Gson().toJson(responseBean);
                    new RealmController().saveSurveysResponse(responseData);


                } else {
                    Utility.showToast(response.getErrorMsg());
                }

                getClientResponse();

            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
                Utility.showToast(e.getData());
            }
        });

    }

    //hit web service here
    public void getClientResponse() {
        showProgress(R.string.loading_client);
        ClientRequest request = new ClientRequest();
        ClientService service = new ClientService();
        service.executeService(request, new BaseApiCallback<ClientResponse>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(@NonNull ClientResponse response) {
                super.onSuccess(response);
                if (response != null && response.getStatusCode() == 200) {
                    ClientResponse.ResponseBean responseBean = response.getResponse();
                    String responseData = new Gson().toJson(responseBean);
                    new RealmController().saveClientsResponse(responseData);

                } else {
                    Utility.showToast(response.getErrorMsg());
                }

                getCustomerResponse();
            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
                Utility.showToast(e.getData());
            }
        });

    }

    //hit web service here
    public void getCustomerResponse() {
        showProgress(R.string.loading_customer);
        CustomerRequest request = new CustomerRequest();
        CustomerService service = new CustomerService();
        service.executeService(request, new BaseApiCallback<CustomerResponse>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(@NonNull CustomerResponse response) {
                super.onSuccess(response);
                if (response != null && response.getStatusCode() == 200) {
                    CustomerResponse.ResponseBean responseBean = response.getResponse();
                    String responseData = new Gson().toJson(responseBean);
                    new RealmController().saveCustomersResponse(responseData);
                } else {
                    Utility.showToast(response.getErrorMsg());
                }
                getCategoryResponse();

            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
                Utility.showToast(e.getData());
            }
        });

    }

    //hit web service here
    public void getCategoryResponse() {
        showProgress(R.string.loading_category);
        CategoryRequest request = new CategoryRequest();
        CategoryService service = new CategoryService();
        service.executeService(request, new BaseApiCallback<CategoryResponse>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(@NonNull CategoryResponse response) {
                super.onSuccess(response);
                if (response != null && response.getStatusCode() == 200) {
                    CategoryResponse.ResponseBean responseBean = response.getResponse();
                    String responseData = new Gson().toJson(responseBean);
                    new RealmController().saveCategoryResponse(responseData);
                } else {
                    Utility.showToast(response.getErrorMsg());
                }

                getQuestionsResponse();
            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
                Utility.showToast(e.getData());
            }
        });

    }

    //hit web service here
    public void getQuestionsResponse() {
        showProgress(R.string.loading_question);
        QuestionsRequest request = new QuestionsRequest();
        QuestionsService service = new QuestionsService();
        service.executeService(request, new BaseApiCallback<QuestionsResponse>() {
            @Override
            public void onComplete() {
                dismissProgress();
            }

            @Override
            public void onSuccess(@NonNull QuestionsResponse response) {
                super.onSuccess(response);
                if (response != null && response.getStatusCode() == 200) {
                    QuestionsResponse.ResponseBean responseBean = response.getResponse();
                    String responseData = new Gson().toJson(responseBean);
                    new RealmController().saveQuestions(responseData);
                } else {
                    Utility.showToast(response.getErrorMsg());
                }

                goToMain();
            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
                Utility.showToast(e.getData());
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
