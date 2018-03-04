package com.universe.android.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.universe.android.R;
import com.universe.android.adapter.StateAdapter;
import com.universe.android.helper.RecyclerTouchListener;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.StaeAndCropResponse;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.StateAndCropRequest;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.StateAndCropService;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;
import com.universe.android.web.BaseApiCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import in.editsoft.api.exception.APIException;

/**
 * Created by gaurav.pandey on 03-03-2018.
 */

public class StateAndCropFragment extends DialogFragment {
    private View view;
    private TextView textViewState;
    private ImageView imageViewStateClose;

    private SwipeRefreshLayout swipeRefreshLayoutStateandCrop;
    private RecyclerView recyclerViewStateandCrop;

    private ArrayList<StaeAndCropResponse.ResponseBean.StateBean> stateBeanArrayList;
    private StateAdapter stateAdapter;
    String stateString;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.stateandcropfragment, container, false);
        initialization();
        setUpElements();
        setUpListeners();
        return view;
    }

    private void setUpListeners() {
        imageViewStateClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        recyclerViewStateandCrop.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerViewStateandCrop, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                stateString = stateBeanArrayList.get(position).getState_name();
                SetStateData setDataListListener = (SetStateData) getActivity();
                setDataListListener.submitStateData(stateString);
                dismiss();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void setUpElements() {
        stateBeanArrayList = new ArrayList<>();
        recyclerViewStateandCrop.setLayoutManager(new LinearLayoutManager(getActivity()));
        stateAdapter = new StateAdapter(getActivity(), stateBeanArrayList);
        recyclerViewStateandCrop.setAdapter(stateAdapter);
        setUpWebservice();

    }

    private void setUpWebservice() {
        if (!Utility.isConnected()) {
            Utility.showToast(R.string.msg_disconnected);
        } else {
            stateService();
        }
    }


    private void initialization() {
        textViewState = view.findViewById(R.id.textViewState);
        imageViewStateClose = view.findViewById(R.id.imageViewStateClose);
        recyclerViewStateandCrop = view.findViewById(R.id.recyclerViewStateandCrop);
    }

    public void stateService() {
        StateAndCropRequest stateAndCropRequest = new StateAndCropRequest();
        StateAndCropService stateAndCropService = new StateAndCropService();
        stateAndCropService.executeService(stateAndCropRequest, new BaseApiCallback<StaeAndCropResponse>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(@NonNull StaeAndCropResponse response) {
                super.onSuccess(response);
                List<StaeAndCropResponse.ResponseBean.StateBean> responseBean = response.getResponse().getState();
                Prefs.putIntegerPrefs(AppConstants.STATECODE,responseBean.get(0).getState_code());
                String value = new Gson().toJson(responseBean);
                StaeAndCropResponse.ResponseBean.StateBean[] stateBeans = new Gson().fromJson(value, StaeAndCropResponse.ResponseBean.StateBean[].class);
                Collections.addAll(stateBeanArrayList, stateBeans);
                stateAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
            }
        });
    }





    public interface SetStateData {
        public void submitStateData(String State);
    }
}
