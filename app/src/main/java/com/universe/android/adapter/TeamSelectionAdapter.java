package com.universe.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.model.Number;

import java.util.ArrayList;

/**
 * Created by gaurav.pandey on 21-02-2018.
 */


public class TeamSelectionAdapter extends RecyclerView.Adapter<TeamSelectionAdapter.TeamSelectViewHolder> {
    private ArrayList<Number> numberArrayList;
    private Context mContext;


    public TeamSelectionAdapter(ArrayList<Number> numbers, Context mContext) {
        this.numberArrayList = numbers;
        this.mContext=mContext;
    }


    @Override
    public TeamSelectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.part_team_selection_item, parent, false);
        return new TeamSelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TeamSelectViewHolder holder, int position) {
        holder.checkboxTeamSelection.setOnCheckedChangeListener(null);
        holder.checkboxTeamSelection.setChecked(numberArrayList.get(position).isSelected());
        holder.checkboxTeamSelection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                numberArrayList.get(holder.getAdapterPosition()).setSelected(isChecked);
            }
        });
        holder.textViewTeamSelection.setText(numberArrayList.get(position).getTextONEs());

    }

    @Override
    public int getItemCount() {
        return numberArrayList.size();
    }

    public class TeamSelectViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewTeamSelection;
        private TextView textViewTeamSelection;
        private CheckBox checkboxTeamSelection;

        public TeamSelectViewHolder(View itemView) {
            super(itemView);
            imageViewTeamSelection = itemView.findViewById(R.id.imageViewTeamSelection);
            textViewTeamSelection = itemView.findViewById(R.id.textViewTeamSelection);
            checkboxTeamSelection = itemView.findViewById(R.id.checkboxTeamSelection);
        }
    }


}
