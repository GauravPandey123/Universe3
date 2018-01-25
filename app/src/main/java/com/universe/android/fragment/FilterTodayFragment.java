package com.universe.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.universe.android.R;
import com.universe.android.activity.BaseActivity;

/**
 * Created by gaurav.pandey on 25-01-2018.
 */

public class FilterTodayFragment extends BaseFragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.filtertodayfragment,container,false);
        return view;
    }
}
