package com.universe.android.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.universe.android.R;
import com.universe.android.adapter.DrawerAdapter;
import com.universe.android.fragment.AdminFragment;
import com.universe.android.fragment.ProfileFragment;
import com.universe.android.fragment.QuestionaireTeamSuverFragment;
import com.universe.android.fragment.QuestionarieSelectionFragment;
import com.universe.android.fragment.SurveySelectionFragment;
import com.universe.android.fragment.SyncResponsesFragment;
import com.universe.android.model.DrawerItem;
import com.universe.android.realmbean.RealmController;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private Toolbar mToolbar;
    private LinearLayoutManager layoutManager;
    private DrawerAdapter mDrawerAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<DrawerItem> mDrawerItemList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private int mContainerId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();
        setUpElements();
        setUpListeners();
    }

    private void setUpListeners() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        final String type=Prefs.getStringPrefs(AppConstants.TYPE);

        mDrawerAdapter.setOnItemClickLister(new DrawerAdapter.OnItemSelecteListener() {
            @Override
            public void onItemSelected(View v, int position) {

                if (position==0){
                    mDrawerLayout.closeDrawers();
                    mToolbar.setTitle(R.string.profile);
                    replaceFragment(new ProfileFragment(), mContainerId);
                }

                if (type.equalsIgnoreCase("cd")){
                    if (position==1){
                        mDrawerLayout.closeDrawers();
                        mToolbar.setTitle(R.string.questionairemenu);
                        replaceFragment(new SurveySelectionFragment().newInstance(getResources().getString(R.string.questionairemenu)), mContainerId);

                    }else if (position==2){
                        mDrawerLayout.closeDrawers();
                        mToolbar.setTitle(R.string.survey_report);
                        replaceFragment(new SurveySelectionFragment().newInstance(AppConstants.SURVEYREPORT), mContainerId);

                    }else if (position==3){
                        mDrawerLayout.closeDrawers();
                        mToolbar.setTitle(getString(R.string.sync_responses));
                        replaceFragment(new SyncResponsesFragment(), mContainerId);

                    }else if (position==4){
                        mDrawerLayout.closeDrawers();
                        new RealmController().clearRealm(MainActivity.this);
                        Intent i=new Intent(MainActivity.this,LoginActivity.class);
                        i.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                }else if (type.equalsIgnoreCase("rm") || type.equalsIgnoreCase("zm")){

                    if (position==1){

                            mDrawerLayout.closeDrawers();
                            mToolbar.setTitle(R.string.work_flows);
                            replaceFragment(new SurveySelectionFragment().newInstance(AppConstants.WORKFLOWS), mContainerId);

                    }else if (position==2){
                        mDrawerLayout.closeDrawers();
                        mToolbar.setTitle(R.string.teamSurveyReport);
                        replaceFragment(new QuestionaireTeamSuverFragment(), mContainerId);
                    }else if (position==3){
                        mDrawerLayout.closeDrawers();
                        mToolbar.setTitle(getString(R.string.sync_responses));
                        replaceFragment(new SyncResponsesFragment(), mContainerId);

                    }else if (position==4){
                        mDrawerLayout.closeDrawers();
                        new RealmController().clearRealm(MainActivity.this);
                        Intent i=new Intent(MainActivity.this,LoginActivity.class);
                        i.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                }else if (type.equalsIgnoreCase("admin")){
                    if (position==1){
                        mDrawerLayout.closeDrawers();
                        mToolbar.setTitle(R.string.admin);
                        replaceFragment(new AdminFragment(), mContainerId);
                    }else if (position==2){
                        mDrawerLayout.closeDrawers();
                        mToolbar.setTitle(getString(R.string.sync_responses));
                        replaceFragment(new SyncResponsesFragment(), mContainerId);

                    }else if (position==3){
                        mDrawerLayout.closeDrawers();
                        new RealmController().clearRealm(MainActivity.this);
                        Intent i=new Intent(MainActivity.this,LoginActivity.class);
                        i.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                }
             /*   switch (position) {
                    case 0:
                        mDrawerLayout.closeDrawers();
                        mToolbar.setTitle(R.string.profile);
                        replaceFragment(new ProfileFragment(), mContainerId);
                        break;

                        if (type.equalsIgnoreCase("cd")){

                        }
                    case 1:
                        mDrawerLayout.closeDrawers();
                        mToolbar.setTitle(R.string.dashboard);
                    case 2:
                        mDrawerLayout.closeDrawers();
                        mToolbar.setTitle(R.string.survey_report);
                        replaceFragment(new SurveySelectionFragment().newInstance(AppConstants.SURVEYREPORT), mContainerId);
                        break;

                    case 3:
                        mDrawerLayout.closeDrawers();
                        mToolbar.setTitle(getString(R.string.sync_responses));
                        replaceFragment(new SyncResponsesFragment(), mContainerId);
                        break;

                    case 4:
                        mDrawerLayout.closeDrawers();
                        mToolbar.setTitle(R.string.questionairemenu);
                        replaceFragment(new SurveySelectionFragment().newInstance(getResources().getString(R.string.questionairemenu)), mContainerId);
                        break;

                    case 6:
                        mDrawerLayout.closeDrawers();
                        mToolbar.setTitle(R.string.admin);
                        replaceFragment(new AdminFragment(), mContainerId);
                        break;
                    case 7:
                        mDrawerLayout.closeDrawers();
                        mToolbar.setTitle(R.string.work_flows);
                        replaceFragment(new SurveySelectionFragment().newInstance(AppConstants.WORKFLOWS), mContainerId);
                        break;

                    case 8:
                        mDrawerLayout.closeDrawers();
                        mToolbar.setTitle(R.string.team_survey_report);
                        replaceFragment(new QuestionaireTeamSuverFragment(), mContainerId);
                        break;
<<<<<<< HEAD
=======
                       // mContext.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);


                    case 12:
                        mDrawerLayout.closeDrawers();
                        new RealmController().clearRealm(MainActivity.this);
                        Intent i=new Intent(MainActivity.this,LoginActivity.class);
                        i.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        break;
>>>>>>> d771e06f5802c60a97297089c0938f05deed160b

                }*/

            }
        });
    }


    private void setUpElements() {
        mDrawerItemList = new ArrayList<DrawerItem>();
        //Dummy Data
        String type=Prefs.getStringPrefs(AppConstants.TYPE);
        if (type.equalsIgnoreCase("cd")){

            DrawerItem item4 = new DrawerItem();
            item4.setIcon(R.drawable.survey_report);
            item4.setTitle("Questionaire");
            mDrawerItemList.add(item4);


            DrawerItem item2 = new DrawerItem();
            item2.setIcon(R.drawable.survey_report);
            item2.setTitle("Survey Report");
            mDrawerItemList.add(item2);


            DrawerItem item3 = new DrawerItem();
            item3.setIcon(R.drawable.survey_report);
            item3.setTitle("Sync Responses");
            mDrawerItemList.add(item3);

            DrawerItem item9 = new DrawerItem();
            item9.setIcon(R.drawable.ic_logout);
            item9.setTitle("Logout");
            mDrawerItemList.add(item9);
        } else if (type.equalsIgnoreCase("rm") || type.equalsIgnoreCase("zm")) {
            DrawerItem item11 = new DrawerItem();
            item11.setIcon(R.drawable.survey_report);
            item11.setTitle(getResources().getString(R.string.work_flows));
            mDrawerItemList.add(item11);

            DrawerItem item12 = new DrawerItem();
            item12.setIcon(R.drawable.survey_report);
            item12.setTitle(getResources().getString(R.string.teamSurveyReport));
            mDrawerItemList.add(item12);

            DrawerItem item3 = new DrawerItem();
            item3.setIcon(R.drawable.survey_report);
            item3.setTitle("Sync Responses");
            mDrawerItemList.add(item3);

            DrawerItem item9 = new DrawerItem();
            item9.setIcon(R.drawable.ic_logout);
            item9.setTitle("Logout");
            mDrawerItemList.add(item9);

        }else if (type.equalsIgnoreCase("admin")) {

            DrawerItem item10 = new DrawerItem();
            item10.setIcon(R.drawable.survey_report);
            item10.setTitle(getResources().getString(R.string.admin));
            mDrawerItemList.add(item10);

            DrawerItem item3 = new DrawerItem();
            item3.setIcon(R.drawable.survey_report);
            item3.setTitle("Sync Responses");
            mDrawerItemList.add(item3);

            DrawerItem item9 = new DrawerItem();
            item9.setIcon(R.drawable.ic_logout);
            item9.setTitle("Logout");
            mDrawerItemList.add(item9);

        }
       /* mDrawerItemList = new ArrayList<DrawerItem>();
        DrawerItem item = new DrawerItem();
        item.setIcon(R.drawable.ic_dashboard);
        item.setTitle("DashBoard");
        mDrawerItemList.add(item);

        DrawerItem item2 = new DrawerItem();
        item2.setIcon(R.drawable.survey_report);
        item2.setTitle("Survey Report");
        mDrawerItemList.add(item2);

        DrawerItem item3 = new DrawerItem();
        item3.setIcon(R.drawable.survey_report);
        item3.setTitle("Sync Responses");
        mDrawerItemList.add(item3);

        DrawerItem item4 = new DrawerItem();
        item4.setIcon(R.drawable.survey_report);
        item4.setTitle("Questionaire");
        mDrawerItemList.add(item4);

        DrawerItem item5 = new DrawerItem();
        item5.setIcon(R.drawable.ic_analytics);
        item5.setTitle("Analytics");
        mDrawerItemList.add(item5);

        DrawerItem item10 = new DrawerItem();
        item10.setIcon(R.drawable.survey_report);
        item10.setTitle(getResources().getString(R.string.admin));
        mDrawerItemList.add(item10);

        DrawerItem item11 = new DrawerItem();
        item11.setIcon(R.drawable.survey_report);
        item11.setTitle(getResources().getString(R.string.work_flows));
        mDrawerItemList.add(item11);

        DrawerItem item12 = new DrawerItem();
        item12.setIcon(R.drawable.survey_report);
        item12.setTitle(getResources().getString(R.string.teamSurveyReport));
        mDrawerItemList.add(item12);

        DrawerItem item6 = new DrawerItem();
        item6.setIcon(R.drawable.managementteams);
        item6.setTitle("Manage Teams");
        mDrawerItemList.add(item6);

        DrawerItem item7 = new DrawerItem();
        item7.setIcon(R.drawable.help);
        item7.setTitle("Help");
        mDrawerItemList.add(item7);

        DrawerItem item8 = new DrawerItem();
        item8.setIcon(R.drawable.survey_report);
        item8.setTitle("Update Application");
        mDrawerItemList.add(item8);

        DrawerItem item9 = new DrawerItem();
        item9.setIcon(R.drawable.ic_logout);
        item9.setTitle("Logout");
        mDrawerItemList.add(item9);*/


        mDrawerAdapter = new DrawerAdapter(mDrawerItemList, mContext);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mDrawerAdapter);

    }

    private void initialization() {
        mToolbar = findViewById(R.id.appBar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.dashboard);
        mRecyclerView = findViewById(R.id.drawerRecyclerView);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mContainerId = R.id.fragment_container;
        String type = Prefs.getStringPrefs(AppConstants.TYPE);


        if (type.equalsIgnoreCase("cd")) {
            addFragment(new SurveySelectionFragment().newInstance(getResources().getString(R.string.questionairemenu)), mContainerId);
        }  else if (type.equalsIgnoreCase("rm") || type.equalsIgnoreCase("zm")) {
            addFragment(new SurveySelectionFragment().newInstance(AppConstants.WORKFLOWS), mContainerId);
         }else if (type.equalsIgnoreCase("admin")){
            addFragment(new AdminFragment(), mContainerId);
        }

        mDrawerLayout.closeDrawers();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressLint("RestrictedApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
