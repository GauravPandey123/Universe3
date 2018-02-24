package com.universe.android.workflows;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.activity.BaseActivity;
import com.universe.android.activity.CategoryExpandableListActivity;
import com.universe.android.adapter.WorkFLowAdapter;
import com.universe.android.adapter.WorkFLowDetailAdapter;
import com.universe.android.helper.RecyclerTouchListener;
import com.universe.android.model.AnswersModal;
import com.universe.android.realmbean.RealmAnswers;
import com.universe.android.realmbean.RealmCustomer;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by gaurav.pandey on 24-01-2018.
 */

public class WorkFlowsActivity extends BaseActivity {
    //decalre the Views here
    private RecyclerView recyclerViewWorkFLowsDetail;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recylerViewStatus;
    private SwipeRefreshLayout swipeRefreshLayoutStatus;
    private ImageView imageViewBack;

    private ArrayList<AnswersModal> stringArrayList;
    private LinearLayoutManager linearLayoutManager;
    private WorkFLowAdapter surveyDetailAdapter;
    private String strType,surveyId,customerId;
    private LinearLayout llPending,ll_inprogress,ll_completed,ll_rejected;
    private TextView tvPending,tvInprogress,tvCompleted,tvRejected;
    private ImageView imgCD,imgRM,imgZM;
    private TextView textViewCd,textViewRM,textViewZM;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workflow_activity);
        initialization();
        setUpElements();
        setUpListeners();
    prepareList("");

    }

    @Override
    protected void onResume() {
        super.onResume();
        setCount();

    }

    private void setCount() {
        String userId=Prefs.getStringPrefs(AppConstants.UserId);
        String mapping= Prefs.getStringPrefs(AppConstants.MAPPING);
        try {
            JSONArray array=new JSONArray(mapping);

            for (int i=0;i<array.length();i++){
                JSONObject jsonObject=array.getJSONObject(i);
                JSONObject jsonObject1=jsonObject.getJSONObject(AppConstants.DETAILS);

                String id=jsonObject1.optString(AppConstants.ID);
                String name=jsonObject1.optString(AppConstants.EMPLOYEE_NAME);
                if (jsonObject.optString(AppConstants.TYPE).equalsIgnoreCase("rm")){


                    if (userId.equalsIgnoreCase(id)){
                        textViewRM.setText(AppConstants.ME);
                    }else {
                        if (Utility.validateString(name))
                        textViewRM.setText(name.substring(0));
                    }



                }
                if (jsonObject.optString(AppConstants.TYPE).equalsIgnoreCase("zm")){


                    if (userId.equalsIgnoreCase(id)){
                        textViewZM.setText(AppConstants.ME);
                    }else {
                        if (Utility.validateString(name))
                            textViewZM.setText(name.substring(0));
                    }



                }

            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
        Realm realm = Realm.getDefaultInstance();

        try {

        RealmAnswers realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.CUSTOMERID,customerId).equalTo(AppConstants.SURVEYID,surveyId).findFirst();

        if (realmAnswers!=null){
            if (Utility.validateString(realmAnswers.getSubmitbyCD())){
                if (realmAnswers.getCd_Status().equalsIgnoreCase("1")){


                }else  if (realmAnswers.getCd_Status().equalsIgnoreCase("5")){

                }else {

                }

            }

            if (Utility.validateString(realmAnswers.getSubmitbyRM())){
                if (realmAnswers.getRm_STatus().equalsIgnoreCase("2")){

                }else  if (realmAnswers.getRm_STatus().equalsIgnoreCase("3")){

                }else {

                }

            }

            if (Utility.validateString(realmAnswers.getSubmitbyZM())){
                if (realmAnswers.getZm_Status().equalsIgnoreCase("2")){

                }else if (realmAnswers.getZm_Status().equalsIgnoreCase("3")){

                }else {

                }

            }
        }




        } catch (Exception e) {
            realm.close();
            e.printStackTrace();
        } finally {
            realm.close();
        }

    }

    private void prepareList(String type) {
        if (stringArrayList == null) stringArrayList = new ArrayList<>();
        stringArrayList.clear();
        Realm realm = Realm.getDefaultInstance();

        try {


         RealmAnswers realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.CUSTOMERID,customerId).equalTo(AppConstants.SURVEYID,surveyId).findFirst();



            if (realmAnswers != null) {
                JSONArray array=new JSONArray(realmAnswers.getWorkflow());
                JSONArray array1=new JSONArray(array.get(0).toString());
                for (int i = 0; i < array1.length(); i++) {
                    JSONObject jsonObject=array1.getJSONObject(i);
                    AnswersModal modal = new AnswersModal();
                    modal.setTitle(jsonObject.optString(AppConstants.USERNAME));
                    modal.setStatus(jsonObject.optString(AppConstants.STATUS));

                  //  modal.setDate(AppConstants.format10.format(realmAnswers.get(i).getDate()));
                    stringArrayList.add(modal);
                }
            }else {
                Utility.showToast(getString(R.string.no_data));
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




    private void setUpListeners() {
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }

    private void setUpElements() {

        surveyDetailAdapter = new WorkFLowAdapter(mContext, stringArrayList);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewWorkFLowsDetail.setLayoutManager(linearLayoutManager);
        recyclerViewWorkFLowsDetail.setItemAnimator(new DefaultItemAnimator());
        recyclerViewWorkFLowsDetail.setAdapter(surveyDetailAdapter);


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initialization() {
        stringArrayList = new ArrayList<>();

        recyclerViewWorkFLowsDetail = findViewById(R.id.recylerViewSurveyDetail);
        TextView textViewMobileNo = (TextView) findViewById(R.id.textViewMobileNo);
        textViewMobileNo.setText(getResources().getString(R.string.workflow_mobile_no));
        imageViewBack = findViewById(R.id.imageviewback);

         llPending = (LinearLayout) findViewById(R.id.ll_pending);
         ll_inprogress = (LinearLayout) findViewById(R.id.ll_inprogress);
         ll_completed = (LinearLayout) findViewById(R.id.ll_completed);
        imgCD = (ImageView) findViewById(R.id.imageCD);
        imgRM = (ImageView) findViewById(R.id.imgRM);
        imgZM = (ImageView) findViewById(R.id.imgZM);

        textViewCd = (TextView) findViewById(R.id.textViewCD);
        textViewRM = (TextView) findViewById(R.id.textViewRM);
        textViewZM = (TextView) findViewById(R.id.textViewZM);


        TextView textViewStatus=(TextView)findViewById(R.id.textViewStatus);
        textViewStatus.setVisibility(View.GONE);


        llPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.animateView(v);
                prepareList(getString(R.string.pending));
            }
        });

        ll_inprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.animateView(v);
                prepareList(getString(R.string.inprogress));
            }
        });
        ll_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.animateView(v);
                prepareList(getString(R.string.completed));
            }
        });

       




        Intent intent = getIntent();
        if (intent != null) {
            strType = intent.getExtras().getString(AppConstants.STR_TITLE);
            surveyId = intent.getExtras().getString(AppConstants.SURVEYID);
            customerId = intent.getExtras().getString(AppConstants.CUSTOMERID);
        }

        TextView tvHeaderName=(TextView)findViewById(R.id.tvHeaderName);
        tvHeaderName.setText(strType);

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}