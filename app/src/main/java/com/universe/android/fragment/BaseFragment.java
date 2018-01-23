package com.universe.android.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.universe.android.activity.BaseActivity;
import com.universe.android.utility.Utility;

/**
 * Created by gaurav.pandey on 23-01-2018.
 */

public class BaseFragment extends Fragment {

    protected BaseActivity mActivity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = (BaseActivity) getActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void showProgress() {
        showProgress("Loading");
    }

    public void showProgress(String message) {
        mActivity.showProgress(message);
    }

    public void showProgress(int msgId) {
        String message = Utility.getStringRes(msgId);
        mActivity.showProgress(message);
    }

    public void dismissProgress() {
        mActivity.dismissProgress();
    }


}
