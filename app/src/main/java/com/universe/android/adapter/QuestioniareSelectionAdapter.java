package com.universe.android.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;
import com.universe.android.R;
import com.universe.android.activity.FIlterActivity;
import com.universe.android.activity.QuestionaireActivity;
import com.universe.android.activity.QuestionsCategoryActivity;
import com.universe.android.activity.SearchCustomersActivity;
import com.universe.android.helper.FontClass;
import com.universe.android.model.ItemModel;

import java.util.List;


public class QuestioniareSelectionAdapter extends RecyclerView.Adapter<QuestioniareSelectionAdapter.ViewHolder> {

    private final List<ItemModel> data;
    private Context context;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    private QuestionCategoryItemAdapter questionCategoryItemAdapter;


    public QuestioniareSelectionAdapter(final List<ItemModel> data) {
        this.data = data;
        for (int i = 0; i < data.size(); i++) {
            expandState.append(i, false);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.recycler_view_list_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ItemModel item = data.get(position);
        holder.setIsRecyclable(false);
        holder.textView.setText(item.description);
        holder.textView.setTypeface(FontClass.openSansRegular(context));
        holder.expandableLayout.setInRecyclerView(true);
        holder.expandableLayout.setInterpolator(item.interpolator);
        holder.expandableLayout.setExpanded(expandState.get(position));
        holder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                createRotateAnimator(holder.buttonLayout, 0f, 180f).start();
                expandState.put(position, true);
            }

            @Override
            public void onPreClose() {
                createRotateAnimator(holder.buttonLayout, 180f, 0f).start();
                expandState.put(position, false);
            }
        });

        holder.buttonLayout.setRotation(expandState.get(position) ? 180f : 0f);
        holder.buttonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickButton(holder.expandableLayout);
            }
        });

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, QuestionaireActivity.class));
                // context.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        holder.recyclerviewCategory.setLayoutManager(new LinearLayoutManager(context));
        questionCategoryItemAdapter = new QuestionCategoryItemAdapter(context);
        holder.recyclerviewCategory.setAdapter(questionCategoryItemAdapter);
        questionCategoryItemAdapter.notifyDataSetChanged();
    }

    private void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public RelativeLayout buttonLayout;
        public LinearLayout lianearLayoutCrystalCustomers, lianearLayoutReailters;
        private RecyclerView recyclerviewCategory;

        public ExpandableLinearLayout expandableLayout;

        public ViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.textViewReatilers);
            buttonLayout = v.findViewById(R.id.button);
            expandableLayout = v.findViewById(R.id.expandableLayout);
//            lianearLayoutCrystalCustomers = v.findViewById(R.id.lianearLayoutCrystalCustomers);
//            lianearLayoutReailters = v.findViewById(R.id.lianearLayoutReailters);
            recyclerviewCategory = v.findViewById(R.id.recyclerviewCategory);

        }
    }

    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }
}