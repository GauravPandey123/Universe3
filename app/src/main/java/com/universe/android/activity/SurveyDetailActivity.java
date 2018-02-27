package com.universe.android.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.universe.android.R;
import com.universe.android.adapter.StatusAdapter;
import com.universe.android.adapter.SurveyDetailAdapter;
import com.universe.android.adapter.WorkFLowDetailAdapter;
import com.universe.android.fragment.SurveyDetailDialogFragment;
import com.universe.android.helper.FontClass;

import com.universe.android.model.AnswersModal;
import com.universe.android.model.CustomerModal;
import com.universe.android.model.StatusModel;
import com.universe.android.realmbean.RealmAnswers;
import com.universe.android.realmbean.RealmCustomer;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by gaurav.pandey on 24-01-2018.
 */

public class SurveyDetailActivity extends BaseActivity {
    //decalre the Views here
    private RecyclerView recyclerViewSurveyDetail;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshLayout swipeRefreshLayoutStatus;
    private ImageView imageViewBack, imageViewFilter;
    private FloatingActionMenu floatingActionMenu;
    private FloatingActionButton floatingCrystal, floatingRetailers;
    private TextView textViewToday, textViewtarget, textViewAchievement, textViewInProgress;
    private TextView textViewNewRetailers, textViewCrystalmembers, textViewCompletedQuestionaire;
    private LinearLayoutManager linearLayoutManager;
    private SurveyDetailAdapter surveyDetailAdapter;
    private TextView textViewtargetCount, textViewCompletedCount, textViewAchievementPercentage, textViewInProgressCount;
    private TextView textViewNewRetailersCount, textViewCrystalMembersCount;
    private RelativeLayout relativelayoutTarget, relativeLayoutSubmitted, realtiveLayoutAchivement, relativelayoutInprogress;
    private RelativeLayout realativeNewRetailers, relativeLayoutCrystalCustomers;
    private ArrayList<AnswersModal> stringArrayList;

    FragmentManager fm = getSupportFragmentManager();
    private String fromDate, toDate, statusData;

    Intent intent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surveyreportfragment);
        initialization();
        setUpElements();
        setUpListeners();
        prepareList(getString(R.string.pending));
        setCounts();
    }

    private void setCounts() {
        Realm realm = Realm.getDefaultInstance();
        try {
            long realmPending = realm.where(RealmAnswers.class).equalTo(AppConstants.CD_STATUS, "1").equalTo(AppConstants.RM_STATUS, "0").equalTo(AppConstants.ZM_STATUS, "4").count();
            long realmInprogress = realm.where(RealmAnswers.class).equalTo(AppConstants.CD_STATUS, "1").equalTo(AppConstants.RM_STATUS, "2").equalTo(AppConstants.ZM_STATUS, "0").count();
            long realmCompleted = realm.where(RealmAnswers.class).equalTo(AppConstants.CD_STATUS, "2").equalTo(AppConstants.RM_STATUS, "2").equalTo(AppConstants.ZM_STATUS, "2").count();
            long realmRejected = realm.where(RealmAnswers.class).equalTo(AppConstants.CD_STATUS, "3").equalTo(AppConstants.RM_STATUS, "3").equalTo(AppConstants.ZM_STATUS, "3").count();
//            textViewInProgressCount.setText(realmPending + "");
            textViewInProgressCount.setText(realmInprogress + "");
            textViewCompletedCount.setText(realmCompleted + "");
//            tvRejected.setText(realmRejected + "");

        } catch (Exception e) {
            realm.close();
            e.printStackTrace();
        } finally {
            realm.close();
        }
    }


    private void setUpListeners() {
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageViewFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SurveyDetailDialogFragment dFragment = new SurveyDetailDialogFragment();
                // Show DialogFragment
                dFragment.show(fm, "Dialog Fragment");
//                showDialog();
            }
        });
        floatingActionMenu.setClosedOnTouchOutside(true);
        floatingCrystal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, SearchCustomersActivity.class));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                floatingActionMenu.close(true);

            }
        });

        floatingRetailers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, SearchCustomersActivity.class));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                floatingActionMenu.close(true);

            }
        });
