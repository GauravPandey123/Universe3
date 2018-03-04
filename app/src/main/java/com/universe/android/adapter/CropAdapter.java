package com.universe.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.StaeAndCropResponse;

import java.util.ArrayList;

/**
 * Created by gaurav.pandey on 03-03-2018.
 */

public class CropAdapter extends RecyclerView.Adapter<CropAdapter.CropDoctorViewHolder> {

    private Context mContext;
    private ArrayList<StaeAndCropResponse.ResponseBean.CropBean> cropBeanArrayList;
    private StaeAndCropResponse.ResponseBean.CropBean cropBean;


    public CropAdapter(Context mContext, ArrayList<StaeAndCropResponse.ResponseBean.CropBean> cropBeanArrayList) {
        this.mContext = mContext;
        this.cropBeanArrayList = cropBeanArrayList;
    }

    @Override
    public CropDoctorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.statelistitem, parent, false);
        return new CropDoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CropDoctorViewHolder holder, int position) {
        cropBean = cropBeanArrayList.get(position);
        holder.textViewState.setText(cropBean.getCROP_NAME());

    }

    @Override
    public int getItemCount() {
        return cropBeanArrayList.size();
    }

    public class CropDoctorViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewState;

        public CropDoctorViewHolder(View itemView) {
            super(itemView);
            textViewState = itemView.findViewById(R.id.textViewState);
        }
    }
}
