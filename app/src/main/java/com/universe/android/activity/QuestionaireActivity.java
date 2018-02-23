package com.universe.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.adapter.CustomExpandableListAdapter;
import com.universe.android.fragment.QuestionsCategoryFragment;
import com.universe.android.helper.FontClass;
import com.universe.android.helper.PagerSlidingTabStrip;
import com.universe.android.model.CategoryModal;
import com.universe.android.model.Questions;
import com.universe.android.realmbean.RealmCategory;
import com.universe.android.realmbean.RealmCustomer;
import com.universe.android.realmbean.RealmQuestion;
import com.universe.android.realmbean.RealmSurveys;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class QuestionaireActivity extends BaseActivity {
    private ViewPager mViewPager;
    private TextView textViewHeader, textViewRetailersNameMap, textViewMobileNoMap, textViewStatusMap;
    private ImageView imageViewSearchBack;
    private EditText editTextSearch;
    private PagerSlidingTabStrip pagerSlidingTabStrip;
    private QuestionaireTabsFilterAdapter questionaireTabsFilterAdapter;
    private String title,surveyId,customerId;
    private ArrayList<CategoryModal> categoryModals=new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionaire_activity);
        initialization();
        prepareCategory();
        setUpElements();
        setUpListeners();
        setupDetail();

    }
    private void setupDetail() {
        Realm realm = Realm.getDefaultInstance();
        try{
            RealmCustomer realmCustomer=realm.where(RealmCustomer.class).equalTo(AppConstants.ID,customerId).findFirst();

            if (Utility.validateString(realmCustomer.getName()))
                textViewRetailersNameMap.setText(realmCustomer.getName());

            textViewMobileNoMap.setText(realmCustomer.getContactNo()+" | "+
                    realmCustomer.getTerritory()+" | "+realmCustomer.getState()+"  \n"+
                    "Pincode - "+realmCustomer.getPincode());


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
        imageViewSearchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        editTextSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,QuestionaireSearchActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

            }
        });
    }

    private void setUpElements() {
        pagerSlidingTabStrip.setTabBackground(R.color.grey);
        pagerSlidingTabStrip.setIndicatorHeight(dptoPixel(4));
        pagerSlidingTabStrip.setIndicatorColor(getResources().getColor(R.color.app_theme_color));
        pagerSlidingTabStrip.setShouldExpand(true);
        pagerSlidingTabStrip.setTextSize((int) getResources().getDimension(R.dimen.text_size_medium));
        pagerSlidingTabStrip.setDeactivateTextColor(getResources().getColor(R.color.white));
        pagerSlidingTabStrip.setActivateTextColor(getResources().getColor(R.color.white));
        mViewPager.setOffscreenPageLimit(4);
        questionaireTabsFilterAdapter = new QuestionaireTabsFilterAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(questionaireTabsFilterAdapter);
        pagerSlidingTabStrip.setViewPager(mViewPager);
        pagerSlidingTabStrip.setTypeface(FontClass.openSansRegular(mContext));

    }

    // code to set adapter on feedlist view pager
    public class  QuestionaireTabsFilterAdapter extends FragmentPagerAdapter {

        private QuestionaireTabsFilterAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return new QuestionsCategoryFragment().newInstance(surveyId,categoryModals.get(position).getId(),customerId,position);

        }

        @Override
        public int getCount() {
            return categoryModals.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return categoryModals.get(position).getCategoryName();
        }


    }


    public int dptoPixel(int dp) {
        //code to convert dp to pixel
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;
        return (int) Math.ceil(dp * logicalDensity);
    }

    private void initialization() {
        mViewPager = findViewById(R.id.questionaireViewPager);
        textViewHeader = findViewById(R.id.textViewHeader);
        textViewRetailersNameMap = findViewById(R.id.textViewRetailersNameMap);
        textViewMobileNoMap = findViewById(R.id.textViewMobileNoMap);
        textViewStatusMap = findViewById(R.id.textViewStatusMap);
        imageViewSearchBack = findViewById(R.id.imageviewbackSearch);
        editTextSearch =findViewById(R.id.searchcustomers);
        pagerSlidingTabStrip=findViewById(R.id.pagerSlidingStrip);
        textViewHeader.setTypeface(FontClass.openSemiBold(mContext));
        textViewRetailersNameMap.setTypeface(FontClass.openSansRegular(mContext));
        textViewMobileNoMap.setTypeface(FontClass.openSansRegular(mContext));
        textViewStatusMap.setTypeface(FontClass.openSansRegular(mContext));
        Intent intent=getIntent();
        if (intent!=null){
            title= intent.getExtras().getString(AppConstants.STR_TITLE);
            surveyId= intent.getExtras().getString(AppConstants.SURVEYID);
            customerId= intent.getExtras().getString(AppConstants.CUSTOMERID);
        }

        textViewHeader.setText(title);

    }

    private void prepareCategory(){




        Realm realm = Realm.getDefaultInstance();
        RealmSurveys realmSurveys=realm.where(RealmSurveys.class).equalTo(AppConstants.ID,surveyId).findFirst();

        try {
            JSONArray jsonArray = new JSONArray(realmSurveys.getCategoryId());
            for (int l=0;l<jsonArray.length();l++){
                RealmCategory realmCategoryDetails;

                realmCategoryDetails = realm.where(RealmCategory.class).equalTo(AppConstants.ID,jsonArray.get(l).toString())/*.equalTo(AppConstants.SURVEYID,surveyId)*/.findFirst();
                if (realmCategoryDetails != null) {


                    CategoryModal categoryModal = new CategoryModal();
                    categoryModal.setId(realmCategoryDetails.getId());
                    categoryModal.setCategoryName(realmCategoryDetails.getCategoryName());
                    categoryModal.setStatus(realmCategoryDetails.getStatus());
                    try {
                        RealmResults<RealmQuestion> realmQuestions=realm.where(RealmQuestion.class).equalTo(AppConstants.CATEGORYID,realmCategoryDetails.getId()).equalTo(AppConstants.SURVEYID,surveyId).findAll();

                        //  if (realmQuestions != null && realmQuestions.size() > 0) {
                        //         String categoryId = realmCategoryDetails.get(k).getId();
                        ArrayList<Questions> questionsArrayList = new ArrayList<>();

                        for (int i=0;i<realmQuestions.size();i++){


                            Questions questions =new Questions();
                            questions.setQuestionId(realmQuestions.get(i).getId());
                            questions.setTitle((i+1)+". "+realmQuestions.get(i).getTitle());
                            questions.setStatus(realmQuestions.get(i).getRequired());
                            questionsArrayList.add(questions);

                        }
                        categoryModal.setQuestionCount(questionsArrayList.size()+"");
                        categoryModal.setQuestions(questionsArrayList);

                        categoryModals.add(categoryModal);

                        //   }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }







                }else{

                }





            }




        }catch (Exception e0){

            e0.printStackTrace();
            realm.close();
        }finally {
            if(!realm.isClosed()){
                realm.close();
            }

        }


    }




}
