package com.universe.android.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.adapter.StatusAdapter;
import com.universe.android.fragment.CropFragment;
import com.universe.android.fragment.DistributorFragment;
import com.universe.android.fragment.StateAndCropFragment;
import com.universe.android.fragment.TerroryFragment;
import com.universe.android.fragment.VillageFragement;
import com.universe.android.helper.FontClass;
import com.universe.android.model.StatusModel;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.AddNewReatiler.AddNewReatilerRequest;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.AddNewReatiler.AddNewReatilerResponse;
import com.universe.android.resource.Login.NewRetailor.StateAndCrop.AddNewReatiler.AddNewReatilerService;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Log;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;
import com.universe.android.web.BaseApiCallback;

import java.util.ArrayList;

import in.editsoft.api.exception.APIException;

/**
 * Created by gaurav.pandey on 13-02-2018.
 */

public class AddNewRetailorsActivity extends BaseActivity implements StateAndCropFragment.SetStateData, TerroryFragment.SubmitTerroitryData, CropFragment.SetCropData, DistributorFragment.submitDistributor, VillageFragement.VillageSubmission {

    private ImageView imageviewbackRetailors;
    private TextView textViewRetailorsName;
    private TextInputLayout textInputLayoutRetailorCrop, textInputLayoutRetailorName, textInputLayoutRetailorTerroitryName, textInputLayoutTerroitryAddress, textInputLayoutRetailorTotalSales;
    private TextInputLayout textInputLayoutRetailorMobileNumber, textInputLayoutRetailorDistributorName, textInputLayoutRetailorFocusVillage;
    private EditText retailorName, retailorTerroitry, retailorTerroitryAddress, retailorPhoneNumber, retailorTerroitryRetailorDistributor;
    private EditText retailorTerroitryRetailorTotalSales, retailorTerroitryRetailorFocusVillage, retailorTerroitryCrop;
    private TextView textViewRetailors;
    private TextInputLayout textInputLayoutRetailorState;
    private EditText editTextRetailersState;
    private TextInputLayout textInputLayoutTerroitryPincode;
    private EditText editTextTerroitryPinCode;
    private StatusAdapter statusAdapter;
    private ArrayList<StatusModel> statusModels;
    private ArrayList<StatusModel> multiselectSatuslist = new ArrayList<>();
    private String cropString, stateDataString, terroritryDataString, distributorString, villageSubmitString;
    private String reatilersNameString, addressString, phoneString, totalSalesString, pincodeString;
    private RelativeLayout relativeLayoutSubmit;


