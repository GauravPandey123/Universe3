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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.universe.android.R;
import com.universe.android.adapter.CrystalCutomerAdapter;
import com.universe.android.adapter.InProgressTeamSurveyAdapter;
import com.universe.android.adapter.NewReatilerAdapter;
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
    private InProgressTeamSurveyAdapter inProgressTeamSurveyAdapter;
    private NewReatilerAdapter newReatilerAdapter;
    private CrystalCutomerAdapter crystalCutomerAdapter;
    private ArrayList<CrystalReportResponse.ResponseBean.SubmittedBean.ListBean> responseBeanArrayList;
    private ArrayList<CrystalReportResponse.ResponseBean.InprogressBean.ListBeanX> inprogressArraylist;
    private ArrayList<CrystalReportResponse.ResponseBean.NewRetailerBean.ListBeanXX> listBeanXXArrayList;
    private ArrayList<CrystalReportResponse.ResponseBean.CrystalCustomerBean.ListBeanXXX> listBeanXXXArrayList;

    Intent intent;
    private String surveyId;
    private ImageView imageViewPenindg, imageViewSubmitted, imageViewNewReatilers, imageViewCrystalCustomer;
    private TextView tvPending, textViewPending, tvInprogress, textViewApproved, tvCompleted, textViewCompleted, tvRejected, textViewVerifier;
    private LinearLayout ll_inprogress;
    private LinearLayout ll_pending, ll_completed, ll_rejected;
    private TextView textViewMobileNo;
    private String cdId;
    CircleImageView circleImageView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_survey_detail_report);
        initialization();
        setUpELements();
        setUpTeamWebser();
        setUpNewReatiler();
        crystalElement();
        setUpListeners();
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
                setWebservice();
            }
        });
        ll_inprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.animateView(view);

                setUpTeamWebser();
            }
        });

        ll_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.animateView(view);
                setUpNewReatiler();

            }
        });

        ll_rejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.animateView(view);
                crystalElement();

            }
        });

    }


    public void setWebservice() {
        if (!Utility.isConnected()) {
            Utility.showToast(R.string.msg_disconnected);
        } else {
            InProgressWebservice();
        }
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
        circleImageView = findViewById(R.id.circleCustomer);
        relativeLayout.setVisibility(View.GONE);
        imageViewPenindg.setImageResource(R.drawable.pending);
//        imageViewSubmitted.setImageResource(R.drawable.ic_submitted);
//        imageViewNewReatilers.setImageResource(R.drawable.ic_yellow_user);
//        imageViewCrystalCustomer.setImageResource(R.drawable.ic_customer);

        Glide.with(mContext).load(Prefs.getStringPrefs(AppConstants.picture)).into(circleImageView);
        textViewPending.setText("In Progress");
        textViewApproved.setText("Submitted");


        textViewMobileNo.setText(Prefs.getStringPrefs(AppConstants.phone));

        surveyId = intent.getExtras().getString(AppConstants.CDID);
        textViewAchievementNumbers.setText("0".concat("%"));
        textViewCompleted.setText("New Retailers");
        textViewVerifier.setText("       Crystal      Customers");
        textViewCrystalDoctor.setText(intent.getStringExtra(AppConstants.CrystaDoctorName));
        textViewCrystalDoctor.setTypeface(FontClass.openSansBold(mContext));
        textViewAchievement.setTypeface(FontClass.openSansRegular(mContext));
        textViewPosition.setTypeface(FontClass.openSansRegular(mContext));
        textViewAchievementNumbers.setTypeface(FontClass.openSansRegular(mContext));
        textViewAchievement.setTypeface(FontClass.openSansRegular(mContext));

        textViewPosition.setText(Prefs.getStringPrefs(AppConstants.EMPLOYEE_NAME));
        textViewAchievementNumbers.setText("10 %");
    }


    private void TeamReportService() {
        CrystalReportRequest crystalReportRequest = new CrystalReportRequest();
        crystalReportRequest.setSurveyId(Prefs.getStringPrefs(AppConstants.TeamSurveyId));
        crystalReportRequest.setCdId(surveyId);
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
                List<CrystalReportResponse.ResponseBean.SubmittedBean.ListBean> responseBeans = response.getResponse().getSubmitted().getList();
                responseBeanArrayList.clear();
                tvPending.setText(String.valueOf(response.getResponse().getSubmitted().getCount()));
                String value = new Gson().toJson(responseBeans);
                CrystalReportResponse.ResponseBean.SubmittedBean.ListBean[] responseBeans1 = new Gson().fromJson(value, CrystalReportResponse.ResponseBean.SubmittedBean.ListBean[].class);
                Collections.addAll(responseBeanArrayList, responseBeans1);
                teamSurveyDetailAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
                Log.e(TAG, "Failure");
            }
        });
    }


    private void NewReatilerService() {
        CrystalReportRequest crystalReportRequest = new CrystalReportRequest();
        crystalReportRequest.setSurveyId(Prefs.getStringPrefs(AppConstants.TeamSurveyId));
        crystalReportRequest.setCdId(surveyId);
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
                List<CrystalReportResponse.ResponseBean.NewRetailerBean.ListBeanXX> responseBeans = response.getResponse().getNewRetailer().getList();
                responseBeanArrayList.clear();
                tvPending.setText(String.valueOf(response.getResponse().getSubmitted().getCount()));
                String value = new Gson().toJson(responseBeans);
                CrystalReportResponse.ResponseBean.NewRetailerBean.ListBeanXX[] responseBeans1 = new Gson().fromJson(value, CrystalReportResponse.ResponseBean.NewRetailerBean.ListBeanXX[].class);
                Collections.addAll(listBeanXXArrayList, responseBeans1);
                teamSurveyDetailAdapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
                Log.e(TAG, "Failure");
            }
        });
    }

    private void CrystalsWebService() {
        CrystalReportRequest crystalReportRequest = new CrystalReportRequest();
        crystalReportRequest.setSurveyId(Prefs.getStringPrefs(AppConstants.TeamSurveyId));
        crystalReportRequest.setCdId(surveyId);
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
                List<CrystalReportResponse.ResponseBean.CrystalCustomerBean.ListBeanXXX> responseBeans = response.getResponse().getCrystalCustomer().getList();
                responseBeanArrayList.clear();
                tvRejected.setText(String.valueOf(response.getResponse().getSubmitted().getCount()));
                String value = new Gson().toJson(responseBeans);
                CrystalReportResponse.ResponseBean.CrystalCustomerBean.ListBeanXXX[] responseBeans1 = new Gson().fromJson(value, CrystalReportResponse.ResponseBean.CrystalCustomerBean.ListBeanXXX[].class);
                Collections.addAll(listBeanXXXArrayList, responseBeans1);
                teamSurveyDetailAdapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
                Log.e(TAG, "Failure");
            }
        });
    }


    private void InProgressWebservice() {
        CrystalReportRequest crystalReportRequest = new CrystalReportRequest();
        crystalReportRequest.setSurveyId(Prefs.getStringPrefs(AppConstants.TeamSurveyId));
        crystalReportRequest.setCdId(surveyId);
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
                List<CrystalReportResponse.ResponseBean.InprogressBean.ListBeanX> responseBeans = response.getResponse().getInprogress().getList();
                inprogressArraylist.clear();
                tvCompleted.setText(String.valueOf(response.getResponse().getSubmitted().getCount()));
                String value = new Gson().toJson(responseBeans);
                CrystalReportResponse.ResponseBean.InprogressBean.ListBeanX[] responseBeans1 = new Gson().fromJson(value, CrystalReportResponse.ResponseBean.InprogressBean.ListBeanX[].class);
                Collections.addAll(inprogressArraylist, responseBeans1);
                inProgressTeamSurveyAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
                Log.e(TAG, "Failure");
            }
        });
    }


    private void setUpNewReatiler() {
        listBeanXXArrayList = new ArrayList<>();
        newReatilerAdapter = new NewReatilerAdapter(mContext, listBeanXXArrayList);
        teamSurveyDetailAdapter.notifyDataSetChanged();
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewWorkFLowsDetail.setLayoutManager(linearLayoutManager);
        recyclerViewWorkFLowsDetail.setItemAnimator(new DefaultItemAnimator());
        recyclerViewWorkFLowsDetail.setAdapter(newReatilerAdapter);
        NewReatilerService();

    }

    private void setUpTeamWebser() {
        responseBeanArrayList = new ArrayList<>();
        teamSurveyDetailAdapter = new TeamSurveyDetailAdapter(mContext, responseBeanArrayList);
        teamSurveyDetailAdapter.notifyDataSetChanged();
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewWorkFLowsDetail.setLayoutManager(linearLayoutManager);
        recyclerViewWorkFLowsDetail.setItemAnimator(new DefaultItemAnimator());
        recyclerViewWorkFLowsDetail.setAdapter(teamSurveyDetailAdapter);
        TeamReportService();

    }

    private void setUpELements() {
        inprogressArraylist = new ArrayList<>();
        inProgressTeamSurveyAdapter = new InProgressTeamSurveyAdapter(mContext, inprogressArraylist);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewWorkFLowsDetail.setLayoutManager(linearLayoutManager);
        recyclerViewWorkFLowsDetail.setItemAnimator(new DefaultItemAnimator());
        recyclerViewWorkFLowsDetail.setAdapter(inProgressTeamSurveyAdapter);

        InProgressWebservice();
    }

    private void crystalElement() {
        listBeanXXXArrayList = new ArrayList<>();
        crystalCutomerAdapter = new CrystalCutomerAdapter(mContext, listBeanXXXArrayList);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewWorkFLowsDetail.setLayoutManager(linearLayoutManager);
        recyclerViewWorkFLowsDetail.setItemAnimator(new DefaultItemAnimator());
        recyclerViewWorkFLowsDetail.setAdapter(crystalCutomerAdapter);

        CrystalsWebService();
    }
}
