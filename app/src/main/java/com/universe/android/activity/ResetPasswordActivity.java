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
                startActivity(new Intent(mContext, LoginActivity.class));
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

    }

    private void initialization() {
        textInputLayoutRestPassord = findViewById(R.id.textInputLayoutRestPassord);
        textInputLayoutNewPassword = findViewById(R.id.textInputLayoutNewPassword);
        textInputLayoutConfirmPassword=findViewById(R.id.textInputLayoutConfirmPassword);
        input_reset_password =findViewById(R.id.input_reset_password);
        input_reset_newpassword=findViewById(R.id.input_reset_newpassword);
        input_reset_confirmPassword=findViewById(R.id.input_reset_confirmPassword);
        appCompatButtonSubmit=findViewById(R.id.btn_submit_reset_password);

        textInputLayoutRestPassord.setTypeface(FontClass.openSansRegular(mContext));
        textInputLayoutNewPassword.setTypeface(FontClass.openSansRegular(mContext));
        textInputLayoutConfirmPassword.setTypeface(FontClass.openSansRegular(mContext));
        input_reset_password.setTypeface(FontClass.openSansLight(mContext));
        input_reset_newpassword.setTypeface(FontClass.openSansLight(mContext));
        input_reset_confirmPassword.setTypeface(FontClass.openSansLight(mContext));
        appCompatButtonSubmit.setTypeface(FontClass.openSansRegular(mContext));



    }
}
