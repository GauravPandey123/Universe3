package com.universe.android.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.universe.android.R;
//import com.universe.android.adapter.CrystalCutomerAdapter;
//import com.universe.android.adapter.InProgressTeamSurveyAdapter;
//import com.universe.android.adapter.NewReatilerAdapter;
import com.universe.android.adapter.SurveyDetailAdapter;
import com.universe.android.adapter.TeamSurveyDetailAdapter;
import com.universe.android.helper.FontClass;
import com.universe.android.model.AnswersModal;
import com.universe.android.model.ListBean;
import com.universe.android.realmbean.RealmAnswers;
import com.universe.android.realmbean.RealmCustomer;
import com.universe.android.resource.Login.CrystalReport.CrystalReportRequest;
import com.universe.android.resource.Login.CrystalReport.CrystalReportResponse;
import com.universe.android.resource.Login.CrystalReport.CrystalReportService;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Log;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;
import com.universe.android.web.BaseApiCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import in.editsoft.api.exception.APIException;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit2.Call;

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
    private ArrayList<ListBean> responseBeanArrayList;

    Intent intent;
    private String surveyId;
    private ImageView imageViewPenindg, imageViewSubmitted, imageViewNewReatilers, imageViewCrystalCustomer;
    private TextView tvPending, textViewPending, tvInprogress, textViewApproved, tvCompleted, textViewCompleted, tvRejected, textViewVerifier;
    private LinearLayout ll_inprogress;
    private LinearLayout ll_pending, ll_completed, ll_rejected;
    private TextView textViewMobileNo;
    private String cdId;
    CircleImageView circleImageView;
    private CrystalReportResponse.ResponseBean mCrystelReport;
    private String name;
    private TextView title;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_survey_detail_report);
        initialization();
        setUpElements();

        setUpListeners();
    }

    private void setUpElements() {
        responseBeanArrayList = new ArrayList<>();
        recyclerViewWorkFLowsDetail.setLayoutManager(new LinearLayoutManager(mContext));
        teamSurveyDetailAdapter = new TeamSurveyDetailAdapter(mContext, responseBeanArrayList,2);
        recyclerViewWorkFLowsDetail.setAdapter(teamSurveyDetailAdapter);

        teamSubmitted();
    }

    private void setUpListeners() {
        imageviewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ll_pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.animateView(view);

                if (mCrystelReport.getInprogress() != null) {
                    teamSurveyDetailAdapter.setResponseBeanArrayList(mCrystelReport.getInprogress().getList(),2);
                }
                // setWebservice();
            }
        });
        ll_inprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.animateView(view);

                if (mCrystelReport.getSubmitted() != null) {
                    teamSurveyDetailAdapter.setResponseBeanArrayList(mCrystelReport.getSubmitted().getList(),1);
                }

            }
        });

        ll_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.animateView(view);
                // setUpNewReatiler();
                if (mCrystelReport.getNewRetailer() != null) {
                    teamSurveyDetailAdapter.setResponseBeanArrayList(mCrystelReport.getNewRetailer().getList(),0);
                }

            }
        });

        ll_rejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.animateView(view);
                //  crystalElement();
                if (mCrystelReport.getCrystalCustomer() != null) {
                    teamSurveyDetailAdapter.setResponseBeanArrayList(mCrystelReport.getCrystalCustomer().getList(),0);
                }

            }
        });
        ImageView actionButton = findViewById(R.id.actionButton);

