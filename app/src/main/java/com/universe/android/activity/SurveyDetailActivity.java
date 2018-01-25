package com.universe.android.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.universe.android.R;
import com.universe.android.activity.BaseActivity;
import com.universe.android.adapter.SurveyDetailAdapter;
import com.universe.android.fragment.BaseFragment;
import com.universe.android.fragment.FilterTodayFragment;
import com.universe.android.fragment.SurveySelectionFragment;
import com.universe.android.helper.PagerSlidingTabStrip;

/**
 * Created by gaurav.pandey on 24-01-2018.
 */

public class SurveyDetailActivity extends BaseActivity {
    //declare the views here
    private Dialog dialog;

    //decalre the variable here
    private PagerSlidingTabStrip pagerSlidingTabStripFilter;
    private ViewPager viewPagerFIlter;
    private RecyclerView recyclerViewSurveyDetail;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private SurveyDetailAdapter surveyDetailAdapter;
    private ImageView imageViewBack, imageViewFilter,imageViewClose;

    private FilterViewPagerAdapter filterViewPagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surveyreportfragment);
        initialization();
        setUpElements();
        setUpListeners();
    }

    private void setUpListeners() {
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageViewFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void setUpElements() {
        surveyDetailAdapter = new SurveyDetailAdapter(mContext);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewSurveyDetail.setLayoutManager(linearLayoutManager);
        recyclerViewSurveyDetail.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSurveyDetail.setAdapter(surveyDetailAdapter);
    }

    private void initialization() {
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerViewSurveyDetail = findViewById(R.id.recylerViewSurveyDetail);
        imageViewBack = findViewById(R.id.imageviewback);
        imageViewFilter = findViewById(R.id.imageviewfilter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void showDialog() {
        dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.filter_layout);
        dialog.setCanceledOnTouchOutside(true);
        dialogInitialization();
        dialog.show();
    }

    private void dialogInitialization() {
        pagerSlidingTabStripFilter = dialog.findViewById(R.id.pagerSlidingTripFilter);
        viewPagerFIlter = dialog.findViewById(R.id.viewPagerFilter);
        imageViewClose =dialog.findViewById(R.id.imageViewClose);
        pagerSlidingTabStripFilter.setTabBackground(R.drawable.c_background_tab);
        pagerSlidingTabStripFilter.setIndicatorHeight(dptoPixel(4));
        pagerSlidingTabStripFilter.setIndicatorColor(getResources().getColor(R.color.white));
        pagerSlidingTabStripFilter.setShouldExpand(true);
        pagerSlidingTabStripFilter.setTextSize((int) getResources().getDimension(R.dimen.text_size_medium));
        pagerSlidingTabStripFilter.setDeactivateTextColor(getResources().getColor(R.color.white));
        pagerSlidingTabStripFilter.setActivateTextColor(getResources().getColor(R.color.white));
        viewPagerFIlter.setOffscreenPageLimit(4);
//        filterViewPagerAdapter = new FilterViewPagerAdapter(getSupportFragmentManager());
//        viewPagerFIlter.setAdapter(filterViewPagerAdapter);
//        pagerSlidingTabStripFilter.setViewPager(viewPagerFIlter);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public int dptoPixel(int dp) {
        //code to convert dp to pixel
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;
        return (int) Math.ceil(dp * logicalDensity);
    }

    // code to set adapter on feedlist view pager
    public class FilterViewPagerAdapter extends FragmentPagerAdapter {
        String[] pagetitles = getResources().getStringArray(R.array.filterItem);

        private FilterViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
//            if (position == 0) {
//                 new FilterTodayFragment();
//            } else if (position == 1) {
//                 new  FilterTodayFragment();
//            } else {
//                new FilterTodayFragment();
//            }
//            return new FilterTodayFragment();
            return null;
        }

        @Override
        public int getCount() {
            return pagetitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pagetitles[position];
        }


    }


}
