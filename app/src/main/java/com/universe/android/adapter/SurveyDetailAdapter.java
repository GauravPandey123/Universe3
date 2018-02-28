package com.universe.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        holder.textViewRetailersName.setTypeface(FontClass.openSansBold(mContext));
        holder.textViewMobileNo.setTypeface(FontClass.openSansRegular(mContext));
        holder.textViewStatus.setTypeface(FontClass.openSansRegular(mContext));
        if (Utility.validateString(stringArrayList.get(position).getTitle()))
            holder.textViewRetailersName.setText(stringArrayList.get(position).getTitle());

        holder.textViewMobileNo.setText(stringArrayList.get(position).getContactNo() + " | " +
                stringArrayList.get(position).getTerritory() + " | " + stringArrayList.get(position).getState() + "  " +
                stringArrayList.get(position).getDate());


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
        private TextView textViewRetailersName, textViewMobileNo, textViewStatus;

        public SurveyViewHolder(View itemView) {
            super(itemView);
            textViewRetailersName = itemView.findViewById(R.id.textViewRetailersName);
            textViewMobileNo = itemView.findViewById(R.id.textViewMobileNo);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemSelected(view, getAdapterPosition());

                }
            });*/

        }
    }
}
