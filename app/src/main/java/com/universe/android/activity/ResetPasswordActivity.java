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
import com.universe.android.resource.Login.ResetPassword.ResetPasswordRequest;
import com.universe.android.resource.Login.ResetPassword.ResetPasswordResponse;
import com.universe.android.resource.Login.ResetPassword.ResetPasswordService;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;
import com.universe.android.web.BaseApiCallback;

import in.editsoft.api.exception.APIException;

/**
 * Created by gaurav.pandey on 01-02-2018.
 */

public class ResetPasswordActivity extends BaseActivity {

    private TextInputLayout textInputLayoutRestPassord, textInputLayoutNewPassword, textInputLayoutConfirmPassword;

    private EditText input_reset_password, input_reset_newpassword, input_reset_confirmPassword;

    private AppCompatButton appCompatButtonSubmit;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);
        initialization();
        setUpElements();
    }

    private void setUpElements() {
        appCompatButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = input_reset_password.getText().toString();
                String pass1 = input_reset_newpassword.getText().toString();
                String pass2 = input_reset_confirmPassword.getText().toString();
                if (validateDetails(pass, pass1, pass2)) {
                    if (!Utility.isConnected()) {
                        Utility.showToast(R.string.msg_disconnected);
                    } else {
                        submitPasswordRequest(pass, pass1);}
                }

            }
        });

    }

    private void initialization() {
        textInputLayoutRestPassord = findViewById(R.id.textInputLayoutRestPassord);
        textInputLayoutNewPassword = findViewById(R.id.textInputLayoutNewPassword);
        textInputLayoutConfirmPassword = findViewById(R.id.textInputLayoutConfirmPassword);
        input_reset_password = findViewById(R.id.input_reset_password);
        input_reset_newpassword = findViewById(R.id.input_reset_newpassword);
        input_reset_confirmPassword = findViewById(R.id.input_reset_confirmPassword);
        appCompatButtonSubmit = findViewById(R.id.btn_submit_reset_password);

        textInputLayoutRestPassord.setTypeface(FontClass.openSansRegular(mContext));
        textInputLayoutNewPassword.setTypeface(FontClass.openSansRegular(mContext));
        textInputLayoutConfirmPassword.setTypeface(FontClass.openSansRegular(mContext));
        input_reset_password.setTypeface(FontClass.openSansLight(mContext));
        input_reset_newpassword.setTypeface(FontClass.openSansLight(mContext));
        input_reset_confirmPassword.setTypeface(FontClass.openSansLight(mContext));
        appCompatButtonSubmit.setTypeface(FontClass.openSansRegular(mContext));

    }

    private void submitPasswordRequest(String pass1, String pass2) {
        showProgress();
        ResetPasswordRequest updatePasswordRequest = new ResetPasswordRequest();
        updatePasswordRequest.setUserId(Prefs.getStringPrefs(AppConstants.UserId));
        updatePasswordRequest.setOldPassword(pass1);
        updatePasswordRequest.setNewPassword(pass2);
        ResetPasswordService updatePasswordService = new ResetPasswordService();
        updatePasswordService.executeService(updatePasswordRequest, new BaseApiCallback<ResetPasswordResponse>() {
            @Override
            public void onComplete() {
                dismissProgress();
            }

            @Override
            public void onSuccess(@NonNull ResetPasswordResponse response) {
                super.onSuccess(response);
                if (response.getStatusCode() == AppConstants.SUCCESS) {
                    Utility.showToast(response.getResponse());
                    startActivity(new Intent(mContext, LoginActivity.class));
                    finish();
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                } else {
                    Utility.showToast(response.getErrorMsg());

                }
            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
            }

        });
    }

    private boolean validateDetails(String pass, String pass1, String pass2) {
        if (!Utility.validateString(pass)) {
            Utility.showToast(R.string.msg_enter_pass1);
            return false;
        } else if (pass.length() < 6) {
            Utility.showToast(R.string.msg_pass_error);
            return false;
        } else if (!Utility.validateString(pass1)) {
            Utility.showToast(R.string.msg_enter_pass2);
            return false;
        } else if (pass1.length() < 6) {
            Utility.showToast(R.string.msg_pass_error);
            return false;
        } else if (!pass1.equals(pass2)) {
            Utility.showToast(R.string.msg_pass_match);
            return false;
        } else {
            return true;
        }
    }
}
