package com.universe.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.helper.FontClass;

import java.util.ArrayList;

/**
 * Created by gaurav.pandey on 01-02-2018.
 */

public class QuestionsSearchAdapter extends RecyclerView.Adapter<QuestionsSearchAdapter.QuestionsViewHolder> {

    private ArrayList<String> stringArrayList;
    private Context mContext;


    public QuestionsSearchAdapter(ArrayList<String> stringArrayList, Context mContext) {
        this.stringArrayList = stringArrayList;
        this.mContext = mContext;
    }

    @Override
    public QuestionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.part_questions_item, parent, false);
        return new QuestionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionsViewHolder holder, int position) {
        holder.textViewSearch.setTypeface(FontClass.openSansRegular(mContext));

    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public class QuestionsViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewSearch;

        public QuestionsViewHolder(View itemView) {
            super(itemView);
            textViewSearch = itemView.findViewById(R.id.textViewSearch);
        }
    }

}
