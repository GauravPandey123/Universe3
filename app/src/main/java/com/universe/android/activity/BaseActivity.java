package com.universe.android.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;


import com.universe.android.R;

import static com.universe.android.utility.Utility.getStringRes;


/**
 * Created by gaurav.pandey on 02-01-2018.
 */

public class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    private ProgressDialog mProgressDialog;
    public boolean isReplaced = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        mContext = this;
    }

    public int setLayoutId() {
        return R.layout.activity_main;
    }

    public void addFragment(Fragment fragment, int containerId) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerId, fragment)
                .commitAllowingStateLoss();
    }

    public void replaceFragment(Fragment fragment, int containerId) {
        isReplaced = true;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment)
                .commitAllowingStateLoss();
    }

    public void showProgress() {
        showProgress(getStringRes(R.string.msg_load_default));
    }

    public void showProgress(String msg) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            dismissProgress();
        }
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    public void showProgress(int msgId) {
        String message = getStringRes(msgId);
        showProgress(message);
    }

    public void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
