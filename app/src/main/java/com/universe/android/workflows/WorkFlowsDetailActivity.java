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
import com.universe.android.adapter.WorkFLowDetailAdapter;
import com.universe.android.enums.DesignationEnum;
import com.universe.android.helper.RecyclerTouchListener;
import com.universe.android.model.AnswersModal;
import com.universe.android.realmbean.RealmAnswers;
import com.universe.android.realmbean.RealmCustomer;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.SpacesItemDecoration;
import com.universe.android.utility.Utility;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by gaurav.pandey on 24-01-2018.
 */

public class WorkFlowsDetailActivity extends BaseActivity {
    //decalre the Views here
    private RecyclerView recyclerViewWorkFLowsDetail;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recylerViewStatus;
    private SwipeRefreshLayout swipeRefreshLayoutStatus;
    private ImageView imageViewBack;

    private ArrayList<AnswersModal> stringArrayList;
    private LinearLayoutManager linearLayoutManager;
    private WorkFLowDetailAdapter surveyDetailAdapter;
    private String strType, surveyId;
    private LinearLayout llPending, ll_inprogress, ll_completed, ll_rejected;
    private TextView tvPending, tvInprogress, tvCompleted, tvRejected;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workflows_details_fragment);
        initialization();
        setUpElements();
        setUpListeners();


    }

    @Override
    protected void onResume() {
        super.onResume();
        setCount();
        llPending.setBackgroundColor(getResources().getColor(R.color.white));
        ll_completed.setBackgroundColor(getResources().getColor(R.color.survey_background_color));
        ll_inprogress.setBackgroundColor(getResources().getColor(R.color.survey_background_color));
        ll_rejected.setBackgroundColor(getResources().getColor(R.color.survey_background_color));
        prepareList(getString(R.string.pending));
    }

    private void setCount() {

        Realm realm = Realm.getDefaultInstance();
        try {

            long realmPending = 0, realmInprogress = 0, realmCompleted = 0, realmRejected = 0;
            String designation = Prefs.getStringPrefs(AppConstants.TYPE);
            if (designation.equalsIgnoreCase(DesignationEnum.approval1.toString())) {
                realmPending = realm.where(RealmAnswers.class).equalTo(AppConstants.approval1_status, "0").count();
                realmInprogress = realm.where(RealmAnswers.class).equalTo(AppConstants.approval1_status, "2").equalTo(AppConstants.requester_status, "1").count();
                realmCompleted = realm.where(RealmAnswers.class).equalTo(AppConstants.approval1_status, "2").equalTo(AppConstants.requester_status, "2").count();
                realmRejected = realm.where(RealmAnswers.class).equalTo(AppConstants.approval1_status, "3").count();

            }
            if (designation.equalsIgnoreCase(DesignationEnum.approval2.toString())) {
                realmPending = realm.where(RealmAnswers.class).equalTo(AppConstants.approval2_status, "0").count();
                realmInprogress = realm.where(RealmAnswers.class).equalTo(AppConstants.approval2_status, "2").equalTo(AppConstants.requester_status, "1").count();
                realmCompleted = realm.where(RealmAnswers.class).equalTo(AppConstants.approval2_status, "2").equalTo(AppConstants.requester_status, "2").count();
                realmRejected = realm.where(RealmAnswers.class).equalTo(AppConstants.approval2_status, "3").count();

            }
            if (designation.equalsIgnoreCase(DesignationEnum.approval3.toString())) {
                realmPending = realm.where(RealmAnswers.class).equalTo(AppConstants.approval3_status, "0").count();
                realmInprogress = realm.where(RealmAnswers.class).equalTo(AppConstants.approval3_status, "2").equalTo(AppConstants.requester_status, "1").count();
                realmCompleted = realm.where(RealmAnswers.class).equalTo(AppConstants.approval3_status, "2").equalTo(AppConstants.requester_status, "2").count();
                realmRejected = realm.where(RealmAnswers.class).equalTo(AppConstants.approval3_status, "3").count();

            }
            if (designation.equalsIgnoreCase(DesignationEnum.approval4.toString())) {
                realmPending = realm.where(RealmAnswers.class).equalTo(AppConstants.approval4_status, "0").count();
                realmInprogress = realm.where(RealmAnswers.class).equalTo(AppConstants.approval4_status, "2").equalTo(AppConstants.requester_status, "1").count();
                realmCompleted = realm.where(RealmAnswers.class).equalTo(AppConstants.approval4_status, "2").equalTo(AppConstants.requester_status, "2").count();
                realmRejected = realm.where(RealmAnswers.class).equalTo(AppConstants.approval4_status, "3").count();

            }
            if (designation.equalsIgnoreCase(DesignationEnum.approval5.toString())) {
                realmPending = realm.where(RealmAnswers.class).equalTo(AppConstants.approval5_status, "0").count();
                realmInprogress = realm.where(RealmAnswers.class).equalTo(AppConstants.approval5_status, "2").equalTo(AppConstants.requester_status, "1").count();
                realmCompleted = realm.where(RealmAnswers.class).equalTo(AppConstants.approval5_status, "2").equalTo(AppConstants.requester_status, "2").count();
                realmRejected = realm.where(RealmAnswers.class).equalTo(AppConstants.approval5_status, "3").count();

            }
            if (designation.equalsIgnoreCase(DesignationEnum.approval6.toString())) {
                realmPending = realm.where(RealmAnswers.class).equalTo(AppConstants.approval6_status, "0").count();
                realmInprogress = realm.where(RealmAnswers.class).equalTo(AppConstants.approval6_status, "2").equalTo(AppConstants.requester_status, "1").count();
                realmCompleted = realm.where(RealmAnswers.class).equalTo(AppConstants.approval6_status, "2").equalTo(AppConstants.requester_status, "2").count();
                realmRejected = realm.where(RealmAnswers.class).equalTo(AppConstants.approval6_status, "3").count();

            }


            tvPending.setText(realmPending + "");
            tvInprogress.setText(realmInprogress + "");
            tvCompleted.setText(realmCompleted + "");
            tvRejected.setText(realmRejected + "");

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
            RealmResults<RealmAnswers> realmAnswers = null;
            String designation = Prefs.getStringPrefs(AppConstants.TYPE);
            if (designation.equalsIgnoreCase(DesignationEnum.approval1.toString())) {
                realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.approval1_status, "0").findAll();
                if (type.equalsIgnoreCase(getString(R.string.inprogress)))
                    realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.approval1_status, "2").equalTo(AppConstants.requester_status, "1").findAll();
                if (type.equalsIgnoreCase(getString(R.string.completed)))
                    realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.approval1_status, "2").equalTo(AppConstants.requester_status, "2").findAll();
                if (type.equalsIgnoreCase(getString(R.string.rejected)))
                    realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.approval1_status, "3").findAll();

            }
            if (designation.equalsIgnoreCase(DesignationEnum.approval2.toString())) {
                realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.approval2_status, "0").findAll();
                if (type.equalsIgnoreCase(getString(R.string.inprogress)))
                    realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.approval2_status, "2").equalTo(AppConstants.requester_status, "1").findAll();
                if (type.equalsIgnoreCase(getString(R.string.completed)))
                    realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.approval2_status, "2").equalTo(AppConstants.requester_status, "2").findAll();
                if (type.equalsIgnoreCase(getString(R.string.rejected)))
                    realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.approval2_status, "3").findAll();

            }
            if (designation.equalsIgnoreCase(DesignationEnum.approval3.toString())) {
                realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.approval3_status, "0").findAll();
                if (type.equalsIgnoreCase(getString(R.string.inprogress)))
                    realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.approval3_status, "2").equalTo(AppConstants.requester_status, "1").findAll();
                if (type.equalsIgnoreCase(getString(R.string.completed)))
                    realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.approval3_status, "2").equalTo(AppConstants.requester_status, "2").findAll();
                if (type.equalsIgnoreCase(getString(R.string.rejected)))
                    realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.approval3_status, "3").findAll();

            }
            if (designation.equalsIgnoreCase(DesignationEnum.approval4.toString())) {
                realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.approval4_status, "0").findAll();
                if (type.equalsIgnoreCase(getString(R.string.inprogress)))
                    realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.approval4_status, "2").equalTo(AppConstants.requester_status, "1").findAll();
                if (type.equalsIgnoreCase(getString(R.string.completed)))
                    realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.approval4_status, "2").equalTo(AppConstants.requester_status, "2").findAll();
                if (type.equalsIgnoreCase(getString(R.string.rejected)))
                    realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.approval4_status, "3").findAll();

            }
            if (designation.equalsIgnoreCase(DesignationEnum.approval5.toString())) {
                realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.approval5_status, "0").findAll();

                if (type.equalsIgnoreCase(getString(R.string.inprogress)))
                    realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.approval5_status, "2").equalTo(AppConstants.requester_status, "1").findAll();
                if (type.equalsIgnoreCase(getString(R.string.completed)))
                    realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.approval5_status, "2").equalTo(AppConstants.requester_status, "2").findAll();
                if (type.equalsIgnoreCase(getString(R.string.rejected)))
                    realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.approval5_status, "3").findAll();

            }
            if (designation.equalsIgnoreCase(DesignationEnum.approval6.toString())) {
                realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.approval6_status, "0").findAll();
                if (type.equalsIgnoreCase(getString(R.string.inprogress)))
                    realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.approval6_status, "2").equalTo(AppConstants.requester_status, "1").findAll();
                if (type.equalsIgnoreCase(getString(R.string.completed)))
                    realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.approval6_status, "2").equalTo(AppConstants.requester_status, "2").findAll();
                if (type.equalsIgnoreCase(getString(R.string.rejected)))
                    realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.approval6_status, "3").findAll();

            }


            if (realmAnswers != null && realmAnswers.size() > 0) {
                for (int i = 0; i < realmAnswers.size(); i++) {
                    AnswersModal modal = new AnswersModal();
                    modal.set_id(realmAnswers.get(i).get_id());

                    RealmCustomer realmCustomer = realm.where(RealmCustomer.class).equalTo(AppConstants.ID, realmAnswers.get(i).getCustomerId()).findFirst();
                    if (realmCustomer.getCustomer().equalsIgnoreCase(AppConstants.CrystalCustomer)) {
                        modal.setTitle(realmCustomer.getName());
                        modal.setState(realmCustomer.getState());
                        modal.setContactNo(realmCustomer.getContactNo());
                    } else {
                        modal.setTitle(realmCustomer.getRetailerName());
                        modal.setState(realmCustomer.getAddress());
                        modal.setContactNo(realmCustomer.getMobile());
                    }


                    modal.setTerritory(realmCustomer.getTerritory());
                    modal.setPincode(realmCustomer.getPincode());
                    modal.setCustomerId(realmCustomer.getId());
                    modal.setStatus(type);
                    modal.setCustomer(realmCustomer.getCustomer());
                    modal.setDate(AppConstants.format2.format(realmAnswers.get(i).getCreatedAt()));
                    stringArrayList.add(modal);
                }
            } else {
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

        surveyDetailAdapter.setOnItemClickLister(new WorkFLowDetailAdapter.OnItemSelecteListener() {
            @Override
            public void onItemSelected(View v, int position) {
                Intent intent = new Intent(mContext, CategoryExpandableListActivity.class);

                //  if (!stringArrayList.get(position).getStatus().equalsIgnoreCase(getString(R.string.pending))){
                intent = new Intent(mContext, WorkFlowsActivity.class);
                //  }
                intent.putExtra(AppConstants.STR_TITLE, strType);
                intent.putExtra(AppConstants.SURVEYID, surveyId);
                intent.putExtra(AppConstants.CUSTOMERID, stringArrayList.get(position).getCustomerId());
                intent.putExtra(AppConstants.CUSTOMER, stringArrayList.get(position).getCustomer());
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

            }
        });

        recyclerViewWorkFLowsDetail.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerViewWorkFLowsDetail, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(mContext, CategoryExpandableListActivity.class);
                //  if (!stringArrayList.get(position).getStatus().equalsIgnoreCase(getString(R.string.pending))){
                intent = new Intent(mContext, WorkFlowsActivity.class);
                //  }
                intent.putExtra(AppConstants.STR_TITLE, strType);
                intent.putExtra(AppConstants.SURVEYID, surveyId);
                intent.putExtra(AppConstants.CUSTOMERID, stringArrayList.get(position).getCustomerId());
                intent.putExtra(AppConstants.CUSTOMER, stringArrayList.get(position).getCustomer());
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void setUpElements() {

        surveyDetailAdapter = new WorkFLowDetailAdapter(mContext, stringArrayList);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewWorkFLowsDetail.setLayoutManager(linearLayoutManager);
        recyclerViewWorkFLowsDetail.addItemDecoration(new SpacesItemDecoration(10));
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
        ll_rejected = (LinearLayout) findViewById(R.id.ll_rejected);

        tvPending = (TextView) findViewById(R.id.tvPending);
        tvInprogress = (TextView) findViewById(R.id.tvInprogress);
        tvCompleted = (TextView) findViewById(R.id.tvCompleted);
        tvRejected = (TextView) findViewById(R.id.tvRejected);

        TextView textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        textViewStatus.setVisibility(View.GONE);


        llPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llPending.setBackgroundColor(getResources().getColor(R.color.white));
                ll_completed.setBackgroundColor(getResources().getColor(R.color.survey_background_color));
                ll_inprogress.setBackgroundColor(getResources().getColor(R.color.survey_background_color));
                ll_rejected.setBackgroundColor(getResources().getColor(R.color.survey_background_color));
                prepareList(getString(R.string.pending));

            }
        });

        ll_inprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ll_inprogress.setBackgroundColor(getResources().getColor(R.color.white));
                ll_completed.setBackgroundColor(getResources().getColor(R.color.survey_background_color));
                llPending.setBackgroundColor(getResources().getColor(R.color.survey_background_color));
                ll_rejected.setBackgroundColor(getResources().getColor(R.color.survey_background_color));
                prepareList(getString(R.string.inprogress));
            }
        });
        ll_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_completed.setBackgroundColor(getResources().getColor(R.color.white));
                llPending.setBackgroundColor(getResources().getColor(R.color.survey_background_color));
                ll_inprogress.setBackgroundColor(getResources().getColor(R.color.survey_background_color));
                ll_rejected.setBackgroundColor(getResources().getColor(R.color.survey_background_color));
                prepareList(getString(R.string.completed));
            }
        });

        ll_rejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_completed.setBackgroundColor(getResources().getColor(R.color.survey_background_color));
                llPending.setBackgroundColor(getResources().getColor(R.color.survey_background_color));
                ll_inprogress.setBackgroundColor(getResources().getColor(R.color.survey_background_color));
                ll_rejected.setBackgroundColor(getResources().getColor(R.color.white));
                prepareList(getString(R.string.rejected));
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            strType = intent.getExtras().getString(AppConstants.TYPE);
            surveyId = intent.getExtras().getString(AppConstants.SURVEYID);
        }
        TextView tvHeaderName = (TextView) findViewById(R.id.tvHeaderName);
        tvHeaderName.setText(strType);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
