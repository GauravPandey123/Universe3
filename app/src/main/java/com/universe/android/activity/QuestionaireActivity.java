package com.universe.android.activity;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.soundcloud.android.crop.Crop;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.soundcloud.android.crop.Crop;
import com.universe.android.resource.Login.CutomerPictureChange.CustomerPictureRequest;
import com.universe.android.resource.Login.CutomerPictureChange.CustomerPictureResponse;
import com.universe.android.resource.Login.CutomerPictureChange.CustomerPictureService;
import com.universe.android.web.BaseApiCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import de.hdodenhof.circleimageview.CircleImageView;
import in.editsoft.api.exception.APIException;
import com.universe.android.R;
import com.universe.android.adapter.CustomExpandableListAdapter;
import com.universe.android.fragment.QuestionsCategoryFragment;
import com.universe.android.helper.FontClass;
import com.universe.android.helper.PagerSlidingTabStrip;
import com.universe.android.listneners.PageChangeInterface;
import com.universe.android.model.CategoryModal;
import com.universe.android.model.CustomerModal;
import com.universe.android.model.Questions;
import com.universe.android.okkhttp.APIClient;
import com.universe.android.okkhttp.UniverseAPI;
import com.universe.android.realmbean.RealmAnswers;
import com.universe.android.realmbean.RealmCategory;
import com.universe.android.realmbean.RealmController;
import com.universe.android.realmbean.RealmCustomer;
import com.universe.android.realmbean.RealmQuestion;
import com.universe.android.realmbean.RealmSurveys;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmResults;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ru.bullyboo.view.CircleSeekBar;

