package com.universe.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.helper.FontClass;
import com.universe.android.model.AnswersModal;
import com.universe.android.utility.Utility;

import java.util.ArrayList;

/**
 * Created by gaurav.pandey on 24-01-2018.
 */

public class WorkFLowAdapter extends RecyclerView.Adapter<WorkFLowAdapter.SurveyViewHolder> {
    private Context mContext;
    private ArrayList<AnswersModal> stringArrayList;
    private OnItemSelecteListener mListener;

    public WorkFLowAdapter(Context mContext, ArrayList<AnswersModal> stringArrayList) {
        this.mContext = mContext;
        this.stringArrayList = stringArrayList;

    }

    @Override
    public SurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.workflow_item, parent, false);
        return new SurveyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SurveyViewHolder holder, int position) {

        if (position==0){
            holder.view1.setVisibility(View.GONE);
        }else{
            holder.view1.setVisibility(View.VISIBLE);
        }
        if (position==stringArrayList.size()-1){
            holder.view2.setVisibility(View.GONE);
        }else{
            holder.view2.setVisibility(View.VISIBLE);
        }
        holder.textViewRetailersName.setTypeface(FontClass.openSansBold(mContext));
        holder.textViewMobileNo.setTypeface(FontClass.openSansRegular(mContext));

        if (Utility.validateString(stringArrayList.get(position).getTitle()))
        holder.textViewRetailersName.setText(stringArrayList.get(position).getTitle());

        if (Utility.validateString(stringArrayList.get(position).getReason())){
            holder.textViewMobileNo.setText(stringArrayList.get(position).getDate()+
                    "  \n"+
                    stringArrayList.get(position).getStatus()+    "  \n"+"Reason - "+stringArrayList.get(position).getReason()+    "  \n"+"RequestId - "+stringArrayList.get(position).getRequestId());

        }else {
            holder.textViewMobileNo.setText(stringArrayList.get(position).getDate()+
                    "  \n"+
                    stringArrayList.get(position).getStatus()+    "  \n"+    "  \n"+"RequestId - "+stringArrayList.get(position).getRequestId());

        }

            holder.imgCustomer.setVisibility(View.GONE);

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
        private View view1,view2;
        public SurveyViewHolder(View itemView) {
            super(itemView);
            textViewRetailersName = itemView.findViewById(R.id.textViewRetailersName);
            textViewMobileNo = itemView.findViewById(R.id.textViewMobileNo);
            imgCustomer=itemView.findViewById(R.id.imgCustomer);
            view1=itemView.findViewById(R.id.view1);
            view2=itemView.findViewById(R.id.view2);

           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemSelected(view, getAdapterPosition());

                }
            });*/

        }
    }
}
