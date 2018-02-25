package com.universe.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.universe.android.R;
import com.universe.android.adapter.SurveyDetailAdapter;
import com.universe.android.adapter.TeamSurveyDetailAdapter;
import com.universe.android.helper.FontClass;
import com.universe.android.model.AnswersModal;
import com.universe.android.realmbean.RealmAnswers;
import com.universe.android.realmbean.RealmCustomer;
import com.universe.android.resource.Login.CrystalReport.CrystalReportRequest;
import com.universe.android.resource.Login.CrystalReport.CrystalReportResponse;
import com.universe.android.resource.Login.CrystalReport.CrystalReportService;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Log;
import com.universe.android.utility.Utility;
import com.universe.android.web.BaseApiCallback;

import java.util.ArrayList;
import java.util.List;

import in.editsoft.api.exception.APIException;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by gaurav.pandey on 12-02-2018.
 */

public class TeamSurveyDetailReport extends BaseActivity {
    private static final String TAG = TeamSurveyDetailReport.class.getSimpleName();
    RelativeLayout relativeLayout;
    ImageView imageviewback;
    private RecyclerView recyclerViewWorkFLowsDetail;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<AnswersModal> stringArrayList;
    private LinearLayoutManager linearLayoutManager;
    private SurveyDetailAdapter surveyDetailAdapter;
    private TextView textViewCrystalDoctor, textViewAmtala, textViewPosition, textViewAchievementNumbers, textViewAchievement;
    private TeamSurveyDetailAdapter teamSurveyDetailAdapter;
    private ArrayList<CrystalReportResponse.ResponseBean> responseBeanArrayList;
    Intent intent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_survey_detail_report);
        initialization();
        setUpELements();
        setUpListeners();
    }

    private void setUpListeners() {
        imageviewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    public void setWebservice() {
        if (!Utility.isConnected()) {
            Utility.showToast(R.string.msg_disconnected);
        } else {
            TeamReportService();
        }
    }

    private void initialization() {
        intent = getIntent();
        relativeLayout = findViewById(R.id.include);
        imageviewback = findViewById(R.id.imageviewback);
        textViewCrystalDoctor = findViewById(R.id.textViewCraystalDoctor);
        textViewAmtala = findViewById(R.id.textViewAmtala);
        textViewPosition = findViewById(R.id.textViewPosition);
        textViewAchievementNumbers = findViewById(R.id.textViewAchievementNumbers);
        textViewAchievement = findViewById(R.id.textViewAchievement);
        relativeLayout.setVisibility(View.GONE);
        textViewAchievementNumbers.setText("20".concat("%"));
        textViewCrystalDoctor.setText(intent.getStringExtra(AppConstants.CrystaDoctorName));
        textViewCrystalDoctor.setTypeface(FontClass.openSansBold(mContext));
        textViewAchievement.setTypeface(FontClass.openSansRegular(mContext));
        textViewPosition.setTypeface(FontClass.openSansRegular(mContext));
        textViewAchievementNumbers.setTypeface(FontClass.openSansRegular(mContext));
        textViewAchievement.setTypeface(FontClass.openSansRegular(mContext));
    }


    private void TeamReportService() {
        CrystalReportRequest crystalReportRequest = new CrystalReportRequest();
        crystalReportRequest.setCdId("5a8e81022741361f5827ae85");
        CrystalReportService crystalReportService = new CrystalReportService();
        crystalReportService.executeService(crystalReportRequest, new BaseApiCallback<CrystalReportResponse>() {
            @Override
            public void onComplete() {
                Log.e(TAG, "complete");

            }

            @Override
            public void onSuccess(@NonNull CrystalReportResponse response) {
                super.onSuccess(response);
                Log.e(TAG, "Success");
                List<CrystalReportResponse.ResponseBean> responseBeans = response.getResponse();
                String value = new Gson().toJson(responseBeans);
                CrystalReportResponse.ResponseBean[] responseBeans1 = new Gson().fromJson(value, CrystalReportResponse.ResponseBean[].class);
                for (CrystalReportResponse.ResponseBean responseBean : responseBeans1) {
                    responseBeanArrayList.add(responseBean);
                }
                teamSurveyDetailAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
                Log.e(TAG, "Failure");
            }
        });
    }


    private void setUpELements() {
        responseBeanArrayList = new ArrayList<>();
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerViewWorkFLowsDetail = findViewById(R.id.recylerViewSurveyDetail);
//        surveyDetailAdapter = new SurveyDetailAdapter(mContext, stringArrayList);
        teamSurveyDetailAdapter = new TeamSurveyDetailAdapter(mContext, responseBeanArrayList);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewWorkFLowsDetail.setLayoutManager(linearLayoutManager);
        recyclerViewWorkFLowsDetail.setItemAnimator(new DefaultItemAnimator());
        recyclerViewWorkFLowsDetail.setAdapter(teamSurveyDetailAdapter);
        setWebservice();
    }
}
