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

public class VillageAdapter extends RecyclerView.Adapter<VillageAdapter.VillageViewHolder> {
    private ArrayList<DistributorResponse.ResponseBean.VillageBean> villageBeanArrayList;
    private Context mContext;
    private DistributorResponse.ResponseBean.VillageBean villageBean;


    public VillageAdapter(Context mContext, ArrayList<DistributorResponse.ResponseBean.VillageBean> villageBeanArrayList) {
        this.mContext = mContext;
        this.villageBeanArrayList = villageBeanArrayList;
    }

    @Override
    public VillageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.statelistitem, parent, false);
        return new VillageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VillageViewHolder holder, int position) {
        villageBean = villageBeanArrayList.get(position);
        holder.textViewState.setText(villageBean.getVillage_name());
    }

    @Override
    public int getItemCount() {
        return villageBeanArrayList.size();
    }

    public class VillageViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewState;

        public VillageViewHolder(View itemView) {
            super(itemView);
            textViewState = itemView.findViewById(R.id.textViewState);
        }
    }
}
