package com.universe.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;

import com.universe.android.R;

/**
 * Created by gaurav.pandey on 24-01-2018.
 */

public class SurveySelectionActivity extends BaseActivity {
    private CardView cardViewRetailer, cardViewDistributor;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialization();
        setupElemenets();
    }

    private void setupElemenets() {
        cardViewRetailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SurveySelectionActivity.this, SurveyDetailActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        cardViewDistributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SurveySelectionActivity.this, SurveyDetailActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    private void initialization() {
        cardViewRetailer = findViewById(R.id.retailerSelection);
        cardViewDistributor = findViewById(R.id.retailerDistributor);
    }

    @Override
    public int setLayoutId() {
        return R.layout.surveydetailsselection;
    }
}
