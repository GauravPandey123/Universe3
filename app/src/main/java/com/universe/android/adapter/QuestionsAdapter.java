package com.universe.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.universe.android.R;

/**
 * Created by gaurav.pandey on 08-02-2018.
 */

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder> {
    Context mContext;


    public QuestionsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public QuestionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.part_questionaire_category, parent, false);
        return new QuestionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class QuestionsViewHolder extends RecyclerView.ViewHolder {
        public QuestionsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
