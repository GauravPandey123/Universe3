package com.universe.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.helper.FontClass;
import com.universe.android.realmbean.RealmCustomer;
import com.universe.android.realmbean.RealmSurveys;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;

import io.realm.Realm;

/**
 * Created by gaurav.pandey on 14-02-2018.
 */

public class AddNewRetailors extends BaseActivity {
    private ImageView imageviewbackSearch;
    private TextView textViewHeader;
    private TextView textViewReatilers;
    private TextView retailerPending;
    private TextView textViewCrystalCustomers,textViewReatilersDate;
    private TextView textViewCustomersDetails, textViewNewCustomers, textViewNewRetailers;
    private TextView textViewReatlorsSelect, textViewCrystalSelect;
    private CardView cardView, cardViewReatailor;
    private String surveyId, strTitle,strExpiryDate,strStatus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_retailor_activity);
        Intent intent = getIntent();
        if (intent != null) {
            surveyId = intent.getExtras().getString(AppConstants.SURVEYID);
            strTitle = intent.getExtras().getString(AppConstants.TYPE);
            strExpiryDate = intent.getExtras().getString(AppConstants.EXPIRYDATE);
            strStatus = intent.getExtras().getString(AppConstants.STATUS);
        }



        initialization();
        setUpElemnts();
        setUpListeners();
        setupDetail();
    }

    private void setupDetail() {
        Realm realm = Realm.getDefaultInstance();
        try{
            RealmSurveys realmSurveys=realm.where(RealmSurveys.class).equalTo(AppConstants.ID,surveyId).findFirst();

            if (Utility.validateString(realmSurveys.getTitle()))
                textViewReatilers.setText(realmSurveys.getTitle());

            if (Utility.validateString(strExpiryDate))
                textViewReatilersDate.setText(strExpiryDate);

            if (Utility.validateString(strStatus))
                retailerPending.setText(strStatus);



        }catch (Exception e0){
            e0.printStackTrace();
            realm.close();
        }finally {
            if(!realm.isClosed()){
                realm.close();
            }

        }
    }

    private void setUpListeners() {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SearchCustomersActivity.class);
                intent.putExtra(AppConstants.TYPE,strTitle);
                intent.putExtra(AppConstants.SURVEYID,surveyId);
                Prefs.putStringPrefs(AppConstants.CUSTOMER,AppConstants.CrystalCustomer);
                intent.putExtra(AppConstants.CUSTOMER,AppConstants.CrystalCustomer);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        cardViewReatailor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SearchCustomersActivity.class);
                intent.putExtra(AppConstants.TYPE,strTitle);
                intent.putExtra(AppConstants.SURVEYID,surveyId);
                Prefs.putStringPrefs(AppConstants.CUSTOMER,AppConstants.NEW);
                intent.putExtra(AppConstants.CUSTOMER,AppConstants.NEW);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    private void setUpElemnts() {
        imageviewbackSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initialization() {
        imageviewbackSearch = findViewById(R.id.imageviewbackSearch);
        textViewReatilersDate=findViewById(R.id.textViewReatilersDate);
        textViewHeader = findViewById(R.id.textViewHeader);
        textViewReatilers = findViewById(R.id.textViewReatilers);
        retailerPending = findViewById(R.id.retailerPending);
        textViewCrystalCustomers = findViewById(R.id.textViewCrystalCustomers);
        textViewCustomersDetails = findViewById(R.id.textViewCustomersDetails);
        textViewNewCustomers = findViewById(R.id.textViewNewCustomers);
        textViewNewRetailers = findViewById(R.id.textViewNewRetailers);
        cardView = findViewById(R.id.cardView);
        cardViewReatailor = findViewById(R.id.cardViewReatailor);
        textViewReatlorsSelect = findViewById(R.id.textViewReatlorsSelect);
        textViewCrystalSelect = findViewById(R.id.textViewCrystalSelect);

        textViewNewRetailers.setTypeface(FontClass.openSansLight(mContext));
        textViewNewCustomers.setTypeface(FontClass.openSansRegular(mContext));
        textViewHeader.setTypeface(FontClass.openSansRegular(mContext));
        textViewReatilers.setTypeface(FontClass.openSansRegular(mContext));
        retailerPending.setTypeface(FontClass.openSansRegular(mContext));
        textViewCrystalCustomers.setTypeface(FontClass.openSansRegular(mContext));
        textViewCustomersDetails.setTypeface(FontClass.openSansRegular(mContext));
        textViewReatlorsSelect.setTypeface(FontClass.openSansRegular(mContext));
        textViewCrystalSelect.setTypeface(FontClass.openSansRegular(mContext));

        textViewHeader.setText(strTitle);
    }
}
