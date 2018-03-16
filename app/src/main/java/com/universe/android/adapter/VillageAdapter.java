package com.universe.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.universe.android.R;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.DistributorAndVillage.DistributorResponse;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by gaurav.pandey on 04-03-2018.
 */

public class VillageAdapter extends RecyclerView.Adapter<VillageAdapter.VillageViewHolder> {
    private ArrayList<DistributorResponse.ResponseBean.VillageBean> villageBeanArrayList;
    private Context mContext;
    private DistributorResponse.ResponseBean.VillageBean villageBean;


    public VillageAdapter(Context mContext, ArrayList<DistributorResponse.ResponseBean.VillageBean> villageBeanArrayList) {
        this.mContext = mContext;
        this.villageBeanArrayList = villageBeanArrayList;
    }

    @Override
    public VillageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.part_team_selection_item, parent, false);
        return new VillageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VillageViewHolder holder, final int position) {
        villageBean = villageBeanArrayList.get(position);
        holder.textViewTeamSelection.setText(villageBean.getVillage_name());
        holder.checkboxTeamSelection.setText("Checkbox " + position);
        holder.checkboxTeamSelection.setChecked(villageBeanArrayList.get(position).isSelected());
        if (getSaveList().contains(villageBeanArrayList.get(position).get_id())) {
            holder.checkboxTeamSelection.setChecked(true);
        }
        holder.checkboxTeamSelection.setTag(villageBeanArrayList.get(position));
        holder.checkboxTeamSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox cb = (CheckBox) view;
                if (cb.isChecked()) {
                    storeData(villageBeanArrayList.get(position).get_id());
                } else {
                    removeData(villageBeanArrayList.get(position).get_id());

                }
            }
        });

    }

    public void removeData(String data) {
        ArrayList<String> arrayList;
        Gson gson = new Gson();
        String json = Prefs.getStringPrefs(AppConstants.VillageData);
        if (!json.equals("")) {
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            arrayList = gson.fromJson(json, type);
            arrayList.remove(data);
            String json1 = gson.toJson(arrayList);
            Prefs.putStringPrefs(AppConstants.VillageData, json1);
        }


    }

    public ArrayList<String> getSaveList() {
        ArrayList<String> arrayList;
        Gson gson = new Gson();
        String json = Prefs.getStringPrefs(AppConstants.VillageData);
        if (!json.equals("")) {
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            arrayList = gson.fromJson(json, type);

        } else {
            arrayList = new ArrayList<>();
        }
        return arrayList;
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


    @Override
    public int getItemCount() {
        return villageBeanArrayList.size();
    }

    public class VillageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewTeamSelection;
        private TextView textViewTeamSelection;
        private CheckBox checkboxTeamSelection;


        public VillageViewHolder(View itemView) {
            super(itemView);
            imageViewTeamSelection = itemView.findViewById(R.id.imageViewTeamSelection);
            textViewTeamSelection = itemView.findViewById(R.id.textViewTeamSelection);
            checkboxTeamSelection = itemView.findViewById(R.id.checkboxTeamSelection);
        }
    }
}
