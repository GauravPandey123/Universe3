
package com.universe.android.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.SeekBar;
import android.widget.TextView;


import com.universe.android.R;
import com.universe.android.adapter.CustomExpandableListAdapter;
import com.universe.android.component.NonScrollExpandableListView;
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
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;
import com.universe.android.workflows.WorkFlowsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ru.bullyboo.view.CircleSeekBar;

public class CategoryExpandableListActivity extends AppCompatActivity {
    private JSONObject jsonSubmitReq = new JSONObject();
    private Toolbar toolbar;
    List<CategoryModal> arraylistTitle = new ArrayList<>();
    HashMap<CategoryModal, List<Questions>> expandableListDetail = new HashMap<CategoryModal, List<Questions>>();
    private NonScrollExpandableListView expandableListView;
    private CustomExpandableListAdapter expandableListAdapter;
    private String title,surveyId,customerId;
    private TextView textViewRetailersNameMap,textViewMobileNoMap;
    Button btnReject;
    Button btnApprove;
    private CircleSeekBar seekBar;
    private String updateId;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_category_expand_list);


        initViews();
        setHeader();

        Intent intent=getIntent();
        if (intent!=null){
           title= intent.getExtras().getString(AppConstants.STR_TITLE);
            surveyId= intent.getExtras().getString(AppConstants.SURVEYID);
            customerId= intent.getExtras().getString(AppConstants.CUSTOMERID);
           // customerId="5a83ca4296318c134c534cb9";
        }
        TextView toolbarTtile=(TextView)findViewById(R.id.toolbarTtile);
        toolbarTtile.setText(title);
        setupDetail();


        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        Utility.animateView(v);

        if (title.contains(AppConstants.WORKFLOWS)) {
            showReasonDialog();

        }else{

            jsonSubmitReq = prepareJsonRequest("Reject", "");

            if (Utility.isConnected()) {
                submitAnswers(updateId, true);
            } else {
                saveNCDResponseLocal(updateId, true);
            }
        }


           /* String updateId = "";
            if (view.getTag() != null) {
                if (view.getTag() instanceof String) {
                    updateId = (String) view.getTag();
                }
            }
            showReviewConfirmAlert(updateId, false);*/
                }

        });
        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.animateView(v);
                jsonSubmitReq = prepareJsonRequest("Approve", "");
                if (Utility.isConnected()){
                    submitAnswers(updateId,true);
                }else {
                    saveNCDResponseLocal(updateId,true);
                }


           /* String updateId = "";
            if (view.getTag() != null) {
                if (view.getTag() instanceof String) {
                    updateId = (String) view.getTag();
                }
            }
            showReviewConfirmAlert(updateId, false);*/
                }

        });
    }



    private JSONObject prepareJsonRequest(String type, String reason) {
        jsonSubmitReq=new JSONObject();
        JSONArray array=new JSONArray();
        Realm realm = Realm.getDefaultInstance();
        try{
            RealmResults<RealmAnswers> realmCategoryAnswers=realm.where(RealmAnswers.class).equalTo(AppConstants.CUSTOMERID,customerId).equalTo(AppConstants.SURVEYID,surveyId).findAll();

            if (realmCategoryAnswers!=null && realmCategoryAnswers.size()>0) {
                if (realmCategoryAnswers.get(0).isSync()) {
                    updateId = realmCategoryAnswers.get(0).get_id();
                }
                array = new JSONArray(realmCategoryAnswers.get(0).getWorkflow());
                jsonSubmitReq.put(AppConstants.ANSWERS, new JSONArray(realmCategoryAnswers.get(0).getAnswers()));
                if (Utility.validateString(updateId))
                jsonSubmitReq.put(AppConstants.ID, realmCategoryAnswers.get(0).get_id());
                String designation=Prefs.getStringPrefs(AppConstants.TYPE);
                if (designation.equalsIgnoreCase("cd"))
                jsonSubmitReq.put(AppConstants.SUBMITBY_CD, Prefs.getStringPrefs(AppConstants.UserId));
                if (designation.equalsIgnoreCase("rm"))
                jsonSubmitReq.put(AppConstants.SUBMITBY_RM, Prefs.getStringPrefs(AppConstants.UserId));
                if (designation.equalsIgnoreCase("zm"))
                jsonSubmitReq.put(AppConstants.SUBMITBY_ZM, Prefs.getStringPrefs(AppConstants.UserId));

                if (title.contains(AppConstants.WORKFLOWS)) {

                    if (designation.equalsIgnoreCase("rm")) {
                        if (type.equalsIgnoreCase("Approve")) {
                            jsonSubmitReq.put(AppConstants.CD_STATUS, "1");
                            jsonSubmitReq.put(AppConstants.RM_STATUS, "2");
                            jsonSubmitReq.put(AppConstants.ZM_STATUS, "0");
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put(AppConstants.DATE, Utility.getTodaysDate());
                            jsonObject.put(AppConstants.USERNAME, Prefs.getStringPrefs(AppConstants.USERNAME));
                            jsonObject.put(AppConstants.UserId, Prefs.getStringPrefs(AppConstants.UserId));
                            jsonObject.put(AppConstants.STATUS, "Approved");
                            array.put(jsonObject);
                        } else {
                            jsonSubmitReq.put(AppConstants.CD_STATUS, "3");
                            jsonSubmitReq.put(AppConstants.RM_STATUS, "3");
                            jsonSubmitReq.put(AppConstants.ZM_STATUS, "4");
                            jsonSubmitReq.put(AppConstants.REASON, reason);
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put(AppConstants.DATE, Utility.getTodaysDate());
                            jsonObject.put(AppConstants.UserId, Prefs.getStringPrefs(AppConstants.UserId));
                            jsonObject.put(AppConstants.USERNAME, Prefs.getStringPrefs(AppConstants.USERNAME));
                            jsonObject.put(AppConstants.STATUS, "Rejected");
                            array.put(jsonObject);
                        }
                    }else{
                        if (type.equalsIgnoreCase("Approve")) {
                            jsonSubmitReq.put(AppConstants.CD_STATUS, "2");
                            jsonSubmitReq.put(AppConstants.RM_STATUS, "2");
                            jsonSubmitReq.put(AppConstants.ZM_STATUS, "2");
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put(AppConstants.DATE, Utility.getTodaysDate());
                            jsonObject.put(AppConstants.UserId, Prefs.getStringPrefs(AppConstants.UserId));
                            jsonObject.put(AppConstants.USERNAME, Prefs.getStringPrefs(AppConstants.USERNAME));
                            jsonObject.put(AppConstants.STATUS, "Approved");
                            array.put(jsonObject);
                        } else {
                            jsonSubmitReq.put(AppConstants.CD_STATUS, "3");
                            jsonSubmitReq.put(AppConstants.RM_STATUS, "3");
                            jsonSubmitReq.put(AppConstants.ZM_STATUS, "3");
                            jsonSubmitReq.put(AppConstants.REASON, reason);
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put(AppConstants.DATE, Utility.getTodaysDate());
                            jsonObject.put(AppConstants.UserId, Prefs.getStringPrefs(AppConstants.UserId));
                            jsonObject.put(AppConstants.USERNAME, Prefs.getStringPrefs(AppConstants.USERNAME));
                            jsonObject.put(AppConstants.STATUS, "Rejected");
                            array.put(jsonObject);
                        }
                    }
                } else {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(AppConstants.DATE, Utility.getTodaysDate());
                    jsonObject.put(AppConstants.UserId, Prefs.getStringPrefs(AppConstants.UserId));
                    jsonObject.put(AppConstants.USERNAME, Prefs.getStringPrefs(AppConstants.USERNAME));
                    jsonObject.put(AppConstants.STATUS, "Submitted");
                    array.put(jsonObject);
                    jsonSubmitReq.put(AppConstants.CD_STATUS, "1");
                    jsonSubmitReq.put(AppConstants.RM_STATUS, "0");
                    jsonSubmitReq.put(AppConstants.ZM_STATUS, "4");
                }
                //  jsonSubmitReq.put(AppConstants.CATEGORYID, categoryId);
                jsonSubmitReq.put(AppConstants.SURVEYID, surveyId);
                jsonSubmitReq.put(AppConstants.CUSTOMERID, customerId);

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


    protected void saveResponseLocal(String formName, JSONObject jsonSubmitReq, String updateId) {
        if (jsonSubmitReq != null) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            try {
                if (Utility.validateString(updateId)) {
                    jsonSubmitReq.put(AppConstants.ID, updateId);
                } else {
                    if (jsonSubmitReq != null && !jsonSubmitReq.has(AppConstants.ID)) {
                        UUID randomId = UUID.randomUUID();
                        String id = String.valueOf(randomId);
                        jsonSubmitReq.put(AppConstants.ID, id);
                    }
                }
               /* if (isSync) {
                    jsonSubmitReq.put(AppConstants.ISUPDATE, false);
                } else {
                    jsonSubmitReq.put(AppConstants.ISSYNC, false);
                }*/



                    realm.createOrUpdateObjectFromJson(RealmAnswers.class, jsonSubmitReq);


            } catch (Exception e) {
                realm.cancelTransaction();
                realm.close();
            } finally {
                realm.commitTransaction();
                realm.close();
            }
        }
    }

    private void showMessageDialog(Context context, final boolean isBack, final String isUpdateId) {
        String strMsg = getResources().getString(R.string.save_data_msg);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(Html.fromHtml(strMsg));

        Button dialogButton = (Button) dialog.findViewById(R.id.btnYes);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.animateView(v);
                dialog.dismiss();


                Intent i=new Intent(CategoryExpandableListActivity.this, SearchCustomersActivity.class);

                if (btnApprove.getText().toString().equalsIgnoreCase("Approve")){
                     i=new Intent(CategoryExpandableListActivity.this, WorkFlowsActivity.class);

                }

                i.putExtra(AppConstants.STR_TITLE,title);
                i.putExtra(AppConstants.SURVEYID,surveyId);
                i.putExtra(AppConstants.CUSTOMERID,customerId);
                i.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
                if (Utility.validateString(isUpdateId)) {

                } else {

                }
            }
        });
        dialog.show();
    }

    private void saveNCDResponseLocal(String isUpdate, boolean isBack) {
        saveResponseLocal("", jsonSubmitReq, isUpdate);
        try {
            if (jsonSubmitReq.has(AppConstants.ID)) {
                jsonSubmitReq.remove(AppConstants.ID);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        showMessageDialog(this, isBack, isUpdate);
    }

    public void setHeader() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        // toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
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


    @Override
    protected void onResume() {

        super.onResume();
        prepareCategory();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initViews(){
        expandableListView = (NonScrollExpandableListView) findViewById(R.id.expandableListView);
        textViewMobileNoMap = (TextView) findViewById(R.id.textViewMobileNoMap);
        textViewRetailersNameMap = (TextView) findViewById(R.id.textViewRetailersNameMap);
        btnReject = (Button) findViewById(R.id.btnReject);
        btnApprove = (Button) findViewById(R.id.btnApprove);
      //  seekbar=(SeekBar)findViewById(R.id.seek_bar);
         seekBar = (CircleSeekBar) findViewById(R.id.seek_bar);

        expandableListView.setGroupIndicator(null);


        expandableListView
                .setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                    @Override
                    public boolean onChildClick(
                            ExpandableListView parent, View v,
                            int groupPosition, int childPosition,
                            long id) {
                        Utility.animateView(v);
                        Intent i=new Intent(CategoryExpandableListActivity.this,QuestionaireActivity.class);
                        i.putExtra(AppConstants.STR_TITLE,title);
                        i.putExtra(AppConstants.SURVEYID,surveyId);
                        i.putExtra(AppConstants.CUSTOMERID,customerId);
                        i.putExtra(AppConstants.UPDATEID,updateId);
                        Prefs.putStringPrefs(AppConstants.VISIBLITY,"");
                        i.putExtra(AppConstants.GROUP_POSITION,groupPosition);
                        startActivity(i);

                        return false;
                    }
                });
    }


    private void prepareCategory(){

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
                    updateId=realmAnswers.get_id();
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

            expandableListView.setVisibility(View.VISIBLE);
            expandableListAdapter = new CustomExpandableListAdapter(CategoryExpandableListActivity.this, arraylistTitle, expandableListDetail);
            expandableListView.setAdapter(expandableListAdapter);

            TextView textViewProgress=(TextView)findViewById(R.id.progressBarinsideText);
            seekBar.setValue(progressRequired);
            seekBar.setMaxValue(progressTotal);
            int percent=(progressRequired*100)/progressTotal;
            textViewProgress.setText(percent+"%");
            if (title.contains(AppConstants.WORKFLOWS)){
                btnApprove.setText(getString(R.string.approve));
                btnReject.setText(getString(R.string.reject));
                if (arraylistTitle.size()!=arrISView.size()){
                    btnApprove.setBackgroundResource(R.color.grey);
                    btnApprove.setEnabled(false);
                    btnReject.setBackgroundResource(R.color.red);
                    btnReject.setEnabled(true);

                }else {
                    btnApprove.setBackgroundResource(R.color.green);
                    btnApprove.setEnabled(true);
                    btnReject.setBackgroundResource(R.color.red);
                    btnReject.setEnabled(true);
                }
            }else {
                if (categoryAnswered.contains("No") || categoryAnswered.contains("")) {
                    btnApprove.setBackgroundResource(R.color.grey);
                    btnApprove.setEnabled(false);
                    btnReject.setBackgroundResource(R.color.grey);
                    btnReject.setEnabled(false);

                } else {
                    btnApprove.setBackgroundResource(R.color.green);
                    btnApprove.setEnabled(true);
                    btnReject.setBackgroundResource(R.color.green);
                    btnReject.setEnabled(true);
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
    protected void showToastMessage(String strMsg) {
        Utility.showToastMessage(this, strMsg);
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
        if (btnReject.getText().toString().equalsIgnoreCase("Reject")) {
             url = UniverseAPI.WEB_SERVICE_CREATE_APPROVE_METHOD;
            if (Utility.validateString(isUpdateId)) {
                url = UniverseAPI.WEB_SERVICE_CREATE_APPROVE_METHOD;
            }
        }else {
            if (Utility.validateString(isUpdateId)){
                url = UniverseAPI.WEB_SERVICE_CREATE_UPDATE_METHOD;
            }else{
                url = UniverseAPI.WEB_SERVICE_CREATE_ANSWER_METHOD;
            }

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
                                showMessageDialog(CategoryExpandableListActivity.this, isBack, isUpdateId);
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


    public void showReasonDialog(){
        final EditText taskEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.reason))
                .setView(taskEditText)
                .setPositiveButton(getString(R.string.submit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String reason = String.valueOf(taskEditText.getText());
                        jsonSubmitReq = prepareJsonRequest("Reject",reason);


                        if (Utility.isConnected()){
                            submitAnswers(updateId,true);
                        }else {
                            saveNCDResponseLocal(updateId,false);
                        }
                    }
                })
                .setNegativeButton(getString(R.string.cancel), null)
                .create();
        dialog.show();


    }
}

