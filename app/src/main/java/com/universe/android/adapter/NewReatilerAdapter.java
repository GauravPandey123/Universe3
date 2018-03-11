//package com.universe.android.adapter;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.universe.android.R;
//import com.universe.android.resource.Login.CrystalReport.CrystalReportResponse;
//
//import java.util.ArrayList;
//
///**
// * Created by gaurav.pandey on 07-03-2018.
// */
//
//public class NewReatilerAdapter extends RecyclerView.Adapter<NewReatilerAdapter.NewReatilerViewHolder> {
//    private ArrayList<CrystalReportResponse.ResponseBean.NewRetailerBean.ListBeanXX> listBeanXXArrayList;
//    private Context mContext;
//    private CrystalReportResponse.ResponseBean.NewRetailerBean.ListBeanXX listBeanXX;
//
//    public NewReatilerAdapter(Context mContext, ArrayList<CrystalReportResponse.ResponseBean.NewRetailerBean.ListBeanXX> listBeanXXES) {
//        this.mContext = mContext;
//        this.listBeanXXArrayList = listBeanXXES;
//
//    }
//
//
//    @Override
//    public NewReatilerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.part_survey_detail, parent, false);
//        return new NewReatilerViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(NewReatilerViewHolder holder, int position) {
//        listBeanXX = listBeanXXArrayList.get((position));
//        holder.textViewRetailersName.setText(listBeanXX.getCustomerId().getName());
//        holder.textViewMobileNo.setText("" + listBeanXX.getCustomerId().getContactNo() + "\n" + "|" + listBeanXX.getCustomerId().getAddress());
//        holder.imageViewStatus.setVisibility(View.GONE);
//        holder.textViewStatus.setVisibility(View.GONE);
//    }
//
//    @Override
//    public int getItemCount() {
//        return listBeanXXArrayList.size();
//    }
//
//    public class NewReatilerViewHolder extends RecyclerView.ViewHolder {
//        private TextView textViewRetailersName, textViewMobileNo, textViewStatus;
//        private ImageView imageViewStatus;
//
//        public NewReatilerViewHolder(View itemView) {
//            super(itemView);
//            textViewRetailersName = itemView.findViewById(R.id.textViewRetailersName);
//            textViewMobileNo = itemView.findViewById(R.id.textViewMobileNo);
//            textViewStatus = itemView.findViewById(R.id.tvStatus);
//            imageViewStatus = itemView.findViewById(R.id.imageViewStatus);
//        }
//    }
//
//}