public class QuestionaireActivity extends BaseActivity  implements PageChangeInterface{
    private ViewPager mViewPager;
    private TextView textViewHeader, textViewRetailersNameMap, textViewMobileNoMap, textViewStatusMap;
    private ImageView imageViewSearchBack;
    private EditText editTextSearch;
    private TabLayout pagerSlidingTabStrip;
    private QuestionaireTabsFilterAdapter questionaireTabsFilterAdapter;
    private String title,surveyId,customerId;
    private ArrayList<CategoryModal> categoryModals=new ArrayList<>();
    private String updateId;
    public static PageChangeInterface pageChangeInterface;
    private int positionValue;
    private JSONObject jsonSubmitReq = new JSONObject();
    List<CategoryModal> arraylistTitle = new ArrayList<>();
    HashMap<CategoryModal, List<Questions>> expandableListDetail = new HashMap<CategoryModal, List<Questions>>();
    private int groupPosition=0;
    private CircleSeekBar seekBar;
    boolean isSync=false;
    String searchText="",strCustomer="";
    ProgressBar mProgress;
    private String strStatus="";
    private LinearLayout llStatus;
    private ImageView imageStatus;
    private TextView textStatus;
    CircleImageView circleImageView;
    private String mImageUrl;
    private boolean isUpdateImage = false;
     CustomerPictureResponse CustomerPictureResponse;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionaire_activity);
        pageChangeInterface=this;
        initialization();
        prepareCategory();
        setUpElements();
        setUpListeners();
        setupDetail();

    }

    @Override
    protected void onResume() {
        super.onResume();
        calculateProgress();
    }

    private void setupDetail() {
        Realm realm = Realm.getDefaultInstance();
        try{
            RealmCustomer realmCustomer=realm.where(RealmCustomer.class).equalTo(AppConstants.ID,customerId).findFirst();

            if (realmCustomer.getCustomer().equalsIgnoreCase(AppConstants.CrystalCustomer)){
                if (Utility.validateString(realmCustomer.getName()))
                    textViewRetailersNameMap.setText(realmCustomer.getName());

                textViewMobileNoMap.setText(realmCustomer.getContactNo()+" | "+
                        realmCustomer.getTerritory()+" | "+realmCustomer.getState()+"  \n"+
                        "Pincode - "+realmCustomer.getPincode());

            }else{
                if (Utility.validateString(realmCustomer.getRetailerName()))
                    textViewRetailersNameMap.setText(realmCustomer.getRetailerName());

                textViewMobileNoMap.setText(realmCustomer.getMobile()+" | "+
                        realmCustomer.getTerritory_code()+" | "+realmCustomer.getState_code()+"  \n"+
                        "Pincode - "+realmCustomer.getPincode());
            }


        }catch (Exception e0){
            e0.printStackTrace();
            realm.close();
        }finally {
            if(!realm.isClosed()){
                realm.close();
            }
        }
    }
    private void setUpListeners() {
        imageViewSearchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void setUpElements() {
       /* pagerSlidingTabStrip.setTabBackground(R.color.white);
        pagerSlidingTabStrip.setIndicatorHeight(dptoPixel(6));
        pagerSlidingTabStrip.setIndicatorColor(getResources().getColor(R.color.app_theme_color));
        pagerSlidingTabStrip.setShouldExpand(true);
        pagerSlidingTabStrip.setTextSize((int) getResources().getDimension(R.dimen.text_size_medium));
        pagerSlidingTabStrip.setDeactivateTextColor(getResources().getColor(R.color.black));
        pagerSlidingTabStrip.setActivateTextColor(getResources().getColor(R.color.black));*/
        mViewPager.setOffscreenPageLimit(4);

        questionaireTabsFilterAdapter = new QuestionaireTabsFilterAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(questionaireTabsFilterAdapter);
        mViewPager.setCurrentItem(groupPosition);
        //      pagerSlidingTabStrip.setViewPager(mViewPager);
        //      pagerSlidingTabStrip.setTypeface(FontClass.openSansRegular(mContext));

        pagerSlidingTabStrip.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                System.out.println("HHEjndfEEE");
                positionValue=position;
            }

            @Override
            public void onPageSelected(int position) {

                mViewPager.setCurrentItem(position);

                mViewPager.getAdapter().notifyDataSetChanged();
                System.out.println("HHEEEE");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                questionaireTabsFilterAdapter.notifyDataSetChanged();
                //    QuestionsCategoryFragment.pageChangeInterface.onDataPass(categoryModals.get(positionValue).getId(),positionValue);

            }
        });

    }

    @Override
    public void onDataPass(String data,int positionValue,String categoryid) {

    }

    // code to set adapter on feedlist view pager
    public class  QuestionaireTabsFilterAdapter extends FragmentPagerAdapter {

        private QuestionaireTabsFilterAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {


            return new QuestionsCategoryFragment().newInstance(surveyId,categoryModals.get(position).getId(),customerId,position,updateId,strCustomer);

        }

        @Override
        public int getCount() {
            return categoryModals.size();
        }
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public int getItemPosition(Object object) {
            if (object instanceof Fragment) {
                QuestionsCategoryFragment f = (QuestionsCategoryFragment) object;
                f.updateFragment(mViewPager.getCurrentItem(),categoryModals.get(mViewPager.getCurrentItem()).getId(),searchText,"1");
            }
            return super.getItemPosition(object);
        }
        @Override
        public CharSequence getPageTitle(int position) {

            return categoryModals.get(position).getCategoryName();

        }


    }


    public int dptoPixel(int dp) {
        //code to convert dp to pixel
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;
        return (int) Math.ceil(dp * logicalDensity);
    }

    private void initialization() {
        mViewPager = findViewById(R.id.questionaireViewPager);
        textViewHeader = findViewById(R.id.textViewHeader);
        textViewRetailersNameMap = findViewById(R.id.textViewRetailersNameMap);
        textViewMobileNoMap = findViewById(R.id.textViewMobileNoMap);
        textViewStatusMap = findViewById(R.id.textViewStatusMap);
        imageViewSearchBack = findViewById(R.id.imageviewbackSearch);
        seekBar = (CircleSeekBar) findViewById(R.id.seek_bar);
        llStatus = (LinearLayout) findViewById(R.id.llStatus);
        imageStatus = (ImageView) findViewById(R.id.imageStatus);
        textStatus = (TextView) findViewById(R.id.textStatus);
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.circular_progress);
        mProgress = (ProgressBar) findViewById(R.id.circularProgressbar);
        mProgress.setProgress(0);   // Main Progress
        mProgress.setSecondaryProgress(100); // Secondary Progress
        mProgress.setMax(100); // Maximum Progress
        mProgress.setProgressDrawable(drawable);
        pagerSlidingTabStrip=findViewById(R.id.pagerSlidingStrip);
        textViewHeader.setTypeface(FontClass.openSemiBold(mContext));
        textViewRetailersNameMap.setTypeface(FontClass.openSansRegular(mContext));
        textViewMobileNoMap.setTypeface(FontClass.openSansRegular(mContext));
        textViewStatusMap.setTypeface(FontClass.openSansRegular(mContext));
        CircleImageView circleImageView=(CircleImageView)findViewById(R.id.circularImageViewMap);
        Intent intent=getIntent();
        if (intent!=null){
            title= intent.getExtras().getString(AppConstants.STR_TITLE);
            surveyId= intent.getExtras().getString(AppConstants.SURVEYID);
            customerId= intent.getExtras().getString(AppConstants.CUSTOMERID);
            updateId=intent.getExtras().getString(AppConstants.UPDATEID);
            groupPosition=intent.getExtras().getInt(AppConstants.GROUP_POSITION);
            strCustomer=intent.getExtras().getString(AppConstants.CUSTOMER);
            if (strCustomer==null){
                strCustomer="";
            }
        }
        circleImageView = findViewById(R.id.circularImageViewMap);
