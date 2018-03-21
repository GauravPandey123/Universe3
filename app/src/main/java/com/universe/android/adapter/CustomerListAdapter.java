package com.universe.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.helper.FontClass;
import com.universe.android.model.AnswersModal;
import com.universe.android.model.CustomerModal;
import com.universe.android.utility.Utility;

import java.util.ArrayList;

/**
 * Created by gaurav.pandey on 24-01-2018.
 */

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.SurveyViewHolder> {
    private Context mContext;
    private ArrayList<CustomerModal> stringArrayList;
    private OnItemSelecteListener mListener;

    public CustomerListAdapter(Context mContext, ArrayList<CustomerModal> stringArrayList) {
        this.mContext = mContext;
        this.stringArrayList = stringArrayList;

    }

    @Override
    public SurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.part_survey_detail, parent, false);
        return new SurveyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SurveyViewHolder holder, int position) {
        holder.textViewRetailersName.setTypeface(FontClass.openSansRegular(mContext));
        holder.textViewMobileNo.setTypeface(FontClass.openSansLight(mContext));
        holder.textViewDate.setTypeface(FontClass.openSansLight(mContext));
        if (Utility.validateString(stringArrayList.get(position).getTitle()))
            holder.textViewRetailersName.setText(stringArrayList.get(position).getTitle().substring(0, 1).toUpperCase() + stringArrayList.get(position).getTitle().substring(1).toLowerCase());
        holder.textViewMobileNo.setText(stringArrayList.get(position).getContactNo() + " | " +
                stringArrayList.get(position).getTerritory() + " | " + stringArrayList.get(position).getState());
        holder.textViewDate.setVisibility(View.GONE);


        if (Utility.validateString(stringArrayList.get(position).getStatus())) {
            if (stringArrayList.get(position).getStatus().equalsIgnoreCase("1")) {
                holder.llStatus.setVisibility(View.VISIBLE);
                holder.textViewDate.setText("|" + stringArrayList.get(position).getDate());
                holder.textViewDate.setVisibility(View.VISIBLE);
                holder.tvStatus.setText("Submitted");
            } else if (stringArrayList.get(position).getStatus().equalsIgnoreCase("2")) {
                holder.llStatus.setVisibility(View.VISIBLE);
                holder.textViewDate.setVisibility(View.GONE);
                holder.tvStatus.setText("Approved");

            } else if (stringArrayList.get(position).getStatus().equalsIgnoreCase("3")) {
                holder.llStatus.setVisibility(View.VISIBLE);
                holder.textViewDate.setVisibility(View.GONE);
                holder.tvStatus.setText("Rejected");


            } else {
                holder.llStatus.setVisibility(View.GONE);
            }
        } else {
            holder.llStatus.setVisibility(View.GONE);
        }


    }

    public void setOnItemClickLister(OnItemSelecteListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public interface OnItemSelecteListener {
        public void onItemSelected(View v, int position);
    }

    public class SurveyViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewRetailersName, textViewMobileNo, textViewStatus, tvStatus;
        private LinearLayout llStatus;
        private RelativeLayout relativeLayout;
        private TextView textViewDate;

        public SurveyViewHolder(View itemView) {
            super(itemView);
            textViewRetailersName = itemView.findViewById(R.id.textViewRetailersName);
            textViewMobileNo = itemView.findViewById(R.id.textViewMobileNo);
            llStatus = itemView.findViewById(R.id.llStatus);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
            textViewDate = itemView.findViewById(R.id.textViewDate);

        }
    }
}
