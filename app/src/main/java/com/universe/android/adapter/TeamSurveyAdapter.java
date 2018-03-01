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
import com.universe.android.resource.Login.login.LoginResponse;
import com.universe.android.utility.AppConstants;

import java.util.ArrayList;

/**
 * Created by gaurav.pandey on 24-02-2018.
 */

public class TeamSurveyAdapter extends RecyclerView.Adapter<TeamSurveyAdapter.TeamSurveyViewHolder> {
    private Context mContext;
    private int percent;
    private ArrayList<LoginResponse.ResponseBean.SurveyDetailsBean> surveyDetailsBeans;
    private LoginResponse.ResponseBean.SurveyDetailsBean surveyDetailsBean;


    public TeamSurveyAdapter(Context mContext, ArrayList<LoginResponse.ResponseBean.SurveyDetailsBean> surveyDetailsBeans) {
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
        holder.textViewHeader.setText(surveyDetailsBean.getDoctorAssign().get(position).getCrystalDoctorName());
        String totalString = "" + surveyDetailsBean.getDoctorAssign().get(position).getTotal();
        String completedString = "" + surveyDetailsBean.getDoctorAssign().get(position).getCompleted();

        holder.itemTitle.setText(String.valueOf(totalString));
        holder.itemno.setText(String.valueOf(completedString));
        int n = Integer.parseInt(totalString);
        int v = Integer.parseInt(completedString);
        percent = v * 100 / n;
        holder.itempercentage.setText(String.valueOf(percent).concat("%"));

        holder.textViewteamInprogress.setText(String.valueOf(surveyDetailsBean.getInprogress()));
        holder.textViewNewReatilors.setText(String.valueOf(surveyDetailsBean.getNewRetailer()));
        holder.textViewCrystalMembers.setText(String.valueOf(surveyDetailsBean.getCrystalCustomer()));
        setUpListeners(holder, position);

    }

    private void setUpListeners(TeamSurveyViewHolder holder, final int position) {

        holder.relativeLayoutHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TeamSurveyDetailReport.class);
                intent.putExtra(AppConstants.Percent, percent);
                intent.putExtra(AppConstants.CrystaDoctorName, surveyDetailsBean.getDoctorAssign().get(position).getCrystalDoctorName());
                intent.putExtra(AppConstants.CDID, surveyDetailsBean.getDoctorAssign().get(position).get_id());
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