//        if (CustomerPictureResponse.getResponse().getImage() != null) {
//            Glide.with(mContext)
//                    .load(CustomerPictureResponse.getResponse().getImage())
//                    .into(circleImageView);
//        }
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageOptions();
            }
        });


        if (strCustomer.equalsIgnoreCase(AppConstants.CrystalCustomer)){
            circleImageView.setImageResource(R.drawable.ic_customer);
        }else {
            circleImageView.setImageResource(R.drawable.ic_retailer);
        }

        textViewHeader.setText(title);

        final SearchView searchView=(SearchView)findViewById(R.id.searchView);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        assert searchManager != null;
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        if (searchView != null) {
            searchView.setSearchableInfo(searchableInfo);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public boolean onQueryTextSubmit(String query) {
                    //     if (object instanceof Fragment) {
                    //  QuestionsCategoryFragment f = new QuestionsCategoryFragment();
                    // QuestionsCategoryFragment.pageChangeInterface.onDataPass(query,mViewPager.getCurrentItem(),categoryModals.get(mViewPager.getCurrentItem()).getId());
                    //    }

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    searchText=newText;
                    //   QuestionsCategoryFragment.pageChangeInterface.onDataPass(newText ,positionValue,categoryModals.get(mViewPager.getCurrentItem()).getId());

                    return false;
                }
            });
        }


      /*  searchcustomers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });*/
    }

    private void prepareCategory(){




        Realm realm = Realm.getDefaultInstance();
        RealmSurveys realmSurveys=realm.where(RealmSurveys.class).equalTo(AppConstants.ID,surveyId).findFirst();

        try {
            JSONArray jsonArray = new JSONArray(realmSurveys.getCategoryId());
            for (int l=0;l<jsonArray.length();l++){
                RealmCategory realmCategoryDetails;

                realmCategoryDetails = realm.where(RealmCategory.class).equalTo(AppConstants.ID,jsonArray.get(l).toString())/*.equalTo(AppConstants.SURVEYID,surveyId)*/.findFirst();
                if (realmCategoryDetails != null) {


                    CategoryModal categoryModal = new CategoryModal();
                    categoryModal.setId(realmCategoryDetails.getId());
                    categoryModal.setCategoryName(realmCategoryDetails.getCategoryName());
                    categoryModal.setStatus(realmCategoryDetails.getStatus());
                    try {
                        RealmResults<RealmQuestion> realmQuestions=realm.where(RealmQuestion.class).equalTo(AppConstants.CATEGORYID,realmCategoryDetails.getId())/*.equalTo(AppConstants.SURVEYID,surveyId)*/.findAll();

                        //  if (realmQuestions != null && realmQuestions.size() > 0) {
                        //         String categoryId = realmCategoryDetails.get(k).getId();
                        ArrayList<Questions> questionsArrayList = new ArrayList<>();

                        for (int i=0;i<realmQuestions.size();i++){


                            Questions questions =new Questions();
                            questions.setQuestionId(realmQuestions.get(i).getId());
                            questions.setTitle((i+1)+". "+realmQuestions.get(i).getTitle());
                            questions.setStatus(realmQuestions.get(i).getRequired());
                            questionsArrayList.add(questions);

                        }
                        categoryModal.setQuestionCount(questionsArrayList.size()+"");
                        categoryModal.setQuestions(questionsArrayList);

                        categoryModals.add(categoryModal);

                        //   }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }







                }else{

                }





            }




        }catch (Exception e0){

            e0.printStackTrace();
            realm.close();
        }finally {
            if(!realm.isClosed()){
                realm.close();
            }

        }


    }

    private JSONObject prepareJsonRequest(String type, String reason) {
        jsonSubmitReq=new JSONObject();
        JSONArray array=new JSONArray();
        Realm realm = Realm.getDefaultInstance();
        try{
            RealmResults<RealmAnswers> realmCategoryAnswers=realm.where(RealmAnswers.class).equalTo(AppConstants.CUSTOMERID,customerId).equalTo(AppConstants.SURVEYID,surveyId).findAll();

            if (realmCategoryAnswers!=null && realmCategoryAnswers.size()>0) {
                isSync=realmCategoryAnswers.get(0).isSync();
                if (realmCategoryAnswers.get(0).isSync()) {
                    updateId = realmCategoryAnswers.get(0).get_id();
                }else{

                }
                array = new JSONArray(realmCategoryAnswers.get(0).getWorkflow());

             /*   JSONObject jsonObject=new JSONObject(array1.get(mViewPager.getCurrentItem()).toString());
                String designation=Prefs.getStringPrefs(AppConstants.TYPE);
                JSONObject updatePosition = new JSONObject();
                if (designation.equalsIgnoreCase("rm")) {
                    updatePosition.put(AppConstants.ISVIEW, "1");
                    updatePosition.put(AppConstants.ISVIEWBYZM, "0");
                } else if (designation.equalsIgnoreCase("zm")) {
                    updatePosition.put(AppConstants.ISVIEW, "1");
                    updatePosition.put(AppConstants.ISVIEWBYZM, "1");
                } else {
                    updatePosition.put(AppConstants.ISVIEW, "0");
                    updatePosition.put(AppConstants.ISVIEWBYZM, "0");
                }
                updatePosition.put(AppConstants.CATEGORYID, jsonObject.optString(AppConstants.CATEGORYID));
                updatePosition.put(AppConstants.QUESTIONS, new JSONArray(jsonObject.optString(AppConstants.QUESTIONS)));
                array1.put(mViewPager.getCurrentItem(), updatePosition);
                */


                jsonSubmitReq.put(AppConstants.ANSWERS, new JSONArray(realmCategoryAnswers.get(0).getAnswers()));
                if (isSync && Utility.validateString(updateId))
                    jsonSubmitReq.put(AppConstants.ID, realmCategoryAnswers.get(0).get_id());
                String designation= Prefs.getStringPrefs(AppConstants.TYPE);
                if (designation.equalsIgnoreCase("cd")) {
                    jsonSubmitReq.put(AppConstants.SUBMITBY_CD, Prefs.getStringPrefs(AppConstants.UserId));
                    jsonSubmitReq.put(AppConstants.SUBMITBY_RM, realmCategoryAnswers.get(0).getSubmitbyRM());
                    jsonSubmitReq.put(AppConstants.SUBMITBY_ZM, realmCategoryAnswers.get(0).getSubmitbyZM());
                }   if (designation.equalsIgnoreCase("rm")) {
                    jsonSubmitReq.put(AppConstants.SUBMITBY_CD, realmCategoryAnswers.get(0).getSubmitbyCD());
                    jsonSubmitReq.put(AppConstants.SUBMITBY_RM, Prefs.getStringPrefs(AppConstants.UserId));
                    jsonSubmitReq.put(AppConstants.SUBMITBY_ZM, realmCategoryAnswers.get(0).getSubmitbyZM());
                } if (designation.equalsIgnoreCase("zm")) {
                    jsonSubmitReq.put(AppConstants.SUBMITBY_CD, realmCategoryAnswers.get(0).getSubmitbyCD());
                    jsonSubmitReq.put(AppConstants.SUBMITBY_RM, realmCategoryAnswers.get(0).getSubmitbyRM());
                    jsonSubmitReq.put(AppConstants.SUBMITBY_ZM, Prefs.getStringPrefs(AppConstants.UserId));
                }
                if (title.contains(AppConstants.WORKFLOWS)) {
                    if (type.equalsIgnoreCase("rm")){
                        jsonSubmitReq.put(AppConstants.CD_STATUS, "1");
                        jsonSubmitReq.put(AppConstants.RM_STATUS, "0");
                        jsonSubmitReq.put(AppConstants.ZM_STATUS, "4");
                    }else {
                        jsonSubmitReq.put(AppConstants.CD_STATUS, "1");
                        jsonSubmitReq.put(AppConstants.RM_STATUS, "2");
                        jsonSubmitReq.put(AppConstants.ZM_STATUS, "0");
                    }
                } else {
                    jsonSubmitReq.put(AppConstants.CD_STATUS, "5");
                    jsonSubmitReq.put(AppConstants.RM_STATUS, "4");
                    jsonSubmitReq.put(AppConstants.ZM_STATUS, "4");
                }
                //  jsonSubmitReq.put(AppConstants.CATEGORYID, categoryId);
                jsonSubmitReq.put(AppConstants.SURVEYID, surveyId);
                jsonSubmitReq.put(AppConstants.CUSTOMERID, customerId);

                jsonSubmitReq.put(AppConstants.CUSTOMER,strCustomer);

                jsonSubmitReq.put(AppConstants.WORKFLOW, array);
                jsonSubmitReq.put(AppConstants.DATE, Utility.getTodaysDate());
            }




        }catch (Exception e0){
            e0.printStackTrace();
            realm.close();
        }finally {
            if(!realm.isClosed()){
                realm.close();
            }

        }

        return jsonSubmitReq;
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        String designation=Prefs.getStringPrefs(AppConstants.TYPE);
        if (designation.equalsIgnoreCase("cd")) {
            if (strStatus.equalsIgnoreCase("1") || strStatus.equalsIgnoreCase("2")) {
                finish();
            } else {
                if (Utility.isConnected()) {
                    jsonSubmitReq = prepareJsonRequest("Reject", "");
                    submitAnswers(updateId, true);
                } else {
                    finish();
                }
            }
        }else {
            if (!strStatus.equalsIgnoreCase("2") || !strStatus.equalsIgnoreCase("3")) {
                finish();
            } else {
                if (Utility.isConnected()) {
                    jsonSubmitReq = prepareJsonRequest("Reject", "");
                    submitAnswers(updateId, true);
                } else {
                    finish();
                }
            }
        }
    }

    private void submitAnswers(final String isUpdateId, final boolean isBack) {
        if (Utility.validateString(isUpdateId)) {
            try {
                jsonSubmitReq.put(AppConstants.ID, isUpdateId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonSubmitReq.has(AppConstants.ISSYNC)) {
            jsonSubmitReq.remove(AppConstants.ISSYNC);
        }
        if (jsonSubmitReq.has(AppConstants.ISUPDATE)) {
            jsonSubmitReq.remove(AppConstants.ISUPDATE);
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.submittingAnswers));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        OkHttpClient okHttpClient = APIClient.getHttpClient();
        RequestBody requestBody = RequestBody.create(UniverseAPI.JSON, jsonSubmitReq.toString());
        String url="";

        url = UniverseAPI.WEB_SERVICE_CREATE_ANSWER_METHOD;
        if (Utility.validateString(isUpdateId) && isSync) {
            url = UniverseAPI.WEB_SERVICE_CREATE_UPDATE_METHOD;
        }



        /* else if (formId.equalsIgnoreCase(FormEnum.category.toString())) {
            url = UniverseAPI.WEB_SERVICE_CREATE_CATEGORY_METHOD;
        } else if (formId.equalsIgnoreCase(FormEnum.customer.toString())) {
            url = UniverseAPI.WEB_SERVICE_CREATE_CUSTOMER_METHOD;
        } else if (formId.equalsIgnoreCase(FormEnum.client.toString())) {
            url = UniverseAPI.WEB_SERVICE_CREATE_ClIENT_METHOD;
        }*/


        Request request = APIClient.getPostRequest(this, url, requestBody);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                if (progressDialog != null) progressDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToastMessage(e.getMessage());
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

                            if (!Utility.validateString(updateId)){
                                Realm realm = Realm.getDefaultInstance();
                                try {
                                    realm.beginTransaction();
                                    RealmResults<RealmAnswers> realmDeleteInputForms = realm.where(RealmAnswers.class).equalTo(AppConstants.ISSYNC, false).equalTo(AppConstants.CUSTOMERID,customerId).equalTo(AppConstants.SURVEYID,surveyId).findAll();
                                    if (realmDeleteInputForms != null && realmDeleteInputForms.size() > 0) {
                                        realmDeleteInputForms.deleteAllFromRealm();
                                    }
                                } catch (Exception e) {
                                    realm.cancelTransaction();
                                    realm.close();
                                    e.printStackTrace();
                                } finally {
                                    realm.commitTransaction();
                                    realm.close();
                                }
                            }
                            new RealmController().saveFormInputFromAnswersSubmit(jsonResponse.toString(), isUpdateId, "");
                        }


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (progressDialog != null) progressDialog.dismiss();
                                finish();
                            }
                        });

                    } else {
                        if (progressDialog != null) progressDialog.dismiss();
                    }

                } catch (Exception e) {
                    if (progressDialog != null) progressDialog.dismiss();
                    e.printStackTrace();
                } finally {
                }

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) result.getExtras().get("data");
            Uri tempUri = getImageUri(mActivity, photo);
            beginCrop(tempUri);
        } else if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    public void imageUpload(String path) {
//        ((BaseActivity) getActivity()).showProgress();
        CustomerPictureRequest profileRequest = new CustomerPictureRequest();
        profileRequest.setCustomerId(customerId);
        profileRequest.setIsPicture(1);
        profileRequest.setPhoto(path);
        CustomerPictureService profileService = new CustomerPictureService();
        profileService.executeService(profileRequest, new BaseApiCallback<CustomerPictureResponse>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(@NonNull CustomerPictureResponse response) {
                super.onSuccess(response);
                Glide.with(mContext)
                        .load(response.getResponse().getImage())
                        .into(circleImageView);
                Prefs.putBooleanPrefs(AppConstants.PROFILE_CHECK, true);
            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
            }
        });
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(mContext.getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(mActivity);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            mImageUrl = Crop.getOutput(result).getPath();
            if (Crop.getOutput(result).getPath() != null) {
                File file = new File(Crop.getOutput(result).getPath());
                Glide.with(mActivity)
                        .load(file)
                        .into(circleImageView);
                isUpdateImage = true;
                imageUpload(mImageUrl);
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(mActivity, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    protected void showToastMessage(String strMsg) {
        Utility.showToastMessage(this, strMsg);
    }

    private void calculateProgress(){

        int progressTotal=0;
        int progressRequired=0;

        arraylistTitle = new ArrayList<>();
        expandableListDetail=new HashMap<CategoryModal, List<Questions>>();
        ArrayList<String> arrISView=new ArrayList<>();

        Realm realm = Realm.getDefaultInstance();
        RealmSurveys realmSurveys=realm.where(RealmSurveys.class).equalTo(AppConstants.ID,surveyId).findFirst();

        try {
            JSONArray jsonArray = new JSONArray(realmSurveys.getCategoryId());
            for (int l=0;l<jsonArray.length();l++){

                RealmAnswers realmAnswers=realm.where(RealmAnswers.class).equalTo(AppConstants.SURVEYID,surveyId).equalTo(AppConstants.CUSTOMERID,customerId).findFirst();

                if (realmAnswers!=null){
                    if (Utility.validateString(realmAnswers.getCd_Status()))
                        strStatus=realmAnswers.getCd_Status();

                    if (realmAnswers.isSync()) {
                        //  updateId = realmAnswers.get_id();
                    }
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
                                    showToastMessage(getResources().getString(R.string.no_data));
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
                            RealmResults<RealmQuestion> realmQuestions = realm.where(RealmQuestion.class).equalTo(AppConstants.CATEGORYID, realmCategoryDetails.getId())/*.equalTo(AppConstants.SURVEYID, surveyId)*/.findAll();

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
                        showToastMessage(getResources().getString(R.string.no_data));
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
                expandableListDetail.put(arraylistTitle.get(m), productDetailsModelArrayList);
            }



            TextView textViewProgress=(TextView)findViewById(R.id.progressBarinsideText);
            seekBar.setValue(progressRequired);
            seekBar.setMaxValue(progressTotal);
            mProgress.setProgress(progressRequired);
            mProgress.setMax(progressTotal);
            //seekbar.setProgress(progressRequired);
            // seekbar.setMax(progressTotal);
            int percent=(progressRequired*100)/progressTotal;
            textViewProgress.setText(percent+"%");


            if (title.contains(AppConstants.WORKFLOWS)){
                textViewProgress.setVisibility(View.VISIBLE);
                mProgress.setVisibility(View.VISIBLE);
                llStatus.setVisibility(View.GONE);
              /*  String type=Prefs.getStringPrefs(AppConstants.TYPE);
                if (type.equalsIgnoreCase("rm")){
                    if (strStatus.equalsIgnoreCase("1") ||strStatus.equalsIgnoreCase("3")){

                        textViewProgress.setVisibility(View.GONE);
                        mProgress.setVisibility(View.GONE);
                        llStatus.setVisibility(View.VISIBLE);

                        if (strStatus.equalsIgnoreCase("1")){
                            textStatus.setText("Submitted");
                            imageStatus.setImageResource(R.drawable.ic_submitted);
                        }else if (strStatus.equalsIgnoreCase("2")){
                            textStatus.setText("Approved");
                            imageStatus.setImageResource(R.drawable.ic_submitted);
                        }else if (strStatus.equalsIgnoreCase("3")){
                            textStatus.setText("Rejected");
                            imageStatus.setImageResource(R.drawable.rejected);
                        }
                    }else {


                    }
                }else {

                }*/

            }else {
                if (strStatus.equalsIgnoreCase("1") || strStatus.equalsIgnoreCase("2") || strStatus.equalsIgnoreCase("3")) {

                    textViewProgress.setVisibility(View.GONE);
                    mProgress.setVisibility(View.GONE);
                    llStatus.setVisibility(View.VISIBLE);

                    if (strStatus.equalsIgnoreCase("1")) {
                        textStatus.setText("Submitted");
                        imageStatus.setImageResource(R.drawable.ic_submitted);
                    } else if (strStatus.equalsIgnoreCase("2")) {
                        textStatus.setText("Approved");
                        imageStatus.setImageResource(R.drawable.ic_submitted);
                    } else if (strStatus.equalsIgnoreCase("3")) {
                        textStatus.setText("Rejected");
                        imageStatus.setImageResource(R.drawable.rejected);
                    }
                } else {

                    textViewProgress.setVisibility(View.VISIBLE);
                    mProgress.setVisibility(View.VISIBLE);
                    llStatus.setVisibility(View.GONE);
                }
            }


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
