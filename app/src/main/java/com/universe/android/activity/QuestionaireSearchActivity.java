package com.universe.android.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import com.universe.android.R;
import com.universe.android.adapter.QuestionsSearchAdapter;
import com.universe.android.adapter.SurveyDetailAdapter;
import com.universe.android.helper.FontClass;

import java.util.ArrayList;

/**
 * Created by gaurav.pandey on 01-02-2018.
 */

public class QuestionaireSearchActivity extends BaseActivity {
    //declare the View here
    private ImageView imageViewSearch;
    private SearchView searchViewQuestions;
    private EditText editTextSearchQuestions;
    private ImageView iv_icon_cancel_search;
    private SwipeRefreshLayout swipeRefreshLayoutSearch;

    //declare the variable here
    private ArrayList<String> stringArrayList;
    private QuestionsSearchAdapter questionsSearchAdapter;
    private RecyclerView recyclerViewQuestionList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionaire_search_activity);
        initialization();
        setUpElements();
        setUpListeners();

    }

    private void setUpListeners() {
    }

    private void setUpElements() {
    }

    public void addTextListener() {
        editTextSearchQuestions.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();

                final ArrayList<String> filteredList = new ArrayList<>();

                for (int i = 0; i < stringArrayList.size(); i++) {

                    final String text = stringArrayList.get(i).toLowerCase();
                    if (text.contains(query)) {

                        filteredList.add(stringArrayList.get(i));
                    }
                }

                recyclerViewQuestionList.setLayoutManager(new LinearLayoutManager(mContext));
                questionsSearchAdapter = new QuestionsSearchAdapter(filteredList, mContext);
                recyclerViewQuestionList.setAdapter(questionsSearchAdapter);
                questionsSearchAdapter.notifyDataSetChanged();  // data set changed
            }
        });
    }


    private void searchQuestions() {
        stringArrayList.add(" Q-1 What is your business bifurcation among categories ");
        stringArrayList.add(" Q-1 What is your business bifurcation among categories ");
        stringArrayList.add(" Q-1 What is your business bifurcation among categories ");
        stringArrayList.add(" Q-1 What is your business bifurcation among categories ");
        stringArrayList.add(" Q-1 What is your business bifurcation among categories ");
        stringArrayList.add(" Q-1 What is your business bifurcation among categories ");
        stringArrayList.add(" Q-1 What is your business bifurcation among categories ");
        stringArrayList.add(" Q-1 What is your business bifurcation among categories ");
        stringArrayList.add(" Q-1 What is your business bifurcation among categories ");
        stringArrayList.add(" Q-1 What is your business bifurcation among categories ");
        stringArrayList.add(" Q-1 What is your business bifurcation among categories ");
        stringArrayList.add(" Q-1 What is your business bifurcation among categories ");
    }

    private void initialization() {
        stringArrayList = new ArrayList<>();

        imageViewSearch = findViewById(R.id.iv_search_icon);
        editTextSearchQuestions = findViewById(R.id.et_search_questions);
        searchViewQuestions = findViewById(R.id.searchViewQuestion);
        iv_icon_cancel_search = findViewById(R.id.iv_icon_cancel_search);
        swipeRefreshLayoutSearch = findViewById(R.id.swiperefreshLayoutSearch);
        recyclerViewQuestionList = findViewById(R.id.rv_questions_list);
        editTextSearchQuestions.setTypeface(FontClass.openSansRegular(mContext));


        recyclerViewQuestionList.setHasFixedSize(true);
        recyclerViewQuestionList.setLayoutManager(new LinearLayoutManager(this));
        searchQuestions();  // in this method, Create a list of items.
        // call the adapter with argument list of items and context.
        questionsSearchAdapter = new QuestionsSearchAdapter(stringArrayList, mContext);
        recyclerViewQuestionList.setAdapter(questionsSearchAdapter);
        addTextListener();


    }
}
