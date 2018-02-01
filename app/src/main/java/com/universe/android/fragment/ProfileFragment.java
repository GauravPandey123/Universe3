package com.universe.android.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.helper.FontClass;

/**
 * Created by gaurav.pandey on 31-01-2018.
 */

public class ProfileFragment extends Fragment {
    View view;

    TextView textViewUserNameProfile, textViewPhoneNumberProfile, textViewRolesProfile, textViewLob, textViewTLerritor;

    EditText editTextPhoneProfile, editTextRolesProfile, editTextLob, editTextTerritory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_fragement, container, false);
        initialization();
        setUpElements();
        return view;
    }

    private void setUpElements() {
        textViewUserNameProfile.setTypeface(FontClass.openSansRegular(getActivity()));
        textViewPhoneNumberProfile.setTypeface(FontClass.openSansRegular(getActivity()));
        textViewRolesProfile.setTypeface(FontClass.openSansRegular(getActivity()));
        textViewLob.setTypeface(FontClass.openSansRegular(getActivity()));
        textViewTLerritor.setTypeface(FontClass.openSansRegular(getActivity()));
        editTextPhoneProfile.setTypeface(FontClass.openSansLight(getActivity()));
        editTextRolesProfile.setTypeface(FontClass.openSansLight(getActivity()));
        editTextLob.setTypeface(FontClass.openSansLight(getActivity()));
        editTextTerritory.setTypeface(FontClass.openSansLight(getActivity()));
    }

    private void initialization() {
        textViewUserNameProfile = view.findViewById(R.id.textViewUserNameProfile);
        textViewPhoneNumberProfile = view.findViewById(R.id.textViewPhoneNumberProfile);
        textViewRolesProfile = view.findViewById(R.id.textViewRolesProfile);
        textViewLob = view.findViewById(R.id.textViewLob);
        textViewTLerritor = view.findViewById(R.id.textViewTLerritory);
        editTextPhoneProfile = view.findViewById(R.id.editTextPhoneProfile);
        editTextRolesProfile = view.findViewById(R.id.editTextRolesProfile);
        editTextLob = view.findViewById(R.id.editTextLob);
        editTextTerritory = view.findViewById(R.id.editTextTerritory);

    }
}
