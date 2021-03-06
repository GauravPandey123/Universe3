package com.universe.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.helper.FontClass;
import com.universe.android.model.AnswersModal;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Utility;

import java.util.ArrayList;

/**
 * Created by gaurav.pandey on 24-01-2018.
 */

public class WorkFLowDetailAdapter extends RecyclerView.Adapter<WorkFLowDetailAdapter.SurveyViewHolder> {
    private Context mContext;
    private ArrayList<AnswersModal> stringArrayList;
    private OnItemSelecteListener mListener;

    public WorkFLowDetailAdapter(Context mContext, ArrayList<AnswersModal> stringArrayList) {
        this.mContext = mContext;
        this.stringArrayList = stringArrayList;

    }

    @Override
    public SurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.workflow_detail_item, parent, false);
        return new SurveyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SurveyViewHolder holder, int position) {
        holder.llViews.setVisibility(View.GONE);
        holder.textViewRetailersName.setTypeface(FontClass.openSansBold(mContext));
        holder.textViewMobileNo.setTypeface(FontClass.openSansRegular(mContext));

        if (Utility.validateString(stringArrayList.get(position).getTitle()))
        holder.textViewRetailersName.setText(stringArrayList.get(position).getTitle());

            holder.textViewMobileNo.setText(stringArrayList.get(position).getContactNo()+" | "+
                    stringArrayList.get(position).getTerritory()+" | "+stringArrayList.get(position).getState()+"  \n"+
                   stringArrayList.get(position).getDate());


            if (stringArrayList.get(position).getCustomer().equalsIgnoreCase(AppConstants.CrystalCustomer)){
                holder.imgCustomer.setImageResource(R.drawable.ic_customer);
            }else{
                holder.imgCustomer.setImageResource(R.drawable.ic_retailer);
            }

    }

    public void setOnItemClickLister(OnItemSelecteListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public interface OnItemSelecteListener {
        public void onItemSelected(View v, int position);
    }

    public class SurveyViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewRetailersName, textViewMobileNo, textViewStatus;
        private ImageView imgCustomer;
        private RelativeLayout llViews;

        public SurveyViewHolder(View itemView) {
            super(itemView);
            textViewRetailersName = itemView.findViewById(R.id.textViewRetailersName);
            textViewMobileNo = itemView.findViewById(R.id.textViewMobileNo);
            imgCustomer=itemView.findViewById(R.id.imgCustomer);
            llViews=itemView.findViewById(R.id.llViews);

           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemSelected(view, getAdapterPosition());

                }
            });*/

        }
    }
}
