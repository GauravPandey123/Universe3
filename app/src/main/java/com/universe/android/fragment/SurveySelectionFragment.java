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


import com.universe.android.activity.SurveyDetailActivity;
import com.universe.android.adapter.SurveyListAdapter;
import com.universe.android.enums.DesignationEnum;
import com.universe.android.helper.FontClass;
import com.universe.android.model.SurveysModal;
import com.universe.android.realmbean.RealmAnswers;
import com.universe.android.realmbean.RealmSurveys;
import com.universe.android.realmbean.RealmWorkFlow;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;
import com.universe.android.workflows.WorkFlowsDetailActivity;

import java.util.ArrayList;
import java.util.Date;
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
        return view;
    }

    private void initialization(View view) {
        RelativeLayout include = view.findViewById(R.id.include);
        include.setVisibility(View.GONE);
        TextView textView = view.findViewById(R.id.tvName);
        textView.setVisibility(View.GONE);
        FloatingActionButton fabAdd = view.findViewById(R.id.fabAdd);
        fabAdd.setVisibility(View.GONE);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SurveyListAdapter(getActivity(), surveysModals, strType);
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
                    int count = 0;
                    int total = 0;
                    String type = Prefs.getStringPrefs(AppConstants.TYPE);
                    if (type.equalsIgnoreCase(DesignationEnum.requester.toString())) {
                        RealmWorkFlow realmWorkFlow = realm.where(RealmWorkFlow.class).equalTo(AppConstants.SURVEYID, realmSurveys.get(i).getId()).findFirst();

                        if (realmWorkFlow != null)
                            total = realmWorkFlow.getTarget();
                        RealmResults<RealmAnswers> realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.SURVEYID, modal.getId()).findAll();
                        for (int k = 0; k < realmAnswers.size(); k++) {
                            if (realmAnswers.get(k).getRequester_status() != null) {
                                if (realmAnswers.get(k).getRequester_status().equalsIgnoreCase("1") || realmAnswers.get(k).getRequester_status().equalsIgnoreCase("2")) {
                                    total = total - 1;
                                }
                            }
                        }
                    } else if (type.equalsIgnoreCase(DesignationEnum.approval1.toString())) {
                        RealmResults<RealmAnswers> realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.SURVEYID, modal.getId()).findAll();
                        for (int k = 0; k < realmAnswers.size(); k++) {
                            if (realmAnswers.get(k).getApproval1_status() != null) {
                                if (realmAnswers.get(k).getApproval1_status().equalsIgnoreCase("0")) {
                                    total = total + 1;
                                }
                            }
                        }
                    } else if (type.equalsIgnoreCase(DesignationEnum.approval2.toString())) {
                        RealmResults<RealmAnswers> realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.SURVEYID, modal.getId()).findAll();
                        for (int k = 0; k < realmAnswers.size(); k++) {
                            if (realmAnswers.get(k).getApproval2_status() != null) {
                                if (realmAnswers.get(k).getApproval2_status().equalsIgnoreCase("0")) {
                                    total = total + 1;
                                }
                            }
                        }
                    } else if (type.equalsIgnoreCase(DesignationEnum.approval3.toString())) {
                        RealmResults<RealmAnswers> realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.SURVEYID, modal.getId()).findAll();
                        for (int k = 0; k < realmAnswers.size(); k++) {
                            if (realmAnswers.get(k).getApproval3_status() != null) {
                                if (realmAnswers.get(k).getApproval3_status().equalsIgnoreCase("0")) {
                                    total = total + 1;
                                }
                            }
                        }
                    } else if (type.equalsIgnoreCase(DesignationEnum.approval4.toString())) {
                        RealmResults<RealmAnswers> realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.SURVEYID, modal.getId()).findAll();
                        for (int k = 0; k < realmAnswers.size(); k++) {
                            if (realmAnswers.get(k).getApproval4_status() != null) {
                                if (realmAnswers.get(k).getApproval4_status().equalsIgnoreCase("0")) {
                                    total = total + 1;
                                }
                            }
                        }
                    } else if (type.equalsIgnoreCase(DesignationEnum.approval5.toString())) {
                        RealmResults<RealmAnswers> realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.SURVEYID, modal.getId()).findAll();
                        for (int k = 0; k < realmAnswers.size(); k++) {
                            if (realmAnswers.get(k).getApproval5_status() != null) {
                                if (realmAnswers.get(k).getApproval5_status().equalsIgnoreCase("0")) {
                                    total = total + 1;
                                }
                            }
                        }
                    } else if (type.equalsIgnoreCase(DesignationEnum.approval6.toString())) {
                        RealmResults<RealmAnswers> realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.SURVEYID, modal.getId()).findAll();
                        for (int k = 0; k < realmAnswers.size(); k++) {
                            if (realmAnswers.get(k).getApproval6_status() != null) {
                                if (realmAnswers.get(k).getApproval6_status().equalsIgnoreCase("0")) {
                                    total = total + 1;
                                }
                            }
                        }
                    }
                    modal.setStatus(total + "");

                    modal.setExpiry(realmSurveys.get(i).getExpiryDate());
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

        if (surveysModals.size() == 0) {
            Utility.showToast(getString(R.string.no_data));
        }

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }


}
