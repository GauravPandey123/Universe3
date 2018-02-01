package com.universe.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;

import com.universe.android.R;
import com.universe.android.helper.FontClass;

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
                startActivity(new Intent(mContext, OtpActivity.class));
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
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
}
