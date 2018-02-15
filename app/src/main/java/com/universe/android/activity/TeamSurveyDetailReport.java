package com.universe.android.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.adapter.SurveyDetailAdapter;
import com.universe.android.helper.FontClass;

import java.util.ArrayList;

/**
 * Created by gaurav.pandey on 12-02-2018.
 */

public class TeamSurveyDetailReport extends BaseActivity {
    RelativeLayout relativeLayout;
    ImageView imageviewback;
    private RecyclerView recyclerViewWorkFLowsDetail;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<String> stringArrayList;
    private LinearLayoutManager linearLayoutManager;
    private SurveyDetailAdapter surveyDetailAdapter;
    private TextView textViewCrystalDoctor, textViewAmtala, textViewPosition, textViewAchievementNumbers, textViewAchievement;

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

    private void initialization() {
        relativeLayout = findViewById(R.id.include);
        imageviewback = findViewById(R.id.imageviewback);
        textViewCrystalDoctor = findViewById(R.id.textViewCraystalDoctor);
        textViewAmtala = findViewById(R.id.textViewAmtala);
        textViewPosition = findViewById(R.id.textViewPosition);
        textViewAchievementNumbers = findViewById(R.id.textViewAchievementNumbers);
        textViewAchievement = findViewById(R.id.textViewAchievement);
        relativeLayout.setVisibility(View.GONE);
        textViewCrystalDoctor.setTypeface(FontClass.openSansBold(mContext));
        textViewAchievement.setTypeface(FontClass.openSansRegular(mContext));
        textViewPosition.setTypeface(FontClass.openSansRegular(mContext));
        textViewAchievementNumbers.setTypeface(FontClass.openSansRegular(mContext));
        textViewAchievement.setTypeface(FontClass.openSansRegular(mContext));

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

    private void setUpELements() {
        stringArrayList = new ArrayList<>();
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerViewWorkFLowsDetail = findViewById(R.id.recylerViewSurveyDetail);
        searchList();  // in this method, Create a list of items.
        surveyDetailAdapter = new SurveyDetailAdapter(mContext, stringArrayList);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewWorkFLowsDetail.setLayoutManager(linearLayoutManager);
        recyclerViewWorkFLowsDetail.setItemAnimator(new DefaultItemAnimator());
        recyclerViewWorkFLowsDetail.setAdapter(surveyDetailAdapter);
    }
}
