package com.universe.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.activity.TeamSurveyDetailReport;
import com.universe.android.resource.Login.SurveyDetails.SurverDetailResponse;
import com.universe.android.resource.Login.login.LoginResponse;
import com.universe.android.utility.AppConstants;

import java.util.ArrayList;

/**
 * Created by gaurav.pandey on 24-02-2018.
 */

public class TeamSurveyAdapter extends RecyclerView.Adapter<TeamSurveyAdapter.TeamSurveyViewHolder> {
    private Context mContext;
    private int percent;
    private ArrayList<SurverDetailResponse.ResponseBean.CrystaDoctorBean> surveyDetailsBeans;
    private SurverDetailResponse.ResponseBean.CrystaDoctorBean surveyDetailsBean;


    public TeamSurveyAdapter(Context mContext, ArrayList<SurverDetailResponse.ResponseBean.CrystaDoctorBean> surveyDetailsBeans) {
        this.surveyDetailsBeans = surveyDetailsBeans;
        this.mContext = mContext;

    }

    @Override
    public TeamSurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        return new TeamSurveyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TeamSurveyViewHolder holder, int position) {
        surveyDetailsBean = surveyDetailsBeans.get(position);
        holder.textViewHeader.setText(surveyDetailsBean.getDetail().getName());
        String totalString = "" + surveyDetailsBean.getTotalAssign();
        String completedString = "" + surveyDetailsBean.getSubmittedCount();

        holder.itemTitle.setText("Target: " + String.valueOf(totalString));
        holder.itemno.setText("Submitted : " + String.valueOf(completedString));
        int n = Integer.parseInt(totalString);
        int v = Integer.parseInt(completedString);
        try {
            percent = v * 100 / n;
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.itempercentage.setText("Achievement: " + String.valueOf(percent).concat("%"));

        holder.textViewteamInprogress.setText("In Progress: " + String.valueOf(surveyDetailsBean.getProgress()));
        holder.textViewNewReatilors.setText("New Retailer: " + String.valueOf(surveyDetailsBean.getRetailorCount()));
        holder.textViewCrystalMembers.setText("Crystal Customer: " + String.valueOf(surveyDetailsBean.getCrystalCustomer()));
        setUpListeners(holder, position);

    }

    private void setUpListeners(TeamSurveyViewHolder holder, final int position) {

        holder.relativeLayoutHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TeamSurveyDetailReport.class);
                intent.putExtra(AppConstants.Percent, percent);
                intent.putExtra(AppConstants.CrystaDoctorName, surveyDetailsBean.getDetail().getName());
                intent.putExtra(AppConstants.CDID, surveyDetailsBean.getDetail().get_id());
                mContext.startActivity(intent);
//                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    @Override
    public int getItemCount() {
        return surveyDetailsBeans.size();
    }

    public class TeamSurveyViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewHeader;
        private TextView itemTitle, itemno, itempercentage;
        private TextView textViewteamInprogress, textViewNewReatilors, textViewCrystalMembers;
        private RelativeLayout relativeLayoutHeader;


        public TeamSurveyViewHolder(View itemView) {
            super(itemView);
            textViewHeader = itemView.findViewById(R.id.sectionTitle);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemno = itemView.findViewById(R.id.itemno);
            itempercentage = itemView.findViewById(R.id.itempercentage);
            textViewteamInprogress = itemView.findViewById(R.id.textViewteamInprogress);
            textViewNewReatilors = itemView.findViewById(R.id.textViewNewReatilors);
            textViewCrystalMembers = itemView.findViewById(R.id.textViewCrystalMembers);
            relativeLayoutHeader = itemView.findViewById(R.id.relativeLayoutHeader);
        }
    }
}
