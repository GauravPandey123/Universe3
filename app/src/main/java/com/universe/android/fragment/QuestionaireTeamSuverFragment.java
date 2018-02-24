package com.universe.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.universe.android.R;
import com.universe.android.activity.TeamSurveyDeatilActivity;
import com.universe.android.adapter.SurveyListAdapter;
import com.universe.android.adapter.TeamSurveySelectionAdapter;
import com.universe.android.helper.RecyclerTouchListener;
import com.universe.android.model.CrystalDoctorModel;
import com.universe.android.model.SurveysModal;
import com.universe.android.realmbean.RealmCrystalDoctor;
import com.universe.android.realmbean.RealmSurveys;
import com.universe.android.utility.AppConstants;

import org.json.JSONObject;

import java.util.ArrayList;

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
    private ArrayList<CrystalDoctorModel> crystalDoctorModels;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.team_survey_selection_fragemnt, container, false);
        initialization();
        prepareList();
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
//        teamSurveySelectionAdapter = new TeamSurveySelectionAdapter(getActivity());

        RecyclerView recyclerView =  view.findViewById(R.id.recyclerViewTeamSurveySelection);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        teamSurveySelectionAdapter = new TeamSurveySelectionAdapter(getActivity(), crystalDoctorModels);
        recyclerView.setAdapter(teamSurveySelectionAdapter);
//        linearLayoutManager = new LinearLayoutManager(getActivity());
//        recyclerViewTeamSurveySelection.setLayoutManager(linearLayoutManager);
//        recyclerViewTeamSurveySelection.computeVerticalScrollOffset();
//        recyclerViewTeamSurveySelection.setAdapter(teamSurveySelectionAdapter);
    }

    private void initialization() {
        swipeRefrehLayoutTeamSurveySelection = view.findViewById(R.id.swipeRefrehLayoutTeamSurveySelection);
        recyclerViewTeamSurveySelection = view.findViewById(R.id.recyclerViewTeamSurveySelection);
    }
    private void prepareList() {
        if (crystalDoctorModels == null) crystalDoctorModels = new ArrayList<>();
        crystalDoctorModels.clear();
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<RealmCrystalDoctor> realmSurveys = realm.where(RealmCrystalDoctor.class).findAll();
            if (realmSurveys != null && realmSurveys.size() > 0) {
                for (int i = 0; i < realmSurveys.size(); i++) {
                    CrystalDoctorModel modal = new CrystalDoctorModel();
                    modal.setId(realmSurveys.get(i).getId());
                    JSONObject jsonObject=new JSONObject(realmSurveys.get(i).getDetails());
                    modal.setTitle(jsonObject.optString(AppConstants.TITLE));
                 //   modal.setStatus(realmSurveys.get(i).getPending()+"");
                    modal.setStatus(10+"");
//                    modal.setExpiryDate(AppConstants.format2.format(realmSurveys.get(i).getExpiryDate()));
                    crystalDoctorModels.add(modal);
                }
            }
        } catch (Exception e) {
            realm.close();
            e.printStackTrace();
        } finally {
            realm.close();
        }

        if (teamSurveySelectionAdapter != null) {
            teamSurveySelectionAdapter.notifyDataSetChanged();
        }
    }

}
