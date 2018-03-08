package com.universe.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.universe.android.model.CategoryModal;
import com.universe.android.model.Questions;
import com.universe.android.realmbean.RealmAnswers;
import com.universe.android.realmbean.RealmCategory;
import com.universe.android.realmbean.RealmCustomer;
import com.universe.android.realmbean.RealmQuestion;
import com.universe.android.realmbean.RealmSurveys;
import com.universe.android.resource.Login.LocationUpdate.UpDateLocationResponse;
import com.universe.android.resource.Login.LocationUpdate.UpadteLocationRequest;
import com.universe.android.resource.Login.LocationUpdate.UpdateLocationService;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;
import com.universe.android.web.BaseApiCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import in.editsoft.api.exception.APIException;
import io.realm.Realm;
import io.realm.RealmResults;

import ru.bullyboo.view.CircleSeekBar;

public class MapsOneActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, PlaceSelectionListener {

    private GoogleMap mMap;
    private TextView textViewHeader, textViewRetailersNameMap, textViewMobileNoMap, textViewStatusMap, textViewSetLocation;
    private String title, surveyId, customerId, strCustomer;
    private ImageView imageViewLocation;

    private ImageView imageViewSearch, imageViewSearchBack;
    private Activity activity;
    private CircleImageView circleImageViewMap;
    List<CategoryModal> arraylistTitle = new ArrayList<>();
    private CircleSeekBar seekBar;
    ProgressBar mProgress;
    private CircleImageView circleImageView;


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
            strCustomer = intent.getExtras().getString(AppConstants.CUSTOMER);
        }

        initialization();
        setUpElements();
        setUpListners();
        setupDetail();

    }

    @Override
    protected void onResume() {
        super.onResume();
        prepareCategory();
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

        seekBar = (CircleSeekBar) findViewById(R.id.seek_bar);
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.circular_progress);
        mProgress = (ProgressBar) findViewById(R.id.circularProgressbar);
        mProgress.setProgress(0);   // Main Progress
        mProgress.setSecondaryProgress(100); // Secondary Progress
        mProgress.setMax(100); // Maximum Progress
        mProgress.setProgressDrawable(drawable);
        textViewHeader.setTypeface(FontClass.openSemiBold(mContext));
        textViewRetailersNameMap.setTypeface(FontClass.openSansRegular(mContext));
        textViewMobileNoMap.setTypeface(FontClass.openSansRegular(mContext));
        textViewStatusMap.setTypeface(FontClass.openSansRegular(mContext));
        textViewSetLocation.setTypeface(FontClass.openSansRegular(mContext));


        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageOptions();
            }
        });
        if (Prefs.getStringPrefs(AppConstants.CUSTOMERIMAGE) != null) {
            Glide.with(mActivity).load(Prefs.getStringPrefs(AppConstants.CUSTOMERIMAGE)).into(circleImageView);
        } else {
            circleImageView.setImageResource(R.drawable.ic_customer);
            if (strCustomer.equalsIgnoreCase(AppConstants.CrystalCustomer)) {
                circleImageViewMap.setImageResource(R.drawable.ic_customer);
            } else {
                circleImageViewMap.setImageResource(R.drawable.ic_retailer);
            }
            textViewHeader.setText(title);
        }
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

            if (realmCustomer.getCustomer().equalsIgnoreCase(AppConstants.CrystalCustomer)) {
                if (Utility.validateString(realmCustomer.getName()))
                    textViewRetailersNameMap.setText(realmCustomer.getName());

                textViewMobileNoMap.setText(realmCustomer.getContactNo() + " | " +
                        realmCustomer.getTerritory() + " | " + realmCustomer.getState() + "  \n" +
                        "Pincode - " + realmCustomer.getPincode());

            } else {
                if (Utility.validateString(realmCustomer.getRetailerName()))
                    textViewRetailersNameMap.setText(realmCustomer.getRetailerName());

                textViewMobileNoMap.setText(realmCustomer.getMobile() + " | " +
                        realmCustomer.getTerritory_code() + " | " + realmCustomer.getState_code() + "  \n" +
                        "Pincode - " + realmCustomer.getPincode());
            }

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
        Toast.makeText(this, "Place Selected" + " ".concat(place.getAddress().toString()), Toast.LENGTH_SHORT).show();
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
                Prefs.putStringPrefs(AppConstants.LATTITUDE, response.getResponse().getLocation().getLat());
                Prefs.putStringPrefs(AppConstants.LONGITUDE, response.getResponse().getLocation().getLongX());
                //  Prefs.putStringPrefs(AppConstants.LATTITUDE, response.getResponse().getLat());
                //   Prefs.putStringPrefs(AppConstants.LONGITUDE, response.getResponse().getLongX());
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

                Prefs.putStringPrefs(AppConstants.LATTITUDE, response.getResponse().getLocation().getLat());
                Prefs.putStringPrefs(AppConstants.LONGITUDE, response.getResponse().getLocation().getLongX());
