package com.universe.android.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.universe.android.resource.Login.CrystalReport.CrystalReportResponse;

import java.util.ArrayList;

/**
 * Created by gaurav.pandey on 05-03-2018.
 */

public class InProgressAdapter extends RecyclerView.Adapter<InProgressAdapter.InProgressViewHolder> {

//    private ArrayList<CrystalReportResponse.ResponseBean.InprogressBean>
    @Override
    public InProgressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(InProgressViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class InProgressViewHolder extends RecyclerView.ViewHolder {
        public InProgressViewHolder(View itemView) {
            super(itemView);
        }
    }
}
