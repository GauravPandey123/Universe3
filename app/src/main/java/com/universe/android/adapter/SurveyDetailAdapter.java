package com.universe.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class SurveyDetailAdapter extends RecyclerView.Adapter<SurveyDetailAdapter.SurveyViewHolder> {
    private Context mContext;
    private ArrayList<AnswersModal> stringArrayList;
    private OnItemSelecteListener mListener;

    public SurveyDetailAdapter(Context mContext, ArrayList<AnswersModal> stringArrayList) {
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
        holder.textViewMobileNo.setTypeface(FontClass.openSansRegular(mContext));
        holder.textViewDate.setTypeface(FontClass.openSansRegular(mContext));
        if (Utility.validateString(stringArrayList.get(position).getTitle()))
            holder.textViewRetailersName.setText(stringArrayList.get(position).getTitle().substring(0, 1).concat(stringArrayList.get(position).getTitle().substring(1).toLowerCase()));
        holder.textViewMobileNo.setText(new StringBuilder().append(stringArrayList.get(position).getContactNo()).append(" | ").append(stringArrayList.get(position).getTerritory().substring(0, 1).toUpperCase().concat(stringArrayList.get(position).getTerritory().substring(1).toLowerCase())).append(" | ").append(stringArrayList.get(position).getState().substring(0, 1).concat(stringArrayList.get(position).getState().substring(1).toLowerCase())).toString());
        holder.textViewDate.setVisibility(View.GONE);

        if (Utility.validateString(stringArrayList.get(position).getStatus())) {
            if (stringArrayList.get(position).getStatus().equalsIgnoreCase("1")) {
                holder.llStatus.setVisibility(View.VISIBLE);
                holder.textViewDate.setText("|" + stringArrayList.get(position).getDate());
                holder.imageViewStatus.setImageResource(R.drawable.ic_submitted);
                holder.textViewDate.setVisibility(View.VISIBLE);
                holder.tvStatus.setText(R.string.completed);
            } else if (stringArrayList.get(position).getStatus().equalsIgnoreCase("5")) {
                holder.llStatus.setVisibility(View.VISIBLE);
                holder.textViewDate.setVisibility(View.GONE);
                holder.imageViewStatus.setImageResource(R.drawable.ic_inprogress);
                holder.tvStatus.setText(R.string.inprogress);
            } else {
                holder.llStatus.setVisibility(View.GONE);
            }
        } else {
            holder.llStatus.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }
    public void setOnItemClickLister(OnItemSelecteListener mListener) {
        this.mListener = mListener;
    }
    public interface OnItemSelecteListener {
        public void onItemSelected(View v, int position);
    }

    public class SurveyViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewRetailersName, textViewMobileNo, tvStatus;
        private LinearLayout llStatus;
        public ImageView imageViewStatus;
        public TextView textViewDate;

        public SurveyViewHolder(View itemView) {
            super(itemView);
            textViewRetailersName = itemView.findViewById(R.id.textViewRetailersName);
            textViewMobileNo = itemView.findViewById(R.id.textViewMobileNo);
            imageViewStatus = itemView.findViewById(R.id.imageViewStatus);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            llStatus = itemView.findViewById(R.id.llStatus);
        }
    }
}
