package com.universe.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.Territory.TerroritryResponse;

import java.util.ArrayList;

/**
 * Created by gaurav.pandey on 03-03-2018.
 */

public class TerroritryAdapter extends RecyclerView.Adapter<TerroritryAdapter.TerroritryViewHolder> {
    private Context mContext;
    private ArrayList<TerroritryResponse.ResponseBean> responseBeanArrayList;
    private TerroritryResponse.ResponseBean responseBean;

    public TerroritryAdapter(Context mContext, ArrayList<TerroritryResponse.ResponseBean> responseBeanArrayList) {
        this.mContext = mContext;
        this.responseBeanArrayList = responseBeanArrayList;
    }


    @Override
    public TerroritryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.statelistitem, parent, false);
        return new TerroritryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TerroritryViewHolder holder, int position) {
        responseBean = responseBeanArrayList.get(position);
        holder.textViewState.setText(responseBean.getName());
    }

    @Override
    public int getItemCount() {
        return responseBeanArrayList.size();
    }

    public class TerroritryViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewState;

        public TerroritryViewHolder(View itemView) {
            super(itemView);
            textViewState = itemView.findViewById(R.id.textViewState);
        }
    }
}
