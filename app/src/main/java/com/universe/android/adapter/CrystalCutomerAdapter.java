package com.universe.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.resource.Login.CrystalReport.CrystalReportResponse;

import java.util.ArrayList;

/**
 * Created by gaurav.pandey on 07-03-2018.
 */

//public class CrystalCutomerAdapter extends RecyclerView.Adapter<CrystalCutomerAdapter.CrystalCustomerViewHolder> {
//
////    public ArrayList<CrystalReportResponse.ResponseBean.CrystalCustomerBean.ListBeanXXX> listBeanXXXArrayList;
////    private Context mContext;
////    private CrystalReportResponse.ResponseBean.CrystalCustomerBean.ListBeanXXX listBeanXXX;
////
//
////    public CrystalCutomerAdapter(Context mContext, ArrayList<CrystalReportResponse.ResponseBean.CrystalCustomerBean.ListBeanXXX> listBeanXXXArrayList) {
////        this.mContext = mContext;
////        this.listBeanXXXArrayList = listBeanXXXArrayList;
////    }
////
////    @Override
////    public CrystalCustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
////        View view = LayoutInflater.from(mContext).inflate(R.layout.part_survey_detail, parent, false);
////        return new CrystalCustomerViewHolder(view);
////    }
////
////    @Override
////    public void onBindViewHolder(CrystalCustomerViewHolder holder, int position) {
////        listBeanXXX=listBeanXXXArrayList.get(position);
////        holder.textViewRetailersName.setText(listBeanXXX.getCustomerId().getName());
////        holder.textViewMobileNo.setText(""+listBeanXXX.getCustomerId().getContactNo()+"\n"+"|"+listBeanXXX.getCustomerId().getAddress());
////        holder.imageViewStatus.setVisibility(View.GONE);
////        holder.textViewStatus.setVisibility(View.GONE);
////    }
////
////    @Override
////    public int getItemCount() {
////        return listBeanXXXArrayList.size();
////    }
////
////    public class CrystalCustomerViewHolder extends RecyclerView.ViewHolder {
////        private TextView textViewRetailersName, textViewMobileNo, textViewStatus;
////        private ImageView imageViewStatus;
////        public CrystalCustomerViewHolder(View itemView) {
////            super(itemView);
////        }
////    }
//
//}
