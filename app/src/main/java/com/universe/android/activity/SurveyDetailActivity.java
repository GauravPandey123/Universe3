package com.universe.android.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.universe.android.R;
import com.universe.android.adapter.SurveyDetailAdapter;
import com.universe.android.helper.FontClass;

import java.util.ArrayList;

/**
 * Created by gaurav.pandey on 24-01-2018.
 */

public class SurveyDetailActivity extends BaseActivity {
    //declare the views here

    //decalre the variable here
    private RecyclerView recyclerViewSurveyDetail;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private SurveyDetailAdapter surveyDetailAdapter;
    private ImageView imageViewBack, imageViewFilter;
    private FloatingActionMenu floatingActionMenu;
    private FloatingActionButton floatingCrystal, floatingRetailers;
    private TextView textViewToday, textViewtarget, textViewAchievement, textViewInProgress;
    private TextView textViewNewRetailers, textViewCrystalmembers, textViewCompletedQuestionaire;
    private TextView textViewPeriodFrom, textViewPeriodTo, textViewPeriodStatus, textViewReset, textViewApplyFilter;
    private EditText input_period_from, input_period_to, input_period_status;
    private  Dialog dialog;
    private ImageView imageViewCancel;

    private ArrayList<String> stringArrayList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surveyreportfragment);
        initialization();
        setUpElements();
        setUpListeners();
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
                showDialog();
//                startActivity(new Intent(mContext, FIlterActivity.class));
//                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    private void setUpElements() {
        searchList();  // in this method, Create a list of items.
        surveyDetailAdapter = new SurveyDetailAdapter(mContext, stringArrayList);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewSurveyDetail.setLayoutManager(linearLayoutManager);
        recyclerViewSurveyDetail.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSurveyDetail.setAdapter(surveyDetailAdapter);
    }

    private void initialization() {
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

        textViewToday.setTypeface(FontClass.openSemiBold(mContext));
        textViewtarget.setTypeface(FontClass.openSansRegular(mContext));
        textViewAchievement.setTypeface(FontClass.openSansRegular(mContext));
        textViewInProgress.setTypeface(FontClass.openSansRegular(mContext));
        textViewNewRetailers.setTypeface(FontClass.openSansRegular(mContext));
        textViewCrystalmembers.setTypeface(FontClass.openSansRegular(mContext));
        textViewCompletedQuestionaire.setTypeface(FontClass.openSansRegular(mContext));

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

    }

    private void searchList() {
        stringArrayList.add("Agro Inputs Corporation");
        stringArrayList.add("Aj AgroChemicals");
        stringArrayList.add("Blossom AgriCore");
        stringArrayList.add("Chemical India");
        stringArrayList.add("Duncan India");
        stringArrayList.add("Gange Pestiside");
        stringArrayList.add("Agro Inputs Corporation");
        stringArrayList.add("Aj AgroChemicals");
        stringArrayList.add("Blossom AgriCore");
        stringArrayList.add("Chemical India");
        stringArrayList.add("Duncan India");
        stringArrayList.add("Gange Pestiside");
    }

    public void showDialog() {
        // Create custom dialog object
        dialog= new Dialog(mContext);
        // Include dialog.xml file
        dialog.setContentView(R.layout.filter_dialog);
        // Set dialog title
        dialoginitialization();
        dialogSetUpElements();
        dialog.show();


    }

    private void dialogSetUpElements() {

    }

    private void dialoginitialization() {
        textViewPeriodFrom = dialog.findViewById(R.id.textViewPeriodFrom);
        textViewPeriodTo = dialog.findViewById(R.id.textViewPeriodTo);
        textViewPeriodStatus = dialog.findViewById(R.id.textViewPeriodStatus);
        textViewReset = dialog.findViewById(R.id.textViewReset);
        textViewApplyFilter = dialog.findViewById(R.id.textViewApplyFilter);
        input_period_from = dialog.findViewById(R.id.input_period_from);
        input_period_to = dialog.findViewById(R.id.input_period_to);
        input_period_status = dialog.findViewById(R.id.input_period_status);

        textViewPeriodFrom.setTypeface(FontClass.openSansRegular(mContext));
        textViewPeriodTo.setTypeface(FontClass.openSansRegular(mContext));
        textViewPeriodStatus.setTypeface(FontClass.openSansRegular(mContext));
        textViewReset.setTypeface(FontClass.openSansRegular(mContext));
        textViewApplyFilter.setTypeface(FontClass.openSansLight(mContext));
        input_period_from.setTypeface(FontClass.openSansLight(mContext));
        input_period_to.setTypeface(FontClass.openSansLight(mContext));
        input_period_status.setTypeface(FontClass.openSansLight(mContext));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
