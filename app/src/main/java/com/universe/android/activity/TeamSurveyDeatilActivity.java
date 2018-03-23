package com.universe.android.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.universe.android.R;

import com.universe.android.adapter.TeamSurveyAdapter;
import com.universe.android.fragment.TeamSurveyDialogFragment;
import com.universe.android.helper.RecyclerTouchListener;
import com.universe.android.resource.Login.SurveyDetails.SurverDetailResponse;
import com.universe.android.resource.Login.SurveyDetails.SurveyDeatailRequest;
import com.universe.android.resource.Login.SurveyDetails.SurveyDetailService;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;
import com.universe.android.web.BaseApiCallback;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import in.editsoft.api.exception.APIException;

/**
 * Created by gaurav.pandey on 12-02-2018.
 */

public class TeamSurveyDeatilActivity extends BaseActivity implements TeamSurveyDialogFragment.TeamSurveyData {
    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 1000;
    private CardView carView;
    private RecyclerView reyclerViewCategory;
    private ImageView imageViewBack;
    private TextView textViewtargetCount, textViewCompletedCount, textViewAchievementPercentage, textViewInProgressCount, textViewNewRetailersCount, textViewCrystalMembersCount;
    int percent;
    private ImageView imageViewCloseStatus;
    private ImageView imageviewfilter;

    private ArrayList<SurverDetailResponse.ResponseBean.CrystaDoctorBean> surveyDetailsBeanArrayList;
    private TeamSurveyAdapter teamSurveyAdapter;

