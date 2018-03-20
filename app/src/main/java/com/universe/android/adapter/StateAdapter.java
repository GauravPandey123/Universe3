package com.universe.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.StaeAndCropResponse;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;

import java.util.ArrayList;

/**
 * Created by gaurav.pandey on 03-03-2018.
 */

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.StateViewHolder> {
    public Context mContext;
    private ArrayList<StaeAndCropResponse.ResponseBean.StateBean> stateBeanArrayList;
    private StaeAndCropResponse.ResponseBean.StateBean stateBean;

    public StateAdapter(Context mContext, ArrayList<StaeAndCropResponse.ResponseBean.StateBean> stateBeanArrayList) {
        this.mContext = mContext;
        this.stateBeanArrayList = stateBeanArrayList;

    }

    @Override
    public StateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.statelistitem, parent, false);
        return new StateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StateViewHolder holder, int position) {
        stateBean = stateBeanArrayList.get(position);
        if (stateBean != null) {
            holder.textViewState.setText(stateBean.getState_name());
        }
    }

    @Override
    public int getItemCount() {
        return stateBeanArrayList.size();
    }

    public class StateViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewState;

        public StateViewHolder(View itemView) {
            super(itemView);
            textViewState = itemView.findViewById(R.id.textViewState);
        }
    }
}
