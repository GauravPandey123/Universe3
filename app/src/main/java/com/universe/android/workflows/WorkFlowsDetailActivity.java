package com.universe.android.workflows;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.activity.BaseActivity;
import com.universe.android.activity.QuestionsCategoryActivity;
import com.universe.android.adapter.SurveyDetailAdapter;
import com.universe.android.helper.RecyclerTouchListener;
import com.universe.android.utility.AppConstants;

import java.util.ArrayList;

/**
 * Created by gaurav.pandey on 24-01-2018.
 */

public class WorkFlowsDetailActivity extends BaseActivity {
    //decalre the Views here
    private RecyclerView recyclerViewWorkFLowsDetail;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recylerViewStatus;
    private SwipeRefreshLayout swipeRefreshLayoutStatus;
    private ImageView imageViewBack;

    private ArrayList<String> stringArrayList;
    private LinearLayoutManager linearLayoutManager;
    private SurveyDetailAdapter surveyDetailAdapter;
    private String strType;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workflows_details_fragment);
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

        surveyDetailAdapter.setOnItemClickLister(new SurveyDetailAdapter.OnItemSelecteListener() {
            @Override
            public void onItemSelected(View v, int position) {
                Intent intent = new Intent(mContext, QuestionsCategoryActivity.class);
                intent.putExtra(AppConstants.TYPE, strType);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

            }
        });

        recyclerViewWorkFLowsDetail.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerViewWorkFLowsDetail, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(mContext, QuestionsCategoryActivity.class);
                intent.putExtra(AppConstants.TYPE, strType);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void setUpElements() {
        searchList();  // in this method, Create a list of items.
        surveyDetailAdapter = new SurveyDetailAdapter(mContext, stringArrayList);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewWorkFLowsDetail.setLayoutManager(linearLayoutManager);
        recyclerViewWorkFLowsDetail.setItemAnimator(new DefaultItemAnimator());
        recyclerViewWorkFLowsDetail.setAdapter(surveyDetailAdapter);


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initialization() {
        stringArrayList = new ArrayList<>();
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerViewWorkFLowsDetail = findViewById(R.id.recylerViewSurveyDetail);
        TextView textViewMobileNo = (TextView) findViewById(R.id.textViewMobileNo);
        textViewMobileNo.setText(getResources().getString(R.string.workflow_mobile_no));
        imageViewBack = findViewById(R.id.imageviewback);
        TextView textViewStatus = (TextView) findViewById(R.id.textViewStatus);


        Intent intent = getIntent();
        if (intent != null) {
            strType = intent.getExtras().getString(AppConstants.TYPE);
        }

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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
