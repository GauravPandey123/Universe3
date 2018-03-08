package com.universe.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.activity.AddNewRetailors;
import com.universe.android.activity.CategoryExpandableListActivity;
import com.universe.android.activity.SearchCustomersActivity;
import com.universe.android.activity.SurveyDetailActivity;
import com.universe.android.model.SurveysModal;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;
import com.universe.android.workflows.WorkFlowsDetailActivity;

import java.util.ArrayList;

import in.editsoft.api.util.App;

/**
 * Created by gaurav.pandey on 30-01-2018.
 */

public class SurveyListAdapter extends RecyclerView.Adapter<SurveyListAdapter.StatusViewHolder> {
    public ArrayList<SurveysModal> surveysModals = new ArrayList<>();
    public Context mContext;
    private String strType;

    public SurveyListAdapter(Context mContext, ArrayList<SurveysModal> stausList,String type) {
        this.mContext = mContext;
        this.surveysModals = stausList;
        this.strType=type;

    }


    @Override
    public StatusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.survey_list_row, null);
        return new StatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StatusViewHolder holder, final int position) {
        final SurveysModal surveysModal = surveysModals.get(position);
        if (Utility.validateString(surveysModal.getTitle())){
            holder.tvTitle.setText(surveysModal.getTitle());
        }
        if (Utility.validateString(surveysModal.getExpiryDate())){
            holder.tvExpiryDate.setText(surveysModal.getExpiryDate());
        }
        if (Utility.validateString(surveysModal.getStatus())){
            holder.tvPending.setText(surveysModal.getStatus());
        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=null;

                String type=Prefs.getStringPrefs(AppConstants.TYPE);
                if (type.equalsIgnoreCase("cd")) {
                    if (position == 0) {
                        i = new Intent(mContext, AddNewRetailors.class);
                    } else {
                        i = new Intent(mContext, SearchCustomersActivity.class);
                        Prefs.putStringPrefs(AppConstants.CUSTOMER, AppConstants.CrystalCustomer);
                        Prefs.putStringPrefs(AppConstants.STR_TITLE, strType+" "+surveysModal.getTitle());
                        i.putExtra(AppConstants.CUSTOMER, AppConstants.CrystalCustomer);
                    }
                }

                if (strType.equalsIgnoreCase(AppConstants.WORKFLOWS)){
                    i=new Intent(mContext, WorkFlowsDetailActivity.class);
                }
                if(strType.equalsIgnoreCase(AppConstants.SURVEYREPORT))
                {
                    i=new Intent(mContext, SurveyDetailActivity.class);
                }

                i.putExtra(AppConstants.TYPE,strType+" "+surveysModal.getTitle());
                i.putExtra(AppConstants.SURVEYID,surveysModal.getId());
                i.putExtra(AppConstants.STATUS,surveysModal.getStatus());
                i.putExtra(AppConstants.EXPIRYDATE,surveysModal.getExpiryDate());

                mContext.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return surveysModals.size();
    }

    public class StatusViewHolder extends RecyclerView.ViewHolder {

        private final View item;
        private TextView tvTitle,tvExpiryDate,tvPending;


        public StatusViewHolder(View itemView) {
            super(itemView);
            item=itemView;
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvExpiryDate = itemView.findViewById(R.id.tvExpiryDate);
            tvPending = itemView.findViewById(R.id.tvPending);
        }
    }
}
