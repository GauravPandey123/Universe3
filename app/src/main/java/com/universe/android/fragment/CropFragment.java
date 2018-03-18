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

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.universe.android.R;
import com.universe.android.adapter.CropAdapter;
import com.universe.android.adapter.StateAdapter;
import com.universe.android.helper.RecyclerTouchListener;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.DistributorAndVillage.DistributorResponse;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.StaeAndCropResponse;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.StateAndCropRequest;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.StateAndCropService;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;
import com.universe.android.web.BaseApiCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import in.editsoft.api.exception.APIException;

/**
 * Created by gaurav.pandey on 03-03-2018.
 */

public class CropFragment extends DialogFragment {
    private View view;
    private TextView textViewState;
    private ImageView imageViewStateClose;

    private SwipeRefreshLayout swipeRefreshLayoutStateandCrop;
    private RecyclerView recyclerViewStateandCrop;

    private ArrayList<StaeAndCropResponse.ResponseBean.CropBean> stateBeanArrayList;
    private CropAdapter cropAdapter;
    String stateString;
    private String villageId = "", villageName = "";
    private RelativeLayout relativeLayoutSubmit;


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
        relativeLayoutSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < getSaveList().size(); i++) {
                    if (getSaveList().get(i) != null) {
                        villageId += "\"" + getSaveList().get(i) + "\"";
                        if (i < (getSaveList().size() - 1)) {
                            villageId += ", ";
                        }
                    }
                }

                StringBuilder stringBuilder = new StringBuilder();
                for (StaeAndCropResponse.ResponseBean.CropBean number : stateBeanArrayList) {
                    for (String fata : getSaveList()) {
                        if (number.get_id().equals(fata)) {
                            if (stringBuilder.length() > 0)
                                stringBuilder.append(", ");
                            stringBuilder.append(number.getCROP_NAME());
                        }
                    }
                }
                villageName = stringBuilder.toString();
                SetCropData setDataListListener = (SetCropData) getActivity();
                setDataListListener.submitCropData(villageName, "["+villageId+"]");
                dismiss();


            }
        });
    }

    private void setUpElements() {
        stateBeanArrayList = new ArrayList<>();
        recyclerViewStateandCrop.setLayoutManager(new LinearLayoutManager(getActivity()));
        cropAdapter = new CropAdapter(getActivity(), stateBeanArrayList);
        recyclerViewStateandCrop.setAdapter(cropAdapter);
        setUpWebservice();
    }

    private void setUpWebservice() {
        if (!Utility.isConnected()) {
            Utility.showToast(R.string.msg_disconnected);
        } else {
            cropService();
        }
    }

    public void cropService() {
        StateAndCropRequest stateAndCropRequest = new StateAndCropRequest();
        StateAndCropService stateAndCropService = new StateAndCropService();
        stateAndCropService.executeService(stateAndCropRequest, new BaseApiCallback<StaeAndCropResponse>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(@NonNull StaeAndCropResponse response) {
                super.onSuccess(response);
                List<StaeAndCropResponse.ResponseBean.CropBean> responseBean = response.getResponse().getCrop();
                String value = new Gson().toJson(responseBean);
                StaeAndCropResponse.ResponseBean.CropBean[] stateBeans = new Gson().fromJson(value, StaeAndCropResponse.ResponseBean.CropBean[].class);
                Collections.addAll(stateBeanArrayList, stateBeans);
                cropAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
            }
        });
    }

    private void initialization() {
        textViewState = view.findViewById(R.id.textViewState);
        imageViewStateClose = view.findViewById(R.id.imageViewStateClose);
        recyclerViewStateandCrop = view.findViewById(R.id.recyclerViewStateandCrop);
        relativeLayoutSubmit = view.findViewById(R.id.realtiveLayoutSubmitVillage);
        textViewState.setText("Select Crop");
    }

    public interface SetCropData {
        public void submitCropData(String State, String cropId);
    }

    public ArrayList<String> getSaveList() {
        ArrayList<String> arrayList;
        Gson gson = new Gson();
        String json = Prefs.getStringPrefs(AppConstants.VillageData);
        if (!json.equals("")) {
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            arrayList = gson.fromJson(json, type);

        } else {
            arrayList = new ArrayList<>();
        }
        return arrayList;
    }
}
