package com.universe.android.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.resource.Login.CrystalReport.CrystalReportResponse;

import java.util.ArrayList;

/**
 * Created by gaurav.pandey on 22-02-2018.
 */

public class TeamSurveyDetailAdapter extends RecyclerView.Adapter<TeamSurveyDetailAdapter.TeamSurveyViewHolder> {
    Context mContext;
    private ArrayList<CrystalReportResponse.ResponseBean.SubmittedBean.ListBean> responseBeanArrayList;

    public TeamSurveyDetailAdapter(Context mContext, ArrayList<CrystalReportResponse.ResponseBean.SubmittedBean.ListBean> responseBeans) {
        this.mContext = mContext;
        this.responseBeanArrayList = responseBeans;
    }

    @Override
    public TeamSurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.part_survey_detail, parent, false);
        return new TeamSurveyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(TeamSurveyViewHolder holder, int position) {
        CrystalReportResponse.ResponseBean.SubmittedBean.ListBean responseBean = responseBeanArrayList.get(position);
        holder.textViewRetailersName.setText(responseBean.getCustomerId().getName());
        holder.textViewMobileNo.setText(""+responseBean.getCustomerId().getContactNo()+"|"+responseBean.getCustomerId().getAddress());
    }

    @Override
    public int getItemCount() {
        return responseBeanArrayList.size();
    }

    public class TeamSurveyViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewRetailersName, textViewMobileNo, textViewStatus;

        public TeamSurveyViewHolder(View itemView) {
            super(itemView);
            textViewRetailersName = itemView.findViewById(R.id.textViewRetailersName);
            textViewMobileNo = itemView.findViewById(R.id.textViewMobileNo);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
        }
    }
}
