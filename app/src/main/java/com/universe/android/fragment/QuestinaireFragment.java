package com.universe.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.aakira.expandablelayout.Utils;
import com.universe.android.R;
import com.universe.android.activity.BaseActivity;
import com.universe.android.adapter.QuestioniareSelectionAdapter;
import com.universe.android.model.ItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gaurav Pandey on 27-01-2018.
 */

public class QuestinaireFragment extends BaseActivity {
    View view;
    RecyclerView recyclerViewQuestionire;
    QuestioniareSelectionAdapter questioniareSelectionAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questtionairefragment);
        initialization();
        setUpElements();
    }

    private void initialization() {
        recyclerViewQuestionire = findViewById(R.id.recyclerview_questionaire);
    }

    private void setUpElements() {
//        recyclerViewQuestionire.addItemDecoration(new DividerItemDecoration(getActivity()));
        recyclerViewQuestionire.setLayoutManager(new LinearLayoutManager(mContext));

        final List<ItemModel> data = new ArrayList<>();
        data.add(new ItemModel(
                "Total Retailers               10 Pending",
                Utils.createInterpolator(Utils.ACCELERATE_DECELERATE_INTERPOLATOR)));
        recyclerViewQuestionire.setAdapter(new QuestioniareSelectionAdapter(data));
    }
}
