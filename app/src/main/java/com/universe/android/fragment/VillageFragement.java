package com.universe.android.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
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
import com.universe.android.adapter.DistributorAdapter;
import com.universe.android.adapter.VillageAdapter;
import com.universe.android.helper.RecyclerTouchListener;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.DistributorAndVillage.DistributorRequest;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.DistributorAndVillage.DistributorResponse;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.DistributorAndVillage.DistributorService;
import com.universe.android.resource.Login.login.LoginResponse;
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
 * Created by gaurav.pandey on 04-03-2018.
 */

public class VillageFragement extends DialogFragment {
    View view;
    private TextView textViewState;
    private ImageView imageViewStateClose;

    private SwipeRefreshLayout swipeRefreshLayoutStateandCrop;
    private RecyclerView recyclerViewStateandCrop;

    private ArrayList<DistributorResponse.ResponseBean.VillageBean> stateBeanArrayList;
    private VillageAdapter villageAdapter;
    String villageString;
    private RelativeLayout relativeLayoutSubmit;
    private String villageId = "", villageName = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.stateandcropfragment, container, false);
        initialization();
        setUpElements();
        setUpListeners();
        return view;
    }

    private void initialization() {
        textViewState = view.findViewById(R.id.textViewState);
        imageViewStateClose = view.findViewById(R.id.imageViewStateClose);
        recyclerViewStateandCrop = view.findViewById(R.id.recyclerViewStateandCrop);
        relativeLayoutSubmit = view.findViewById(R.id.realtiveLayoutSubmitVillage);

        textViewState.setText("Select Village");
    }

    private void setUpElements() {
        stateBeanArrayList = new ArrayList<>();
        recyclerViewStateandCrop.setLayoutManager(new LinearLayoutManager(getActivity()));
        villageAdapter = new VillageAdapter(getActivity(), stateBeanArrayList);
        recyclerViewStateandCrop.setAdapter(villageAdapter);
        setUpWebServices();
    }

    private void setUpWebServices() {
        if (!Utility.isConnected()) {
            Utility.showToast(R.string.msg_disconnected);
        } else {
            VillageName();
        }
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
                for (DistributorResponse.ResponseBean.VillageBean number : stateBeanArrayList) {
                    for (String fata : getSaveList()) {
                        if (number.get_id().equals(fata)) {
                            if (stringBuilder.length() > 0)
                                stringBuilder.append(", ");
                            stringBuilder.append(number.getVillage_name());
                        }
                    }
                }
                villageName = stringBuilder.toString();
                VillageSubmission setDataListListener = (VillageSubmission) getActivity();
                setDataListListener.showVillage(villageName, "[" + villageId + "]");
                dismiss();


            }
        });


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


    public void VillageName() {
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
                List<DistributorResponse.ResponseBean.VillageBean> responseBean = response.getResponse().getVillage();
                String value = new Gson().toJson(responseBean);
                DistributorResponse.ResponseBean.VillageBean[] stateBeans = new Gson().fromJson(value, DistributorResponse.ResponseBean.VillageBean[].class);
                Collections.addAll(stateBeanArrayList, stateBeans);
                villageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
            }
        });
    }

    public interface VillageSubmission {
        public void showVillage(String villageString, String villageId);
    }
}
