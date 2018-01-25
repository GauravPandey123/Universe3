package com.universe.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.universe.android.R;
import com.universe.android.activity.BaseActivity;
import com.universe.android.activity.SurveyDetailActivity;

/**
 * Created by gaurav.pandey on 24-01-2018.
 */

public class SurveySelectionFragment extends BaseFragment {
    private CardView cardViewRetailer, cardViewDistributor;
    View view;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.surveydetailsselection, container, false);
        initialization();
        setupElemenets();
        return view;
    }

    private void setupElemenets() {
        cardViewRetailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SurveyDetailActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        cardViewDistributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SurveyDetailActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    private void initialization() {
        cardViewRetailer = view.findViewById(R.id.retailerSelection);
        cardViewDistributor = view.findViewById(R.id.retailerDistributor);
    }


}
