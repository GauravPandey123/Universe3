package com.universe.android.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.universe.android.R;
import com.universe.android.fragment.SurveySelectionFragment;


public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    EditText editTextInputEmail, editTextInputPassword, editTextMobile;
    AppCompatButton textViewLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialization();
        setUpElements();

        textViewLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    private void initialization() {
        textViewLogin = (AppCompatButton) findViewById(R.id.btn_login);
        editTextInputEmail = (EditText) findViewById(R.id.input_email);
        editTextInputPassword = (EditText) findViewById(R.id.input_phoneno);
        editTextMobile = (EditText) findViewById(R.id.input_password);
    }


    private void setUpElements() {
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }
        showProgress();
        textViewLogin.setEnabled(false);
        String email = editTextInputEmail.getText().toString();
        String password = editTextInputPassword.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        dismissProgress();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        textViewLogin.setEnabled(true);
        Intent intent=new Intent(mContext,SurveySelectionFragment.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        textViewLogin.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = editTextInputEmail.getText().toString();
        String password = editTextInputPassword.getText().toString();
        String phone = editTextMobile.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextInputEmail.setError("enter a valid email address");
            valid = false;
        } else {
            editTextInputEmail.setError(null);
        }

//        if (phone.isEmpty() || phone.length() != 10) {
//            editTextMobile.setError("Enter Valid Mobile Number");
//            valid = false;
//        } else {
//            editTextMobile.setError(null);
//        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            editTextInputPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            editTextInputPassword.setError(null);
        }


        return valid;
    }
}
