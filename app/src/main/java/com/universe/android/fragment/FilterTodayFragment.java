package com.universe.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.activity.BaseActivity;
import com.universe.android.helper.FontClass;

/**
 * Created by gaurav.pandey on 25-01-2018.
 */

public class FilterTodayFragment extends BaseFragment {
    View view;

    private TextView textViewPeriodFrom, textViewPeriodTo, textViewPeriodStatus, textViewReset, textViewApplyFilter;

    private EditText input_period_from, input_period_to, input_period_status;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.filtertodayfragment, container, false);
        initialization();
        setUpElements();
        return view;
    }

    private void setUpElements() {
    }

    private void initialization() {
        textViewPeriodFrom = view.findViewById(R.id.textViewPeriodFrom);
        textViewPeriodTo = view.findViewById(R.id.textViewPeriodTo);
        textViewPeriodStatus = view.findViewById(R.id.textViewPeriodStatus);
        textViewReset = view.findViewById(R.id.textViewReset);
        textViewApplyFilter = view.findViewById(R.id.textViewApplyFilter);
        input_period_from = view.findViewById(R.id.input_period_from);
        input_period_to = view.findViewById(R.id.input_period_to);
        input_period_status = view.findViewById(R.id.input_period_status);

        textViewPeriodFrom.setTypeface(FontClass.openSansRegular(getActivity()));
        textViewPeriodTo.setTypeface(FontClass.openSansRegular(getActivity()));
        textViewPeriodStatus.setTypeface(FontClass.openSansRegular(getActivity()));
        textViewReset.setTypeface(FontClass.openSansRegular(getActivity()));
        textViewApplyFilter.setTypeface(FontClass.openSansLight(getActivity()));
        input_period_from.setTypeface(FontClass.openSansLight(getActivity()));
        input_period_to.setTypeface(FontClass.openSansLight(getActivity()));
        input_period_status.setTypeface(FontClass.openSansLight(getActivity()));


    }
}
