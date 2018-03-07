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

public class OtpActivity extends BaseActivity {

    private TextInputLayout textInputLayoutOtp;
    private EditText editTextOtp;
    private AppCompatButton appCompatButtonOtp;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialization();
        setUpElements();

    }

    private void setUpElements() {
        appCompatButtonOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, ResetPasswordActivity.class));
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    private void initialization() {
        textInputLayoutOtp = findViewById(R.id.textInputLayoutOtp);
        editTextOtp = findViewById(R.id.input_otp);
        appCompatButtonOtp = findViewById(R.id.btn_submit_otp);

        textInputLayoutOtp.setTypeface(FontClass.openSansRegular(mContext));
        editTextOtp.setTypeface(FontClass.openSansLight(mContext));
        appCompatButtonOtp.setTypeface(FontClass.openSansRegular(mContext));


    }

}
