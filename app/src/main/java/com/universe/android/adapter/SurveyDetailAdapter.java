package com.universe.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.universe.android.R;

/**
 * Created by gaurav.pandey on 24-01-2018.
 */

public class SurveyDetailAdapter extends RecyclerView.Adapter<SurveyDetailAdapter.SurveyViewHolder> {
    private Context mContext;

    public SurveyDetailAdapter(Context mContext) {
        this.mContext = mContext;

    }

    @Override
    public SurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.part_survey_detail, parent, false);
        return new SurveyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SurveyViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class SurveyViewHolder extends RecyclerView.ViewHolder {
        public SurveyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
