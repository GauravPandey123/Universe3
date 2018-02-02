package com.universe.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.helper.FontClass;

/**
 * Created by gaurav.pandey on 29-01-2018.
 */

public class QuestionCategoryItemAdapter extends RecyclerView.Adapter<QuestionCategoryItemAdapter.QuestionCategoryViewHolder> {
    private Context mContext;

    public QuestionCategoryItemAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public QuestionCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.part_question_category_item, parent, false);
        return new QuestionCategoryItemAdapter.QuestionCategoryViewHolder(view);

    }

    @Override
    public void onBindViewHolder(QuestionCategoryViewHolder holder, int position) {
        holder.textViewCategoryName.setTypeface(FontClass.openSansRegular(mContext));

    }

    @Override
    public int getItemCount() {
        return 10;
    }


    public class QuestionCategoryViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCategoryName;

        public QuestionCategoryViewHolder(View itemView) {
            super(itemView);
            textViewCategoryName = itemView.findViewById(R.id.textViewQuestionCategoryName);
        }
    }
}
