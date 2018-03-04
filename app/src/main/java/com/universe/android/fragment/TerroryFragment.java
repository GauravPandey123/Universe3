package com.universe.android.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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
import com.universe.android.adapter.TerroritryAdapter;
import com.universe.android.helper.RecyclerTouchListener;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.StaeAndCropResponse;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.Territory.TerroritryResponse;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.Territory.TerroritryService;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.Territory.TerrorityRequest;
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

public class TerroryFragment extends DialogFragment {
    private View view;

    private TextView textViewState;
    private ImageView imageViewStateClose;

    private RecyclerView recyclerViewStateandCrop;
    private ArrayList<TerroritryResponse.ResponseBean> responseBeans;

    private TerroritryAdapter terroritryAdapter;

    private String terroirtyString;


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
        recyclerViewStateandCrop.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerViewStateandCrop, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                terroirtyString = responseBeans.get(position).getName();
                SubmitTerroitryData setDataListListener = (SubmitTerroitryData) getActivity();
                setDataListListener.submitTerroitryData(terroirtyString);
                dismiss();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void setUpElements() {
        responseBeans = new ArrayList<>();
        recyclerViewStateandCrop.setLayoutManager(new LinearLayoutManager(getActivity()));
        terroritryAdapter = new TerroritryAdapter(getActivity(), responseBeans);
        recyclerViewStateandCrop.setAdapter(terroritryAdapter);
        setUpWebService();
    }

    public void setUpWebService() {
        if (!Utility.isConnected()) {
            Utility.showToast(R.string.msg_disconnected);
        } else {
            setTerroritry();
        }
    }

    private void initialization() {
        textViewState = view.findViewById(R.id.textViewState);
        imageViewStateClose = view.findViewById(R.id.imageViewStateClose);
        recyclerViewStateandCrop = view.findViewById(R.id.recyclerViewStateandCrop);
    }


    public void setTerroritry() {
        TerrorityRequest terrorityRequest = new TerrorityRequest();
        terrorityRequest.setState_code(20016);
        TerroritryService terroritryService = new TerroritryService();
        terroritryService.executeService(terrorityRequest, new BaseApiCallback<TerroritryResponse>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(@NonNull TerroritryResponse response) {
                super.onSuccess(response);
                List<TerroritryResponse.ResponseBean> responseBean = response.getResponse();
                Prefs.putIntegerPrefs(AppConstants.TerroitryCode, responseBean.get(0).getTerritory_code());
                String value = new Gson().toJson(responseBean);
                TerroritryResponse.ResponseBean[] stateBeans = new Gson().fromJson(value, TerroritryResponse.ResponseBean[].class);
                Collections.addAll(responseBeans, stateBeans);
                terroritryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
            }
        });
    }

    public interface SubmitTerroitryData {
        void submitTerroitryData(String Terroitry);
    }
}