    FragmentManager fm = getSupportFragmentManager();
    String teamId;
    String fromDateString, toDateString;
    Date fromDateTime = null;
    Date toDates = null;
    private ArrayList<String> headerList = new ArrayList<>();
    private ArrayList<String> stringArrayListChecked;
    String surveyName;
    TextView title,textViewToday;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_survey_activity);
        initialization();
        setUpElements();
        prepareHeaderList();
        ImageView actionButton = findViewById(R.id.actionButton);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.animateView(v);
                if (isStoragePermissionGranted()) {
                    String title = ((TextView) findViewById(R.id.textViewSurveyDetailActivity)).getText().toString();
                    createExcelFileTeamSurveyReport(headerList, surveyDetailsBeanArrayList, title.replace(" ", "_"), title.replace(" ", "_") + ".xls", title, getResources().getString(R.string.sharetitle) + " of " + title + "\n\n" + getResources().getString(R.string.thankyou));
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
                    }
                }
            }
        });
    }

    private void prepareHeaderList() {
        headerList = new ArrayList<>();
        // headerList.add("#");
        List<String> tablesHeader = new ArrayList<>();
        tablesHeader = Arrays.asList(getResources().getStringArray(R.array.team_survey_report));
        for (int i = 0; i < tablesHeader.size(); i++) {
            headerList.add(tablesHeader.get(i));
        }

    }

    private void setUpElements() {
        carView.setVisibility(View.GONE);
        surveyDetailsBeanArrayList = new ArrayList<>();
        reyclerViewCategory.setLayoutManager(new LinearLayoutManager(mContext));
        teamSurveyAdapter = new TeamSurveyAdapter(mContext, surveyDetailsBeanArrayList);
        reyclerViewCategory.setAdapter(teamSurveyAdapter);
        setWebService();

        reyclerViewCategory.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), reyclerViewCategory, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(mContext, TeamSurveyDetailReport.class);
                intent.putExtra(AppConstants.CrystaDoctorName, surveyDetailsBeanArrayList.get(position).getDetail().getName());
                intent.putExtra(AppConstants.CDID, surveyDetailsBeanArrayList.get(position).getDetail().get_id());
                intent.putExtra(AppConstants.TeamSurveyname,surveyName);
                mContext.startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }

    private void initialization() {
        Intent intent = getIntent();
        carView = findViewById(R.id.carView);
        surveyName = intent.getStringExtra(AppConstants.TeamSurveyname);
        reyclerViewCategory = findViewById(R.id.reyclerViewCategory);
        textViewToday=findViewById(R.id.textViewToday);
        imageViewBack = findViewById(R.id.imageviewback);
        imageviewfilter = findViewById(R.id.imageviewfilter);
        textViewtargetCount = findViewById(R.id.textViewtargetCount);
        textViewCompletedCount = findViewById(R.id.textViewCompletedCount);
        textViewAchievementPercentage = findViewById(R.id.textViewAchievementPercentage);
        textViewInProgressCount = findViewById(R.id.textViewInProgressCount);
        textViewNewRetailersCount = findViewById(R.id.textViewNewRetailersCount);
        textViewCrystalMembersCount = findViewById(R.id.textViewCrystalMembersCount);
        title=findViewById(R.id.textViewSurveyDetailActivity);
        title.setText(surveyName+"\n"+"Team Survey Report");
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageviewfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamSurveyDialogFragment dFragment = new TeamSurveyDialogFragment();
                dFragment.show(fm, "Dialog Fragment");
            }
        });


    }


    private void setWebService() {
        if (!Utility.isConnected()) {
            Utility.showToast(R.string.msg_disconnected);
        } else {
           // teamSurveyDetail();
        }
    }

    public void teamSurveyDetail() {
        showProgress();
        SurveyDeatailRequest surveyDeatailRequest = new SurveyDeatailRequest();
        surveyDeatailRequest.setSurveyId(Prefs.getStringPrefs(AppConstants.TeamSurveyId));
        surveyDeatailRequest.setEmployee_code(Prefs.getStringPrefs(AppConstants.employee_code));
        surveyDeatailRequest.setType("rsm");
        SurveyDetailService surveyDetailService = new SurveyDetailService();
        surveyDetailService.executeService(surveyDeatailRequest, new BaseApiCallback<SurverDetailResponse>() {
            @Override
            public void onComplete() {
                dismissProgress();
            }

            @Override
            public void onSuccess(@NonNull SurverDetailResponse response) {
                super.onSuccess(response);
                SurverDetailResponse.ResponseBean responseBeans = response.getResponse();

                String totalString = String.valueOf(responseBeans.getTarget());
                String completedString = String.valueOf(responseBeans.getSubmitted());
                int n = Integer.parseInt(totalString);
                int v = Integer.parseInt(completedString);
                try {
                    percent = v * 100 / n;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                textViewtargetCount.setText(totalString);
                textViewCompletedCount.setText(completedString);
                textViewInProgressCount.setText(String.valueOf(responseBeans.getInprogress()));
                textViewNewRetailersCount.setText(String.valueOf(responseBeans.getNewRetailer()));
                textViewCrystalMembersCount.setText(String.valueOf(responseBeans.getCrystalCustomer()));
                textViewAchievementPercentage.setText(String.valueOf(percent).concat("%"));
                List<SurverDetailResponse.ResponseBean.CrystaDoctorBean> crystaDoctorBeans = responseBeans.getCrystaDoctor();
                String value = new Gson().toJson(crystaDoctorBeans);
                SurverDetailResponse.ResponseBean.CrystaDoctorBean[] surveyDetailsBeans = new Gson().fromJson(value, SurverDetailResponse.ResponseBean.CrystaDoctorBean[].class);
                Collections.addAll(surveyDetailsBeanArrayList, surveyDetailsBeans);
                teamSurveyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
            }
        });

    }

    private void setFilter(String type, String id, final String fromate, final String toDate) {
        SurveyDeatailRequest teamSurveyFilterRequest = new SurveyDeatailRequest();
        teamSurveyFilterRequest.setSurveyId((Prefs.getStringPrefs(AppConstants.TeamSurveyId)));
        teamSurveyFilterRequest.setEmployee_code(Prefs.getStringPrefs(AppConstants.employee_code));
        teamSurveyFilterRequest.setType("rsm");
        teamSurveyFilterRequest.setFilter("filter");
        teamSurveyFilterRequest.setEmployeeId(id);
        teamSurveyFilterRequest.setStartDate(fromate);
        teamSurveyFilterRequest.setEndDate(toDate);
        SurveyDetailService surveyDetailService = new SurveyDetailService();
        surveyDetailService.executeService(teamSurveyFilterRequest, new BaseApiCallback<SurverDetailResponse>() {
            @Override
            public void onComplete() {
                dismissProgress();
            }

            @Override
            public void onSuccess(@NonNull SurverDetailResponse response) {
                super.onSuccess(response);
                SurverDetailResponse.ResponseBean responseBeans = response.getResponse();
                surveyDetailsBeanArrayList.clear();
                String totalString = String.valueOf(responseBeans.getTarget());
                String completedString = String.valueOf(responseBeans.getSubmitted());
                int n = Integer.parseInt(totalString);
                int v = Integer.parseInt(completedString);
                try {
                    percent = v * 100 / n;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                textViewtargetCount.setText(totalString);
                textViewCompletedCount.setText(completedString);
                textViewInProgressCount.setText(String.valueOf(responseBeans.getInprogress()));
                textViewNewRetailersCount.setText(String.valueOf(responseBeans.getNewRetailer()));
                textViewCrystalMembersCount.setText(String.valueOf(responseBeans.getCrystalCustomer()));
                textViewAchievementPercentage.setText(String.valueOf(percent).concat("%"));
                textViewToday.setText("For The Perioed"+fromate+toDate);
                List<SurverDetailResponse.ResponseBean.CrystaDoctorBean> crystaDoctorBeans = responseBeans.getCrystaDoctor();
                String value = new Gson().toJson(crystaDoctorBeans);
                SurverDetailResponse.ResponseBean.CrystaDoctorBean[] surveyDetailsBeans = new Gson().fromJson(value, SurverDetailResponse.ResponseBean.CrystaDoctorBean[].class);
                if (surveyDetailsBeanArrayList.size() < 1) {
                    reyclerViewCategory.setVisibility(View.GONE);
                    return;
                }
                reyclerViewCategory.setVisibility(View.VISIBLE);
                Collections.addAll(surveyDetailsBeanArrayList, surveyDetailsBeans);

                teamSurveyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
                Utility.showToast(e.getData());
            }
        });

    }


    @Override
    public void submitTeamSurveyData(String id, String fromDate, String toDate) {
        teamId = id;
        fromDateString = fromDate;
        toDateString = toDate;
        Date date = null;
        Date toDateTime = null;
        try {
            date = AppConstants.format2.parse(fromDate);
            fromDateTime = AppConstants.format3.parse(AppConstants.format3.format(date));
            fromDateTime = AppConstants.format2.parse(toDate);
            toDateTime = AppConstants.format3.parse(AppConstants.format3.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(toDateTime);
        cal.add(Calendar.DATE, 1);
        toDates = cal.getTime();
        setFilter("rsm", teamId, fromDateString, toDateString);
    }
}
