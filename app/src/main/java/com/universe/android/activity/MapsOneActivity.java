package com.universe.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.universe.android.R;
import com.universe.android.helper.FontClass;
import com.universe.android.realmbean.RealmCustomer;
import com.universe.android.resource.Login.LocationUpdate.UpDateLocationResponse;
import com.universe.android.resource.Login.LocationUpdate.UpadteLocationRequest;
import com.universe.android.resource.Login.LocationUpdate.UpdateLocationService;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;
import com.universe.android.web.BaseApiCallback;

import de.hdodenhof.circleimageview.CircleImageView;
import in.editsoft.api.exception.APIException;
import io.realm.Realm;

public class MapsOneActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, PlaceSelectionListener {

    private GoogleMap mMap;
    private TextView textViewHeader, textViewRetailersNameMap, textViewMobileNoMap, textViewStatusMap, textViewSetLocation;
    private String title, surveyId, customerId;
    private ImageView imageViewLocation;

    private ImageView imageViewSearch, imageViewSearchBack;
    private Activity activity;
    private CircleImageView circleImageViewMap;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(this);

        Intent intent = getIntent();
        if (intent != null) {
            title = intent.getExtras().getString(AppConstants.STR_TITLE);
            surveyId = intent.getExtras().getString(AppConstants.SURVEYID);
            customerId = intent.getExtras().getString(AppConstants.CUSTOMERID);
        }

        initialization();
        setUpElements();
        setUpListners();
        setupDetail();

    }

    private void initialization() {
        imageViewSearchBack = findViewById(R.id.imageviewbackSearch);
        circleImageViewMap = findViewById(R.id.circularImageViewMap);
        textViewHeader = findViewById(R.id.textViewHeader);
        textViewRetailersNameMap = findViewById(R.id.textViewRetailersNameMap);
        textViewMobileNoMap = findViewById(R.id.textViewMobileNoMap);
        textViewStatusMap = findViewById(R.id.textViewStatusMap);
        textViewSetLocation = findViewById(R.id.textViewSetLocation);
        imageViewLocation = findViewById(R.id.imageViewLocation);

        textViewHeader.setTypeface(FontClass.openSemiBold(mContext));
        textViewRetailersNameMap.setTypeface(FontClass.openSansRegular(mContext));
        textViewMobileNoMap.setTypeface(FontClass.openSansRegular(mContext));
        textViewStatusMap.setTypeface(FontClass.openSansRegular(mContext));
        textViewSetLocation.setTypeface(FontClass.openSansRegular(mContext));

        textViewHeader.setText(title);
    }


    private void setUpElements() {

    }

    private void setUpListners() {
        textViewSetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateLocationService(Prefs.getStringPrefs(AppConstants.LATTITUDE), Prefs.getStringPrefs(AppConstants.LONGITUDE));
                updateLocationServiceEmployee(Prefs.getStringPrefs(AppConstants.LATTITUDE), Prefs.getStringPrefs(AppConstants.LONGITUDE));
            }
        });

        imageViewSearchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        imageViewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMap(new LatLng(28.4595, 77.0266), "");
            }
        });
    }

    private void setupDetail() {
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmCustomer realmCustomer = realm.where(RealmCustomer.class).equalTo(AppConstants.ID, customerId).findFirst();

            if (Utility.validateString(realmCustomer.getName()))
                textViewRetailersNameMap.setText(realmCustomer.getName());
            textViewMobileNoMap.setText(String.format("%s | %s | %s  \nPincode - %s", realmCustomer.getContactNo(), realmCustomer.getTerritory(), realmCustomer.getState(), realmCustomer.getPincode()));

        } catch (Exception e0) {
            e0.printStackTrace();
            realm.close();
        } finally {
            if (!realm.isClosed()) {
                realm.close();
            }

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        updateMap(new LatLng(28.4595, 77.0266), "");
    }

    @Override
    public void onMapClick(LatLng latLng) {
        updateMap(latLng, "User Tap Location");
    }

    @Override
    public void onPlaceSelected(Place place) {
        Toast.makeText(this, "Place Selected".concat(place.getAddress().toString()), Toast.LENGTH_SHORT).show();
        updateMap(place.getLatLng(), String.valueOf(place.getName()));


    }

    @Override
    public void onError(Status status) {
        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }

    private void updateMap(LatLng latLng, String place) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.latitude, latLng.longitude), 10.0f));
        Prefs.putStringPrefs(AppConstants.LATTITUDE, String.valueOf(latLng.latitude));
        Prefs.putStringPrefs(AppConstants.LONGITUDE, String.valueOf(latLng.longitude));
    }

    public void updateLocationService(String lat, String lan) {
        showProgress();
        UpadteLocationRequest upadteLocationRequest = new UpadteLocationRequest();
        upadteLocationRequest.setUserId("5a8eb8b82741361f5827afb5");
        upadteLocationRequest.setLat(lat);
        upadteLocationRequest.setLng(lan);
        upadteLocationRequest.setType(AppConstants.customer);
        upadteLocationRequest.setCustomerId(customerId);
        UpdateLocationService updateLocationService = new UpdateLocationService();
        updateLocationService.executeService(upadteLocationRequest, new BaseApiCallback<UpDateLocationResponse>() {
            @Override
            public void onComplete() {
                dismissProgress();
            }

            @Override
            public void onSuccess(@NonNull UpDateLocationResponse response) {
                super.onSuccess(response);
                Prefs.putStringPrefs(AppConstants.LATTITUDE, response.getResponse().getLat());
                Prefs.putStringPrefs(AppConstants.LONGITUDE, response.getResponse().getLongX());
            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
                Utility.showToast(e.getData());
            }
        });


    }

    public void updateLocationServiceEmployee(String lat, String lan) {
        showProgress();
        UpadteLocationRequest upadteLocationRequest = new UpadteLocationRequest();
        upadteLocationRequest.setUserId("5a8eb8b82741361f5827afb5");
        upadteLocationRequest.setLat(lat);
        upadteLocationRequest.setLng(lan);
        upadteLocationRequest.setType(AppConstants.employee);
        upadteLocationRequest.setCustomerId(customerId);
        UpdateLocationService updateLocationService = new UpdateLocationService();
        updateLocationService.executeService(upadteLocationRequest, new BaseApiCallback<UpDateLocationResponse>() {
            @Override
            public void onComplete() {
                dismissProgress();
            }

            @Override
            public void onSuccess(@NonNull UpDateLocationResponse response) {
                super.onSuccess(response);
                Prefs.putStringPrefs(AppConstants.LATTITUDE, response.getResponse().getLat());
                Prefs.putStringPrefs(AppConstants.LONGITUDE, response.getResponse().getLongX());
                Intent intent = new Intent(mContext, QuestionsCategoryActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                Utility.showToast(R.string.location_updated);
            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
                Utility.showToast(e.getData());
            }
        });


    }


}
