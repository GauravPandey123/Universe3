package com.universe.android.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;
import com.universe.android.R;
import com.universe.android.model.ItemModel;

import java.util.List;

/**
 * Created by gaurav.pandey on 29-01-2018.
 */

public class QuestionsCategoryAdapter extends RecyclerView.Adapter<QuestionsCategoryAdapter.QuestionsViewHolder> {
    private Context mContext;
    private final List<ItemModel> data;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    private QuestionCategoryItemAdapter questionCategoryItemAdapter;

    public QuestionsCategoryAdapter(Context mContext, List<ItemModel> data) {
        this.mContext = mContext;
        this.data = data;
        for (int i = 0; i < data.size(); i++) {
            expandState.append(i, false);
        }
    }

    @Override
    public QuestionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.part_question_category, parent, false);
        return new QuestionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final QuestionsViewHolder holder, final int position) {
        final ItemModel item = data.get(position);
        holder.setIsRecyclable(false);
        holder.textViewCategory.setText(item.description);
        holder.expandableLayoutCategory.setInRecyclerView(true);
        holder.expandableLayoutCategory.setInterpolator(item.interpolator);
        holder.expandableLayoutCategory.setExpanded(expandState.get(position));
        holder.expandableLayoutCategory.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                createRotateAnimator(holder.relativeLayoutButton, 0f, 180f).start();
                expandState.put(position, true);
            }

            @Override
            public void onPreClose() {
                createRotateAnimator(holder.relativeLayoutButton, 180f, 0f).start();
                expandState.put(position, false);
            }
        });

        holder.expandableLayoutCategory.setRotation(expandState.get(position) ? 180f : 0f);
        holder.expandableLayoutCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickButton(holder.expandableLayoutCategory);


            }
        });
//        holder.recyclerviewCategory.setLayoutManager(new LinearLayoutManager(mContext));
//        questionCategoryItemAdapter = new QuestionCategoryItemAdapter(mContext);
//        holder.recyclerviewCategory.setAdapter(questionCategoryItemAdapter);
//        questionCategoryItemAdapter.notifyDataSetChanged();



    }


    private void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class QuestionsViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout relativeLayoutButton, relativeLayoutCategory;
        private TextView textViewCategory, textViewToatalQuestions;
        private ExpandableLinearLayout expandableLayoutCategory;
        private RecyclerView recyclerviewCategory;

        public QuestionsViewHolder(View itemView) {
            super(itemView);
            relativeLayoutButton = itemView.findViewById(R.id.relativeLayoutButton);
            relativeLayoutCategory = itemView.findViewById(R.id.relativeLayoutCategory);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);
            textViewToatalQuestions = itemView.findViewById(R.id.textViewToatalQuestions);
            expandableLayoutCategory = itemView.findViewById(R.id.expandableLayoutCategory);
           // recyclerviewCategory = itemView.findViewById(R.id.recyclerviewCategory);
        }
    }

    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }

}
