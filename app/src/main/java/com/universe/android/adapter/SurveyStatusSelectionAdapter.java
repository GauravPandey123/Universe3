package com.universe.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.helper.FontClass;
import com.universe.android.model.SurveyReportModel;

import java.util.ArrayList;

/**
 * Created by gaurav.pandey on 21-03-2018.
 */

public class SurveyStatusSelectionAdapter extends RecyclerView.Adapter<SurveyStatusSelectionAdapter.SurveySelctionViewHolder> {
    private ArrayList<SurveyReportModel> surveyReportModels;
    private Context mContext;

    private SurveyStatusSelectionAdapter(Context mContext, ArrayList<SurveyReportModel> surveyReportModels) {
        this.surveyReportModels = surveyReportModels;
        this.mContext = mContext;

    }

    @Override
    public SurveySelctionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.part_team_selection_item, parent, false);
        return new SurveySelctionViewHolder(view);

    }

    @Override
    public void onBindViewHolder(SurveySelctionViewHolder holder, int position) {
        SurveyReportModel surveyReportModel = surveyReportModels.get(position);
        holder.checkBox.setVisibility(View.GONE);
        holder.textViewTeamSelection.setTypeface(FontClass.openSansRegular(mContext));
        holder.textViewTeamSelection.setText(surveyReportModel.getTitleString());
    }

    @Override
    public int getItemCount() {
        return surveyReportModels.size();
    }

    public class SurveySelctionViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTeamSelection;
        private CheckBox checkBox;

        public SurveySelctionViewHolder(View itemView) {
            super(itemView);
            textViewTeamSelection = itemView.findViewById(R.id.textViewTeamSelection);
            checkBox = itemView.findViewById(R.id.checkboxTeamSelection);

        }
    }
}
