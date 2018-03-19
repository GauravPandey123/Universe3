package com.universe.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.helper.FontClass;
import com.universe.android.model.CrystalDoctorModel;
import com.universe.android.model.CustomerModal;
import com.universe.android.resource.Login.login.LoginResponse;
import com.universe.android.resource.Login.surveyList.SurveyListResponse;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by gaurav.pandey on 20-02-2018.
 */

public class TeamSurveySelectionAdapter extends RecyclerView.Adapter<TeamSurveySelectionAdapter.TeamSurveyViewHolder> {

    private Context mContext;
    private ArrayList<SurveyListResponse.ResponseBean> mappingBeans;

    public TeamSurveySelectionAdapter(Context mContext, ArrayList<SurveyListResponse.ResponseBean> mappingBeans) {
        this.mContext = mContext;
        this.mappingBeans = mappingBeans;
    }

    @Override
    public TeamSurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.survey_list_row, parent, false);
        return new TeamSurveyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TeamSurveyViewHolder holder, int position) {
        SurveyListResponse.ResponseBean mappingBean = mappingBeans.get(position);
        if (Utility.validateString(mappingBean.getSurveyDetails().getExpiryDate())) {
            holder.tvExpiryDate.setText(mappingBean.getSurveyDetails().getExpiryDate());
        }
        String dateresult = mappingBean.getSurveyDetails().getExpiryDate();
        if (dateresult != null) {
            DateFormat writeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            ;
            DateFormat readFormat = new SimpleDateFormat(" dd-MMM-yyyy");
            Date date = null;
            try {
                date = writeFormat.parse(dateresult);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String formattedDate = "";
            if (date != null) {
                formattedDate = readFormat.format(date);
            }
            holder.tvExpiryDate.setText("End Date: " + formattedDate);


            holder.tvTitle.setText(mappingBean.getSurveyDetails().getTitle());
            holder.tvPending.setText(String.valueOf(mappingBean.getPending()));
            setUpListners(holder, position);
        }
        holder.tvTitle.setTypeface(FontClass.openSansRegular(mContext));
        holder.tvExpiryDate.setTypeface(FontClass.openSansLight(mContext));
        holder.tvPending.setTypeface(FontClass.openSansLight(mContext));
    }

    private void setUpListners(TeamSurveyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mappingBeans.size();
    }

    public class TeamSurveyViewHolder extends RecyclerView.ViewHolder {
        private final View item;
        private TextView tvTitle, tvExpiryDate, tvPending;

        public TeamSurveyViewHolder(View itemView) {
            super(itemView);
            item = itemView;
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvExpiryDate = itemView.findViewById(R.id.tvExpiryDate);
            tvPending = itemView.findViewById(R.id.tvPending);
        }
    }
}
