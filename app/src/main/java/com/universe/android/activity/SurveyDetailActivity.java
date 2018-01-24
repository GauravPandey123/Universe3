package com.universe.android.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.universe.android.R;
import com.universe.android.adapter.SurveyDetailAdapter;

/**
 * Created by gaurav.pandey on 24-01-2018.
 */

public class SurveyDetailActivity extends BaseActivity {
    //declare the views here
    private RecyclerView recyclerViewSurveyDetail;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private SurveyDetailAdapter surveyDetailAdapter;
    private ImageView imageViewBack, imageViewFilter;

    //decalre the variable here


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
    }


    private void setUpElements() {
        surveyDetailAdapter = new SurveyDetailAdapter(mContext);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewSurveyDetail.setLayoutManager(linearLayoutManager);
        recyclerViewSurveyDetail.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSurveyDetail.setAdapter(surveyDetailAdapter);


    }

    private void initialization() {
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerViewSurveyDetail = findViewById(R.id.recylerViewSurveyDetail);
        imageViewBack =findViewById(R.id.imageviewback);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
