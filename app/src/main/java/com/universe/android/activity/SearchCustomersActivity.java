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

import java.util.ArrayList;
import java.util.List;

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
    private String surveyId,strTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_customers_activity);
        Intent intent=getIntent();
        if (intent!=null){

            surveyId= intent.getExtras().getString(AppConstants.SURVEYID);
            strTitle= intent.getExtras().getString(AppConstants.TYPE);

        }

        TextView title=(TextView)findViewById(R.id.textViewSuverDetail);
        title.setText(strTitle);
        initialization();
        setUpElements();
        setUpListeners();
        prepareList();
    }

    private void setUpListeners() {
        surveyDetailAdapter.setOnItemClickLister(new CustomerListAdapter.OnItemSelecteListener() {
            @Override
            public void onItemSelected(View v, int position) {
                Intent intent=new Intent(mContext,MapsActivity.class);
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
                Intent intent = new Intent(mContext, MapsActivity.class);
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
        editTextSearchcustomers.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();

                final ArrayList<CustomerModal> filteredList = new ArrayList<>();

             /*   for (int i = 0; i < stringArrayList.size(); i++) {

                    final String text = stringArrayList.get(i).toLowerCase();
                    if (text.contains(query)) {

                        filteredList.add(stringArrayList.get(i));
                    }
                }*/

                recyclerViewSearch.setLayoutManager(new LinearLayoutManager(mContext));
                surveyDetailAdapter = new CustomerListAdapter(mContext, filteredList);
                recyclerViewSearch.setAdapter(surveyDetailAdapter);
                surveyDetailAdapter.notifyDataSetChanged();  // data set changed
            }
        });
    }

    private void prepareList() {
        if (stringArrayList == null) stringArrayList = new ArrayList<>();
        stringArrayList.clear();
        Realm realm = Realm.getDefaultInstance();

        try {
            RealmResults<RealmCustomer> realmCustomers = realm.where(RealmCustomer.class).findAll();




            if (realmCustomers != null && realmCustomers.size() > 0) {
                for (int i = 0; i < realmCustomers.size(); i++) {
                    CustomerModal modal = new CustomerModal();
                    modal.setId(realmCustomers.get(i).get_id());


                    RealmAnswers realmAnswers1=realm.where(RealmAnswers.class).equalTo(AppConstants.CUSTOMERID,realmCustomers.get(i).get_id()).findFirst();

                    if (realmAnswers1!=null){
                        String status=realmAnswers1.getCd_Status();
                        modal.setStatus(status);
                    }else {
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
