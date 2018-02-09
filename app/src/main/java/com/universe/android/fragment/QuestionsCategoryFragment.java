package com.universe.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.activity.BaseActivity;
import com.universe.android.adapter.QuestionsAdapter;
import com.universe.android.adapter.QuestionsSearchAdapter;
import com.universe.android.helper.FontClass;

/**
 * Created by gaurav.pandey on 25-01-2018.
 */

public class QuestionsCategoryFragment extends BaseFragment {
    private View view;
    private RecyclerView recyclerViewQuestionsCategory;
    private SwipeRefreshLayout swipeRefreshLayoutCategory;
    private QuestionsAdapter questionsAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.questions_category_fragment, container, false);
        initialization();
        setUpElements();
        setUpListeners();
        return view;
    }

    private void setUpListeners() {
    }

    private void setUpElements() {
        recyclerViewQuestionsCategory.setLayoutManager(new LinearLayoutManager(mContext));
        questionsAdapter = new QuestionsAdapter(mContext);
        recyclerViewQuestionsCategory.setAdapter(questionsAdapter);
//        questionsAdapter.notifyDataSetChanged();
    }

    private void initialization() {
        recyclerViewQuestionsCategory = view.findViewById(R.id.recyclerViewQuestionsCategory);
        swipeRefreshLayoutCategory = view.findViewById(R.id.swipeRefreshLayoutQuestionsCategory);
        mContext = getActivity();
        mActivity = (BaseActivity) mContext;
    }
}
