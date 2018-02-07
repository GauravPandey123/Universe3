package com.universe.android.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.universe.android.R;
import com.universe.android.realmbean.RealmController;
import com.universe.android.helper.FontClass;
import com.universe.android.resource.Login.LoginRequest;
import com.universe.android.resource.Login.LoginResponse;
import com.universe.android.resource.Login.LoginService;
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
        showProgress();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);
        LoginService loginService = new LoginService();
        loginService.executeService(loginRequest, new BaseApiCallback<LoginResponse>() {
            @Override
            public void onComplete() {
                dismissProgress();
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
                Prefs.putBooleanPrefs(AppConstants.Login_Status, true);
                new RealmController().saveQuestions(mContext);
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
                Utility.showToast(e.getData());
            }
        });

    }


}
