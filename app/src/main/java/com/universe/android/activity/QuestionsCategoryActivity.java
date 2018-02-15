package com.universe.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.Utils;
import com.universe.android.R;
import com.universe.android.adapter.QuestioniareSelectionAdapter;
import com.universe.android.helper.FontClass;
import com.universe.android.model.ItemModel;
import com.universe.android.utility.AppConstants;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by gaurav.pandey on 29-01-2018.
 */

public class QuestionsCategoryActivity extends BaseActivity {
    private CircleImageView circleImageViewMap;
    private TextView textViewHeader, textViewRetailersNameMap, textViewMobileNoMap, textViewStatusMap;
    private RecyclerView recyclerViewCateGory;
    private ImageView imageViewSearchBack;
    private String strType;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions_category_activity);
        initialization();
        setUpElements();
        setUpListners();
    }

    private void setUpListners() {
        imageViewSearchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUpElements() {
        recyclerViewCateGory.setLayoutManager(new LinearLayoutManager(mContext));

        final List<ItemModel> data = new ArrayList<>();
        data.add(new ItemModel(
                "Category 1",
                Utils.createInterpolator(Utils.ACCELERATE_DECELERATE_INTERPOLATOR)));
        data.add(new ItemModel(
                "Category 2",
                Utils.createInterpolator(Utils.ACCELERATE_DECELERATE_INTERPOLATOR)));
        data.add(new ItemModel(
                "Category 3",
                Utils.createInterpolator(Utils.ACCELERATE_DECELERATE_INTERPOLATOR)));
        recyclerViewCateGory.setAdapter(new QuestioniareSelectionAdapter(data));
    }

    private void initialization() {
        recyclerViewCateGory=findViewById(R.id.reyclerViewCategory);
        textViewHeader = findViewById(R.id.textViewHeader);
        textViewRetailersNameMap = findViewById(R.id.textViewRetailersNameMap);
        textViewMobileNoMap = findViewById(R.id.textViewMobileNoMap);
        textViewStatusMap = findViewById(R.id.textViewStatusMap);
        imageViewSearchBack =findViewById(R.id.imageviewbackSearch);
        LinearLayout ll_button = (LinearLayout) findViewById(R.id.ll_button);

        textViewHeader.setTypeface(FontClass.openSemiBold(mContext));
        textViewRetailersNameMap.setTypeface(FontClass.openSansRegular(mContext));
        textViewMobileNoMap.setTypeface(FontClass.openSansRegular(mContext));
        textViewStatusMap.setTypeface(FontClass.openSansRegular(mContext));



    }

}
