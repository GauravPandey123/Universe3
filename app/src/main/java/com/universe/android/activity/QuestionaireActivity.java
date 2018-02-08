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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.adapter.CardPagerAdapter;
import com.universe.android.fragment.FilterTodayFragment;
import com.universe.android.helper.FontClass;
import com.universe.android.helper.PagerSlidingTabStrip;
import com.universe.android.helper.ShadowTransformer;
import com.universe.android.model.CardItem;

public class QuestionaireActivity extends BaseActivity {
    private ViewPager mViewPager;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private TextView textViewHeader, textViewRetailersNameMap, textViewMobileNoMap, textViewStatusMap;
    private ImageView imageViewSearchBack;
    private EditText editTextSearch;
    private PagerSlidingTabStrip pagerSlidingTabStrip;
    private QuestionaireTabsFilterAdapter questionaireTabsFilterAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionaire_activity);
        initialization();
        setUpElements();
        setUpListeners();

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
        pagerSlidingTabStrip.setTabBackground(R.drawable.c_background_tab);
        pagerSlidingTabStrip.setIndicatorHeight(dptoPixel(4));
        pagerSlidingTabStrip.setIndicatorColor(getResources().getColor(R.color.deactivate_white));
        pagerSlidingTabStrip.setShouldExpand(true);
        pagerSlidingTabStrip.setTextSize((int) getResources().getDimension(R.dimen.text_size_medium));
        pagerSlidingTabStrip.setDeactivateTextColor(getResources().getColor(R.color.deactivate_white));
        pagerSlidingTabStrip.setActivateTextColor(getResources().getColor(R.color.white));
        mViewPager.setOffscreenPageLimit(4);
        questionaireTabsFilterAdapter = new QuestionaireTabsFilterAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(questionaireTabsFilterAdapter);
        pagerSlidingTabStrip.setViewPager(mViewPager);
        pagerSlidingTabStrip.setTypeface(FontClass.openSansRegular(mContext));

//        mCardAdapter = new CardPagerAdapter();
//        mCardAdapter.addCardItem(new CardItem(R.string.text));
//        mCardAdapter.addCardItem(new CardItem(R.string.text));
//        mCardAdapter.addCardItem(new CardItem(R.string.text));
//        mCardAdapter.addCardItem(new CardItem(R.string.text));
//        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
//        mViewPager.setAdapter(mCardAdapter);
//        mViewPager.setPageTransformer(false, mCardShadowTransformer);
//        mViewPager.setOffscreenPageLimit(3);
    }

    // code to set adapter on feedlist view pager
    public class  QuestionaireTabsFilterAdapter extends FragmentPagerAdapter {
        String[] pagetitles = getResources().getStringArray(R.array.filterItem);

        private QuestionaireTabsFilterAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return new FilterTodayFragment();

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

    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    public class QuestionaireTabs extends FragmentPagerAdapter {


        public QuestionaireTabs(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }
    }


}
