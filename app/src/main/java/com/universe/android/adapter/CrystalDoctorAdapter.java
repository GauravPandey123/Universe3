package com.universe.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.helper.FontClass;
import com.universe.android.model.DataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav.pandey on 13-02-2018.
 */

public class CrystalDoctorAdapter extends SectionedRecyclerViewAdapter<RecyclerView.ViewHolder> {
    private List<DataModel> allData;
    private Context mContext;


    public CrystalDoctorAdapter(List<DataModel> allData, Context mContext) {
        this.allData = allData;
        this.mContext = mContext;
    }


    @Override
    public int getSectionCount() {
        return allData.size();
    }

    @Override
    public int getItemCount(int section) {
        return allData.get(section).getAllItemsInSection().size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, boolean header) {
        View v = null;
        if (header) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.crystal_doctor_header, parent, false);
            return new HeaderViewHolder(v);
        } else {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            return new CrystalDoctorItemViewHolder(v);
        }


    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int section) {
        String sectionName = allData.get(section).getHeaderTitle();
        HeaderViewHolder sectionViewHolder = (HeaderViewHolder) holder;
        sectionViewHolder.sectionTitle.setText(sectionName);
        sectionViewHolder.sectionTitle.setTypeface(FontClass.openSansBold(mContext));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int section, int relativePosition, int absolutePosition) {
        ArrayList<String> itemsInSection = allData.get(section).getAllItemsInSection();
        String itemName = itemsInSection.get(relativePosition);
        CrystalDoctorItemViewHolder itemViewHolder = (CrystalDoctorItemViewHolder) holder;
        itemViewHolder.textViewDetail.setText(itemName);
        itemViewHolder.textViewDetail.setTypeface(FontClass.openSansRegular(mContext));
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView sectionTitle;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            sectionTitle = itemView.findViewById(R.id.sectionTitle);
        }
    }

    public class CrystalDoctorItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewDetail;

        public CrystalDoctorItemViewHolder(View itemView) {
            super(itemView);
            textViewDetail = itemView.findViewById(R.id.itemTitle);
        }
    }
}