    FragmentManager fm = getSupportFragmentManager();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_retailors_activity);
        initialization();
        setUpElements();
        setUpListeners();
    }

    private void setUpListeners() {
        retailorTerroitry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TerroryFragment dFragment = new TerroryFragment();
                dFragment.show(fm, "Dialog Fragment");
            }
        });
        retailorTerroitryRetailorDistributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DistributorFragment dFragment = new DistributorFragment();
                dFragment.show(fm, "Dialog Fragment");
            }
        });


        retailorTerroitryRetailorFocusVillage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VillageFragement dFragment = new VillageFragement();
                dFragment.show(fm, "Dialog Fragment");
            }
        });
        retailorTerroitryCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropFragment cropFragment = new CropFragment();
                cropFragment.show(fm, "Dialog Fragment");
            }
        });
        editTextRetailersState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StateAndCropFragment dFragment = new StateAndCropFragment();
                dFragment.show(fm, "Dialog Fragment");
            }
        });
        relativeLayoutSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitDetails();
            }
        });

        imageviewbackRetailors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    private void setUpElements() {
        retailorName.addTextChangedListener(new MyTextWatcher(retailorName));
        retailorPhoneNumber.addTextChangedListener(new MyTextWatcher(retailorPhoneNumber));
        retailorTerroitryAddress.addTextChangedListener(new MyTextWatcher(retailorTerroitryAddress));
        retailorTerroitry.addTextChangedListener(new MyTextWatcher(retailorTerroitry));
        retailorTerroitryRetailorDistributor.addTextChangedListener(new MyTextWatcher(retailorTerroitryRetailorDistributor));
        retailorTerroitryRetailorTotalSales.addTextChangedListener(new MyTextWatcher(retailorTerroitryRetailorTotalSales));
        retailorTerroitryRetailorFocusVillage.addTextChangedListener(new MyTextWatcher(retailorTerroitryRetailorFocusVillage));
        editTextRetailersState.addTextChangedListener(new MyTextWatcher(editTextRetailersState));
        editTextTerroitryPinCode.addTextChangedListener(new MyTextWatcher(editTextTerroitryPinCode));
        retailorTerroitryCrop.addTextChangedListener(new MyTextWatcher(retailorTerroitryCrop));

    }


    //
    public void submitDetails() {
        if (!validateName()) {
            return;
        }
        if (!validateAddress()) {
            return;
        }
        if (!validateDistributoName()) {
            return;
        }
        if (!validateFocusVillage()) {
            return;
        }
        if (!validateTerroitryName()) {
            return;
        }
        if (!validateTotalSales()) {
            return;
        }
        if (!validatePhone()) {
            return;
        }
        if (!validateCrop()) {
            return;
        }
        if (!validateState()) {
            return;
        }
        if (!validatePincode()) {
            return;
        }
        reatilersNameString = retailorName.getText().toString();
        addressString = retailorTerroitryAddress.getText().toString();
        phoneString = retailorPhoneNumber.getText().toString();
        pincodeString = editTextTerroitryPinCode.getText().toString();
        totalSalesString = retailorTerroitryRetailorTotalSales.getText().toString();
        if (!Utility.isConnected()) {
            Utility.showToast(R.string.msg_disconnected);
        } else {
            addNewReatiler(reatilersNameString, phoneString, addressString, pincodeString, totalSalesString);
        }

    }

    private boolean validateName() {
        if (retailorName.getText().toString().trim().isEmpty()) {
            textInputLayoutRetailorName.setError(getString(R.string.err_msg_name));
            requestFocus(retailorName);
            return false;
        } else {
            textInputLayoutRetailorName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateCrop() {
        if (retailorTerroitryCrop.getText().toString().trim().isEmpty()) {
            textInputLayoutRetailorCrop.setError(getString(R.string.err_msg_majorCrop));
            requestFocus(retailorTerroitryCrop);
            return false;
        } else {
            textInputLayoutRetailorCrop.setErrorEnabled(false);
        }

        return true;
    }


    private boolean validateAddress() {
        if (retailorTerroitryAddress.getText().toString().trim().isEmpty()) {
            textInputLayoutTerroitryAddress.setError(getString(R.string.err_msg_address));
            requestFocus(retailorTerroitryAddress);
            return false;
        } else {
            textInputLayoutTerroitryAddress.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateTerroitryName() {
        if (retailorTerroitryAddress.getText().toString().trim().isEmpty()) {
            textInputLayoutTerroitryAddress.setError(getString(R.string.err_msg_terroritry));
            requestFocus(retailorTerroitryAddress);
            return false;
        } else {
            textInputLayoutRetailorName.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateDistributoName() {
        if (retailorTerroitryRetailorDistributor.getText().toString().trim().isEmpty()) {
            textInputLayoutRetailorDistributorName.setError(getString(R.string.err_msg_distributor));
            requestFocus(retailorTerroitryRetailorDistributor);
            return false;
        } else {
            textInputLayoutRetailorDistributorName.setErrorEnabled(false);
        }
        return true;
    }


    private boolean validateTotalSales() {
        if (retailorTerroitryRetailorTotalSales.getText().toString().trim().isEmpty()) {
            textInputLayoutRetailorTotalSales.setError(getString(R.string.err_msg_totalSales));
            requestFocus(retailorTerroitryRetailorTotalSales);
            return false;
        } else {
            textInputLayoutRetailorTotalSales.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePincode() {
        if (editTextTerroitryPinCode.getText().toString().trim().isEmpty()) {
            textInputLayoutTerroitryPincode.setError(getString(R.string.err_msg_pincode));
            requestFocus(editTextTerroitryPinCode);
            return false;
        } else {
            textInputLayoutTerroitryPincode.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateState() {
        if (editTextRetailersState.getText().toString().trim().isEmpty()) {
            textInputLayoutRetailorState.setError(getString(R.string.err_msg_state));
            requestFocus(editTextRetailersState);
            return false;
        } else {
            textInputLayoutRetailorState.setErrorEnabled(false);
        }
        return true;
    }


    private boolean validateFocusVillage() {
        if (retailorTerroitryRetailorFocusVillage.getText().toString().trim().isEmpty()) {
            textInputLayoutRetailorFocusVillage.setError(getString(R.string.err_msg_focusVillage));
            requestFocus(retailorTerroitryRetailorFocusVillage);
            return false;
        } else {
            textInputLayoutRetailorFocusVillage.setErrorEnabled(false);
        }
        return true;
    }

    public boolean validatePhone() {
        String phone = retailorPhoneNumber.getText().toString().trim();
        if (phone.isEmpty() || !isValidPhone(phone)) {
            textInputLayoutRetailorMobileNumber.setError(getString(R.string.err_msg_phone));
            requestFocus(retailorPhoneNumber);
            return false;
        } else {
            textInputLayoutRetailorMobileNumber.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidPhone(String phone) {
        return !TextUtils.isEmpty(phone) && Patterns.PHONE.matcher(phone).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void initialization() {
        statusModels = new ArrayList<>();
        imageviewbackRetailors = findViewById(R.id.imageviewbackRetailors);
        textViewRetailorsName = findViewById(R.id.textViewRetailorsName);
        textInputLayoutRetailorName = findViewById(R.id.textInputLayoutRetailorName);
        textInputLayoutRetailorTerroitryName = findViewById(R.id.textInputLayoutRetailorTerroitryName);
        textInputLayoutTerroitryAddress = findViewById(R.id.textInputLayoutTerroitryAddress);
        textInputLayoutRetailorMobileNumber = findViewById(R.id.textInputLayoutRetailorMobileNumber);
        textInputLayoutRetailorDistributorName = findViewById(R.id.textInputLayoutRetailorDistributorName);
        textInputLayoutRetailorFocusVillage = findViewById(R.id.textInputLayoutRetailorFocusVillage);
        retailorTerroitryRetailorDistributor = findViewById(R.id.retailorTerroitryRetailorDistributor);
        retailorName = findViewById(R.id.retailorName);
        retailorTerroitry = findViewById(R.id.retailorTerroitry);
        retailorTerroitryAddress = findViewById(R.id.retailorTerroitryAddress);
        retailorPhoneNumber = findViewById(R.id.retailorPhoneNumber);
        retailorTerroitryRetailorTotalSales = findViewById(R.id.retailorTerroitryRetailorTotalSales);
        relativeLayoutSubmit = findViewById(R.id.relativeLayoutSubmit);

        textViewRetailors = findViewById(R.id.textViewRetailors);
        textInputLayoutRetailorTotalSales = findViewById(R.id.textInputLayoutRetailorTotalSales);
        retailorTerroitryRetailorFocusVillage = findViewById(R.id.retailorTerroitryRetailorFocusVillage);
        retailorTerroitryCrop = findViewById(R.id.retailorTerroitryCrop);
        textInputLayoutRetailorCrop = findViewById(R.id.textInputLayoutRetailorCrop);
        editTextRetailersState = findViewById(R.id.retailorState);
        textInputLayoutRetailorState = findViewById(R.id.textInputLayoutRetailorState);
        textInputLayoutTerroitryPincode = findViewById(R.id.textInputLayoutTerroitryPincode);
        editTextTerroitryPinCode = findViewById(R.id.retailorTerroitryPinCode);

        textViewRetailorsName.setTypeface(FontClass.openSansRegular(mContext));
        textInputLayoutRetailorName.setTypeface(FontClass.openSansRegular(mContext));
        textInputLayoutRetailorTerroitryName.setTypeface(FontClass.openSansRegular(mContext));
        textInputLayoutTerroitryAddress.setTypeface(FontClass.openSansRegular(mContext));
        textInputLayoutRetailorMobileNumber.setTypeface(FontClass.openSansRegular(mContext));
        textInputLayoutRetailorDistributorName.setTypeface(FontClass.openSansRegular(mContext));
        textInputLayoutRetailorFocusVillage.setTypeface(FontClass.openSansRegular(mContext));
        retailorName.setTypeface(FontClass.openSansRegular(mContext));
        retailorTerroitry.setTypeface(FontClass.openSansRegular(mContext));
        retailorTerroitryAddress.setTypeface(FontClass.openSansRegular(mContext));
        retailorPhoneNumber.setTypeface(FontClass.openSansRegular(mContext));
        retailorTerroitryRetailorTotalSales.setTypeface(FontClass.openSansRegular(mContext));
        textViewRetailors.setTypeface(FontClass.openSansRegular(mContext));
        textInputLayoutRetailorCrop.setTypeface(FontClass.openSansRegular(mContext));
        retailorTerroitryCrop.setTypeface(FontClass.openSansRegular(mContext));

        stateDataString = editTextRetailersState.getText().toString();
        terroritryDataString = retailorTerroitry.getText().toString();
        cropString = retailorTerroitryCrop.getText().toString();
        distributorString = retailorTerroitryRetailorDistributor.getText().toString();

    }

    @Override
    public void submitStateData(String State) {
        stateDataString = State;
        editTextRetailersState.setText(stateDataString);
    }

    @Override
    public void submitTerroitryData(String Terroitry) {
        terroritryDataString = Terroitry;
        retailorTerroitry.setText(Terroitry);
    }

    @Override
    public void submitCropData(String State) {
        cropString = State;
        retailorTerroitryCrop.setText(cropString);
    }

    @Override
    public void submitDistriButor(String DistributorName) {
        distributorString = DistributorName;
        retailorTerroitryRetailorDistributor.setText(distributorString);

    }

    @Override
    public void showVillage(String villageString) {
        villageSubmitString = villageString;
        retailorTerroitryRetailorFocusVillage.setText(villageString);
    }

    public void addNewReatiler(String reiltersName, String phonee, String Address, String pinCode, String totalSales) {
        showProgress();
        AddNewReatilerRequest addNewReatilerRequest = new AddNewReatilerRequest();
        addNewReatilerRequest.setRetailerName(reiltersName);
        addNewReatilerRequest.setMobile(phonee);
        addNewReatilerRequest.setAddress(Address);
        addNewReatilerRequest.setPincode(pinCode);
        addNewReatilerRequest.setTotalSales(totalSales);
        addNewReatilerRequest.setState_code(Prefs.getIntegerPrefs(AppConstants.STATECODE));
        addNewReatilerRequest.setTerritory_code(Prefs.getIntegerPrefs(AppConstants.TerroitryCode));
        addNewReatilerRequest.setDistributer_code(Prefs.getStringPrefs(AppConstants.Distributor_Id));
        addNewReatilerRequest.setVillage_code(Prefs.getStringPrefs(AppConstants.VillageId));
        addNewReatilerRequest.setCropId(Prefs.getStringPrefs(AppConstants.CROPID));

        AddNewReatilerService addNewReatilerService = new AddNewReatilerService();
        addNewReatilerService.executeService(addNewReatilerRequest, new BaseApiCallback<AddNewReatilerResponse>() {
            @Override
            public void onComplete() {
               dismissProgress();
            }

            @Override
            public void onSuccess(@NonNull AddNewReatilerResponse response) {
                super.onSuccess(response);
                Log.e("success", "success");
                finish();
            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
                Log.e("failure", "failure");
            }
        });
    }

    public class MyTextWatcher implements TextWatcher {
        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.retailorName:
                    validateName();
                    break;
                case R.id.retailorPhoneNumber:
                    validatePhone();
                    break;
                case R.id.retailorTerroitry:
                    validateTerroitryName();
                    break;
                case R.id.retailorTerroitryAddress:
                    validateAddress();
                    break;
                case R.id.retailorTerroitryRetailorDistributor:
                    validateDistributoName();
                    break;
                case R.id.retailorTerroitryRetailorTotalSales:
                    validateTotalSales();
                    break;
                case R.id.retailorTerroitryRetailorFocusVillage:
                    validateFocusVillage();
                    break;
                case R.id.retailorTerroitryPinCode:
                    validatePincode();
                    break;
                case R.id.retailorState:
                    validateState();
                    break;

            }

        }
    }


}