//                if (!response.getResponse().isLocationSet()) {
//                    imageLoc.setImageResource(R.drawable.ic_location_off_black_24dp);
//                } else {
//                    imageLoc.setImageResource(R.drawable.ic_location_on_black_24dp);
//
//                }

                Prefs.putBooleanPrefs(AppConstants.LocationUpdate, true);

                //  Prefs.putStringPrefs(AppConstants.LATTITUDE, response.getResponse().getLat());
                //  Prefs.putStringPrefs(AppConstants.LONGITUDE, response.getResponse().getLongX());
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

    private void prepareCategory() {

        int progressTotal = 0;
        int progressRequired = 0;

        arraylistTitle = new ArrayList<>();
        //  expandableListDetail=new HashMap<CategoryModal, List<Questions>>();
        ArrayList<String> arrISView = new ArrayList<>();

        Realm realm = Realm.getDefaultInstance();
        RealmSurveys realmSurveys = realm.where(RealmSurveys.class).equalTo(AppConstants.ID, surveyId).findFirst();

        try {
            JSONArray jsonArray = new JSONArray(realmSurveys.getCategoryId());
            for (int l = 0; l < jsonArray.length(); l++) {

                RealmAnswers realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.SURVEYID, surveyId).equalTo(AppConstants.CUSTOMERID, customerId).findFirst();

                if (realmAnswers != null) {
                    // updateId=realmAnswers.get_id();
                    JSONArray array = new JSONArray(realmAnswers.getAnswers());
                    // JSONArray array1=new JSONArray(array.toString());
                    //   String json=array.get(0).toString();
                    //  JSONArray array1=new JSONArray(json);
                    if (array.length() > 0) {
                        for (int i = 0; i < array.length(); i++) {

                            JSONObject jsonObject = array.getJSONObject(i);
                            String categoryId = jsonObject.optString(AppConstants.CATEGORYID);
                            String isView = jsonObject.optString(AppConstants.ISVIEW);
                            JSONArray questions = jsonObject.getJSONArray(AppConstants.QUESTIONS);
                            if (categoryId.equalsIgnoreCase(jsonArray.get(l).toString())) {
                                RealmCategory realmCategoryDetails = realm.where(RealmCategory.class).equalTo(AppConstants.ID, jsonArray.get(l).toString())/*.equalTo(AppConstants.SURVEYID,surveyId)*/.findFirst();
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

                                        for (int n = 0; n < questions.length(); n++) {

                                            JSONObject jsonObject1 = questions.getJSONObject(n);

                                            Questions questions1 = new Questions();
                                            questions1.setQuestionId(jsonObject1.optString(AppConstants.QUESTIONID));
                                            questions1.setTitle(jsonObject1.optString(AppConstants.TITLE));
                                            questions1.setStatus(jsonObject1.optString(AppConstants.REQUIRED));
                                            questions1.setAnswer(jsonObject1.optString(AppConstants.ANSWER));
                                            questionsArrayList.add(questions1);

                                        }
                                        ArrayList<String> stringsRequired = new ArrayList<>();
                                        ArrayList<String> stringsRequiredAnswers = new ArrayList<>();
                                        ArrayList<String> doneQuestions = new ArrayList<>();
                                        for (int p = 0; p < questionsArrayList.size(); p++) {
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
                                        if (stringsRequired.size() == stringsRequiredAnswers.size()) {
                                            categoryModal.setCategoryAnswered("Yes");
                                        } else {
                                            categoryModal.setCategoryAnswered("No");
                                        }
                                        categoryModal.setQuestionCount(doneQuestions.size() + "/" + questionsArrayList.size());
                                        categoryModal.setQuestions(questionsArrayList);

                                        arraylistTitle.add(categoryModal);
                                        progressRequired = progressRequired + stringsRequiredAnswers.size();
                                        progressTotal = progressTotal + stringsRequired.size();

                                        //   }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                } else {
                                    //    showToastMessage(getResources().getString(R.string.no_data));
                                }

                            }

                        }
                    }
                } else {
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
                            categoryModal.setQuestionCount(questionsArrayList.size() + "");
                            categoryModal.setQuestions(questionsArrayList);
                            progressRequired = 0;
                            progressTotal = 100;
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

            ArrayList<String> categoryAnswered = new ArrayList<>();
            for (int m = 0; m < arraylistTitle.size(); m++) {
                categoryAnswered.add(arraylistTitle.get(m).getCategoryAnswered());
                ArrayList<Questions> productDetailsModelArrayList = new ArrayList<>();
                for (int k = 0; k < arraylistTitle.get(m).getQuestions().size(); k++) {
                    productDetailsModelArrayList.add(arraylistTitle.get(m).getQuestions().get(k));
                }
                //   expandableListDetail.put(arraylistTitle.get(m), productDetailsModelArrayList);
            }


            TextView textViewProgress = (TextView) findViewById(R.id.progressBarinsideText);
            seekBar.setValue(progressRequired);
            seekBar.setMaxValue(progressTotal);
            mProgress.setProgress(progressRequired);
            mProgress.setMax(progressTotal);
            int percent = (progressRequired * 100) / progressTotal;
            textViewProgress.setText(percent + "%");


        } catch (Exception e0) {

            e0.printStackTrace();
            realm.close();
        } finally {
            if (!realm.isClosed()) {
                realm.close();
            }

        }


    }

}
