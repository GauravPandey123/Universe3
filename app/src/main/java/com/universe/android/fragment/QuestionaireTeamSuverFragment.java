package com.universe.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.universe.android.R;
import com.universe.android.activity.BaseActivity;
import com.universe.android.activity.TeamSurveyDeatilActivity;
import com.universe.android.adapter.SurveyListAdapter;
import com.universe.android.adapter.TeamSurveySelectionAdapter;
import com.universe.android.helper.RecyclerTouchListener;
import com.universe.android.model.CrystalDoctorModel;
import com.universe.android.model.SurveysModal;
import com.universe.android.realmbean.RealmCrystalDoctor;
import com.universe.android.realmbean.RealmSurveys;
import com.universe.android.resource.Login.login.LoginRequest;
import com.universe.android.resource.Login.login.LoginResponse;
import com.universe.android.resource.Login.login.LoginService;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Utility;
import com.universe.android.web.BaseApiCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import in.editsoft.api.exception.APIException;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by gaurav.pandey on 12-02-2018.
 */

public class QuestionaireTeamSuverFragment extends BaseFragment {
    private View view;
    private SwipeRefreshLayout swipeRefrehLayoutTeamSurveySelection;
    private RecyclerView recyclerViewTeamSurveySelection;

    private TeamSurveySelectionAdapter teamSurveySelectionAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<LoginResponse.ResponseBean.SurveyDetailsBean> crystalDoctorModels;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.team_survey_selection_fragemnt, container, false);
        initialization();
        setupElemenets();
        setUpListeners();
        return view;
    }

    private void setUpListeners() {
        recyclerViewTeamSurveySelection.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerViewTeamSurveySelection, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getActivity(), TeamSurveyDeatilActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void setupElemenets() {
        crystalDoctorModels = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewTeamSurveySelection);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        teamSurveySelectionAdapter = new TeamSurveySelectionAdapter(getActivity(), crystalDoctorModels);
        recyclerView.setAdapter(teamSurveySelectionAdapter);
        setWebService();
    }

    private void setWebService() {
        if (!Utility.isConnected()) {
            Utility.showToast(R.string.msg_disconnected);
        } else {
            teamSurveyDetail();
        }
    }

    private void initialization() {
        swipeRefrehLayoutTeamSurveySelection = view.findViewById(R.id.swipeRefrehLayoutTeamSurveySelection);
        recyclerViewTeamSurveySelection = view.findViewById(R.id.recyclerViewTeamSurveySelection);
    }

    public void teamSurveyDetail() {
        ((BaseActivity) getActivity()).showProgress();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("BHAJAN.LAL@CRYSTALCROP.COM");
        loginRequest.setPassword("pass123456");
        loginRequest.setLat("22");
        loginRequest.setLng("77");
        loginRequest.setType("report");
        LoginService loginService = new LoginService();
        loginService.executeService(loginRequest, new BaseApiCallback<LoginResponse>() {
            @Override
            public void onComplete() {
                ((BaseActivity) getActivity()).dismissProgress();
            }

            @Override
            public void onSuccess(@NonNull LoginResponse response) {
                super.onSuccess(response);
                List<LoginResponse.ResponseBean.SurveyDetailsBean> responsed = response.getResponse().getSurveyDetails();
                String value = new Gson().toJson(responsed);
                LoginResponse.ResponseBean.SurveyDetailsBean[] surveyDetailsBeans = new Gson().fromJson(value, LoginResponse.ResponseBean.SurveyDetailsBean[].class);
                Collections.addAll(crystalDoctorModels, surveyDetailsBeans);
                teamSurveySelectionAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
            }
        });


    }


}
