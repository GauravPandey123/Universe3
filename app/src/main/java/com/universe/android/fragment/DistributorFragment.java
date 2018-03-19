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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.universe.android.R;
import com.universe.android.adapter.CropAdapter;
import com.universe.android.adapter.DistributorAdapter;
import com.universe.android.helper.RecyclerTouchListener;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.DistributorAndVillage.DistributorRequest;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.DistributorAndVillage.DistributorResponse;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.DistributorAndVillage.DistributorService;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.StaeAndCropResponse;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;
import com.universe.android.web.BaseApiCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import in.editsoft.api.exception.APIException;

/**
 * Created by gaurav.pandey on 04-03-2018.
 */

public class DistributorFragment extends DialogFragment {
    View view;
    private TextView textViewState;
    private ImageView imageViewStateClose;

    private SwipeRefreshLayout swipeRefreshLayoutStateandCrop;
    private RecyclerView recyclerViewStateandCrop;

    private ArrayList<DistributorResponse.ResponseBean.DistributerBean> stateBeanArrayList;
    private DistributorAdapter distributorAdapter;
    String stateString;
    private RelativeLayout realtiveLayoutSubmitVillage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.stateandcropfragment, container, false);
        inittialization();
        setUpElements();
        setUpListeners();
        return view;
    }

    private void inittialization() {
        textViewState = view.findViewById(R.id.textViewState);
        imageViewStateClose = view.findViewById(R.id.imageViewStateClose);
        recyclerViewStateandCrop = view.findViewById(R.id.recyclerViewStateandCrop);
        realtiveLayoutSubmitVillage=view.findViewById(R.id.realtiveLayoutSubmitVillage);
        realtiveLayoutSubmitVillage.setVisibility(View.GONE);
        textViewState.setText("Distributor Name");

    }

    private void setUpElements() {
        stateBeanArrayList = new ArrayList<>();
        recyclerViewStateandCrop.setLayoutManager(new LinearLayoutManager(getActivity()));
        distributorAdapter = new DistributorAdapter(getActivity(), stateBeanArrayList);
        recyclerViewStateandCrop.setAdapter(distributorAdapter);
        setUpWebServices();
    }

    private void setUpWebServices() {
        if (!Utility.isConnected()) {
            Utility.showToast(R.string.msg_disconnected);
        } else {
            distributorName();
        }
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
                stateString = stateBeanArrayList.get(position).getName();
                submitDistributor setDataListListener = (submitDistributor) getActivity();
                setDataListListener.submitDistriButor(stateString);
                dismiss();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }


    public void distributorName() {
        DistributorRequest distributorRequest = new DistributorRequest();
        distributorRequest.setTerritory_code(652);
        DistributorService distributorService = new DistributorService();
        distributorService.executeService(distributorRequest, new BaseApiCallback<DistributorResponse>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(@NonNull DistributorResponse response) {
                super.onSuccess(response);
                List<DistributorResponse.ResponseBean.DistributerBean> responseBean = response.getResponse().getDistributer();
                Prefs.putStringPrefs(AppConstants.Distributor_Id, responseBean.get(0).get_id());
                String value = new Gson().toJson(responseBean);
                DistributorResponse.ResponseBean.DistributerBean[] stateBeans = new Gson().fromJson(value, DistributorResponse.ResponseBean.DistributerBean[].class);
                Collections.addAll(stateBeanArrayList, stateBeans);
                distributorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
            }
        });
    }

    public interface submitDistributor {
        public void submitDistriButor(String DistributorName);
    }
}
