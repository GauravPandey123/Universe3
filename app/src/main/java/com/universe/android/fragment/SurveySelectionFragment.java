package com.universe.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.universe.android.R;


import com.universe.android.adapter.SurveyListAdapter;
import com.universe.android.helper.FontClass;
import com.universe.android.model.SurveysModal;
import com.universe.android.realmbean.RealmAnswers;
import com.universe.android.realmbean.RealmSurveys;
import com.universe.android.utility.AppConstants;
import com.universe.android.workflows.WorkFlowsDetailActivity;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by gaurav.pandey on 24-01-2018.
 */

public class SurveySelectionFragment extends BaseFragment {
    View view;
    private String strType;
    private ArrayList<SurveysModal> surveysModals = new ArrayList<>();
    private SurveyListAdapter adapter;


    public static SurveySelectionFragment newInstance(String type) {
        SurveySelectionFragment myFragment = new SurveySelectionFragment();
        Bundle args = new Bundle();
        args.putString(AppConstants.TYPE, type);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        strType = getArguments().getString(AppConstants.TYPE);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.act_list_view, container, false);
        prepareList();
        initialization(view);
        return view;}

    private void initialization(View view) {
        RelativeLayout include=view.findViewById(R.id.include);
        include.setVisibility(View.GONE);
        TextView textView=view.findViewById(R.id.tvName);
        textView.setVisibility(View.GONE);
        FloatingActionButton fabAdd=view.findViewById(R.id.fabAdd);
        fabAdd.setVisibility(View.GONE);
        RecyclerView recyclerView =  view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SurveyListAdapter(getActivity(), surveysModals,strType);
        recyclerView.setAdapter(adapter);
    }


    private void prepareList() {
        if (surveysModals == null) surveysModals = new ArrayList<>();
        surveysModals.clear();
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<RealmSurveys> realmSurveys = realm.where(RealmSurveys.class).findAllSorted(AppConstants.TITLE, Sort.DESCENDING);
            if (realmSurveys != null && realmSurveys.size() > 0) {
                for (int i = 0; i < realmSurveys.size(); i++) {
                    SurveysModal modal = new SurveysModal();
                    modal.setId(realmSurveys.get(i).getId());
                    modal.setTitle(realmSurveys.get(i).getTitle());
                    int count=0;
                    int total=5;
                    RealmResults<RealmAnswers> realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.SURVEYID,modal.getId()).findAll();
                   for (int k=0;k<realmAnswers.size();k++){
                       if (realmAnswers.get(k).getCd_Status()!=null) {
                           if (realmAnswers.get(k).getCd_Status().equalsIgnoreCase("1")) {
                               total = total - 1;
                           }
                       }
                   }

                    modal.setStatus(total+"");
                    modal.setExpiryDate(AppConstants.format2.format(realmSurveys.get(i).getExpiryDate()));
                    surveysModals.add(modal);
                }
            }
        } catch (Exception e) {
            realm.close();
            e.printStackTrace();
        } finally {
            realm.close();
        }

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }





}
