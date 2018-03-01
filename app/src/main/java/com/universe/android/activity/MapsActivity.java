package com.universe.android.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.universe.android.R;
import com.universe.android.adapter.CustomExpandableListAdapter;
import com.universe.android.helper.FontClass;
import com.universe.android.model.CategoryModal;
import com.universe.android.model.Questions;
import com.universe.android.realmbean.RealmAnswers;
import com.universe.android.realmbean.RealmCategory;
import com.universe.android.realmbean.RealmQuestion;
import com.universe.android.realmbean.RealmSurveys;
import com.universe.android.resource.Login.LocationUpdate.UpDateLocationResponse;
import com.universe.android.resource.Login.LocationUpdate.UpadteLocationRequest;
import com.universe.android.resource.Login.LocationUpdate.UpdateLocationService;
import com.universe.android.realmbean.RealmCustomer;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;
import com.universe.android.web.BaseApiCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import in.editsoft.api.exception.APIException;
import io.realm.Realm;
import io.realm.RealmResults;
import me.tankery.lib.circularseekbar.CircularSeekBar;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private EditText locationSearch;
    private ImageView imageViewSearch, imageViewSearchBack;
    private Activity activity;
    private CircleImageView circleImageViewMap;
    private TextView textViewHeader, textViewRetailersNameMap, textViewMobileNoMap, textViewStatusMap, textViewSetLocation;
    private ImageView imageViewForward;
    private LatLng latLng;
    private double latitude;
    private double longitude;
    List<CategoryModal> arraylistTitle = new ArrayList<>();
    private CircularSeekBar seekBar;
    private String title,surveyId,customerId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        activity = this;
        latitude = Double.parseDouble(Prefs.getStringPrefs(AppConstants.LATTITUDE));
        longitude = Double.parseDouble(Prefs.getStringPrefs((AppConstants.LONGITUDE)));
        Intent intent=getIntent();
        if (intent!=null){
            title= intent.getExtras().getString(AppConstants.STR_TITLE);
            surveyId= intent.getExtras().getString(AppConstants.SURVEYID);
            customerId= intent.getExtras().getString(AppConstants.CUSTOMERID);
            // customerId="5a83ca4296318c134c534cb9";
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initialization();
        setUpElements();
        setUpListners();
        setupDetail();
    }

    private void setUpListners() {
        // Setting a click event handler for the map

    }

    private void setupDetail() {
        Realm realm = Realm.getDefaultInstance();
        try{
            RealmCustomer realmCustomer=realm.where(RealmCustomer.class).equalTo(AppConstants.ID,customerId).findFirst();

            if (Utility.validateString(realmCustomer.getName()))
                textViewRetailersNameMap.setText(realmCustomer.getName());

            textViewMobileNoMap.setText(realmCustomer.getContactNo()+" | "+
                    realmCustomer.getTerritory()+" | "+realmCustomer.getState()+"  \n"+
                    "Pincode - "+realmCustomer.getPincode());


        }catch (Exception e0){
            e0.printStackTrace();
            realm.close();
        }finally {
            if(!realm.isClosed()){
                realm.close();
            }

        }
    }

    private void setUpElements() {
        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMapSearch(v);
                Utility.hideSoftKeyboard(activity);
            }
        });
        locationSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    onMapSearch(v);
                    Utility.hideSoftKeyboard(activity);
                    return true;
                }
                return false;
            }
        });
        imageViewSearchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initialization() {
        imageViewSearch = findViewById(R.id.imageviewSearch);
        imageViewSearchBack = findViewById(R.id.imageviewbackSearch);
        locationSearch = findViewById(R.id.searchcustomers);
        circleImageViewMap = findViewById(R.id.circularImageViewMap);
        textViewHeader = findViewById(R.id.textViewHeader);
        textViewRetailersNameMap = findViewById(R.id.textViewRetailersNameMap);
        textViewMobileNoMap = findViewById(R.id.textViewMobileNoMap);
        textViewStatusMap = findViewById(R.id.textViewStatusMap);
        imageViewForward = findViewById(R.id.imageviewForward);
        textViewSetLocation = findViewById(R.id.textViewSetLocation);

        seekBar = (CircularSeekBar) findViewById(R.id.seek_bar);

        textViewHeader.setTypeface(FontClass.openSemiBold(mContext));
        textViewRetailersNameMap.setTypeface(FontClass.openSansRegular(mContext));
        textViewMobileNoMap.setTypeface(FontClass.openSansRegular(mContext));
        textViewStatusMap.setTypeface(FontClass.openSansRegular(mContext));
        textViewSetLocation.setTypeface(FontClass.openSansRegular(mContext));

        textViewHeader.setText(title);
        imageViewForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CategoryExpandableListActivity.class);
                intent.putExtra(AppConstants.STR_TITLE,title);
                intent.putExtra(AppConstants.SURVEYID,surveyId);
                intent.putExtra(AppConstants.CUSTOMERID,customerId);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

            }
        });
        textViewSetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateLocationService(Prefs.getStringPrefs(AppConstants.LATTITUDE), Prefs.getStringPrefs(AppConstants.LONGITUDE));
            }
        });


    }

    public void onMapSearch(View view) {
        String location = locationSearch.getText().toString();
        List<Address> addressList = null;
        Address address;

        Geocoder geocoder = new Geocoder(this);
        try {
            addressList = geocoder.getFromLocationName(location, 1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        mMap.clear();
        address = addressList.get(0);
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
        CameraPosition position = CameraPosition.builder()
                .target(new LatLng(address.getLatitude(),
                        address.getLongitude()))
                .zoom(16f)
                .bearing(0.0f)
                .tilt(0.0f)
                .build();
        Prefs.putStringPrefs(AppConstants.LATTITUDE,""+address.getLatitude());
        Prefs.putStringPrefs(AppConstants.LONGITUDE,""+address.getLongitude());
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), null);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();
        mMap.setOnMapClickListener(this);

        // Add a marker in Sydney and move the camera
        LatLng latLng = new LatLng(latitude, longitude);
        Prefs.putStringPrefs(AppConstants.LATTITUDE,""+latitude);
        Prefs.putStringPrefs(AppConstants.LONGITUDE,""+longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13.0f));
        mMap.addMarker(new MarkerOptions().position(latLng));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        Prefs.putStringPrefs(AppConstants.LATTITUDE,""+latLng.latitude);
        Prefs.putStringPrefs(AppConstants.LONGITUDE,""+latLng.longitude);
        mMap.clear();
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
        mMap.addMarker(markerOptions);

    }

    public void updateLocationService(String lat, String lan) {
        UpadteLocationRequest upadteLocationRequest = new UpadteLocationRequest();
        upadteLocationRequest.setUserId(Prefs.getStringPrefs(AppConstants.UserId));
        upadteLocationRequest.setLat(lat);
        upadteLocationRequest.setLng(lan);
        UpdateLocationService updateLocationService = new UpdateLocationService();
        updateLocationService.executeService(upadteLocationRequest, new BaseApiCallback<UpDateLocationResponse>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(@NonNull UpDateLocationResponse response) {
                super.onSuccess(response);
                Prefs.putStringPrefs(AppConstants.LATTITUDE,response.getResponse().getLat());
                Prefs.putStringPrefs(AppConstants.LONGITUDE,response.getResponse().getLongX());
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

    private void prepareCategory(){

        int progressTotal=0;
        int progressRequired=0;

        arraylistTitle = new ArrayList<>();
      //  expandableListDetail=new HashMap<CategoryModal, List<Questions>>();
        ArrayList<String> arrISView=new ArrayList<>();

        Realm realm = Realm.getDefaultInstance();
        RealmSurveys realmSurveys=realm.where(RealmSurveys.class).equalTo(AppConstants.ID,surveyId).findFirst();

        try {
            JSONArray jsonArray = new JSONArray(realmSurveys.getCategoryId());
            for (int l=0;l<jsonArray.length();l++){

                RealmAnswers realmAnswers=realm.where(RealmAnswers.class).equalTo(AppConstants.SURVEYID,surveyId).equalTo(AppConstants.CUSTOMERID,customerId).findFirst();

                if (realmAnswers!=null){
                   // updateId=realmAnswers.get_id();
                    JSONArray array=new JSONArray(realmAnswers.getAnswers());
                    // JSONArray array1=new JSONArray(array.toString());
                    //   String json=array.get(0).toString();
                    //  JSONArray array1=new JSONArray(json);
                    if (array.length()>0){
                        for (int i=0;i<array.length();i++){

                            JSONObject jsonObject=array.getJSONObject(i);
                            String categoryId=jsonObject.optString(AppConstants.CATEGORYID);
                            String isView=jsonObject.optString(AppConstants.ISVIEW);
                            JSONArray questions=jsonObject.getJSONArray(AppConstants.QUESTIONS);
                            if (categoryId.equalsIgnoreCase(jsonArray.get(l).toString())){
                                RealmCategory realmCategoryDetails = realm.where(RealmCategory.class).equalTo(AppConstants.ID,jsonArray.get(l).toString())/*.equalTo(AppConstants.SURVEYID,surveyId)*/.findFirst();
                                if (realmCategoryDetails != null) {


                                    CategoryModal categoryModal = new CategoryModal();
                                    categoryModal.setId(realmCategoryDetails.getId());
                                    categoryModal.setCategoryName(realmCategoryDetails.getCategoryName());
                                    categoryModal.setStatus(isView);
                                    if (isView.equalsIgnoreCase("1"))
                                        arrISView.add(isView);
                                    try {
                                        //  RealmResults<RealmQuestion> realmQuestions=realm.where(RealmQuestion.class).equalTo(AppConstants.CATEGORYID,realmCategoryDetails.getId()).equalTo(AppConstants.SURVEYID,surveyId).findAll();

                                        //  if (realmQuestions != null && realmQuestions.size() > 0) {
                                        //         String categoryId = realmCategoryDetails.get(k).getId();
                                        ArrayList<Questions> questionsArrayList = new ArrayList<>();

                                        for (int n=0;n<questions.length();n++){

                                            JSONObject jsonObject1=questions.getJSONObject(n);

                                            Questions questions1 =new Questions();
                                            questions1.setQuestionId(jsonObject1.optString(AppConstants.QUESTIONID));
                                            questions1.setTitle(jsonObject1.optString(AppConstants.TITLE));
                                            questions1.setStatus(jsonObject1.optString(AppConstants.REQUIRED));
                                            questions1.setAnswer(jsonObject1.optString(AppConstants.ANSWER));
                                            questionsArrayList.add(questions1);

                                        }
                                        ArrayList<String> stringsRequired=new ArrayList<>();
                                        ArrayList<String> stringsRequiredAnswers=new ArrayList<>();
                                        ArrayList<String> doneQuestions=new ArrayList<>();
                                        for (int p=0;p<questionsArrayList.size();p++){
                                            if (questionsArrayList.get(p).getStatus().equalsIgnoreCase("Yes")) {

                                                stringsRequired.add(questionsArrayList.get(p).getStatus());
                                            }
                                            if (Utility.validateString(questionsArrayList.get(p).getAnswer()) && questionsArrayList.get(p).getStatus().equalsIgnoreCase("Yes")) {

                                                stringsRequiredAnswers.add(questionsArrayList.get(p).getAnswer());
                                            }
                                            if (Utility.validateString(questionsArrayList.get(p).getAnswer())) {

                                                doneQuestions.add(questionsArrayList.get(p).getAnswer());
                                            }

                                        }
                                        if (stringsRequired.size()==stringsRequiredAnswers.size()){
                                            categoryModal.setCategoryAnswered("Yes");
                                        }else {
                                            categoryModal.setCategoryAnswered("No");
                                        }
                                        categoryModal.setQuestionCount(doneQuestions.size()+"/"+questionsArrayList.size());
                                        categoryModal.setQuestions(questionsArrayList);

                                        arraylistTitle.add(categoryModal);
                                        progressRequired=progressRequired+stringsRequiredAnswers.size();
                                        progressTotal=progressTotal+stringsRequired.size();

                                        //   }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }







                                }else{
                                //    showToastMessage(getResources().getString(R.string.no_data));
                                }

                            }

                        }
                    }
                }else {
                    RealmCategory realmCategoryDetails = realm.where(RealmCategory.class).equalTo(AppConstants.ID, jsonArray.get(l).toString())/*.equalTo(AppConstants.SURVEYID,surveyId)*/.findFirst();
                    if (realmCategoryDetails != null) {


                        CategoryModal categoryModal = new CategoryModal();
                        categoryModal.setId(realmCategoryDetails.getId());
                        categoryModal.setCategoryName(realmCategoryDetails.getCategoryName());
                        categoryModal.setStatus("");
                        categoryModal.setCategoryAnswered("");
                        try {
                            RealmResults<RealmQuestion> realmQuestions = realm.where(RealmQuestion.class).equalTo(AppConstants.CATEGORYID, realmCategoryDetails.getId()).equalTo(AppConstants.SURVEYID, surveyId).findAll();

                            //  if (realmQuestions != null && realmQuestions.size() > 0) {
                            //         String categoryId = realmCategoryDetails.get(k).getId();
                            ArrayList<Questions> questionsArrayList = new ArrayList<>();

                            for (int i = 0; i < realmQuestions.size(); i++) {


                                Questions questions = new Questions();
                                questions.setQuestionId(realmQuestions.get(i).getId());
                                questions.setTitle((i + 1) + ". " + realmQuestions.get(i).getTitle());
                                if (realmQuestions.get(i).getRequired().equalsIgnoreCase("true"))
                                    questions.setStatus("Yes");
                                else {
                                    questions.setStatus("No");
                                }
                                questions.setAnswer("");
                                questionsArrayList.add(questions);

                            }
                            categoryModal.setQuestionCount(questionsArrayList.size()+"");
                            categoryModal.setQuestions(questionsArrayList);
                            progressRequired=0;
                            progressTotal=100;
                            arraylistTitle.add(categoryModal);

                            //   }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {
                    //    showToastMessage(getResources().getString(R.string.no_data));
                    }


                }


            }

            ArrayList<String> categoryAnswered=new ArrayList<>();
            for (int m=0;m<arraylistTitle.size();m++) {
                categoryAnswered.add(arraylistTitle.get(m).getCategoryAnswered());
                ArrayList<Questions> productDetailsModelArrayList=new ArrayList<>();
                for (int k = 0; k < arraylistTitle.get(m).getQuestions().size(); k++) {
                    productDetailsModelArrayList.add(arraylistTitle.get(m).getQuestions().get(k));
                }
             //   expandableListDetail.put(arraylistTitle.get(m), productDetailsModelArrayList);
            }



            TextView textViewProgress=(TextView)findViewById(R.id.progressBarinsideText);
            seekBar.setProgress(progressRequired);
            seekBar.setMax(progressTotal);
            int percent=(progressRequired*100)/progressTotal;
            textViewProgress.setText(percent+"%");



        }catch (Exception e0){

            e0.printStackTrace();
            realm.close();
        }finally {
            if(!realm.isClosed()){
                realm.close();
            }

        }


    }
}