//        actionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Utility.animateView(v);
////                if (isStoragePermissionGranted()) {
////                    String title = ((TextView) findViewById(R.id.textViewSurveyDetailActivity)).getText().toString();
////                    createExcelFileTeamSurveyReport(headerList, surveyDetailsBeanArrayList, title.replace(" ", "_"), title.replace(" ", "_") + ".xls", title, getResources().getString(R.string.sharetitle) + " of " + title + "\n\n" + getResources().getString(R.string.thankyou));
////                } else {
////                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
////                    }
////                }
//            }
//        });

    }

    private void initialization() {
        intent = getIntent();
        relativeLayout = findViewById(R.id.include);
        imageviewback = findViewById(R.id.imageviewback);
        textViewCrystalDoctor = findViewById(R.id.textViewCraystalDoctor);
        textViewAmtala = findViewById(R.id.textViewAmtala);
        textViewPosition = findViewById(R.id.textViewPosition);
        textViewMobileNo = findViewById(R.id.textViewMobileNo);
        textViewAchievementNumbers = findViewById(R.id.textViewAchievementNumbers);
        textViewAchievement = findViewById(R.id.textViewAchievement);
        imageViewPenindg = findViewById(R.id.imagePending);
        imageViewSubmitted = findViewById(R.id.imageViewSubmitted);
        imageViewNewReatilers = findViewById(R.id.imageViewNewReatilers);
        imageViewCrystalCustomer = findViewById(R.id.imageViewCrystalCustomer);
        tvPending = findViewById(R.id.tvPending);
        recyclerViewWorkFLowsDetail = findViewById(R.id.recylerViewSurveyDetail);
        textViewPending = findViewById(R.id.textViewPending);
        tvInprogress = findViewById(R.id.tvInprogress);
        textViewApproved = findViewById(R.id.textViewApproved);
        tvCompleted = findViewById(R.id.tvCompleted);
        textViewCompleted = findViewById(R.id.textViewCompleted);
        tvRejected = findViewById(R.id.tvRejected);
        textViewVerifier = findViewById(R.id.textViewVerifier);
        ll_inprogress = findViewById(R.id.ll_inprogress);
        ll_pending = findViewById(R.id.ll_pending);
        ll_rejected = findViewById(R.id.ll_rejected);
        ll_completed = findViewById(R.id.ll_completed);
        title=findViewById(R.id.textViewTitle);
        circleImageView = findViewById(R.id.circleCustomer);
        relativeLayout.setVisibility(View.GONE);
        imageViewPenindg.setImageResource(R.drawable.pending);
        imageViewSubmitted.setImageResource(R.drawable.ic_submitted);
        imageViewNewReatilers.setImageResource(R.drawable.ic_customer);
        imageViewCrystalCustomer.setImageResource(R.drawable.ic_crystal_cutomer);

        Glide.with(mContext).load(Prefs.getStringPrefs(AppConstants.picture)).into(circleImageView);
        textViewPending.setText("In Progress");
        textViewApproved.setText("Submitted");

        name=intent.getStringExtra(AppConstants.TeamSurveyname);
        textViewMobileNo.setText(Prefs.getStringPrefs(AppConstants.phone));

        cdId = intent.getExtras().getString(AppConstants.CDID);
        textViewCompleted.setText("New Retailers");
        textViewVerifier.setText("  Crystal \n Customers");
        title.setText(name+"\n"+"Team Survey Report");
        textViewCrystalDoctor.setText(intent.getStringExtra(AppConstants.CrystaDoctorName));
        textViewCrystalDoctor.setTypeface(FontClass.openSansBold(mContext));
        textViewAchievement.setTypeface(FontClass.openSansRegular(mContext));
        textViewPosition.setTypeface(FontClass.openSansRegular(mContext));
        textViewAchievementNumbers.setTypeface(FontClass.openSansRegular(mContext));
        textViewAchievement.setTypeface(FontClass.openSansRegular(mContext));

        textViewPosition.setText(Prefs.getStringPrefs(AppConstants.EMPLOYEE_NAME));
        textViewAchievementNumbers.setText(String.format("%d%%", Prefs.getIntegerPrefs(AppConstants.Percent)));

    }


    private void teamSubmitted() {
        showProgress();
        CrystalReportRequest crystalReportRequest = new CrystalReportRequest();
        crystalReportRequest.setType("rsm");
        crystalReportRequest.setCdId("5a8e81022741361f5827ae85");
        crystalReportRequest.setSurveyId("5a86d4a9b69a800980dadd82");
        CrystalReportService crystalReportService = new CrystalReportService();
        crystalReportService.executeService(crystalReportRequest, new BaseApiCallback<CrystalReportResponse>() {
            @Override
            public void onComplete() {
                dismissProgress();
            }

            @Override
            public void onSuccess(@NonNull CrystalReportResponse response) {
                super.onSuccess(response);
                mCrystelReport = response.getResponse();
                teamSurveyDetailAdapter.setResponseBeanArrayList(mCrystelReport.getInprogress().getList(),2);

                tvPending.setText(String.valueOf(mCrystelReport.getInprogress().getCount()));
                tvInprogress.setText(String.valueOf(mCrystelReport.getSubmitted().getCount()));
                tvCompleted.setText(String.valueOf(mCrystelReport.getNewRetailer().getCount()));
                tvInprogress.setText(String.valueOf(mCrystelReport.getCrystalCustomer().getCount()));

            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
            }
        });

    }


}
