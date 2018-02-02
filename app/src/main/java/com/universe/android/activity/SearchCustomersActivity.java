package com.universe.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.adapter.SurveyDetailAdapter;
import com.universe.android.fragment.SurveySelectionFragment;
import com.universe.android.helper.FontClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav.pandey on 28-01-2018.
 */

public class SearchCustomersActivity extends BaseActivity {

    private EditText editTextSearchcustomers;
    private SwipeRefreshLayout swipeRefreshLayoutSearch;
    private RecyclerView recyclerViewSearch;
    private ImageView imageViewback;
    private TextView textViewSuverDetail;

    private SurveyDetailAdapter surveyDetailAdapter;

    private ArrayList<String> stringArrayList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_customers_activity);
        initialization();
        setUpElements();
        setUpListeners();
    }

    private void setUpListeners() {
        surveyDetailAdapter.setOnItemClickLister(new SurveyDetailAdapter.OnItemSelecteListener() {
            @Override
            public void onItemSelected(View v, int position) {
                Intent intent=new Intent(mContext,MapsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

            }
        });
    }

    private void setUpElements() {
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initialization() {
        stringArrayList = new ArrayList<>();
        editTextSearchcustomers = findViewById(R.id.searchcustomers);
        swipeRefreshLayoutSearch = findViewById(R.id.swiperefreshLayoutSearch);
        recyclerViewSearch = findViewById(R.id.recyclerviewsearch);
        imageViewback = findViewById(R.id.imageviewbacSearch);
        textViewSuverDetail = findViewById(R.id.textViewSuverDetail);

        editTextSearchcustomers.setTypeface(FontClass.openSansLight(mContext));
        textViewSuverDetail.setTypeface(FontClass.openSemiBold(mContext));

        recyclerViewSearch.setHasFixedSize(true);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(this));

        searchList();  // in this method, Create a list of items.

        // call the adapter with argument list of items and context.
        surveyDetailAdapter = new SurveyDetailAdapter(mContext, stringArrayList);
        recyclerViewSearch.setAdapter(surveyDetailAdapter);

        addTextListener();

    }

    public void addTextListener() {
        editTextSearchcustomers.addTextChangedListener(new TextWatcher() {
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

                recyclerViewSearch.setLayoutManager(new LinearLayoutManager(mContext));
                surveyDetailAdapter = new SurveyDetailAdapter(mContext, filteredList);
                recyclerViewSearch.setAdapter(surveyDetailAdapter);
                surveyDetailAdapter.notifyDataSetChanged();  // data set changed
            }
        });
    }

    private void searchList() {
        stringArrayList.add("Agro Inputs Corporation");
        stringArrayList.add("Aj AgroChemicals");
        stringArrayList.add("Blossom AgriCore");
        stringArrayList.add("Chemical India");
        stringArrayList.add("Duncan India");
        stringArrayList.add("Gange Pestiside");
        stringArrayList.add("Agro Inputs Corporation");
        stringArrayList.add("Aj AgroChemicals");
        stringArrayList.add("Blossom AgriCore");
        stringArrayList.add("Chemical India");
        stringArrayList.add("Duncan India");
        stringArrayList.add("Gange Pestiside");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
