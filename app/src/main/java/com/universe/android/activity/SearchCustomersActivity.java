package com.universe.android.activity;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.util.Util;
import com.universe.android.R;
import com.universe.android.adapter.CustomerListAdapter;
import com.universe.android.adapter.SurveyDetailAdapter;
import com.universe.android.fragment.SurveySelectionFragment;
import com.universe.android.helper.FontClass;
import com.universe.android.helper.RecyclerTouchListener;
import com.universe.android.model.AnswersModal;
import com.universe.android.model.CustomerModal;
import com.universe.android.realmbean.RealmAnswers;
import com.universe.android.realmbean.RealmCustomer;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Utility;
import com.universe.android.workflows.WorkFlowsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.editsoft.api.util.App;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by gaurav.pandey on 28-01-2018.
 */

public class SearchCustomersActivity extends BaseActivity {

    private EditText editTextSearchcustomers;
    private SwipeRefreshLayout swipeRefreshLayoutSearch;
    private RecyclerView recyclerViewSearch;
    private ImageView imageViewback;
    private TextView textViewSuverDetail;
    private CustomerListAdapter surveyDetailAdapter;

    private ArrayList<CustomerModal> stringArrayList;
    private ArrayList<CustomerModal> arrSearlist;
    private String surveyId, strTitle;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_customers_activity);
        Intent intent = getIntent();
        if (intent != null) {
            surveyId = intent.getExtras().getString(AppConstants.SURVEYID);
            strTitle = intent.getExtras().getString(AppConstants.TYPE);
        }

        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        TextView title=(TextView)findViewById(R.id.textViewSuverDetail);

        title.setText(strTitle);
        initialization();
        setUpElements();
        setUpListeners();
        prepareList();
        setupSearchView(searchView, stringArrayList);

    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    private void setupSearchView(SearchView searchView, final List<CustomerModal> responseList) {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        if (searchView != null) {
            searchView.setSearchableInfo(searchableInfo);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    filter(newText, responseList);
                    return false;
                }
            });
        }

    }

    /**
     * Filter.
     *
     * @param charText the char text
     */
    private void filter(String charText, List<CustomerModal> responseList) {
        if (arrSearlist != null && responseList != null) {
            charText = charText.toLowerCase(Locale.getDefault());
            arrSearlist.clear();
            if (charText.length() == 0) {
                arrSearlist.addAll(responseList);
            } else {
                for (CustomerModal wp : responseList) {
                    if (wp.getTitle() != null) {
                        if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                            arrSearlist.add(wp);
                        }
                    }
                }
            }
        }

        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(mContext));
        surveyDetailAdapter = new CustomerListAdapter(mContext, arrSearlist);
        recyclerViewSearch.setAdapter(surveyDetailAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        prepareList();
    }

    private void setUpListeners() {
        surveyDetailAdapter.setOnItemClickLister(new CustomerListAdapter.OnItemSelecteListener() {
            @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
            @Override
            public void onItemSelected(View v, int position) {
                Intent intent=new Intent(mContext,WorkFlowsActivity.class);


                if (!Utility.validateString(stringArrayList.get(position).getStatus()) || stringArrayList.get(position).getStatus().equalsIgnoreCase("5")) {

                    intent = new Intent(mContext, MapsOneActivity.class);



                }
                intent.putExtra(AppConstants.STR_TITLE,strTitle);
                intent.putExtra(AppConstants.SURVEYID,surveyId);
                intent.putExtra(AppConstants.CUSTOMERID,stringArrayList.get(position).getId());

                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

            }
        });
        recyclerViewSearch.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerViewSearch, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Intent intent=new Intent(mContext,WorkFlowsActivity.class);

                if (!Utility.validateString(stringArrayList.get(position).getStatus()) || stringArrayList.get(position).getStatus().equalsIgnoreCase("5")) {
                    intent = new Intent(mContext, MapsOneActivity.class);
                }
                intent.putExtra(AppConstants.STR_TITLE,strTitle);
                intent.putExtra(AppConstants.SURVEYID,surveyId);
                intent.putExtra(AppConstants.CUSTOMERID,stringArrayList.get(position).getId());

                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
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


        // call the adapter with argument list of items and context.
        surveyDetailAdapter = new CustomerListAdapter(mContext, stringArrayList);
        recyclerViewSearch.setAdapter(surveyDetailAdapter);

        addTextListener();

    }

    public void addTextListener() {


//        editTextSearchcustomers.addTextChangedListener(new TextWatcher() {
//            public void afterTextChanged(Editable s) {
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            public void onTextChanged(CharSequence query, int start, int before, int count) {

////                query = query.toString().toLowerCase();
////
////                final ArrayList<CustomerModal> filteredList = new ArrayList<>();
////
////                for (int i = 0; i < stringArrayList.size(); i++) {
////                    stringArrayList.clear();
////                    final CustomerModal text = filteredList.get(i);
////                    if (text.getName().equalsIgnoreCase("query")) {
////                        filteredList.add(stringArrayList.get(i));
////                    }
////                }
////

//            }
//        });
//    }







    }

    private void prepareList() {
        if (stringArrayList == null) stringArrayList = new ArrayList<>();
        stringArrayList.clear();
        if (arrSearlist == null) arrSearlist = new ArrayList<>();
        arrSearlist.clear();
        Realm realm = Realm.getDefaultInstance();

        try {
            RealmResults<RealmCustomer> realmCustomers = realm.where(RealmCustomer.class)/*.equalTo(AppConstants.SURVEYID,surveyId)*/.findAll();
            if (realmCustomers != null && realmCustomers.size() > 0) {
                for (int i = 0; i < realmCustomers.size(); i++) {
                    CustomerModal modal = new CustomerModal();
                    modal.setId(realmCustomers.get(i).get_id());
                    RealmAnswers realmAnswers1 = realm.where(RealmAnswers.class).equalTo(AppConstants.CUSTOMERID, realmCustomers.get(i).get_id()).findFirst();
                    if (realmAnswers1 != null) {
                        String status = realmAnswers1.getCd_Status();
                        modal.setStatus(status);
                    } else {
                        modal.setStatus("");
                    }
                    modal.setTitle(realmCustomers.get(i).getName());
                    modal.setState(realmCustomers.get(i).getState());
                    modal.setTerritory(realmCustomers.get(i).getTerritory());
                    modal.setPincode(realmCustomers.get(i).getPincode());
                    modal.setContactNo(realmCustomers.get(i).getContactNo());
                    //modal.setStatus(type);
                    modal.setDate(AppConstants.format2.format(realmCustomers.get(i).getCreatedAt()));
                    stringArrayList.add(modal);
                }
            }
        } catch (Exception e) {
            realm.close();
            e.printStackTrace();
        } finally {
            realm.close();
        }

        if (surveyDetailAdapter != null) {
            surveyDetailAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
