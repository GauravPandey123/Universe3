package com.universe.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.DistributorAndVillage.DistributorResponse;

import java.util.ArrayList;

/**
 * Created by gaurav.pandey on 04-03-2018.
 */

public class DistributorAdapter extends RecyclerView.Adapter<DistributorAdapter.distributorViewHolder> {

    private Context mContext;
    private ArrayList<DistributorResponse.ResponseBean.DistributerBean> distributerBeanArrayList;

    public DistributorAdapter(Context mContext, ArrayList<DistributorResponse.ResponseBean.DistributerBean> distributerBeans) {
        this.mContext = mContext;
        this.distributerBeanArrayList = distributerBeans;
    }


    @Override
    public distributorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.statelistitem, parent, false);
        return new distributorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(distributorViewHolder holder, int position) {
        DistributorResponse.ResponseBean.DistributerBean distributerBean = distributerBeanArrayList.get(position);
        if (distributerBean != null) {
            holder.textViewState.setText(distributerBean.getName().substring(0,1).toUpperCase()+distributerBean.getName().substring(1).toLowerCase());
        }
    }

    @Override
    public int getItemCount() {
        return distributerBeanArrayList.size();
    }

    public class distributorViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewState;

        public distributorViewHolder(View itemView) {
            super(itemView);
            textViewState = itemView.findViewById(R.id.textViewState);
        }
    }

}
