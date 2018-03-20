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
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.StaeAndCropResponse;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by gaurav.pandey on 03-03-2018.
 */

public class CropAdapter extends RecyclerView.Adapter<CropAdapter.CropDoctorViewHolder> {

    private Context mContext;
    private ArrayList<StaeAndCropResponse.ResponseBean.CropBean> cropBeanArrayList;
    private StaeAndCropResponse.ResponseBean.CropBean cropBean;


    public CropAdapter(Context mContext, ArrayList<StaeAndCropResponse.ResponseBean.CropBean> cropBeanArrayList) {
        this.mContext = mContext;
        this.cropBeanArrayList = cropBeanArrayList;
    }

    @Override
    public CropDoctorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.part_team_selection_item, parent, false);
        return new CropDoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CropDoctorViewHolder holder, final int position) {
        cropBean = cropBeanArrayList.get(position);
        if (cropBean != null) {
            holder.textViewTeamSelection.setText(cropBean.getCROP_NAME());
            holder.checkboxTeamSelection.setText("Checkbox " + position);
            holder.checkboxTeamSelection.setChecked(cropBeanArrayList.get(position).isSelected());
            if (getSaveList().contains(cropBeanArrayList.get(position).get_id())) {
                holder.checkboxTeamSelection.setChecked(true);
            }
            holder.checkboxTeamSelection.setTag(cropBeanArrayList.get(position));
            holder.checkboxTeamSelection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox cb = (CheckBox) view;
                    if (cb.isChecked()) {
                        storeData(cropBeanArrayList.get(position).get_id());
                    } else {
                        removeData(cropBeanArrayList.get(position).get_id());

                    }
                }
            });
        }
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
        String json = Prefs.getStringPrefs(AppConstants.VillageData);
        if (!json.equals("")) {
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            arrayList = gson.fromJson(json, type);
            arrayList.add(data);
            String json1 = gson.toJson(arrayList);
            Prefs.putStringPrefs(AppConstants.VillageData, json1);

        } else {
            arrayList = new ArrayList<>();
            arrayList.add(data);
            String json1 = gson.toJson(arrayList);
            Prefs.putStringPrefs(AppConstants.VillageData, json1);
        }


    }

    @Override
    public int getItemCount() {
        return cropBeanArrayList.size();
    }

    public class CropDoctorViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewTeamSelection;
        private TextView textViewTeamSelection;
        private CheckBox checkboxTeamSelection;


        public CropDoctorViewHolder(View itemView) {
            super(itemView);
            imageViewTeamSelection = itemView.findViewById(R.id.imageViewTeamSelection);
            textViewTeamSelection = itemView.findViewById(R.id.textViewTeamSelection);
            checkboxTeamSelection = itemView.findViewById(R.id.checkboxTeamSelection);
        }
    }
}
