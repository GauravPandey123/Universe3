package com.universe.android.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.model.StatusModel;

import java.util.ArrayList;

/**
 * Created by gaurav.pandey on 30-01-2018.
 */

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusViewHolder> {
    public ArrayList<StatusModel> stausList = new ArrayList<>();
    public ArrayList<StatusModel> selected_statusList = new ArrayList<>();
    public Context mContext;

    public StatusAdapter(Context mContext, ArrayList<StatusModel> stausList, ArrayList<StatusModel> selected_statusList) {
        this.mContext = mContext;
        this.stausList = stausList;
        this.selected_statusList = selected_statusList;
    }


    @Override
    public StatusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.part_status_card, parent, false);
        return new StatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StatusViewHolder holder, int position) {
        StatusModel movie = stausList.get(position);

        holder.textViewStatus.setText(movie.getStatusString());
        holder.imageViewStatus.setImageResource(movie.getImageViewId());

        if (selected_statusList.contains(stausList.get(position)))
            holder.cardViewStatus.setBackgroundColor(ContextCompat.getColor(mContext, R.color.list_item_selected_state));
        else
            holder.cardViewStatus.setBackgroundColor(ContextCompat.getColor(mContext, R.color.list_item_normal_state));

    }

    @Override
    public int getItemCount() {
        return stausList.size();
    }

    public class StatusViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewStatus;
        private ImageView imageViewStatus;
        private CardView cardViewStatus;

        public StatusViewHolder(View itemView) {
            super(itemView);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            imageViewStatus = itemView.findViewById(R.id.imageViewStatusL);
            cardViewStatus = itemView.findViewById(R.id.cardViewStatus);
        }
    }
}
