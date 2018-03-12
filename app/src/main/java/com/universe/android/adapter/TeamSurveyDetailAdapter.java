package com.universe.android.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.model.ListBean;
import com.universe.android.resource.Login.CrystalReport.CrystalReportResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav.pandey on 22-02-2018.
 */

public class TeamSurveyDetailAdapter extends RecyclerView.Adapter<TeamSurveyDetailAdapter.TeamSurveyViewHolder> {
    Context mContext;
    private List<ListBean> responseBeanArrayList;
    private int type = 0;

    public TeamSurveyDetailAdapter(Context mContext, List<ListBean> responseBeans, int type) {
        this.mContext = mContext;
        this.responseBeanArrayList = responseBeans;
        this.type = type;
    }


    public void setResponseBeanArrayList(List<ListBean> responseBeanArrayList, int type) {
        this.responseBeanArrayList = new ArrayList<>();
        this.responseBeanArrayList = responseBeanArrayList;
        this.type = type;
        notifyDataSetChanged();
    }

    @Override
    public TeamSurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.part_survey_detail, parent, false);
        return new TeamSurveyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(TeamSurveyViewHolder holder, int position) {
        ListBean responseBean = responseBeanArrayList.get(position);
        if (responseBean.getCustomerId() != null) {
            holder.textViewRetailersName.setText(responseBean.getCustomerId().getName());
            holder.textViewMobileNo.setText("" + responseBean.getCustomerId().getContactNo() + "\n" + "|" + responseBean.getCustomerId().getAddress());

        }
        switch (type) {
            case 1:
                holder.ivType.setImageResource(R.drawable.ic_submitted);
                holder.textViewStatus.setText("Submitted");
                break;
            case 2:
                holder.ivType.setImageResource(R.drawable.ic_inprogress);
                holder.textViewStatus.setText("InProgress");
                break;
            default:
                break;
        }


    }

    @Override
    public int getItemCount() {
        return responseBeanArrayList.size();
    }

    public class TeamSurveyViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewRetailersName, textViewMobileNo, textViewStatus;
        private ImageView ivType;

        public TeamSurveyViewHolder(View itemView) {
            super(itemView);
            textViewRetailersName = itemView.findViewById(R.id.textViewRetailersName);
            textViewMobileNo = itemView.findViewById(R.id.textViewMobileNo);
            textViewStatus = itemView.findViewById(R.id.tvStatus);
            ivType = itemView.findViewById(R.id.imageViewStatus);
        }
    }
}
