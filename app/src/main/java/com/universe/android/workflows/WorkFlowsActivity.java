package com.universe.android.workflows;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.activity.BaseActivity;
import com.universe.android.adapter.WorkFLowAdapter;
import com.universe.android.adapter.WorkFLowUserAdapter;
import com.universe.android.enums.DesignationEnum;
import com.universe.android.listneners.PageChangeInterface;
import com.universe.android.model.AnswersModal;
import com.universe.android.model.UserModel;
import com.universe.android.realmbean.RealmAnswers;
import com.universe.android.realmbean.RealmWorkFlow;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.SpacesItemDecoration;
import com.universe.android.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by gaurav.pandey on 24-01-2018.
 */

public class WorkFlowsActivity extends BaseActivity implements PageChangeInterface{
    //decalre the Views here
    private RecyclerView recyclerViewWorkFLowsDetail,recylerViewRoles;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recylerViewStatus;
    private SwipeRefreshLayout swipeRefreshLayoutStatus;
    private ImageView imageViewBack;
    private ArrayList<UserModel> stringArrayListRoles;
    private ArrayList<AnswersModal> stringArrayList;
    private LinearLayoutManager linearLayoutManager,linearLayoutManagerRoles;
    private WorkFLowAdapter surveyDetailAdapter;
    private WorkFLowUserAdapter workFLowUserAdapter;
    private String strType,surveyId,customerId;
    private LinearLayout llPending,ll_inprogress,ll_completed,ll_rejected;
    private TextView tvPending,tvInprogress,tvCompleted,tvRejected;
    private ImageView imgCD,imgRM,imgZM;
    private TextView textViewCd,textViewRM,textViewZM,textViewStatus;



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workflow_activity);
        initialization();
        setUpElements();
        setUpListeners();
        prepareList("","","", "");
        prepareListUsers();
    }

    @Override
    protected void onResume() {
        super.onResume();
      //  setCount();

    }

    private void setCount() {
        String userId=Prefs.getStringPrefs(AppConstants.UserId);
        String type=Prefs.getStringPrefs(AppConstants.TYPE);
        String userName=Prefs.getStringPrefs(AppConstants.USERNAME);

        if (type.equalsIgnoreCase("cd")){

            textViewCd.setText(AppConstants.ME);

            String mapping= Prefs.getStringPrefs(AppConstants.MAPPING);
            try {
                JSONArray array = new JSONArray(mapping);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    JSONObject jsonObject1 = jsonObject.getJSONObject(AppConstants.DETAILS);

                    String id = jsonObject1.optString(AppConstants.ID);
                    String name = jsonObject1.optString(AppConstants.NAME);
                    if (jsonObject.optString(AppConstants.TYPE).equalsIgnoreCase("rm")) {


                        if (userId.equalsIgnoreCase(id)) {
                            textViewRM.setText(AppConstants.ME);
                        } else {
                            if (Utility.validateString(name))
                                textViewRM.setText(name.substring(0));
                        }


                    }
                    if (jsonObject.optString(AppConstants.TYPE).equalsIgnoreCase("zm")) {


                        if (userId.equalsIgnoreCase(id)) {
                            textViewZM.setText(AppConstants.ME);
                        } else {
                            if (Utility.validateString(name))
                                textViewZM.setText(name.substring(0));
                        }

                    }
                }  }

            catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (type.equalsIgnoreCase("rm")){

            textViewRM.setText(AppConstants.ME);


            String mapping= Prefs.getStringPrefs(AppConstants.MAPPING);
            try {
                JSONArray array = new JSONArray(mapping);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    JSONObject jsonObject1;
                    if (jsonObject.has(AppConstants.DETAILS)){
                        jsonObject1 = jsonObject.getJSONObject(AppConstants.DETAILS);
                    }else {
                        jsonObject1=jsonObject;
                    }



                    String id = jsonObject1.optString(AppConstants.ID);
                    String name = jsonObject1.optString(AppConstants.NAME);
                    if (jsonObject.optString(AppConstants.TYPE).equalsIgnoreCase("cd")) {


                        if (userId.equalsIgnoreCase(id)) {
                            textViewCd.setText(AppConstants.ME);
                        } else {
                            if (Utility.validateString(name))
                                textViewCd.setText(name.substring(0));
                        }


                    }
                    if (jsonObject.optString(AppConstants.TYPE).equalsIgnoreCase("zsm")) {


                        if (userId.equalsIgnoreCase(id)) {
                            textViewZM.setText(AppConstants.ME);
                        } else {
                            if (Utility.validateString(name))
                                textViewZM.setText(name.substring(0));
                        }

                    }
                }  }

            catch (JSONException e) {
                e.printStackTrace();
            }


        }

        if (type.equalsIgnoreCase("zm")){

            textViewZM.setText(AppConstants.ME);

            String mapping= Prefs.getStringPrefs(AppConstants.MAPPING);
            try {
                JSONArray array = new JSONArray(mapping);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    JSONObject jsonObject1;
                    if (jsonObject.has(AppConstants.DETAILS)){
                        jsonObject1 = jsonObject.getJSONObject(AppConstants.DETAILS);
                    }else {
                        jsonObject1=jsonObject;
                    }


                    String id = jsonObject1.optString(AppConstants.ID);
                    String name = jsonObject1.optString(AppConstants.NAME);
                    if (jsonObject.optString(AppConstants.TYPE).equalsIgnoreCase("cd")) {


                        if (userId.equalsIgnoreCase(id)) {
                            textViewCd.setText(AppConstants.ME);
                        } else {
                            if (Utility.validateString(name))
                                textViewCd.setText(name.substring(0));
                        }


                    }
                    if (jsonObject.optString(AppConstants.TYPE).equalsIgnoreCase("rsm")) {


                        if (userId.equalsIgnoreCase(id)) {
                            textViewRM.setText(AppConstants.ME);
                        } else {
                            if (Utility.validateString(name))
                                textViewRM.setText(name.substring(0));
                        }

                    }
                }  }

            catch (JSONException e) {
                e.printStackTrace();
            }


        }

        Realm realm = Realm.getDefaultInstance();

        try {

            RealmAnswers realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.CUSTOMERID,customerId).equalTo(AppConstants.SURVEYID,surveyId).findFirst();

            if (realmAnswers!=null){

                if (realmAnswers.getCd_Status().equalsIgnoreCase("1")){
                    imgCD.setImageResource(R.drawable.green_circle);
                }else  if (realmAnswers.getCd_Status().equalsIgnoreCase("5")){
                    imgCD.setImageResource(R.drawable.yellow_circle);
                }else  if (realmAnswers.getCd_Status().equalsIgnoreCase("2")){
                    imgCD.setImageResource(R.drawable.green_circle);
                }else  if (realmAnswers.getCd_Status().equalsIgnoreCase("3")){
                    imgCD.setImageResource(R.drawable.red_circle);
                }else {

                }




                if (realmAnswers.getRm_STatus().equalsIgnoreCase("2")){

                    imgRM.setImageResource(R.drawable.green_circle);
                }else  if (realmAnswers.getRm_STatus().equalsIgnoreCase("3")){

                    imgRM.setImageResource(R.drawable.red_circle);

                }else  if (realmAnswers.getRm_STatus().equalsIgnoreCase("0")){

                    imgRM.setImageResource(R.drawable.yellow_circle);

                }else {

                }




                if (realmAnswers.getZm_Status().equalsIgnoreCase("2")){
                    imgZM.setImageResource(R.drawable.green_circle);
                }else if (realmAnswers.getZm_Status().equalsIgnoreCase("3")){
                    imgZM.setImageResource(R.drawable.red_circle);
                }else if (realmAnswers.getZm_Status().equalsIgnoreCase("0")){
                    imgZM.setImageResource(R.drawable.yellow_circle);
                }else {

                }


            }




        } catch (Exception e) {
            realm.close();
            e.printStackTrace();
        } finally {
            realm.close();
        }

    }

    private void prepareList(String type, String id, String name, String visible) {
        if (!Utility.validateString(id)) {
            if (stringArrayList == null) stringArrayList = new ArrayList<>();
            stringArrayList.clear();
        }
        Realm realm = Realm.getDefaultInstance();

        try {


            RealmAnswers realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.CUSTOMERID,customerId).equalTo(AppConstants.SURVEYID,surveyId).findFirst();



            if (realmAnswers != null) {
                JSONArray array=new JSONArray(realmAnswers.getWorkflow());
                //   JSONArray array1=new JSONArray(array.get(0).toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject=array.getJSONObject(i);
                    AnswersModal modal = new AnswersModal();
                    String userId=Prefs.getStringPrefs(AppConstants.UserId);
                    if (userId.equalsIgnoreCase(jsonObject.optString(AppConstants.ID))){
                        modal.setTitle(AppConstants.ME);
                    }else{
                        modal.setTitle(jsonObject.optString(AppConstants.USERNAME));
                    }

                    if (visible.equalsIgnoreCase("0")){
                        textViewStatus.setVisibility(View.GONE);
                    }else{
                        textViewStatus.setVisibility(View.VISIBLE);
                    }
                    if (Utility.validateString(id)) {
                        if (id.equalsIgnoreCase(jsonObject.optString(AppConstants.UserId))) {
                            textViewStatus.setText(stringArrayList.get(i).getStatus() + " by " + jsonObject.optString(AppConstants.USERNAME) + " on " + stringArrayList.get(i).getDate());
                                break;
                        } else {

                            if (type.equalsIgnoreCase(DesignationEnum.approval1.toString())) {
                                if (realmAnswers.getRequester_status().equalsIgnoreCase("5")) {
                                    textViewStatus.setText("Pending" + " by " + getPendingUserName(DesignationEnum.requester.toString()));

                                }else {
                                    textViewStatus.setText("Pending" + " by " + name);

                                }
                            }
                            if (type.equalsIgnoreCase(DesignationEnum.approval2.toString())) {
                                if (realmAnswers.getRequester_status().equalsIgnoreCase("5")) {
                                    textViewStatus.setText("Pending" + " by " + getPendingUserName(DesignationEnum.requester.toString()));

                                } else if (realmAnswers.getApproval1_status().equalsIgnoreCase("0")) {
                                    textViewStatus.setText("Pending" + " by " + getPendingUserName(DesignationEnum.approval1.toString()));
                                } else {
                                    textViewStatus.setText("Pending" + " by " + name);
                                }
                            }

                            if (type.equalsIgnoreCase(DesignationEnum.approval3.toString())) {
                                if (realmAnswers.getRequester_status().equalsIgnoreCase("5")) {
                                    textViewStatus.setText("Pending" + " by " + getPendingUserName(DesignationEnum.requester.toString()));

                                } else if (realmAnswers.getApproval1_status().equalsIgnoreCase("0")) {
                                    textViewStatus.setText("Pending" + " by " + getPendingUserName(DesignationEnum.approval1.toString()));
                                } else if (realmAnswers.getApproval2_status().equalsIgnoreCase("0")) {
                                    textViewStatus.setText("Pending" + " by " +getPendingUserName(DesignationEnum.approval2.toString()));
                                } else {
                                    textViewStatus.setText("Pending" + " by " + name);                                }
                            }

                            if (type.equalsIgnoreCase(DesignationEnum.approval4.toString())) {
                                if (realmAnswers.getRequester_status().equalsIgnoreCase("5")) {
                                    textViewStatus.setText("Pending" + " by " +getPendingUserName(DesignationEnum.requester.toString()));

                                } else if (realmAnswers.getApproval1_status().equalsIgnoreCase("0")) {
                                    textViewStatus.setText("Pending" + " by " + getPendingUserName(DesignationEnum.approval1.toString()));
                                } else if (realmAnswers.getApproval2_status().equalsIgnoreCase("0")) {
                                    textViewStatus.setText("Pending" + " by " + getPendingUserName(DesignationEnum.approval2.toString()));
                                } else if (realmAnswers.getApproval3_status().equalsIgnoreCase("0")) {
                                    textViewStatus.setText("Pending" + " by " + getPendingUserName(DesignationEnum.approval3.toString()));
                                } else {
                                    textViewStatus.setText("Pending" + " by " + name);                                }
                            }

                            if (type.equalsIgnoreCase(DesignationEnum.approval5.toString())) {
                                if (realmAnswers.getRequester_status().equalsIgnoreCase("5")) {
                                    textViewStatus.setText("Pending" + " by " + getPendingUserName(DesignationEnum.requester.toString()));

                                } else if (realmAnswers.getApproval1_status().equalsIgnoreCase("0")) {
                                    textViewStatus.setText("Pending" + " by " + getPendingUserName(DesignationEnum.approval1.toString()));
                                } else if (realmAnswers.getApproval2_status().equalsIgnoreCase("0")) {
                                    textViewStatus.setText("Pending" + " by " + getPendingUserName(DesignationEnum.approval2.toString()));
                                } else if (realmAnswers.getApproval3_status().equalsIgnoreCase("0")) {
                                    textViewStatus.setText("Pending" + " by " + getPendingUserName(DesignationEnum.approval3.toString()));
                                } else if (realmAnswers.getApproval4_status().equalsIgnoreCase("0")) {
                                    textViewStatus.setText("Pending" + " by " + getPendingUserName(DesignationEnum.approval4.toString()));
                                } else {
                                    textViewStatus.setText("Pending" + " by " + name);                                }
                            }

                            if (type.equalsIgnoreCase(DesignationEnum.approval6.toString())) {
                                if (realmAnswers.getRequester_status().equalsIgnoreCase("5")) {
                                    textViewStatus.setText("Pending" + " by " + getPendingUserName(DesignationEnum.requester.toString()));

                                } else if (realmAnswers.getApproval1_status().equalsIgnoreCase("0")) {
                                    textViewStatus.setText("Pending" + " by " + getPendingUserName(DesignationEnum.approval1.toString()));
                                } else if (realmAnswers.getApproval2_status().equalsIgnoreCase("0")) {
                                    textViewStatus.setText("Pending" + " by " + getPendingUserName(DesignationEnum.approval2.toString()));
                                } else if (realmAnswers.getApproval3_status().equalsIgnoreCase("0")) {
                                    textViewStatus.setText("Pending" + " by " +getPendingUserName(DesignationEnum.approval3.toString()));
                                } else if (realmAnswers.getApproval4_status().equalsIgnoreCase("0")) {
                                    textViewStatus.setText("Pending" + " by " + getPendingUserName(DesignationEnum.approval4.toString()));
                                } else if (realmAnswers.getApproval5_status().equalsIgnoreCase("0")) {
                                    textViewStatus.setText("Pending" + " by " + getPendingUserName(DesignationEnum.approval5.toString()));
                                } else {
                                    textViewStatus.setText("Pending" + " by " + name);                                }
                            }


                        }
                    }else {
                        textViewStatus.setVisibility(View.GONE);
                    }
                    modal.setStatus(jsonObject.optString(AppConstants.STATUS));
                    SimpleDateFormat format1 = new SimpleDateFormat(AppConstants.utc_format1);
                    SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = format1.parse(jsonObject.optString(AppConstants.DATE));
                    System.out.println(format2.format(date));
                    modal.setDate(AppConstants.format10.format(date));
                    //    modal.setDate(AppConstants.format10.format(realmAnswers.get(i).getDate()));
                    if (!Utility.validateString(id))
                    stringArrayList.add(modal);
                }
                if (!Utility.validateString(id))
                Collections.reverse(stringArrayList);


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
            if (!Utility.validateString(id))
            surveyDetailAdapter.notifyDataSetChanged();
        }
    }

    private void prepareListUsers() {
        if (stringArrayListRoles == null) stringArrayListRoles = new ArrayList<>();
        stringArrayListRoles.clear();
        Realm realm = Realm.getDefaultInstance();

        try {


            RealmResults<RealmWorkFlow> realmSurveys = realm.where(RealmWorkFlow.class).equalTo(AppConstants.SURVEYID,surveyId).findAll();



            if (realmSurveys != null) {


               for (int i=0;i<realmSurveys.size();i++){
                   JSONObject jsonObject=new JSONObject(realmSurveys.get(i).getAssignTo());
                   UserModel modal = new UserModel();
                   modal.setUserName(jsonObject.optString(AppConstants.NAME));

                   modal.setId(jsonObject.optString(AppConstants.ID));
                   modal.setUserStatus(realmSurveys.get(i).getLevel());
                   stringArrayListRoles.add(modal);
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

        if (workFLowUserAdapter != null) {
            workFLowUserAdapter.notifyDataSetChanged();
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
    //    recyclerViewWorkFLowsDetail.addItemDecoration(new SpacesItemDecoration(10));
        recyclerViewWorkFLowsDetail.setAdapter(surveyDetailAdapter);



        workFLowUserAdapter = new WorkFLowUserAdapter(mContext, stringArrayListRoles,this);
        recylerViewRoles.addItemDecoration(new SpacesItemDecoration(10));
        recylerViewRoles.setAdapter(workFLowUserAdapter);
        recylerViewRoles.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));



    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initialization() {
        stringArrayList = new ArrayList<>();
        stringArrayListRoles = new ArrayList<>();

        recyclerViewWorkFLowsDetail = findViewById(R.id.recylerViewSurveyDetail);
        recylerViewRoles = findViewById(R.id.recylerViewRoles);
        TextView textViewMobileNo = (TextView) findViewById(R.id.textViewMobileNo);
        textViewMobileNo.setText(getResources().getString(R.string.workflow_mobile_no));
        imageViewBack = findViewById(R.id.imageviewback);

        llPending = (LinearLayout) findViewById(R.id.ll_pending);
        ll_inprogress = (LinearLayout) findViewById(R.id.ll_inprogress);
        ll_completed = (LinearLayout) findViewById(R.id.ll_completed);
        imgCD = (ImageView) findViewById(R.id.imgCD);
        imgRM = (ImageView) findViewById(R.id.imgRM);
        imgZM = (ImageView) findViewById(R.id.imgZM);

        textViewCd = (TextView) findViewById(R.id.textViewCD);
        textViewRM = (TextView) findViewById(R.id.textViewRM);
        textViewZM = (TextView) findViewById(R.id.textViewZM);


        textViewStatus=(TextView)findViewById(R.id.textViewStatus);



        llPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.animateView(v);
                prepareList(getString(R.string.pending),"","", "");
            }
        });

        ll_inprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.animateView(v);
                prepareList(getString(R.string.inprogress),"","", "");
            }
        });
        ll_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.animateView(v);
                prepareList(getString(R.string.completed),"","", "");
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


    @Override
    public void onDataPass(String data, int pos, String categoryId) {


        prepareList(stringArrayListRoles.get(pos).getUserStatus(),stringArrayListRoles.get(pos).getId(),stringArrayListRoles.get(pos).getUserName(),categoryId);
    }



    private String getPendingUserName(String type) {
        String userName="";
        Realm realm = Realm.getDefaultInstance();

        try {


            RealmResults<RealmWorkFlow> realmSurveys = realm.where(RealmWorkFlow.class).equalTo(AppConstants.SURVEYID,surveyId).findAll();



            if (realmSurveys != null) {


                for (int i=0;i<realmSurveys.size();i++){
                    if (type.equalsIgnoreCase(realmSurveys.get(i).getLevel())) {
                        JSONObject jsonObject = new JSONObject(realmSurveys.get(i).getAssignTo());
                        userName=jsonObject.optString(AppConstants.NAME);

                    }


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

       return userName;
    }
}