//        relativelayoutTarget.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//        relativeLayoutSubmitted.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                prepareList(getString(R.string.completed));
//            }
//        });
//
//        realtiveLayoutAchivement.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//        relativelayoutInprogress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                prepareList(getString(R.string.inprogress));
//            }
//        });
//        realativeNewRetailers.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                prepareList(getString(R.string.completed));
//            }
//        });
//        relativeLayoutCrystalCustomers.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    private void setUpElements() {
        surveyDetailAdapter = new SurveyDetailAdapter(mContext, stringArrayList);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewSurveyDetail.setLayoutManager(linearLayoutManager);
        recyclerViewSurveyDetail.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSurveyDetail.setAdapter(surveyDetailAdapter);
    }

    private void initialization() {
        intent = getIntent();
        stringArrayList = new ArrayList<>();
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerViewSurveyDetail = findViewById(R.id.recylerViewSurveyDetail);
        imageViewBack = findViewById(R.id.imageviewback);
        imageViewFilter = findViewById(R.id.imageviewfilter);
        textViewToday = findViewById(R.id.textViewToday);
        textViewtarget = findViewById(R.id.textViewtarget);
        textViewAchievement = findViewById(R.id.textViewAchievement);
        textViewInProgress = findViewById(R.id.textViewInProgress);
        textViewNewRetailers = findViewById(R.id.textViewNewRetailers);
        textViewCrystalmembers = findViewById(R.id.textViewCrystalmembers);
        textViewCompletedQuestionaire = findViewById(R.id.textViewCompletedQuestionaire);
        floatingActionMenu = findViewById(R.id.floating_action_menu_customers);
        floatingCrystal = findViewById(R.id.floatingCrystal);
        floatingRetailers = findViewById(R.id.floatingRetailers);
        textViewtargetCount = findViewById(R.id.textViewtargetCount);
        textViewAchievementPercentage = findViewById(R.id.textViewAchievementPercentage);
        textViewCompletedCount = findViewById(R.id.textViewCompletedCount);
        textViewInProgressCount = findViewById(R.id.textViewInProgressCount);
        textViewNewRetailersCount = findViewById(R.id.textViewNewRetailersCount);
        textViewCrystalMembersCount = findViewById(R.id.textViewCrystalMembersCount);
        relativelayoutTarget = findViewById(R.id.relativelayoutTarget);
        relativeLayoutSubmitted = findViewById(R.id.relativeLayoutSubmitted);
        realtiveLayoutAchivement = findViewById(R.id.realtiveLayoutAchivement);
        relativelayoutInprogress = findViewById(R.id.relativelayoutInprogress);
        realativeNewRetailers = findViewById(R.id.realativeNewRetailers);
        relativeLayoutCrystalCustomers = findViewById(R.id.relativeLayoutCrystalCustomers);

        textViewToday.setTypeface(FontClass.openSemiBold(mContext));
        textViewtarget.setTypeface(FontClass.openSansRegular(mContext));
        textViewAchievement.setTypeface(FontClass.openSansRegular(mContext));
        textViewInProgress.setTypeface(FontClass.openSansRegular(mContext));
        textViewNewRetailers.setTypeface(FontClass.openSansRegular(mContext));
        textViewCrystalmembers.setTypeface(FontClass.openSansRegular(mContext));
        textViewtargetCount.setTypeface(FontClass.openSansRegular(mContext));
        textViewAchievementPercentage.setTypeface(FontClass.openSansRegular(mContext));
        textViewCompletedCount.setTypeface(FontClass.openSansRegular(mContext));
        textViewInProgressCount.setTypeface(FontClass.openSansRegular(mContext));
        textViewNewRetailersCount.setTypeface(FontClass.openSansRegular(mContext));
        textViewCrystalMembersCount.setTypeface(FontClass.openSansRegular(mContext));
        textViewCompletedQuestionaire.setTypeface(FontClass.openSansRegular(mContext));
//\        textViewFilter.setTypeface(FontClass.openSemiBold(mContext));

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (fromDate != null && toDate != null && statusData != null) {
            fromDate = intent.getStringExtra(AppConstants.FROMDATE);
            toDate = intent.getStringExtra(AppConstants.TODATE);
            statusData = intent.getStringExtra(AppConstants.STATUS);
        }
    }

    private void prepareList(String type) {
        if (stringArrayList == null) stringArrayList = new ArrayList<>();
        stringArrayList.clear();
        Realm realm = Realm.getDefaultInstance();

        try {
            RealmResults<RealmAnswers> realmAnswers = null;
            String designation = Prefs.getStringPrefs(AppConstants.TYPE);
            if (designation.equalsIgnoreCase("cd")) {

            }
            if (designation.equalsIgnoreCase("rm")) {
                realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.CD_STATUS, "1").equalTo(AppConstants.RM_STATUS, "0").equalTo(AppConstants.ZM_STATUS, "4").findAll();

                if (type.equalsIgnoreCase(getString(R.string.inprogress))) {
                    realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.CD_STATUS, "1").equalTo(AppConstants.RM_STATUS, "2").equalTo(AppConstants.ZM_STATUS, "0").findAll();

                } else if (type.equalsIgnoreCase(getString(R.string.completed))) {
                    realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.CD_STATUS, "2").equalTo(AppConstants.RM_STATUS, "2").equalTo(AppConstants.ZM_STATUS, "2").findAll();
                } else if (type.equalsIgnoreCase(getString(R.string.rejected))) {
                    realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.CD_STATUS, "3").equalTo(AppConstants.RM_STATUS, "3").equalTo(AppConstants.ZM_STATUS, "4").findAll();
                }
            }
            if (designation.equalsIgnoreCase("zm")) {
                realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.CD_STATUS, "1").equalTo(AppConstants.RM_STATUS, "2").equalTo(AppConstants.ZM_STATUS, "0").findAll();

                if (type.equalsIgnoreCase(getString(R.string.inprogress))) {
                    realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.CD_STATUS, "4").equalTo(AppConstants.RM_STATUS, "4").equalTo(AppConstants.ZM_STATUS, "4").findAll();

                } else if (type.equalsIgnoreCase(getString(R.string.completed))) {
                    realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.CD_STATUS, "2").equalTo(AppConstants.RM_STATUS, "2").equalTo(AppConstants.ZM_STATUS, "2").findAll();
                } else if (type.equalsIgnoreCase(getString(R.string.rejected))) {
                    realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.CD_STATUS, "3").equalTo(AppConstants.RM_STATUS, "3").equalTo(AppConstants.ZM_STATUS, "4").findAll();
                }
            }
            if (realmAnswers != null && realmAnswers.size() > 0) {
                for (int i = 0; i < realmAnswers.size(); i++) {
                    AnswersModal modal = new AnswersModal();
                    modal.set_id(realmAnswers.get(i).get_id());
                    RealmCustomer realmCustomer = realm.where(RealmCustomer.class).equalTo(AppConstants.ID, realmAnswers.get(i).getCustomerId()).findFirst();
                    modal.setTitle(realmCustomer.getName());
                    modal.setState(realmCustomer.getState());
                    modal.setTerritory(realmCustomer.getTerritory());
                    modal.setPincode(realmCustomer.getPincode());
                    modal.setCustomerId(realmCustomer.getId());
                    modal.setContactNo(realmCustomer.getContactNo());
                    modal.setStatus(type);
                    //  modal.setDate(AppConstants.format10.format(realmAnswers.get(i).getDate()));
                    stringArrayList.add(modal);
                }
            } else {
                Utility.showToast(getString(R.string.no_data));
            }
        } catch (Exception e) {
            realm.close();
            e.printStackTrace();
        } finally {
            realm.close();
        }

        if (surveyDetailAdapter != null) {
            surveyDetailAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
