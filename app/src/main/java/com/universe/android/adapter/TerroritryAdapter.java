package com.universe.android.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gaurav.pandey on 03-03-2018.
 */

public class TerroritryAdapter extends RecyclerView.Adapter<TerroritryAdapter.TerroritryViewHolder> {



    @Override
    public TerroritryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(TerroritryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class TerroritryViewHolder extends RecyclerView.ViewHolder {
        public TerroritryViewHolder(View itemView) {
            super(itemView);
        }
    }
}
