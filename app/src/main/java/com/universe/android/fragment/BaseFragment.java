package com.universe.android.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.universe.android.R;
import com.universe.android.activity.BaseActivity;
import com.universe.android.component.QuestionItemListDialog;
import com.universe.android.enums.FormEnum;
import com.universe.android.model.Questions;
import com.universe.android.model.SpinnerList;
import com.universe.android.realmbean.RealmCategory;
import com.universe.android.realmbean.RealmClient;
import com.universe.android.realmbean.RealmCustomer;
import com.universe.android.realmbean.RealmQuestion;
import com.universe.android.realmbean.RealmSurveys;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;

import org.json.JSONObject;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.realm.Realm;

/**
 * Created by gaurav.pandey on 23-01-2018.
 */

public class BaseFragment extends Fragment {
    protected Context mContext;
    protected BaseActivity mActivity;
    protected String formId = null;
    protected String formAnsId = null;
    protected LinearLayout llFields;
    protected Map<String, Questions> questionsMap;
    protected boolean isPopupVisible = false;
    protected boolean isSync;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = (BaseActivity) getActivity();
        llFields = (LinearLayout) mActivity.findViewById(R.id.parent);
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
