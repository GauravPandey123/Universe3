package com.universe.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.model.CrystalDoctorModel;
import com.universe.android.model.CustomerModal;
import com.universe.android.resource.Login.login.LoginResponse;
import com.universe.android.utility.Utility;

import java.util.ArrayList;

/**
 * Created by gaurav.pandey on 20-02-2018.
 */

public class TeamSurveySelectionAdapter extends RecyclerView.Adapter<TeamSurveySelectionAdapter.TeamSurveyViewHolder> {

    private Context mContext;
    private ArrayList<CrystalDoctorModel> mappingBeans;

    public TeamSurveySelectionAdapter(Context mContext, ArrayList<CrystalDoctorModel> mappingBeans) {
        this.mContext = mContext;
        this.mappingBeans = mappingBeans;
    }

    @Override
    public TeamSurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.survey_list_row, parent, false);
        return new TeamSurveyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TeamSurveyViewHolder holder, int position) {
        CrystalDoctorModel mappingBean = mappingBeans.get(position);

        if (Utility.validateString(mappingBean.getTitle())){
            holder.tvTitle.setText(mappingBean.getTitle());
        }
        if (Utility.validateString(mappingBean.getExpiryDate())){
            holder.tvExpiryDate.setText(mappingBean.getExpiryDate());

        }
//        if (Utility.validateString(surveysModal.getStatus())){
//            holder.tvPending.setText(surveysModal.getStatus());
//        }

        setUpListners(holder, position);
    }

    private void setUpListners(TeamSurveyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mappingBeans.size();
    }

    public class TeamSurveyViewHolder extends RecyclerView.ViewHolder {
        private final View item;
        private TextView tvTitle, tvExpiryDate, tvPending;

        public TeamSurveyViewHolder(View itemView) {
            super(itemView);
            item = itemView;
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvExpiryDate = itemView.findViewById(R.id.tvExpiryDate);
            tvPending = itemView.findViewById(R.id.tvPending);
        }
    }
}
