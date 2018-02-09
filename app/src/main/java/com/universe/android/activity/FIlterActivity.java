package com.universe.android.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.fragment.QuestionsCategoryFragment;
import com.universe.android.helper.FontClass;
import com.universe.android.helper.PagerSlidingTabStrip;

/**
 * Created by Gaurav Pandey on 27-01-2018.
 */

public class FIlterActivity extends BaseActivity {
    private PagerSlidingTabStrip pagerSlidingTabStripFilter;
    private ViewPager viewPagerFIlter;
    private FilterViewPagerAdapter filterViewPagerAdapter;
    private ImageView imageViewClose;
    private TextView textViewFilter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_layout);
        initialization();
        setUpElements();
        setUpListeners();
    }

    private void setUpListeners() {
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUpElements() {
    }

    private void initialization() {
        pagerSlidingTabStripFilter = findViewById(R.id.pagerSlidingTripFilter);
        viewPagerFIlter = findViewById(R.id.viewPagerFilter);
        imageViewClose = findViewById(R.id.imageViewClose);
        textViewFilter = findViewById(R.id.textViewFilter);
        textViewFilter.setTypeface(FontClass.openSansBold(mContext));
        pagerSlidingTabStripFilter.setTabBackground(R.drawable.c_background_tab);
        pagerSlidingTabStripFilter.setIndicatorHeight(dptoPixel(4));
        pagerSlidingTabStripFilter.setIndicatorColor(getResources().getColor(R.color.deactivate_white));
        pagerSlidingTabStripFilter.setShouldExpand(true);
        pagerSlidingTabStripFilter.setTextSize((int) getResources().getDimension(R.dimen.text_size_medium));
        pagerSlidingTabStripFilter.setDeactivateTextColor(getResources().getColor(R.color.deactivate_white));
        pagerSlidingTabStripFilter.setActivateTextColor(getResources().getColor(R.color.white));
        viewPagerFIlter.setOffscreenPageLimit(4);
        filterViewPagerAdapter = new FilterViewPagerAdapter(getSupportFragmentManager());
        viewPagerFIlter.setAdapter(filterViewPagerAdapter);
        pagerSlidingTabStripFilter.setViewPager(viewPagerFIlter);

        pagerSlidingTabStripFilter.setTypeface(FontClass.openSansRegular(mContext));


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

            return new QuestionsCategoryFragment();

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
