package com.universe.android.activity;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.universe.android.okkhttp.APIClient;
import com.universe.android.okkhttp.UniverseAPI;
import com.universe.android.realmbean.RealmAnswers;
import com.universe.android.realmbean.RealmCategory;
import com.universe.android.realmbean.RealmController;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import in.editsoft.api.exception.APIException;
import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MapsOneActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, PlaceSelectionListener {

    private GoogleMap mMap;
    private TextView textViewHeader, textViewRetailersNameMap, textViewMobileNoMap, textViewStatusMap, textViewSetLocation;
    private String title, surveyId, customerId, strCustomer;
    private ImageView imageViewLocation;
    private double lat, lng;
    private ImageView imageViewSearch, imageViewSearchBack;
    private Activity activity;
    private CircleImageView circleImageViewMap;
    private ImageView imageLoc;
    List<CategoryModal> arraylistTitle = new ArrayList<>();

    ProgressBar mProgress;
    private CircleImageView circleImageView;
    private String isLocationSet="";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
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


    }

    @Override
    protected void onResume() {
        super.onResume();
        setupDetail();
        prepareCategory();
    }

    private void initialization() {
        imageViewSearchBack = findViewById(R.id.imageviewbackSearch);
        imageLoc = findViewById(R.id.imageLoc);
        textViewHeader = findViewById(R.id.textViewHeader);
        textViewRetailersNameMap = findViewById(R.id.textViewRetailersNameMap);
        textViewMobileNoMap = findViewById(R.id.textViewMobileNoMap);
        textViewStatusMap = findViewById(R.id.textViewStatusMap);
        textViewSetLocation = findViewById(R.id.textViewSetLocation);
        imageViewLocation = findViewById(R.id.imageViewLocation);
        circleImageView = findViewById(R.id.circularImageViewMap);
        lat = Double.parseDouble(Prefs.getStringPrefs(AppConstants.lat));
        lng = Double.parseDouble(Prefs.getStringPrefs(AppConstants.lng));
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.circular_progress);
        mProgress = findViewById(R.id.circularProgressbar);
        mProgress.setProgress(0);   // Main Progress
        mProgress.setSecondaryProgress(100); // Secondary Progress
        mProgress.setMax(100); // Maximum Progress
        mProgress.setProgressDrawable(drawable);
        textViewHeader.setTypeface(FontClass.openSemiBold(mContext));
        textViewRetailersNameMap.setTypeface(FontClass.openSansRegular(mContext));
        textViewMobileNoMap.setTypeface(FontClass.openSansRegular(mContext));
        textViewStatusMap.setTypeface(FontClass.openSansRegular(mContext));
        textViewSetLocation.setTypeface(FontClass.openSansRegular(mContext));
        textViewHeader.setText(title);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageOptions();
            }
        });

    }


    private void setUpElements() {

    }

    private void setUpListners() {
        textViewSetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateLocationService(Prefs.getStringPrefs(AppConstants.LATTITUDE), Prefs.getStringPrefs(AppConstants.LONGITUDE));

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
                updateMap(new LatLng(28.4975915, 77.0828436), "");
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

                textViewMobileNoMap.setText(new StringBuilder().append(realmCustomer.getContactNo()).append(" | ").append(realmCustomer.getTerritory()).append(" | ").append(realmCustomer.getState()).append("  \n").append("Pincode - ").append(realmCustomer.getPincode()).toString());

            } else {
                if (Utility.validateString(realmCustomer.getRetailerName()))
                    textViewRetailersNameMap.setText(realmCustomer.getRetailerName());

                textViewMobileNoMap.setText(new StringBuilder().append(realmCustomer.getMobile()).append(" | ").append(realmCustomer.getTerritory_code()).append(" | ").append(realmCustomer.getState_code()).append("  \n").append("Pincode - ").append(realmCustomer.getPincode()).toString());
            }

            if (Utility.validateString(realmCustomer.getImage())) {
                if (realmCustomer.getImage().equals("")) {
                    if (strCustomer.equalsIgnoreCase(AppConstants.CrystalCustomer)) {
                        circleImageView.setImageResource(R.drawable.ic_crystal_cutomer);
                    } else {
                        circleImageView.setImageResource(R.drawable.ic_customer);
                    }
                } else {
                    Glide.with(mActivity).load(realmCustomer.getImage()).into(circleImageView);
                }

                if (realmCustomer.isLocation()) {
                    imageLoc.setImageResource(R.drawable.ic_location_set);
                } else {
                    imageLoc.setImageResource(R.drawable.red_loc);

                }
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
        updateMap(new LatLng(28.4975915, 77.0828436), "");
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

    private void updateLocationService( String lat, String lan) {
        JSONObject jsonSubmitReq=new JSONObject();

            try {
                jsonSubmitReq.put(AppConstants.USERID, Prefs.getStringPrefs(AppConstants.UserId));
                jsonSubmitReq.put(AppConstants.LAT, lat);
                jsonSubmitReq.put(AppConstants.LNG, lan);
                jsonSubmitReq.put(AppConstants.TYPE, AppConstants.customer);
                jsonSubmitReq.put(AppConstants.CUSTOMERID, customerId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        showProgress();

        OkHttpClient okHttpClient = APIClient.getHttpClient();
        RequestBody requestBody = RequestBody.create(UniverseAPI.JSON, jsonSubmitReq.toString());
        String url = UniverseAPI.WEB_SERVICE_SET_LOCATION_METHOD;



        Request request = APIClient.getPostRequest(this, url, requestBody);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
               dismissProgress();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utility.showToast(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {

                    if (response != null && response.isSuccessful()) {
                        String responseData = response.body().string();
                        if (Utility.validateString(responseData)) {
                            JSONObject jsonResponse = new JSONObject(responseData);
                            jsonResponse = jsonResponse.getJSONObject(AppConstants.RESPONSE);
                            JSONObject location=jsonResponse.optJSONObject(AppConstants.LOCATION);
                            new RealmController().saveFormNewRetailerSubmit(location.toString(), "");

                            if (location.optBoolean(AppConstants.ISLOCATIONSET)) {
                                imageLoc.setImageResource(R.drawable.ic_location_set);
                            } else {
                                imageLoc.setImageResource(R.drawable.red_loc);
                            }
                        }


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                              dismissProgress();

                                updateLocationServiceEmployee(Prefs.getStringPrefs(AppConstants.LATTITUDE), Prefs.getStringPrefs(AppConstants.LONGITUDE));

                            }
                        });

                    } else {
                       dismissProgress();
                    }

                } catch (Exception e) {
                 dismissProgress();
                    e.printStackTrace();
                } finally {
                }

            }
        });

    }


    public void updateLocationServiceEmployee(String lat, String lan) {
        showProgress();
        UpadteLocationRequest upadteLocationRequest = new UpadteLocationRequest();
        upadteLocationRequest.setUserId(Prefs.getStringPrefs(AppConstants.UserId));
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
                Prefs.putStringPrefs(AppConstants.LATTITUDE, response.getResponse().getLocation().getLocationSet().getLat());
                Prefs.putStringPrefs(AppConstants.LONGITUDE, response.getResponse().getLocation().getLocationSet().getLongX());

                finish();
//                Intent intent = new Intent(mContext, CategoryExpandableListActivity.class);
//                intent.putExtra(AppConstants.STR_TITLE, title);
//                intent.putExtra(AppConstants.SURVEYID, surveyId);
//                intent.putExtra(AppConstants.CUSTOMERID, customerId);
//                intent.putExtra(AppConstants.CUSTOMER, strCustomer);

//                startActivity(intent);
//                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
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
        ArrayList<String> stringsRequired = new ArrayList<>();
        ArrayList<String> stringsRequiredAnswers = new ArrayList<>();
        stringsRequired.add("isLocationRequired");
        if (isLocationSet.equalsIgnoreCase("yes")){
            stringsRequiredAnswers.add("isLocationRequired");
        }
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
                                        int required=0,requiredAnswers=0;
                                        if (stringsRequired.contains("isLocationRequired")){
                                            required=stringsRequired.size()-1;
                                        }else {
                                            required=stringsRequired.size();
                                        }
                                        if (stringsRequiredAnswers.contains("isLocationRequired")){
                                            requiredAnswers=stringsRequiredAnswers.size()-1;
                                        }else {
                                            requiredAnswers=stringsRequiredAnswers.size();
                                        }
                                        if (required ==requiredAnswers) {
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
                            categoryModal.setQuestionCount(questionsArrayList.size() + "");
                            categoryModal.setQuestions(questionsArrayList);
                            progressRequired = progressRequired + stringsRequiredAnswers.size();
                            progressTotal = progressTotal + stringsRequired.size();
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
