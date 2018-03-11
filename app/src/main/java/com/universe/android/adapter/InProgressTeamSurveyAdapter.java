//package com.universe.android.adapter;
//
//import android.content.Context;
//import android.media.Image;
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
//public class InProgressTeamSurveyAdapter extends RecyclerView.Adapter<InProgressTeamSurveyAdapter.InProgreeViewHolder> {
//    private Context mContext;
//    private ArrayList<CrystalReportResponse.ResponseBean.InprogressBean.ListBeanX> listBeanXArrayList;
//    private CrystalReportResponse.ResponseBean.InprogressBean.ListBeanX listBeanX;
//
//    public InProgressTeamSurveyAdapter(Context mContext, ArrayList<CrystalReportResponse.ResponseBean.InprogressBean.ListBeanX> listBeanXES) {
//        this.mContext = mContext;
//        this.listBeanXArrayList = listBeanXES;
//    }
//
//    @Override
//    public InProgreeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.part_survey_detail, parent, false);
//        return new InProgreeViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(InProgreeViewHolder holder, int position) {
//        listBeanX=listBeanXArrayList.get((position));
//        holder.textViewRetailersName.setText(listBeanX.getCustomerId().getName());
//        holder.textViewMobileNo.setText(""+listBeanX.getCustomerId().getContactNo()+"\n"+"|"+listBeanX.getCustomerId().getAddress());
//        holder.imageViewStatus.setImageResource(R.drawable.ic_progress);
//        holder.textViewStatus.setText("In Progress");
//    }
//
//    @Override
//    public int getItemCount() {
//        return listBeanXArrayList.size();
//    }
//
//    public class InProgreeViewHolder extends RecyclerView.ViewHolder {
//        private TextView textViewRetailersName, textViewMobileNo, textViewStatus;
//        private ImageView imageViewStatus;
//        public InProgreeViewHolder(View itemView) {
//            super(itemView);
//            textViewRetailersName = itemView.findViewById(R.id.textViewRetailersName);
//            textViewMobileNo = itemView.findViewById(R.id.textViewMobileNo);
//            textViewStatus = itemView.findViewById(R.id.tvStatus);
//            imageViewStatus=itemView.findViewById(R.id.imageViewStatus);
//        }
//    }
//}
