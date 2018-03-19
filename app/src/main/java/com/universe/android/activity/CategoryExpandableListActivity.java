
package com.universe.android.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;


import com.universe.android.R;
import com.universe.android.adapter.CustomExpandableListAdapter;
import com.universe.android.component.NonScrollExpandableListView;
import com.universe.android.enums.DesignationEnum;
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
import com.universe.android.realmbean.RealmWorkFlow;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;
import com.universe.android.workflows.WorkFlowsActivity;

import android.widget.Toast;

import com.universe.android.resource.Login.CutomerPictureChange.CustomerPictureRequest;
import com.universe.android.resource.Login.CutomerPictureChange.CustomerPictureResponse;
import com.universe.android.resource.Login.CutomerPictureChange.CustomerPictureService;
import com.universe.android.web.BaseApiCallback;
import com.universe.android.web.BaseRequest;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;

import com.soundcloud.android.crop.Crop;

import de.hdodenhof.circleimageview.CircleImageView;
import in.editsoft.api.exception.APIException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmResults;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CategoryExpandableListActivity extends BaseActivity {
    private JSONObject jsonSubmitReq = new JSONObject();
    private Toolbar toolbar;
    List<CategoryModal> arraylistTitle = new ArrayList<>();
    HashMap<CategoryModal, List<Questions>> expandableListDetail = new HashMap<CategoryModal, List<Questions>>();
    private NonScrollExpandableListView expandableListView;
    private CustomExpandableListAdapter expandableListAdapter;
    private String title, surveyId, customerId;
    private TextView textViewRetailersNameMap, textViewMobileNoMap;
    Button btnReject;
    Button btnApprove;
    private String updateId, strCustomer = "", strStatus = "";
    private ProgressBar mProgress;
    private LinearLayout llStatus;
    private ImageView imageStatus;
    private TextView textStatus;
    private String mImageUrl;
    private boolean isUpdateImage = false;
    CircleImageView circleImageView;
    private ImageView imageLoc;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_category_expand_list);


        initViews();
        setHeader();

        Intent intent = getIntent();
        if (intent != null) {
            title = intent.getExtras().getString(AppConstants.STR_TITLE);
            surveyId = intent.getExtras().getString(AppConstants.SURVEYID);
            customerId = intent.getExtras().getString(AppConstants.CUSTOMERID);
            strCustomer = intent.getExtras().getString(AppConstants.CUSTOMER);
            // customerId="5a83ca4296318c134c534cb9";
        }
        TextView toolbarTtile = (TextView) findViewById(R.id.toolbarTtile);
        toolbarTtile.setText(title);

        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.animateView(v);

                if (title.contains(AppConstants.WORKFLOWS)) {
                    showReasonDialog();

                } else {

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
                if (Utility.isConnected()) {
                    submitAnswers(updateId, true);
                } else {
                    saveNCDResponseLocal(updateId, true);
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
        jsonSubmitReq = new JSONObject();
        JSONArray array = new JSONArray();
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<RealmAnswers> realmCategoryAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.CUSTOMERID, customerId).equalTo(AppConstants.SURVEYID, surveyId).findAll();

            if (realmCategoryAnswers != null && realmCategoryAnswers.size() > 0) {
                if (realmCategoryAnswers.get(0).isSync()) {
                    updateId = realmCategoryAnswers.get(0).get_id();
                }
                if (Utility.validateString(realmCategoryAnswers.get(0).getCustomer())) {
                    strCustomer = realmCategoryAnswers.get(0).getCustomer();
                }
                array = new JSONArray(realmCategoryAnswers.get(0).getWorkflow());
                jsonSubmitReq.put(AppConstants.ANSWERS, new JSONArray(realmCategoryAnswers.get(0).getAnswers()));
                if (Utility.validateString(updateId))
                    jsonSubmitReq.put(AppConstants.ID, realmCategoryAnswers.get(0).get_id());
                String designation = Prefs.getStringPrefs(AppConstants.TYPE);


                if (designation.equalsIgnoreCase(DesignationEnum.requester.toString())) {
                    jsonSubmitReq.put(DesignationEnum.requester.toString(), Prefs.getStringPrefs(AppConstants.UserId));
                    jsonSubmitReq.put(DesignationEnum.approval1.toString(), realmCategoryAnswers.get(0).getApproval1());
                    jsonSubmitReq.put(DesignationEnum.approval2.toString(), realmCategoryAnswers.get(0).getApproval2());
                    jsonSubmitReq.put(DesignationEnum.approval3.toString(), realmCategoryAnswers.get(0).getApproval3());
                    jsonSubmitReq.put(DesignationEnum.approval4.toString(), realmCategoryAnswers.get(0).getApproval4());
                    jsonSubmitReq.put(DesignationEnum.approval5.toString(), realmCategoryAnswers.get(0).getApproval5());
                    jsonSubmitReq.put(DesignationEnum.approval6.toString(), realmCategoryAnswers.get(0).getApproval6());
                }
                if (designation.equalsIgnoreCase(DesignationEnum.approval1.toString())) {
                    jsonSubmitReq.put(DesignationEnum.requester.toString(), realmCategoryAnswers.get(0).getRequester());
                    jsonSubmitReq.put(DesignationEnum.approval1.toString(), Prefs.getStringPrefs(AppConstants.UserId));
                    jsonSubmitReq.put(DesignationEnum.approval2.toString(), realmCategoryAnswers.get(0).getApproval2());
                    jsonSubmitReq.put(DesignationEnum.approval3.toString(), realmCategoryAnswers.get(0).getApproval3());
                    jsonSubmitReq.put(DesignationEnum.approval4.toString(), realmCategoryAnswers.get(0).getApproval4());
                    jsonSubmitReq.put(DesignationEnum.approval5.toString(), realmCategoryAnswers.get(0).getApproval5());
                    jsonSubmitReq.put(DesignationEnum.approval6.toString(), realmCategoryAnswers.get(0).getApproval6());

                }
                if (designation.equalsIgnoreCase(DesignationEnum.approval2.toString())) {
                    jsonSubmitReq.put(DesignationEnum.requester.toString(), realmCategoryAnswers.get(0).getRequester());
                    jsonSubmitReq.put(DesignationEnum.approval1.toString(), realmCategoryAnswers.get(0).getApproval1());
                    jsonSubmitReq.put(DesignationEnum.approval2.toString(), Prefs.getStringPrefs(AppConstants.UserId));
                    jsonSubmitReq.put(DesignationEnum.approval3.toString(), realmCategoryAnswers.get(0).getApproval3());
                    jsonSubmitReq.put(DesignationEnum.approval4.toString(), realmCategoryAnswers.get(0).getApproval4());
                    jsonSubmitReq.put(DesignationEnum.approval5.toString(), realmCategoryAnswers.get(0).getApproval5());
                    jsonSubmitReq.put(DesignationEnum.approval6.toString(), realmCategoryAnswers.get(0).getApproval6());

                }
                if (designation.equalsIgnoreCase(DesignationEnum.approval3.toString())) {
                    jsonSubmitReq.put(DesignationEnum.requester.toString(), realmCategoryAnswers.get(0).getRequester());
                    jsonSubmitReq.put(DesignationEnum.approval1.toString(), realmCategoryAnswers.get(0).getApproval1());
                    jsonSubmitReq.put(DesignationEnum.approval2.toString(), realmCategoryAnswers.get(0).getApproval2());
                    jsonSubmitReq.put(DesignationEnum.approval3.toString(), Prefs.getStringPrefs(AppConstants.UserId));
                    jsonSubmitReq.put(DesignationEnum.approval4.toString(), realmCategoryAnswers.get(0).getApproval4());
                    jsonSubmitReq.put(DesignationEnum.approval5.toString(), realmCategoryAnswers.get(0).getApproval5());
                    jsonSubmitReq.put(DesignationEnum.approval6.toString(), realmCategoryAnswers.get(0).getApproval6());

                }
                if (designation.equalsIgnoreCase(DesignationEnum.approval4.toString())) {
                    jsonSubmitReq.put(DesignationEnum.requester.toString(), realmCategoryAnswers.get(0).getRequester());
                    jsonSubmitReq.put(DesignationEnum.approval1.toString(), realmCategoryAnswers.get(0).getApproval1());
                    jsonSubmitReq.put(DesignationEnum.approval2.toString(), realmCategoryAnswers.get(0).getApproval2());
                    jsonSubmitReq.put(DesignationEnum.approval3.toString(), realmCategoryAnswers.get(0).getApproval3());
                    jsonSubmitReq.put(DesignationEnum.approval4.toString(), Prefs.getStringPrefs(AppConstants.UserId));
                    jsonSubmitReq.put(DesignationEnum.approval5.toString(), realmCategoryAnswers.get(0).getApproval5());
                    jsonSubmitReq.put(DesignationEnum.approval6.toString(), realmCategoryAnswers.get(0).getApproval6());

                }
                if (designation.equalsIgnoreCase(DesignationEnum.approval5.toString())) {
                    jsonSubmitReq.put(DesignationEnum.requester.toString(), realmCategoryAnswers.get(0).getRequester());
                    jsonSubmitReq.put(DesignationEnum.approval1.toString(), realmCategoryAnswers.get(0).getApproval1());
                    jsonSubmitReq.put(DesignationEnum.approval2.toString(), realmCategoryAnswers.get(0).getApproval2());
                    jsonSubmitReq.put(DesignationEnum.approval3.toString(), realmCategoryAnswers.get(0).getApproval3());
                    jsonSubmitReq.put(DesignationEnum.approval4.toString(), realmCategoryAnswers.get(0).getApproval4());
                    jsonSubmitReq.put(DesignationEnum.approval5.toString(), Prefs.getStringPrefs(AppConstants.UserId));
                    jsonSubmitReq.put(DesignationEnum.approval6.toString(), realmCategoryAnswers.get(0).getApproval6());

                }
                if (designation.equalsIgnoreCase(DesignationEnum.approval6.toString())) {
                    jsonSubmitReq.put(DesignationEnum.requester.toString(), realmCategoryAnswers.get(0).getRequester());
                    jsonSubmitReq.put(DesignationEnum.approval1.toString(), realmCategoryAnswers.get(0).getApproval1());
                    jsonSubmitReq.put(DesignationEnum.approval2.toString(), realmCategoryAnswers.get(0).getApproval2());
                    jsonSubmitReq.put(DesignationEnum.approval3.toString(), realmCategoryAnswers.get(0).getApproval3());
                    jsonSubmitReq.put(DesignationEnum.approval4.toString(), realmCategoryAnswers.get(0).getApproval4());
                    jsonSubmitReq.put(DesignationEnum.approval5.toString(), realmCategoryAnswers.get(0).getApproval5());
                    jsonSubmitReq.put(DesignationEnum.approval6.toString(), Prefs.getStringPrefs(AppConstants.UserId));

                }


                RealmResults<RealmWorkFlow> realmWorkFlows = realm.where(RealmWorkFlow.class).equalTo(AppConstants.SURVEYID, surveyId).findAll();

                String level = realmWorkFlows.get(realmWorkFlows.size() - 1).getLevel();
                if (title.contains(AppConstants.WORKFLOWS)) {
                    if (designation.equalsIgnoreCase(DesignationEnum.approval1.toString())) {
                        if (type.equalsIgnoreCase("Approve")) {
                            jsonSubmitReq.put(AppConstants.requester_status, "1");
                            jsonSubmitReq.put(AppConstants.approval1_status, "2");
                            jsonSubmitReq.put(AppConstants.approval2_status, "0");
                            jsonSubmitReq.put(AppConstants.approval3_status, "4");
                            jsonSubmitReq.put(AppConstants.approval4_status, "4");
                            jsonSubmitReq.put(AppConstants.approval5_status, "4");
                            jsonSubmitReq.put(AppConstants.approval6_status, "4");
                            if (level.equalsIgnoreCase(DesignationEnum.approval1.toString())) {
                                jsonSubmitReq.put(AppConstants.requester_status, "2");
                                jsonSubmitReq.put(AppConstants.approval2_status, "4");
                            }
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put(AppConstants.DATE, Utility.getTodaysDate());
                            jsonObject.put(AppConstants.USERNAME, Prefs.getStringPrefs(AppConstants.employee_name));
                            jsonObject.put(AppConstants.UserId, Prefs.getStringPrefs(AppConstants.UserId));
                            jsonObject.put(AppConstants.STATUS, "Approved");
                            array.put(jsonObject);
                        } else {

                            jsonSubmitReq.put(AppConstants.requester_status, "3");
                            jsonSubmitReq.put(AppConstants.approval1_status, "3");
                            jsonSubmitReq.put(AppConstants.approval2_status, "4");
                            jsonSubmitReq.put(AppConstants.approval3_status, "4");
                            jsonSubmitReq.put(AppConstants.approval4_status, "4");
                            jsonSubmitReq.put(AppConstants.approval5_status, "4");
                            jsonSubmitReq.put(AppConstants.approval6_status, "4");

                            jsonSubmitReq.put(AppConstants.REASON, reason);
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put(AppConstants.DATE, Utility.getTodaysDate());
                            jsonObject.put(AppConstants.UserId, Prefs.getStringPrefs(AppConstants.UserId));
                            jsonObject.put(AppConstants.USERNAME, Prefs.getStringPrefs(AppConstants.employee_name));
                            jsonObject.put(AppConstants.STATUS, "Rejected");
                            array.put(jsonObject);
                        }
                    }
                    if (designation.equalsIgnoreCase(DesignationEnum.approval2.toString())) {
                        if (type.equalsIgnoreCase("Approve")) {
                            jsonSubmitReq.put(AppConstants.requester_status, "1");
                            jsonSubmitReq.put(AppConstants.approval1_status, "2");
                            jsonSubmitReq.put(AppConstants.approval2_status, "2");
                            jsonSubmitReq.put(AppConstants.approval3_status, "0");
                            jsonSubmitReq.put(AppConstants.approval4_status, "4");
                            jsonSubmitReq.put(AppConstants.approval5_status, "4");
                            jsonSubmitReq.put(AppConstants.approval6_status, "4");

                            if (level.equalsIgnoreCase(DesignationEnum.approval2.toString())) {
                                jsonSubmitReq.put(AppConstants.requester_status, "2");
                                jsonSubmitReq.put(AppConstants.approval3_status, "4");
                            }
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put(AppConstants.DATE, Utility.getTodaysDate());
                            jsonObject.put(AppConstants.USERNAME, Prefs.getStringPrefs(AppConstants.employee_name));
                            jsonObject.put(AppConstants.UserId, Prefs.getStringPrefs(AppConstants.UserId));
                            jsonObject.put(AppConstants.STATUS, "Approved");
                            array.put(jsonObject);
                        } else {

                            jsonSubmitReq.put(AppConstants.requester_status, "3");
                            jsonSubmitReq.put(AppConstants.approval1_status, "3");
                            jsonSubmitReq.put(AppConstants.approval2_status, "3");
                            jsonSubmitReq.put(AppConstants.approval3_status, "4");
                            jsonSubmitReq.put(AppConstants.approval4_status, "4");
                            jsonSubmitReq.put(AppConstants.approval5_status, "4");
                            jsonSubmitReq.put(AppConstants.approval6_status, "4");

                            jsonSubmitReq.put(AppConstants.REASON, reason);
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put(AppConstants.DATE, Utility.getTodaysDate());
                            jsonObject.put(AppConstants.UserId, Prefs.getStringPrefs(AppConstants.UserId));
                            jsonObject.put(AppConstants.USERNAME, Prefs.getStringPrefs(AppConstants.employee_name));
                            jsonObject.put(AppConstants.STATUS, "Rejected");
                            array.put(jsonObject);
                        }
                    }
                    if (designation.equalsIgnoreCase(DesignationEnum.approval3.toString())) {
                        if (type.equalsIgnoreCase("Approve")) {
                            jsonSubmitReq.put(AppConstants.requester_status, "1");
                            jsonSubmitReq.put(AppConstants.approval1_status, "2");
                            jsonSubmitReq.put(AppConstants.approval2_status, "2");
                            jsonSubmitReq.put(AppConstants.approval3_status, "2");
                            jsonSubmitReq.put(AppConstants.approval4_status, "0");
                            jsonSubmitReq.put(AppConstants.approval5_status, "4");
                            jsonSubmitReq.put(AppConstants.approval6_status, "4");

                            if (level.equalsIgnoreCase(DesignationEnum.approval3.toString())) {
                                jsonSubmitReq.put(AppConstants.requester_status, "2");
                                jsonSubmitReq.put(AppConstants.approval4_status, "4");
                            }
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put(AppConstants.DATE, Utility.getTodaysDate());
                            jsonObject.put(AppConstants.USERNAME, Prefs.getStringPrefs(AppConstants.employee_name));
                            jsonObject.put(AppConstants.UserId, Prefs.getStringPrefs(AppConstants.UserId));
                            jsonObject.put(AppConstants.STATUS, "Approved");
                            array.put(jsonObject);
                        } else {

                            jsonSubmitReq.put(AppConstants.requester_status, "3");
                            jsonSubmitReq.put(AppConstants.approval1_status, "3");
                            jsonSubmitReq.put(AppConstants.approval2_status, "3");
                            jsonSubmitReq.put(AppConstants.approval3_status, "3");
                            jsonSubmitReq.put(AppConstants.approval4_status, "4");
                            jsonSubmitReq.put(AppConstants.approval5_status, "4");
                            jsonSubmitReq.put(AppConstants.approval6_status, "4");

                            jsonSubmitReq.put(AppConstants.REASON, reason);
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put(AppConstants.DATE, Utility.getTodaysDate());
                            jsonObject.put(AppConstants.UserId, Prefs.getStringPrefs(AppConstants.UserId));
                            jsonObject.put(AppConstants.USERNAME, Prefs.getStringPrefs(AppConstants.employee_name));
                            jsonObject.put(AppConstants.STATUS, "Rejected");
                            array.put(jsonObject);
                        }
                    }
                    if (designation.equalsIgnoreCase(DesignationEnum.approval4.toString())) {
                        if (type.equalsIgnoreCase("Approve")) {
                            jsonSubmitReq.put(AppConstants.requester_status, "1");
                            jsonSubmitReq.put(AppConstants.approval1_status, "2");
                            jsonSubmitReq.put(AppConstants.approval2_status, "2");
                            jsonSubmitReq.put(AppConstants.approval3_status, "2");
                            jsonSubmitReq.put(AppConstants.approval4_status, "2");
                            jsonSubmitReq.put(AppConstants.approval5_status, "0");
                            jsonSubmitReq.put(AppConstants.approval6_status, "4");
                            if (level.equalsIgnoreCase(DesignationEnum.approval4.toString())) {
                                jsonSubmitReq.put(AppConstants.requester_status, "2");
                                jsonSubmitReq.put(AppConstants.approval5_status, "4");
                            }
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put(AppConstants.DATE, Utility.getTodaysDate());
                            jsonObject.put(AppConstants.USERNAME, Prefs.getStringPrefs(AppConstants.employee_name));
                            jsonObject.put(AppConstants.UserId, Prefs.getStringPrefs(AppConstants.UserId));
                            jsonObject.put(AppConstants.STATUS, "Approved");
                            array.put(jsonObject);
                        } else {

                            jsonSubmitReq.put(AppConstants.requester_status, "3");
                            jsonSubmitReq.put(AppConstants.approval1_status, "3");
                            jsonSubmitReq.put(AppConstants.approval2_status, "3");
                            jsonSubmitReq.put(AppConstants.approval3_status, "3");
                            jsonSubmitReq.put(AppConstants.approval4_status, "3");
                            jsonSubmitReq.put(AppConstants.approval5_status, "4");
                            jsonSubmitReq.put(AppConstants.approval6_status, "4");

                            jsonSubmitReq.put(AppConstants.REASON, reason);
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put(AppConstants.DATE, Utility.getTodaysDate());
                            jsonObject.put(AppConstants.UserId, Prefs.getStringPrefs(AppConstants.UserId));
                            jsonObject.put(AppConstants.USERNAME, Prefs.getStringPrefs(AppConstants.employee_name));
                            jsonObject.put(AppConstants.STATUS, "Rejected");
                            array.put(jsonObject);
                        }
                    }
                    if (designation.equalsIgnoreCase(DesignationEnum.approval5.toString())) {
                        if (type.equalsIgnoreCase("Approve")) {
                            jsonSubmitReq.put(AppConstants.requester_status, "1");
                            jsonSubmitReq.put(AppConstants.approval1_status, "2");
                            jsonSubmitReq.put(AppConstants.approval2_status, "2");
                            jsonSubmitReq.put(AppConstants.approval3_status, "2");
                            jsonSubmitReq.put(AppConstants.approval4_status, "2");
                            jsonSubmitReq.put(AppConstants.approval5_status, "2");
                            jsonSubmitReq.put(AppConstants.approval6_status, "0");
                            if (level.equalsIgnoreCase(DesignationEnum.approval5.toString())) {
                                jsonSubmitReq.put(AppConstants.requester_status, "2");
                                jsonSubmitReq.put(AppConstants.approval6_status, "4");
                            }
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put(AppConstants.DATE, Utility.getTodaysDate());
                            jsonObject.put(AppConstants.USERNAME, Prefs.getStringPrefs(AppConstants.employee_name));
                            jsonObject.put(AppConstants.UserId, Prefs.getStringPrefs(AppConstants.UserId));
                            jsonObject.put(AppConstants.STATUS, "Approved");
                            array.put(jsonObject);
                        } else {

                            jsonSubmitReq.put(AppConstants.requester_status, "3");
                            jsonSubmitReq.put(AppConstants.approval1_status, "3");
                            jsonSubmitReq.put(AppConstants.approval2_status, "3");
                            jsonSubmitReq.put(AppConstants.approval3_status, "3");
                            jsonSubmitReq.put(AppConstants.approval4_status, "3");
                            jsonSubmitReq.put(AppConstants.approval5_status, "3");
                            jsonSubmitReq.put(AppConstants.approval6_status, "4");

                            jsonSubmitReq.put(AppConstants.REASON, reason);
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put(AppConstants.DATE, Utility.getTodaysDate());
                            jsonObject.put(AppConstants.UserId, Prefs.getStringPrefs(AppConstants.UserId));
                            jsonObject.put(AppConstants.USERNAME, Prefs.getStringPrefs(AppConstants.employee_name));
                            jsonObject.put(AppConstants.STATUS, "Rejected");
                            array.put(jsonObject);
                        }
                    }

                    if (designation.equalsIgnoreCase(DesignationEnum.approval6.toString())) {
                        if (type.equalsIgnoreCase("Approve")) {
                            jsonSubmitReq.put(AppConstants.requester_status, "2");
                            jsonSubmitReq.put(AppConstants.approval1_status, "2");
                            jsonSubmitReq.put(AppConstants.approval2_status, "2");
                            jsonSubmitReq.put(AppConstants.approval3_status, "2");
                            jsonSubmitReq.put(AppConstants.approval4_status, "2");
                            jsonSubmitReq.put(AppConstants.approval5_status, "2");
                            jsonSubmitReq.put(AppConstants.approval6_status, "2");

                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put(AppConstants.DATE, Utility.getTodaysDate());
                            jsonObject.put(AppConstants.USERNAME, Prefs.getStringPrefs(AppConstants.employee_name));
                            jsonObject.put(AppConstants.UserId, Prefs.getStringPrefs(AppConstants.UserId));
                            jsonObject.put(AppConstants.STATUS, "Approved");
                            array.put(jsonObject);
                        } else {

                            jsonSubmitReq.put(AppConstants.requester_status, "3");
                            jsonSubmitReq.put(AppConstants.approval1_status, "3");
                            jsonSubmitReq.put(AppConstants.approval2_status, "3");
                            jsonSubmitReq.put(AppConstants.approval3_status, "3");
                            jsonSubmitReq.put(AppConstants.approval4_status, "3");
                            jsonSubmitReq.put(AppConstants.approval5_status, "3");
                            jsonSubmitReq.put(AppConstants.approval6_status, "3");

                            jsonSubmitReq.put(AppConstants.REASON, reason);
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put(AppConstants.DATE, Utility.getTodaysDate());
                            jsonObject.put(AppConstants.UserId, Prefs.getStringPrefs(AppConstants.UserId));
                            jsonObject.put(AppConstants.USERNAME, Prefs.getStringPrefs(AppConstants.employee_name));
                            jsonObject.put(AppConstants.STATUS, "Rejected");
                            array.put(jsonObject);
                        }
                    }


                } else {
                    if (designation.equalsIgnoreCase(DesignationEnum.requester.toString())) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put(AppConstants.DATE, Utility.getTodaysDate());
                        jsonObject.put(AppConstants.UserId, Prefs.getStringPrefs(AppConstants.UserId));
                        jsonObject.put(AppConstants.USERNAME, Prefs.getStringPrefs(AppConstants.employee_name));
                        jsonObject.put(AppConstants.STATUS, "Submitted");
                        array.put(jsonObject);
                        jsonSubmitReq.put(AppConstants.requester_status, "1");
                        jsonSubmitReq.put(AppConstants.approval1_status, "0");
                        jsonSubmitReq.put(AppConstants.approval2_status, "4");
                        jsonSubmitReq.put(AppConstants.approval3_status, "4");
                        jsonSubmitReq.put(AppConstants.approval4_status, "4");
                        jsonSubmitReq.put(AppConstants.approval5_status, "4");
                        jsonSubmitReq.put(AppConstants.approval6_status, "4");


                    }
                }
                //  jsonSubmitReq.put(AppConstants.CATEGORYID, categoryId);
                jsonSubmitReq.put(AppConstants.SURVEYID, surveyId);
                jsonSubmitReq.put(AppConstants.CUSTOMERID, customerId);

                jsonSubmitReq.put(AppConstants.CUSTOMER, strCustomer);


                jsonSubmitReq.put(AppConstants.WORKFLOW, array);
                jsonSubmitReq.put(AppConstants.DATE, Utility.getTodaysDate());
            }


        } catch (Exception e0) {
            e0.printStackTrace();
            realm.close();
        } finally {
            if (!realm.isClosed()) {
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


                Intent i = new Intent(CategoryExpandableListActivity.this, SearchCustomersActivity.class);

                if (btnApprove.getText().toString().equalsIgnoreCase("Approve")) {
                    i = new Intent(CategoryExpandableListActivity.this, WorkFlowsActivity.class);

                }

                i.putExtra(AppConstants.STR_TITLE, title);
                i.putExtra(AppConstants.SURVEYID, surveyId);
                i.putExtra(AppConstants.CUSTOMERID, customerId);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
            }

            if (realmCustomer.isLocation()) {
                imageLoc.setImageResource(R.drawable.ic_location_set);
            } else {
                imageLoc.setImageResource(R.drawable.red_loc);

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
    protected void onResume() {
        super.onResume();
        setupDetail();
        prepareCategory();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initViews() {
        expandableListView = (NonScrollExpandableListView) findViewById(R.id.expandableListView);
        textViewMobileNoMap = (TextView) findViewById(R.id.textViewMobileNoMap);
        textViewRetailersNameMap = (TextView) findViewById(R.id.textViewRetailersNameMap);
        btnReject = (Button) findViewById(R.id.btnReject);
        btnApprove = (Button) findViewById(R.id.btnApprove);
        llStatus = (LinearLayout) findViewById(R.id.llStatus);
        imageStatus = (ImageView) findViewById(R.id.imageStatus);
        textStatus = (TextView) findViewById(R.id.textStatus);
        imageLoc = findViewById(R.id.imageLoc);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rlImage);
        //  seekbar=(SeekBar)findViewById(R.id.seek_bar);


        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.circular_progress);
        mProgress = (ProgressBar) findViewById(R.id.circularProgressbar);
        mProgress.setProgress(0);   // Main Progress
        mProgress.setSecondaryProgress(100); // Secondary Progress
        mProgress.setMax(100); // Maximum Progress
        mProgress.setProgressDrawable(drawable);
        circleImageView = (CircleImageView) findViewById(R.id.circularImageViewMap);
        circleImageView = findViewById(R.id.circularImageViewMap);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageOptions();
            }
        });


        llStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryExpandableListActivity.this, WorkFlowsActivity.class);
                intent.putExtra(AppConstants.STR_TITLE, title);
                intent.putExtra(AppConstants.SURVEYID, surveyId);
                intent.putExtra(AppConstants.CUSTOMERID, customerId);
                intent.putExtra(AppConstants.CUSTOMER, strCustomer);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (strStatus.equalsIgnoreCase("1") || strStatus.equalsIgnoreCase("2") || strStatus.equalsIgnoreCase("3")) {

                } else {
                    Intent intent = new Intent(CategoryExpandableListActivity.this, MapsOneActivity.class);
                    intent.putExtra(AppConstants.STR_TITLE, title);
                    intent.putExtra(AppConstants.SURVEYID, surveyId);
                    intent.putExtra(AppConstants.CUSTOMERID, customerId);
                    intent.putExtra(AppConstants.CUSTOMER, strCustomer);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }

            }
        });
        expandableListView.setGroupIndicator(null);


        expandableListView
                .setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                    @Override
                    public boolean onChildClick(
                            ExpandableListView parent, View v,
                            int groupPosition, int childPosition,
                            long id) {
                        Utility.animateView(v);
                        Intent i = new Intent(CategoryExpandableListActivity.this, QuestionaireActivity.class);
                        i.putExtra(AppConstants.STR_TITLE, title);
                        i.putExtra(AppConstants.SURVEYID, surveyId);
                        i.putExtra(AppConstants.CUSTOMERID, customerId);
                        i.putExtra(AppConstants.UPDATEID, updateId);
                        Prefs.putStringPrefs(AppConstants.VISIBLITY, "");
                        i.putExtra(AppConstants.GROUP_POSITION, groupPosition);
                        i.putExtra(AppConstants.STATUS, strStatus);
                        startActivity(i);

                        return false;
                    }
                });
    }


    private void prepareCategory() {

        int progressTotal = 0;
        int progressRequired = 0;

        arraylistTitle = new ArrayList<>();
        expandableListDetail = new HashMap<CategoryModal, List<Questions>>();
        ArrayList<String> arrISView = new ArrayList<>();
        ArrayList<String> arrISViewByZM = new ArrayList<>();

        Realm realm = Realm.getDefaultInstance();
        RealmSurveys realmSurveys = realm.where(RealmSurveys.class).equalTo(AppConstants.ID, surveyId).findFirst();

        try {
            JSONArray jsonArray = new JSONArray(realmSurveys.getCategoryId());
            for (int l = 0; l < jsonArray.length(); l++) {

                RealmAnswers realmAnswers = realm.where(RealmAnswers.class).equalTo(AppConstants.SURVEYID, surveyId).equalTo(AppConstants.CUSTOMERID, customerId).findFirst();

                if (realmAnswers != null) {
                    updateId = realmAnswers.get_id();
                    strStatus = realmAnswers.getRequester_status();
                    JSONArray array = new JSONArray(realmAnswers.getAnswers());
                    // JSONArray array1=new JSONArray(array.toString());
                    //   String json=array.get(0).toString();
                    //  JSONArray array1=new JSONArray(json);
                    if (array.length() > 0) {
                        for (int i = 0; i < array.length(); i++) {

                            JSONObject jsonObject = array.getJSONObject(i);
                            String categoryId = jsonObject.optString(AppConstants.CATEGORYID);
                            String isView = jsonObject.optString(AppConstants.ISVIEW);
                            String isViewByZM = jsonObject.optString(AppConstants.ISVIEWBYZM);
                            JSONArray questions = jsonObject.getJSONArray(AppConstants.QUESTIONS);
                            if (categoryId.equalsIgnoreCase(jsonArray.get(l).toString())) {
                                RealmCategory realmCategoryDetails = realm.where(RealmCategory.class).equalTo(AppConstants.ID, jsonArray.get(l).toString())/*.equalTo(AppConstants.SURVEYID,surveyId)*/.findFirst();
                                if (realmCategoryDetails != null) {


                                    CategoryModal categoryModal = new CategoryModal();
                                    categoryModal.setId(realmCategoryDetails.getId());
                                    categoryModal.setCategoryName(realmCategoryDetails.getCategoryName());
                                    categoryModal.setStatus(isView);
                                    categoryModal.setIsViewByZM(isViewByZM);
                                    if (isView.equalsIgnoreCase("1"))
                                        arrISView.add(isView);

                                    if (isViewByZM.equalsIgnoreCase("1"))
                                        arrISViewByZM.add(isViewByZM);
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
                                    showToastMessage(getResources().getString(R.string.no_data));
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
                        categoryModal.setIsViewByZM("");
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
                        showToastMessage(getResources().getString(R.string.no_data));
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
                expandableListDetail.put(arraylistTitle.get(m), productDetailsModelArrayList);
            }

            expandableListView.setVisibility(View.VISIBLE);
            expandableListAdapter = new CustomExpandableListAdapter(CategoryExpandableListActivity.this, arraylistTitle, expandableListDetail);
            expandableListView.setAdapter(expandableListAdapter);

            TextView textViewProgress = (TextView) findViewById(R.id.progressBarinsideText);
            mProgress.setProgress(progressRequired);
            mProgress.setMax(progressTotal);

            int percent = (progressRequired * 100) / progressTotal;
            textViewProgress.setText(percent + "%");

            if (title.contains(AppConstants.WORKFLOWS)) {
                btnApprove.setText(getString(R.string.approve));
                btnReject.setText(getString(R.string.reject));
                textViewProgress.setVisibility(View.VISIBLE);
                mProgress.setVisibility(View.VISIBLE);
                llStatus.setVisibility(View.GONE);
                String type = Prefs.getStringPrefs(AppConstants.TYPE);
                if (type.equalsIgnoreCase("rm")) {
                    if (arraylistTitle.size() != arrISView.size()) {
                        btnApprove.setBackgroundResource(R.color.grey);
                        btnApprove.setEnabled(false);
                        btnReject.setBackgroundResource(R.color.buttoncolor1);
                        btnReject.setEnabled(true);

                    } else {
                        btnApprove.setBackgroundResource(R.color.buttoncolor);
                        btnApprove.setEnabled(true);
                        btnReject.setBackgroundResource(R.color.buttoncolor1);
                        btnReject.setEnabled(true);
                    }
                } else {
                    if (arraylistTitle.size() != arrISViewByZM.size()) {
                        btnApprove.setBackgroundResource(R.color.grey);
                        btnApprove.setEnabled(false);
                        btnReject.setBackgroundResource(R.color.buttoncolor1);
                        btnReject.setEnabled(true);

                    } else {
                        btnApprove.setBackgroundResource(R.color.buttoncolor);
                        btnApprove.setEnabled(true);
                        btnReject.setBackgroundResource(R.color.buttoncolor1);
                        btnReject.setEnabled(true);
                    }
                }

                if (strStatus.equalsIgnoreCase("2") || strStatus.equalsIgnoreCase("3")) {
                    btnApprove.setVisibility(View.GONE);
                    btnReject.setVisibility(View.GONE);

                    if (strStatus.equalsIgnoreCase("2")) {
                    } else if (strStatus.equalsIgnoreCase("3")) {
                    }
                } else {
                    btnApprove.setVisibility(View.VISIBLE);
                    btnReject.setVisibility(View.VISIBLE);
                }
            } else {
                if (categoryAnswered.contains("No") || categoryAnswered.contains("")) {
                    btnApprove.setBackgroundResource(R.color.grey);
                    btnApprove.setEnabled(false);
                    btnReject.setBackgroundResource(R.color.grey);
                    btnReject.setEnabled(false);

                } else {
                    btnApprove.setBackgroundResource(R.color.buttoncolor);
                    btnApprove.setEnabled(true);
                    btnReject.setBackgroundResource(R.color.buttoncolor);
                    btnReject.setEnabled(true);
                }

                if (strStatus.equalsIgnoreCase("1") || strStatus.equalsIgnoreCase("2")) {
                    btnApprove.setVisibility(View.GONE);
                    btnReject.setVisibility(View.GONE);
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
                    btnApprove.setVisibility(View.VISIBLE);
                    btnReject.setVisibility(View.VISIBLE);
                    textViewProgress.setVisibility(View.VISIBLE);
                    mProgress.setVisibility(View.VISIBLE);
                    llStatus.setVisibility(View.GONE);
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
        String url = "";
        if (btnReject.getText().toString().equalsIgnoreCase("Reject")) {
            url = UniverseAPI.WEB_SERVICE_CREATE_APPROVE_METHOD;
            if (Utility.validateString(isUpdateId)) {
                url = UniverseAPI.WEB_SERVICE_CREATE_APPROVE_METHOD;
            }
        } else {
            if (Utility.validateString(isUpdateId)) {
                url = UniverseAPI.WEB_SERVICE_CREATE_UPDATE_METHOD;
            } else {
                url = UniverseAPI.WEB_SERVICE_CREATE_ANSWER_METHOD;
            }

        }
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
                            if (!Utility.validateString(updateId)) {
                                Realm realm = Realm.getDefaultInstance();
                                try {
                                    realm.beginTransaction();
                                    RealmResults<RealmAnswers> realmDeleteInputForms = realm.where(RealmAnswers.class).equalTo(AppConstants.ISSYNC, false).equalTo(AppConstants.CUSTOMERID, customerId).equalTo(AppConstants.SURVEYID, surveyId).findAll();
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
                Prefs.putStringPrefs(AppConstants.CUSTOMERIMAGE, response.getResponse().getImage());
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


    public void showReasonDialog() {
        final EditText taskEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.reason))
                .setView(taskEditText)
                .setPositiveButton(getString(R.string.submit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String reason = String.valueOf(taskEditText.getText());
                        jsonSubmitReq = prepareJsonRequest("Reject", reason);


                        if (Utility.isConnected()) {
                            submitAnswers(updateId, true);
                        } else {
                            saveNCDResponseLocal(updateId, false);
                        }
                    }
                })
                .setNegativeButton(getString(R.string.cancel), null)
                .create();
        dialog.show();


    }
}

