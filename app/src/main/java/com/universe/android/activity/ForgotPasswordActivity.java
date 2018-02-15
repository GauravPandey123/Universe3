package com.universe.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;

import com.universe.android.R;
import com.universe.android.helper.FontClass;
import com.universe.android.resource.Login.ForgotPassword.ForgotPasswordRequest;
import com.universe.android.resource.Login.ForgotPassword.ForgotPasswordResponse;
import com.universe.android.resource.Login.ForgotPassword.ForgotPasswordService;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Utility;
import com.universe.android.web.BaseApiCallback;

import in.editsoft.api.exception.APIException;

/**
 * Created by gaurav.pandey on 01-02-2018.
 */

public class ForgotPasswordActivity extends BaseActivity {
    private TextInputLayout textInputLayoutForgot;
    private EditText editTextForgot;
    private AppCompatButton appCompatButtonForgot;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        initialization();
        setUpElements();
    }

    private void setUpElements() {
        appCompatButtonForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextForgot.getText().toString();
                if (validateEmail(email)) {
                    if (!Utility.isConnected()) {
                        Utility.showToast(R.string.msg_disconnected);
                    } else {
                        forgotPasswordRequest(email);
                    }
                }}
        });
    }

    private void initialization() {
        textInputLayoutForgot = findViewById(R.id.textInputLayoutForgotPassword);
        editTextForgot = findViewById(R.id.input_email_forgot);
        appCompatButtonForgot = findViewById(R.id.btn_get_otp);
        appCompatButtonForgot.setTypeface(FontClass.openSansRegular(mContext));
        editTextForgot.setTypeface(FontClass.openSansLight(mContext));
        textInputLayoutForgot.setTypeface(FontClass.openSansRegular(mContext));

    }

    private boolean validateEmail(String email) {
        if (!Utility.validateString(email)) {
            Utility.showToast(R.string.msg_enter_email);
            return false;
        } else if (!Utility.validateEmail(email)) {
            Utility.showToast(R.string.msg_email_error);
            return false;
        } else {
            return true;
        }
    }

    public void forgotPasswordRequest(String email) {
        showProgress();
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.setEmail(email);
        ForgotPasswordService forgotPasswordService = new ForgotPasswordService();
        forgotPasswordService.executeService(forgotPasswordRequest, new BaseApiCallback<ForgotPasswordResponse>() {
            @Override
            public void onComplete() {
                dismissProgress();
            }

            @Override
            public void onSuccess(@NonNull ForgotPasswordResponse response) {
                super.onSuccess(response);
                if (response.getStatusCode() == AppConstants.SUCCESS) {
                    startActivity(new Intent(mContext, ResetPasswordActivity.class));
                    finish();
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                } else {
                    Utility.showToast(response.getErrorMsg());

                }
            }
            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
                Utility.showToast(e.getData());
            }
        });

    }
}
