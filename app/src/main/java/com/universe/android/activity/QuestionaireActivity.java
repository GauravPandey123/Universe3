package com.universe.android.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.adapter.CardPagerAdapter;
import com.universe.android.helper.FontClass;
import com.universe.android.helper.ShadowTransformer;
import com.universe.android.model.CardItem;

public class QuestionaireActivity extends BaseActivity {
    private ViewPager mViewPager;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private TextView textViewHeader, textViewRetailersNameMap, textViewMobileNoMap, textViewStatusMap;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionaire_activity);
        initialization();
        setUpElements();

    }

    private void setUpElements() {
        mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem(R.string.text));
        mCardAdapter.addCardItem(new CardItem(R.string.text));
        mCardAdapter.addCardItem(new CardItem(R.string.text));
        mCardAdapter.addCardItem(new CardItem(R.string.text));
        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
    }

    private void initialization() {
        mViewPager = findViewById(R.id.questionaireViewPager);
        textViewHeader = findViewById(R.id.textViewHeader);
        textViewRetailersNameMap = findViewById(R.id.textViewRetailersNameMap);
        textViewMobileNoMap = findViewById(R.id.textViewMobileNoMap);
        textViewStatusMap = findViewById(R.id.textViewStatusMap);

//        textViewHeader.setTypeface(FontClass.openSemiBold(mContext));
//        textViewRetailersNameMap.setTypeface(FontClass.openSansRegular(mContext));
//        textViewMobileNoMap.setTypeface(FontClass.openSansRegular(mContext));
//        textViewStatusMap.setTypeface(FontClass.openSansRegular(mContext));


    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }


}
