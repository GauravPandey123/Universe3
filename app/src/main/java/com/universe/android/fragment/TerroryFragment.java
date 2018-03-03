package com.universe.android.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.Territory.TerroritryResponse;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.Territory.TerroritryService;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.Territory.TerrorityRequest;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.web.BaseApiCallback;

import java.util.ArrayList;

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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.stateandcropfragment, container, false);
        return view;
    }


    public void setTerroritry() {
        TerrorityRequest terrorityRequest = new TerrorityRequest();
        terrorityRequest.setState_code(Prefs.getIntegerPrefs(AppConstants.STATECODE));
        TerroritryService terroritryService = new TerroritryService();
        terroritryService.executeService(terrorityRequest, new BaseApiCallback<TerroritryResponse>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(@NonNull TerroritryResponse response) {
                super.onSuccess(response);
            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
            }
        });
    }
}
