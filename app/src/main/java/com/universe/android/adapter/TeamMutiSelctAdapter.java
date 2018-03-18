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

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.universe.android.R;
import com.universe.android.model.Number;
import com.universe.android.resource.Login.login.LoginResponse;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by gaurav.pandey on 05-03-2018.
 */

public class TeamMutiSelctAdapter extends RecyclerView.Adapter<TeamMutiSelctAdapter.TeamMultiSelectViewHolder> {
    private ArrayList<LoginResponse.ResponseBean.LoginDetailsBean> numberArrayList;
    private Context mContext;
    private boolean isChecked = false;


    public TeamMutiSelctAdapter(Context mContext, ArrayList<LoginResponse.ResponseBean.LoginDetailsBean> numbers) {
        this.mContext = mContext;
        this.numberArrayList = numbers;
    }

    @Override
    public TeamMultiSelectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.part_team_selection_item, parent, false);
        return new TeamMultiSelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TeamMultiSelectViewHolder holder, final int position) {
        holder.checkboxTeamSelection.setText("Checkbox " + position);

        holder.checkboxTeamSelection.setChecked(numberArrayList.get(position).isSelected());
        if (getSaveList().contains(numberArrayList.get(position).getMember().get_id())) {
            holder.checkboxTeamSelection.setChecked(true);
        }

        holder.checkboxTeamSelection.setTag(numberArrayList.get(position));
        holder.checkboxTeamSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox cb = (CheckBox) view;
                if (cb.isChecked()) {
                    storeData(numberArrayList.get(position).getMember().get_id());

                } else {
                    removeData(numberArrayList.get(position).getMember().get_id());

                }

            }
        });


        holder.textViewTeamSelection.setText(numberArrayList.get(position).getMember().getName());


    }

    public void storeData(String data) {
        ArrayList<String> arrayList;
        Gson gson = new Gson();
        String json = Prefs.getStringPrefs(AppConstants.FilterData);
        if (!json.equals("")) {
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            arrayList = gson.fromJson(json, type);
            arrayList.add(data);
            String json1 = gson.toJson(arrayList);
            Prefs.putStringPrefs(AppConstants.FilterData, json1);

        } else {
            arrayList = new ArrayList<>();
            arrayList.add(data);
            String json1 = gson.toJson(arrayList);
            Prefs.putStringPrefs(AppConstants.FilterData, json1);
        }


    }

    public void removeData(String data) {
        ArrayList<String> arrayList;
        Gson gson = new Gson();
        String json = Prefs.getStringPrefs(AppConstants.FilterData);
        if (!json.equals("")) {
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            arrayList = gson.fromJson(json, type);
            arrayList.remove(data);
            String json1 = gson.toJson(arrayList);
            Prefs.putStringPrefs(AppConstants.FilterData, json1);
        }


    }

    public ArrayList<String> getSaveList() {
        ArrayList<String> arrayList;
        Gson gson = new Gson();
        String json = Prefs.getStringPrefs(AppConstants.FilterData);
        if (!json.equals("")) {
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            arrayList = gson.fromJson(json, type);

        } else {
            arrayList = new ArrayList<>();
        }
        return arrayList;
    }


    @Override
    public int getItemCount() {
        return numberArrayList.size();
    }

    public class TeamMultiSelectViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewTeamSelection;
        private TextView textViewTeamSelection;
        private CheckBox checkboxTeamSelection;

        public TeamMultiSelectViewHolder(View itemView) {
            super(itemView);
            imageViewTeamSelection = itemView.findViewById(R.id.imageViewTeamSelection);
            textViewTeamSelection = itemView.findViewById(R.id.textViewTeamSelection);
            checkboxTeamSelection = itemView.findViewById(R.id.checkboxTeamSelection);
        }
    }
}
