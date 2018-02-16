package com.universe.android.activity.admin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.common.collect.Collections2;
import com.google.gson.Gson;
import com.universe.android.R;
import com.universe.android.component.FilterPredicate;
import com.universe.android.component.MultiEdittextListItemDialog;
import com.universe.android.component.MultiSelectItemListDialog;
import com.universe.android.component.QuestionItemListDialog;
import com.universe.android.component.QuestionMapComparator;
import com.universe.android.component.SelectionItemListDialog;
import com.universe.android.enums.FormEnum;
import com.universe.android.enums.FormEnumKeys;
import com.universe.android.model.MultiSpinnerList;
import com.universe.android.model.Questions;
import com.universe.android.model.SpinnerList;
import com.universe.android.okkhttp.APIClient;
import com.universe.android.okkhttp.UniverseAPI;
import com.universe.android.realmbean.RealmCategory;
import com.universe.android.realmbean.RealmClient;
import com.universe.android.realmbean.RealmController;
import com.universe.android.realmbean.RealmCustomer;
import com.universe.android.realmbean.RealmQuestion;
import com.universe.android.realmbean.RealmQuestions;
import com.universe.android.realmbean.RealmSurveys;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.InputFilterMinMax;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class FormQuestionActivity extends FormParentActivity {

    private static final int SAVE_NEXT = 5002;
    private JSONObject jsonSubmitReq = new JSONObject();
    private Map<String, Questions> questionsMap;
    private String formId;
    private String title;
    public TextView spnClient;
    public TextView spnSurvey;
    public TextView spnCategory, spnCategorySingle, spnOptionValues;
    private LinearLayout llClient, llSurvey, llCategory, llCategorySingle, llOptionValues;
    private ArrayList<MultiSpinnerList> selectedCategory = new ArrayList<>();
    private ArrayList<String> selectedOptions = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_form_question);
        setHeader();
        Intent intent = getIntent();
        if (intent != null) {
            title = intent.getStringExtra(AppConstants.STR_TITLE);
            formId = intent.getStringExtra(AppConstants.FORM_ID);
            formAnsId = intent.getStringExtra(AppConstants.FORM_ANS_ID);
        }
        ((TextView) findViewById(R.id.textViewHeader)).setText(title);
        ((TextView) findViewById(R.id.tvName)).setText(title);
        ((TextView) findViewById(R.id.tvName)).setVisibility(View.GONE);

        llClient = (LinearLayout) findViewById(R.id.llClient);
        llSurvey = (LinearLayout) findViewById(R.id.llSurvey);
        llCategory = (LinearLayout) findViewById(R.id.llCategory);
        llOptionValues = (LinearLayout) findViewById(R.id.llOptionValues);
        llCategorySingle = (LinearLayout) findViewById(R.id.llCategorySingle);
        Utility.setTextView(getString(R.string.survey), (TextView) findViewById(R.id.tvSurveyTitle), false);
        Utility.setTextView(getString(R.string.client), (TextView) findViewById(R.id.tvClientTitle), false);
        Utility.setTextView(getString(R.string.category), (TextView) findViewById(R.id.tvCategoryTitle), false);
        Utility.setTextView(getString(R.string.option), (TextView) findViewById(R.id.tvOptionValues), false);
        spnClient = (TextView) findViewById(R.id.spnClient);
        spnSurvey = (TextView) findViewById(R.id.spnSurvey);
        spnCategory = (TextView) findViewById(R.id.spnCategory);
        spnOptionValues = (TextView) findViewById(R.id.spnOptionValues);
        spnCategorySingle = (TextView) findViewById(R.id.spnCategorySingle);

        if (formId.equalsIgnoreCase(FormEnum.customer.toString())) {
            llSurvey.setVisibility(View.GONE);
            llClient.setVisibility(View.VISIBLE);
            llCategory.setVisibility(View.GONE);
            llCategorySingle.setVisibility(View.GONE);
            prepareSurveyList();
            prepareClientList();
        } else if (formId.equalsIgnoreCase(FormEnum.client.toString())) {
            llSurvey.setVisibility(View.GONE);
            llClient.setVisibility(View.GONE);
            llCategory.setVisibility(View.GONE);
            llCategorySingle.setVisibility(View.GONE);
            prepareSurveyList();
        } else if (formId.equalsIgnoreCase(FormEnum.survey.toString())) {
            llSurvey.setVisibility(View.GONE);
            llClient.setVisibility(View.GONE);
            llCategory.setVisibility(View.VISIBLE);
            llCategorySingle.setVisibility(View.GONE);
            prepareCategoryList();
        } else if (formId.equalsIgnoreCase(FormEnum.question.toString())) {
            llSurvey.setVisibility(View.GONE);
            llClient.setVisibility(View.GONE);
            llCategory.setVisibility(View.GONE);
            llCategorySingle.setVisibility(View.VISIBLE);
            prepareCategorySingleList();
        } else {
            llSurvey.setVisibility(View.GONE);
            llCategory.setVisibility(View.GONE);
            llClient.setVisibility(View.GONE);
            llCategorySingle.setVisibility(View.GONE);
        }
        setScreenDetails();
        prepareQuestionList();
        prepareOptionValuesList();

    }


    private void prepareOptionValuesList() {
        spnOptionValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPopupVisible) return;
                isPopupVisible = true;
                List<MultiSpinnerList> spnPanchyatList = null;


                Questions q1 = questionsMap.get(FormEnumKeys.optionValuesCount.toString());
                if (q1 != null) {
                    if (Utility.validateString(q1.getAnswer())) {
                        int size = Integer.parseInt(q1.getAnswer());
                        spnPanchyatList = new ArrayList<>();
                        for (int i = 0; i < size; i++) {
                            MultiSpinnerList spn = new MultiSpinnerList();
                            spn.setName("option " + (i + 1));
                            spn.setId((i + 1) + "");
                            spnPanchyatList.add(spn);
                        }
                    }


                }


                List<MultiSpinnerList> selectdPanchyatItems = (List<MultiSpinnerList>) spnOptionValues.getTag();
                showMultiEdittextList(FormQuestionActivity.this, spnOptionValues, spnPanchyatList, getString(R.string.select_options), selectdPanchyatItems);
            }
        });
    }


    private void prepareCategoryList() {
        spnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPopupVisible) return;
                isPopupVisible = true;
                List<MultiSpinnerList> spnPanchyatList = null;
                final Realm realm = Realm.getDefaultInstance();
                try {

                    RealmResults<RealmCategory> realmPanchyat = realm.where(RealmCategory.class).findAll();

                    spnPanchyatList = new ArrayList<>();
                    for (int i = 0; i < realmPanchyat.size(); i++) {
                        MultiSpinnerList spn = new MultiSpinnerList();
                        spn.setName(realmPanchyat.get(i).getCategoryName());
                        spn.setId(realmPanchyat.get(i).getId());
                        spnPanchyatList.add(spn);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    realm.close();
                } finally {
                    realm.close();
                }
                List<MultiSpinnerList> selectdPanchyatItems = (List<MultiSpinnerList>) spnCategory.getTag();
                showMultiSelectionList1(FormQuestionActivity.this, spnCategory, spnPanchyatList, getString(R.string.select_category), selectdPanchyatItems);
            }
        });
    }


    private void showMultiSelectionList1(Context context, final TextView textView, List<MultiSpinnerList> list, final String defaultMsg, List<MultiSpinnerList> selectedItems) {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setChecked(false);
            }
            MultiSelectItemListDialog selectionPickerDialog = new MultiSelectItemListDialog(context, defaultMsg, selectedItems, list, R.layout.pop_up_question_list, new MultiSelectItemListDialog.ItemPickerListner() {
                @Override
                public void OnDoneButton(Dialog ansPopup, List<MultiSpinnerList> selectedItems) {
                    ansPopup.dismiss();
                    if (selectedItems != null && selectedItems.size() > 0) {
                        setSpnValue(textView, selectedItems);
                    } else {
                        textView.setText(defaultMsg);
                    }
                    textView.setTag(selectedItems);
                }

                @Override
                public void OnCancelButton(Dialog ansPopup, List<MultiSpinnerList> selectedItems) {
                    ansPopup.dismiss();
                    if (selectedItems != null && selectedItems.size() > 0) {
                        setSpnValue(textView, selectedItems);
                    } else {
                        textView.setText(defaultMsg);
                    }
                    textView.setTag(selectedItems);
                }

            });

            if (!selectionPickerDialog.isShowing()) {
                selectionPickerDialog.show();
            }
            selectionPickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    isPopupVisible = false;
                }
            });
        } else {
            isPopupVisible = false;
            showToastMessage(getString(R.string.no_data));
        }

    }

    private void showMultiEdittextList(Context context, final TextView textView, List<MultiSpinnerList> list, final String defaultMsg, List<MultiSpinnerList> selectedItems) {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setChecked(false);
            }
            MultiEdittextListItemDialog selectionPickerDialog = new MultiEdittextListItemDialog(context, defaultMsg, selectedItems, list, R.layout.pop_up_question_list, new MultiEdittextListItemDialog.ItemPickerListner() {
                @Override
                public void OnDoneButton(Dialog ansPopup, List<MultiSpinnerList> selectedItems) {
                    ansPopup.dismiss();
                    if (selectedItems != null && selectedItems.size() > 0) {
                        setSpnValue(textView, selectedItems);
                    } else {
                        textView.setText(defaultMsg);
                    }
                    textView.setTag(selectedItems);
                }

                @Override
                public void OnCancelButton(Dialog ansPopup, List<MultiSpinnerList> selectedItems) {
                    ansPopup.dismiss();
                    if (selectedItems != null && selectedItems.size() > 0) {
                        setSpnValue(textView, selectedItems);
                    } else {
                        textView.setText(defaultMsg);
                    }
                    textView.setTag(selectedItems);
                }

            });

            if (!selectionPickerDialog.isShowing()) {
                selectionPickerDialog.show();
            }
            selectionPickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    isPopupVisible = false;
                }
            });
        } else {
            isPopupVisible = false;
            showToastMessage(getString(R.string.no_data));
        }

    }

    private void setSpnValue(TextView textView, List<MultiSpinnerList> selectedItems) {
        if (selectedItems != null && selectedItems.size() > 0) {
            StringBuilder strBuilder = new StringBuilder();
            if (textView.getId() == R.id.spnOptionValues)
                selectedOptions = new ArrayList<>();
            for (MultiSpinnerList sp : selectedItems) {
                if (strBuilder.length() > 0) strBuilder.append(", ");
                strBuilder.append(sp.getName());
                if (textView.getId() == R.id.spnOptionValues) {

                    selectedOptions.add(sp.getName());
                }
            }
            textView.setText(strBuilder.toString());


        }

    }

    private void prepareSurveyList() {
        spnSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPopupVisible) return;
                isPopupVisible = true;
                List<SpinnerList> spnPanchyatList = null;
                final Realm realm = Realm.getDefaultInstance();
                try {


                    RealmResults<RealmSurveys> realmPanchyat = realm.where(RealmSurveys.class).findAllSorted(AppConstants.TITLE);

                    spnPanchyatList = new ArrayList<>();
                    SpinnerList spn = new SpinnerList();
                    spn.setName(getString(R.string.select_survey));
                    spn.setId(AppConstants.MINUS_ONE);
                    spnPanchyatList.add(spn);
                    for (int i = 0; i < realmPanchyat.size(); i++) {
                        spn = new SpinnerList();
                        spn.setName(realmPanchyat.get(i).getTitle());
                        spn.setId(realmPanchyat.get(i).getId());
                        spnPanchyatList.add(spn);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    realm.close();
                } finally {
                    realm.close();
                }
                showSelectionListPanchyat(FormQuestionActivity.this, spnSurvey, spnPanchyatList, getString(R.string.select_survey));
            }
        });
    }

    private void prepareCategorySingleList() {
        spnCategorySingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPopupVisible) return;
                isPopupVisible = true;
                List<SpinnerList> spnPanchyatList = null;
                final Realm realm = Realm.getDefaultInstance();
                try {


                    RealmResults<RealmCategory> realmPanchyat = realm.where(RealmCategory.class).findAll();

                    spnPanchyatList = new ArrayList<>();
                    SpinnerList spn = new SpinnerList();
                    spn.setName(getString(R.string.select_category));
                    spn.setId(AppConstants.MINUS_ONE);
                    spnPanchyatList.add(spn);
                    for (int i = 0; i < realmPanchyat.size(); i++) {
                        spn = new SpinnerList();
                        spn.setName(realmPanchyat.get(i).getCategoryName());
                        spn.setId(realmPanchyat.get(i).getId());
                        spnPanchyatList.add(spn);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    realm.close();
                } finally {
                    realm.close();
                }
                showSelectionListPanchyat(FormQuestionActivity.this, spnCategorySingle, spnPanchyatList, getString(R.string.select_category));
            }
        });
    }


    private void prepareClientList() {
        spnClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPopupVisible) return;
                isPopupVisible = true;
                List<SpinnerList> spnPanchyatList = null;
                final Realm realm = Realm.getDefaultInstance();
                try {


                    RealmResults<RealmClient> realmPanchyat = realm.where(RealmClient.class).findAllSorted(AppConstants.CREATEDAT);

                    spnPanchyatList = new ArrayList<>();
                    SpinnerList spn = new SpinnerList();
                    spn.setName(getString(R.string.select_client));
                    spn.setId(AppConstants.MINUS_ONE);
                    spnPanchyatList.add(spn);
                    for (int i = 0; i < realmPanchyat.size(); i++) {
                        spn = new SpinnerList();
                        spn.setName(realmPanchyat.get(i).getName());
                        spn.setId(realmPanchyat.get(i).getId());
                        spnPanchyatList.add(spn);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    realm.close();
                } finally {
                    realm.close();
                }
                showSelectionListPanchyat(FormQuestionActivity.this, spnClient, spnPanchyatList, getString(R.string.select_survey));
            }
        });
    }


    private void showSelectionListPanchyat(Context context, final TextView textView, List<SpinnerList> list, final String defaultMsg) {
        if (list != null && list.size() > 0) {
            SelectionItemListDialog selectionPickerDialog = new SelectionItemListDialog(context, defaultMsg, textView.getText().toString().trim(), list, R.layout.pop_up_question_list, new SelectionItemListDialog.ItemPickerListner() {
                @Override
                public void OnDoneButton(Dialog ansPopup, String strAns, SpinnerList spinnerItem) {
                    ansPopup.dismiss();
                    if (Utility.validateString(strAns)) {
                        textView.setText(strAns);
                        textView.setTag(spinnerItem);
                    } else {
                        textView.setText(defaultMsg);
                    }


                }
            });
            if (!selectionPickerDialog.isShowing()) {
                selectionPickerDialog.show();
            }
            selectionPickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    isPopupVisible = false;
                }
            });
        } else {
            isPopupVisible = false;
            showToastMessage(getString(R.string.no_data));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void prepareQuestionList() {
        List<String> noDisplayKeys = new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.question)));
        if (formId.equalsIgnoreCase(FormEnum.survey.toString())) {
            noDisplayKeys = new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.surveys)));
        } else if (formId.equalsIgnoreCase(FormEnum.client.toString())) {
            noDisplayKeys = new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.client)));
        } else if (formId.equalsIgnoreCase(FormEnum.customer.toString())) {
            noDisplayKeys = new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.customer)));
        } else if (formId.equalsIgnoreCase(FormEnum.category.toString())) {
            noDisplayKeys = new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.category)));
        }
        questionsMap = new LinkedHashMap<>();
        questionsMap = prepareFormQuestions(formId, noDisplayKeys);
    }

    public void saveContinue(View view) {
        Utility.hideSoftKeyboard(this);
        jsonSubmitReq = prepareJsonRequest(questionsMap);

        if (validateData(questionsMap)) {
            String updateId = "";
            if (view.getTag() != null) {
                if (view.getTag() instanceof String) {
                    updateId = (String) view.getTag();
                }
            }
            showReviewConfirmAlert(updateId, false);
        }
    }


    public void saveNext(View view) {

        Utility.hideSoftKeyboard(this);
        Utility.animateView(view);
        jsonSubmitReq = prepareJsonRequest(questionsMap);
        if (validateData(questionsMap)) {
            String updateId = "";
            if (view.getTag() != null) {
                if (view.getTag() instanceof String) {
                    updateId = (String) view.getTag();
                }
            }
            showReviewConfirmAlert(updateId, true);
        }
    }


    private void showReviewConfirmAlert(final String isUpdate, final boolean isBack) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(getString(R.string.review_confirm_msg));

        Button dialogButton = (Button) dialog.findViewById(R.id.btnYes);
        dialogButton.setText(getString(R.string.yes));
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utility.animateView(v);
                dialog.dismiss();
                jsonSubmitReq = Utility.formatDates(jsonSubmitReq);
                if (Utility.isConnected()) {
                    submitAnswers(isUpdate, isBack);
                } else {
                    saveNCDResponseLocal(isUpdate, isBack);
                }


            }
        });
        Button dialogNo = (Button) dialog.findViewById(R.id.btnNo);
        dialogNo.setVisibility(View.VISIBLE);
        dialogNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utility.animateView(v);
                dialog.dismiss();
            }
        });
        dialog.show();
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
        String url = UniverseAPI.WEB_SERVICE_CREATE_SURVEY_METHOD;
        if (Utility.validateString(isUpdateId)) {
            url = UniverseAPI.WEB_SERVICE_UPDATE_SURVEY_METHOD;
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

                            new RealmController().saveFormInputFromSubmit(jsonResponse.toString(), isUpdateId, formId);
                        }


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (progressDialog != null) progressDialog.dismiss();
                                showMessageDialog(FormQuestionActivity.this, isBack, isUpdateId);
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


    private void saveNCDResponseLocal(String isUpdate, boolean isBack) {
        saveResponseLocal(formId, jsonSubmitReq, isUpdate);
        try {
            if (jsonSubmitReq.has(AppConstants.ID)) {
                jsonSubmitReq.remove(AppConstants.ID);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        showMessageDialog(this, isBack, isUpdate);
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
                if (Utility.validateString(isUpdateId)) {
                    setResult(RESULT_OK);
                    finish();
                } else {
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
        dialog.show();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected Map<String, Questions> prepareFormQuestions(String stationFormId, List<String> noDisplayKeys) {
        Realm realm = Realm.getDefaultInstance();
        Map<String, Questions> questionsMap = new LinkedHashMap<>();
        try {
            formId = stationFormId;
            RealmResults<RealmQuestions> realmFormQuestions = null;
            JSONObject jsonAnswers = null;
            realmFormQuestions = realm.where(RealmQuestions.class).equalTo(AppConstants.FORM_ID, stationFormId).findAll();
            if (Utility.validateString(formAnsId)) {

                if (formId.equalsIgnoreCase(FormEnum.survey.toString())) {
                    RealmResults<RealmSurveys> realmSurveys = realm.where(RealmSurveys.class).equalTo(AppConstants.ID, formAnsId).findAll();

                    if (realmSurveys != null && realmSurveys.size() > 0) {

                        //      RealmQuestion realmQuestion = realmSurveys.get(0);
                        ((Button) findViewById(R.id.btnSave)).setText("Update & Close");
                        ((Button) findViewById(R.id.btnSave)).setTag(realmSurveys.get(0).getId());
                        ((Button) findViewById(R.id.btnSave)).setVisibility(View.VISIBLE);
                        //  isSync = realmQuestions.get(0).isSync();
                        jsonAnswers = new JSONObject(realmSurveys.get(0).getResponses());
                        JSONArray jsonArray1 = jsonAnswers.getJSONArray(AppConstants.CATEGORY);

                        if (Utility.validateString(jsonArray1.toString())) {
                            JSONArray jsonArray = new JSONArray(jsonArray1.toString());
                            if (jsonArray != null && jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonActivity = jsonArray.optJSONObject(i);
                                    MultiSpinnerList spinnerList = new MultiSpinnerList();
                                    spinnerList.setName(jsonActivity.optString(AppConstants.CATEGORY_NAME));
                                    spinnerList.setId(jsonActivity.optString(AppConstants.ID));
                                    selectedCategory.add(spinnerList);
                                }
                                setSpnValue(spnCategory, selectedCategory);
                            }
                        }

                    }
                } else if (formId.equalsIgnoreCase(FormEnum.client.toString())) {
                    RealmResults<RealmClient> realmClients = realm.where(RealmClient.class).equalTo(AppConstants.ID, formAnsId).findAll();

                    if (realmClients != null && realmClients.size() > 0) {

                        //  RealmQuestion realmQuestion = realmQuestions.get(0);
                        ((Button) findViewById(R.id.btnSave)).setText("Update & Close");
                        ((Button) findViewById(R.id.btnSave)).setTag(realmClients.get(0).getId());
                        //  isSync = realmQuestions.get(0).isSync();
                        jsonAnswers = new JSONObject(realmClients.get(0).getResponses());
                        String surveyId = realmClients.get(0).getSurveyId();
                        if (Utility.validateString(surveyId) && spnSurvey != null) {
                            List<SpinnerList> spnList = new ArrayList<>();
                            SpinnerList spn = new SpinnerList();
                            spn.setChecked(true);
                            spn.setName(realmClients.get(0).getSurveyName());
                            spn.setId(surveyId);
                            spnList.add(spn);
                            spnSurvey.setTag(spn);
                            spnSurvey.setText(spn.getName());
                        }
                    }
                } else if (formId.equalsIgnoreCase(FormEnum.customer.toString())) {
                    RealmResults<RealmCustomer> realmCustomers = realm.where(RealmCustomer.class).equalTo(AppConstants.ID, formAnsId).findAll();

                    if (realmCustomers != null && realmCustomers.size() > 0) {

                        //  RealmQuestion realmQuestion = realmQuestions.get(0);
                        ((Button) findViewById(R.id.btnSave)).setText("Update & Close");
                        ((Button) findViewById(R.id.btnSave)).setTag(realmCustomers.get(0).getId());
                        //  isSync = realmQuestions.get(0).isSync();
                        jsonAnswers = new JSONObject(realmCustomers.get(0).getResponses());
                     /*   String surveyId=realmCustomers.get(0).getSurveyId();
                        if (Utility.validateString(surveyId) && spnSurvey != null) {
                            List<SpinnerList> spnList = new ArrayList<>();
                            SpinnerList spn = new SpinnerList();
                            spn.setChecked(true);
                            spn.setName(realmCustomers.get(0).getSurveyName());
                            spn.setId(surveyId);
                            spnList.add(spn);
                            spnSurvey.setTag(spn);
                            spnSurvey.setText(spn.getName());
                        }*/

                        String clientId = realmCustomers.get(0).getClientId();
                        if (Utility.validateString(clientId) && spnClient != null) {
                            List<SpinnerList> spnList = new ArrayList<>();
                            SpinnerList spn = new SpinnerList();
                            spn.setChecked(true);
                            spn.setName(realmCustomers.get(0).getClientName());
                            spn.setId(clientId);
                            spnList.add(spn);
                            spnClient.setTag(spn);
                            spnClient.setText(spn.getName());
                        }
                    }
                } else if (formId.equalsIgnoreCase(FormEnum.category.toString())) {
                    RealmResults<RealmCategory> realmCategories = realm.where(RealmCategory.class).equalTo(AppConstants.ID, formAnsId).findAll();

                    if (realmCategories != null && realmCategories.size() > 0) {

                        //  RealmQuestion realmQuestion = realmQuestions.get(0);
                        ((Button) findViewById(R.id.btnSave)).setText("Update & Close");
                        ((Button) findViewById(R.id.btnSave)).setTag(realmCategories.get(0).getId());
                        //  isSync = realmQuestions.get(0).isSync();
                        jsonAnswers = new JSONObject(realmCategories.get(0).getResponses());

                    }
                } else if (formId.equalsIgnoreCase(FormEnum.question.toString())) {
                    RealmResults<RealmQuestion> realmQuestions = realm.where(RealmQuestion.class).equalTo(AppConstants.ID, formAnsId).findAll();

                    if (realmQuestions != null && realmQuestions.size() > 0) {

                        //  RealmQuestion realmQuestion = realmQuestions.get(0);
                        ((Button) findViewById(R.id.btnSave)).setText("Update & Close");
                        ((Button) findViewById(R.id.btnSave)).setTag(realmQuestions.get(0).getId());
                        //  isSync = realmQuestions.get(0).isSync();
                        jsonAnswers = new JSONObject(realmQuestions.get(0).getResponses());
                        String categoryId = realmQuestions.get(0).getCategoryId();
                        if (Utility.validateString(categoryId) && spnCategorySingle != null) {
                            List<SpinnerList> spnList = new ArrayList<>();
                            SpinnerList spn = new SpinnerList();
                            spn.setChecked(true);
                            spn.setName(realmQuestions.get(0).getCategoryName());
                            spn.setId(categoryId);
                            spnList.add(spn);
                            spnCategorySingle.setTag(spn);
                            spnCategorySingle.setText(spn.getName());
                        }
                    }
                }


                ((Button) findViewById(R.id.btnSaveNext)).setText(getString(R.string.update_exit));
            }

            String formSchema = realmFormQuestions.get(0).getFormSchema();
            if (Utility.validateString(formSchema)) {
                JSONObject jsonFormSchema = new JSONObject(formSchema);
                JSONObject jsonQuestion = jsonFormSchema.optJSONObject(AppConstants.PROPERTIES);
                if (jsonQuestion != null && jsonQuestion.length() > 0) {
                    if (jsonQuestion != null) {
                        Iterator<?> keys = jsonQuestion.keys();
                        while (keys.hasNext()) {
                            String key = (String) keys.next();
                            if (noDisplayKeys.contains(key)) continue;
                            if (jsonQuestion.get(key) instanceof JSONObject) {
                                JSONObject jsonObject = (JSONObject) jsonQuestion.get(key);
                                Questions question = new RealmController().getQuestionVOFromJson(jsonObject);
                                question.setQuestionId(key);
                                if (jsonAnswers != null && jsonAnswers.optString(key) != null) {
                                    question.setAnswer(jsonAnswers.optString(key));
                                }
                                questionsMap.put(key, question);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            realm.close();
            e.printStackTrace();
        } finally {
            realm.close();
        }
        addQuestionsForm(questionsMap);
        return questionsMap;
    }

    protected JSONObject prepareJsonRequest(Map<String, Questions> questionsMap) {
        JSONObject jsonSubmitReq = new JSONObject();
        JSONObject jsonAnswers = new JSONObject();
        JSONArray jsonCategory = new JSONArray();
        try {
            llFields = (LinearLayout) findViewById(R.id.parent);
            if (llFields != null && llFields.getChildCount() > 0) {
                if (questionsMap != null && questionsMap.size() > 0) {
                    questionsMap = QuestionMapComparator.sortByValue(questionsMap);
                    for (Map.Entry<String, Questions> entry : questionsMap.entrySet()) {
                        Questions question = (Questions) entry.getValue();
                        if (question != null && (question.getInputType().equals(AppConstants.TEXTBOX) || (question.getInputType().equals(AppConstants.TEXTAREA)))) {
                            View childView = llFields.findViewWithTag(question);
                            if (childView != null && childView.getVisibility() == View.VISIBLE) {
                                EditText edtChild = (EditText) childView.findViewById(R.id.edtChild);
                                if (edtChild != null) {
                                    question.setAnswer(edtChild.getText().toString().trim());
                                    if (AppConstants.NUMBER.equals(question.getType())) {
                                        if (Utility.validateString(question.getAnswer())) {
                                            if (question.getAnswer().length() == 10)
                                                jsonSubmitReq.put(question.getQuestionId(), Long.parseLong(question.getAnswer()));

                                            else {
                                                jsonSubmitReq.put(question.getQuestionId(), Integer.parseInt(question.getAnswer()));
                                            }
                                        } else {
                                            jsonSubmitReq.put(question.getQuestionId(), 0);
                                        }
                                    } else if (AppConstants.FLOAT.equals(question.getType())) {
                                        if (Utility.validateString(question.getAnswer())) {
                                            double val = Double.valueOf(question.getAnswer());
                                            double ans = Utility.round(val, 2);
                                            jsonSubmitReq.put(question.getQuestionId(), ans);
                                        } else {
                                            jsonSubmitReq.put(question.getQuestionId(), 0);
                                        }
                                    } else if (AppConstants.STRING.equals(question.getType())) {
                                        jsonSubmitReq.put(question.getQuestionId(), question.getAnswer());
                                    }
                                }
                            }
                        } else if (question != null && (question.getInputType().equals(AppConstants.RADIO))) {
                            View childView = llFields.findViewWithTag(question);
                            if (childView != null && childView.getVisibility() == View.VISIBLE) {
                                RadioGroup radChild = (RadioGroup) childView.findViewById(R.id.radChild);
                                if (radChild != null && radChild.getCheckedRadioButtonId() != -1) {
                                    RadioButton radioButton = (RadioButton) findViewById(radChild.getCheckedRadioButtonId());
                                    String strVal = ((RadioButton) radChild.findViewById(radChild.getCheckedRadioButtonId())).getText().toString().trim();
                                    question.setAnswer(strVal);
                                    if (question.getQuestionId().equalsIgnoreCase(AppConstants.ISACTIVE)) {
                                        if (question.getAnswer().equalsIgnoreCase(AppConstants.YES)) {
                                            jsonSubmitReq.put(question.getQuestionId(), 1);
                                        } else {
                                            jsonSubmitReq.put(question.getQuestionId(), 0);
                                        }
                                    } else {
                                        jsonSubmitReq.put(question.getQuestionId(), question.getAnswer());
                                    }

                                }
                            }
                        } else if (question != null && (question.getInputType().equals(AppConstants.DATE))) {
                            View childView = llFields.findViewWithTag(question);
                            if (childView != null && childView.getVisibility() == View.VISIBLE) {
                                TextView textView = (TextView) childView.findViewById(R.id.tvFormDate);
                                if (textView != null) {
                                    question.setAnswer(textView.getText().toString().trim());
                                    jsonSubmitReq.put(question.getQuestionId(), question.getAnswer());
                                }
                            }
                        } else if (question != null && (question.getInputType().equals(AppConstants.SELECT))) {
                            View childView = llFields.findViewWithTag(question);
                            if (childView != null && childView.getVisibility() == View.VISIBLE) {
                                TextView textView = (TextView) childView.findViewById(R.id.spnSelect);
                                if (textView != null && textView.getTag() != null && Utility.validateString(textView.getText().toString().trim())) {
                                    List<SpinnerList> spinnerList = (List<SpinnerList>) textView.getTag();
                                    Collection<SpinnerList> result = Collections2.filter(spinnerList, new FilterPredicate().predicateSpnList);
                                    if (result != null && result.size() > 0) {
                                        for (SpinnerList s : result) {
                                            question.setAnswer(s.getId());
                                            jsonSubmitReq.put(question.getQuestionId(), s.getId());
                                        }
                                    }
                                }
                            }
                        } else if (question != null && (question.getInputType().equals(AppConstants.CHECKBOX))) {
                            View childView = llFields.findViewWithTag(question);
                            if (childView != null && childView.getVisibility() == View.VISIBLE) {
                                LinearLayout llCheckChild = (LinearLayout) childView.findViewById(R.id.llCheckChild);
                                if (llCheckChild != null) {
                                    JSONArray jsonCheckArray = new JSONArray();
                                    for (int j = 0; j < llCheckChild.getChildCount(); j++) {
                                        View child = llCheckChild.getChildAt(j);
                                        if (llCheckChild.getChildAt(j) instanceof CheckBox) {
                                            CheckBox cb = (CheckBox) child;
                                            if (cb.isChecked()) {
                                                jsonCheckArray.put(cb.getText().toString().trim());
                                            }
                                        }
                                    }
                                    if (jsonCheckArray != null && jsonCheckArray.length() > 0) {
                                        question.setAnswer(jsonCheckArray.toString());
                                    }
                                    jsonSubmitReq.put(question.getQuestionId(), jsonCheckArray);
                                }
                            }
                        }
                    }
                }
            }
            if (jsonSubmitReq != null) {

               /* if (formId.equalsIgnoreCase(FormEnum.client.toString())) {
                  *//*  if (spnSurvey != null && spnSurvey.getTag() != null) {
                        SpinnerList spinnerList = (SpinnerList) spnSurvey.getTag();
                        if (!AppConstants.MINUS_ONE.equals(spinnerList.getId())) {

                            jsonSubmitReq.put(AppConstants.SURVEYID, spinnerList.getId());
                        }
                    }*//*
                }*/
                if (formId.equalsIgnoreCase(FormEnum.survey.toString())) {
                    if (spnCategory != null && spnCategory.getTag() != null) {
                        List<MultiSpinnerList> spinnerList = (List<MultiSpinnerList>) spnCategory.getTag();
                        ArrayList<String> cateId = new ArrayList<>();
                        if (spinnerList != null && spinnerList.size() > 0) {
                            for (int i = 0; i < spinnerList.size(); i++) {
                                JSONObject jsonAct = new JSONObject();
                                jsonAct.put(AppConstants.CATEGORY_NAME, spinnerList.get(i).getName());
                                jsonAct.put(AppConstants.ID, spinnerList.get(i).getId());
                                jsonCategory.put(jsonAct);
                                cateId.add(spinnerList.get(i).getId());
                            }
                            String outStr = "";

                            for (int i = 0; i < cateId.size(); i++) {
                                outStr += "\"" + cateId.get(i) + "\"";

                                if (i < (cateId.size() - 1)) {
                                    outStr += ", ";
                                }
                            }
                            jsonSubmitReq.put(AppConstants.CATEGORY, jsonCategory);
                            jsonSubmitReq.put(AppConstants.CATEGORYID, "[" + outStr + "]");

                        }

                    }
                }
                if (formId.equalsIgnoreCase(FormEnum.question.toString())) {
                    if (spnCategorySingle != null && spnCategorySingle.getTag() != null) {
                        SpinnerList spinnerList = (SpinnerList) spnCategorySingle.getTag();

                        if (!AppConstants.MINUS_ONE.equals(spinnerList.getId())) {

                            jsonSubmitReq.put(AppConstants.CATEGORYID, spinnerList.getId());
                        }


                    }
                    String outStr = "";

                    for (int i = 0; i < selectedOptions.size(); i++) {
                        outStr += "\"" + selectedOptions.get(i) + "\"";

                        if (i < (selectedOptions.size() - 1)) {
                            outStr += ", ";
                        }
                    }
                    if (selectedOptions.size() > 0) {
                        jsonSubmitReq.put(FormEnumKeys.optionValuesCount.toString(), selectedOptions.size());
                        jsonSubmitReq.put(FormEnumKeys.optionValues.toString(), "[" + outStr + "]");
                    }
                }
                if (formId.equalsIgnoreCase(FormEnum.customer.toString())) {
                    if (spnSurvey != null && spnSurvey.getTag() != null) {
                        SpinnerList spinnerList = (SpinnerList) spnSurvey.getTag();
                        if (!AppConstants.MINUS_ONE.equals(spinnerList.getId())) {

                            jsonSubmitReq.put(AppConstants.SURVEYID, spinnerList.getId());
                        }
                    }
                    if (spnClient != null && spnClient.getTag() != null) {
                        SpinnerList spinnerList = (SpinnerList) spnClient.getTag();
                        if (!AppConstants.MINUS_ONE.equals(spinnerList.getId())) {

                            jsonSubmitReq.put(AppConstants.CLIENTID, spinnerList.getId());
                        }
                    }
                }

                jsonSubmitReq.put(AppConstants.RESPONSES, jsonSubmitReq.toString());
                if (jsonSubmitReq.has(AppConstants.CATEGORY)) {
                    jsonSubmitReq.remove(AppConstants.CATEGORY);
                }
                jsonSubmitReq.put(AppConstants.FORM_ID, formId);

                jsonSubmitReq.put(AppConstants.CREATED_BY, Prefs.getStringPrefs(AppConstants.UserId));
                if (Utility.validateString(formAnsId)) {
                    //  jsonSubmitReq.put(AppConstants.UPDATEDAT, Utility.get);
                } else {
                    //   jsonSubmitReq.put(AppConstants.CREATEDAT, userId);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        Log.d("json", jsonSubmitReq.toString());
        return jsonSubmitReq;
    }


    protected void addQuestionsForm(Map<String, Questions> questionsMap) {
        try {
            llFields.removeAllViews();
            View child;
            if (questionsMap != null && questionsMap.size() > 0) {
                questionsMap = QuestionMapComparator.sortByValue(questionsMap);
                for (Map.Entry<String, Questions> entry : questionsMap.entrySet()) {
                    child = null;
                    final Questions question = entry.getValue();
                    if (AppConstants.SECTION.equals(question.getInputType())) {
                        child = getLayoutInflater().inflate(R.layout.field_row_section, null);
                    } else if (AppConstants.TEXTBOX.equals(question.getInputType())) {
                        child = getLayoutInflater().inflate(R.layout.field_row_edit, null);
                        final EditText edtChild = (EditText) child.findViewById(R.id.edtChild);
                        final Map<String, Questions> finalQuestionsMap = questionsMap;
                        edtChild.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                Questions q1 = finalQuestionsMap.get(FormEnumKeys.optionValuesCount.toString());
                                if (q1 != null) {
                                    q1.setAnswer(s.toString());

                                }
                            }
                        });
                        edtChild.setText(question.getAnswer());
                        if (AppConstants.STRING.equals(question.getType())) {
                            edtChild.setInputType(InputType.TYPE_CLASS_TEXT);
                            Utility.setEditFilter(edtChild, question.getMaxLength(), AppConstants.STRING, false, question.isAlpha());
                        } else if (AppConstants.LONG.equals(question.getType())) {
                            edtChild.setInputType(InputType.TYPE_CLASS_NUMBER);
                            if ("0".equals(question.getAnswer())) {
                                edtChild.setText("");
                            }
                            if (question.getMaxValue() != 0) {
                                edtChild.setFilters(new InputFilter[]{new InputFilterMinMax(0, question.getMaxValue())});
                            }
                            if (question.getMaxLength() != 0) {
                                edtChild.setFilters(new InputFilter[]{new InputFilter.LengthFilter(question.getMaxLength())});
                            }
                        } else if (AppConstants.NUMBER.equals(question.getType())) {
                            edtChild.setInputType(InputType.TYPE_CLASS_NUMBER);
                            if ("0".equals(question.getAnswer())) {
                                edtChild.setText("");
                            }
                            if (question.getMaxValue() != 0) {
                                edtChild.setFilters(new InputFilter[]{new InputFilterMinMax(0, question.getMaxValue())});
                            }
                            if (question.getMaxLength() != 0) {
                                edtChild.setFilters(new InputFilter[]{new InputFilter.LengthFilter(question.getMaxLength())});
                            }


                        } else if (AppConstants.FLOAT.equals(question.getType())) {
                            edtChild.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                            if ("0".equals(question.getAnswer())) {
                                edtChild.setText("");
                            }
                            if (question.getMaxValue() != 0) {
                                edtChild.setFilters(new InputFilter[]{new InputFilterMinMax(0, question.getMaxValue())});
                            }
                            if (question.getMaxLength() != 0) {
                                edtChild.setFilters(new InputFilter[]{new InputFilter.LengthFilter(question.getMaxLength())});
                            }
                        }

                    } else if (AppConstants.TEXTAREA.equals(question.getInputType())) {
                        child = getLayoutInflater().inflate(R.layout.field_row_textarea, null);
                        EditText edtChild = (EditText) child.findViewById(R.id.edtChild);
                        edtChild.setText(question.getAnswer());
                        Utility.setEditFilter(edtChild, question.getMaxLength(), AppConstants.STRING, false, question.isAlpha());
                    } else if (AppConstants.RADIO.equals(question.getInputType())) {
                        child = getLayoutInflater().inflate(R.layout.field_radio_logical, null);
                        RadioGroup radioGroup = (RadioGroup) child.findViewById(R.id.radChild);
                        JSONArray jsonArray = new JSONArray(question.getOptionValues());
                        if (jsonArray != null && jsonArray.length() > 0) {
                            if (jsonArray.length() > 2) {
                                radioGroup.setOrientation(LinearLayout.VERTICAL);
                            }
                            for (int k = 0; k < jsonArray.length(); k++) {
                                addRadioButton((String) jsonArray.get(k), radioGroup, question, k);
                            }
                        }
                    } else if (AppConstants.CHECKBOX.equals(question.getInputType())) {
                        child = getLayoutInflater().inflate(R.layout.field_checkbox, null);
                        LinearLayout radioGroup = (LinearLayout) child.findViewById(R.id.llCheckChild);
                        JSONArray jsonArray = new JSONArray(question.getOptionValues());
                        if (jsonArray != null && jsonArray.length() > 0) {
                            if (jsonArray.length() > 2) {
                                radioGroup.setOrientation(LinearLayout.VERTICAL);
                            }
                            for (int k = 0; k < jsonArray.length(); k++) {
                                addCheckBoxButton((String) jsonArray.get(k), radioGroup, question, k);
                            }
                        }
                    } else if (AppConstants.SELECT.equals(question.getInputType())) {
                        child = getLayoutInflater().inflate(R.layout.field_row_select, null);
                        final TextView tvSelect = (TextView) child.findViewById(R.id.spnSelect);

                        addSelectTextView(tvSelect, question);


                    } else if (AppConstants.DATE.equals(question.getInputType())) {
                        child = getLayoutInflater().inflate(R.layout.field_row_date, null);
                        final TextView tvFormDate = (TextView) child.findViewById(R.id.tvFormDate);
                        tvFormDate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                showDatePicker(tvFormDate.getText().toString(), tvFormDate);
                            }
                        });
                        setTodaysDate(tvFormDate);

                    }
                    if (llFields != null && child != null) {
                        if (child.findViewById(R.id.tvAsteric) != null) {
                            child.findViewById(R.id.tvAsteric).setVisibility(View.GONE);
                        }
                        TextView textView = (TextView) child.findViewById(R.id.tvChild);
                        if (textView != null) {
                            String questionTitle;
                            questionTitle = question.getTitle();
                            if (Utility.validateString(questionTitle)) {
                                textView.setText(questionTitle);
                                if (question.isRequired()) {
                                    Utility.setTextView(questionTitle, textView, false);
                                }
                            } else {
                                textView.setVisibility(View.GONE);
                            }
                        }
                        TextView textViewDesc = (TextView) child.findViewById(R.id.tvChildDesc);
                        if (textViewDesc != null) {
                            if (Utility.validateString(question.getDescription())) {
                                textViewDesc.setVisibility(View.VISIBLE);
                                textViewDesc.setText(question.getDescription());
                            } else {
                                textViewDesc.setVisibility(View.GONE);
                            }
                        }
                        if (question.isVisibility()) {
                            child.setVisibility(View.VISIBLE);
                        } else {
                            child.setVisibility(View.GONE);
                            question.setRequired(false);
                            if (Utility.validateString(question.getAnswer())) {
                                child.setVisibility(View.VISIBLE);
                            }
                        }
                        child.setTag(question);
                        llFields.addView(child);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSelectTextView(final TextView tvSelect, final Questions question) {
        try {
            tvSelect.setTag(question);
            if (Utility.validateString(question.getAnswer())) {
                tvSelect.setText(question.getAnswer());
            } else {
                tvSelect.setText(question.getPlaceholder());
            }
            String apiModel = question.getApiModel();
            if (!Utility.validateString(apiModel)) {
                String options = question.getOptionValues();
                if (Utility.validateString(options)) {
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(options);
                        if (jsonArray != null) {
                            List<SpinnerList> spinnerList = new ArrayList<SpinnerList>();
                            for (int m = 0; m < jsonArray.length(); m++) {
                                SpinnerList spinner = new SpinnerList();
                                spinner.setId((String) jsonArray.get(m));
                                if (Utility.validateString(question.getAnswer())) {
                                    if (question.getAnswer().equals((String) jsonArray.get(m))) {
                                        spinner.setChecked(true);
                                    }
                                }
                                spinner.setName((String) jsonArray.get(m));
                                spinnerList.add(spinner);
                            }
                            tvSelect.setTag(spinnerList);
                        }
                    } catch (Exception e) {

                    }
                }
            } else {

                if (question.getQuestionId().equalsIgnoreCase(FormEnum.survey.toString())) {

                } else if (question.getQuestionId().equalsIgnoreCase(FormEnum.customer.toString())) {

                } else if (question.getQuestionId().equalsIgnoreCase(FormEnum.category.toString())) {

                } else if (question.getQuestionId().equalsIgnoreCase(FormEnum.client.toString())) {

                }

            }

            tvSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view.getTag() != null) {
                        List<SpinnerList> spnList = (List<SpinnerList>) view.getTag();
                        if (spnList != null && spnList.size() > 0) {
                            showSelectionList(FormQuestionActivity.this, tvSelect, spnList, question.getPlaceholder(), question);


                        }
                    } else {
                        isPopupVisible = false;
                    }
                }
            });

        } catch (Exception e) {

        }

    }

    protected void showSelectionList(Context context, final TextView textView, final List<SpinnerList> list, final String defaultMsg, final Questions question1) {

        if (list != null && list.size() > 0) {
            QuestionItemListDialog selectionPickerDialog = new QuestionItemListDialog(context, defaultMsg, textView.getText().toString().trim(), list, R.layout.pop_up_question_list, new QuestionItemListDialog.ItemPickerListner() {
                @Override
                public void OnDoneButton(Dialog ansPopup, String strAns, List<SpinnerList> spinnerItem) {
                    ansPopup.dismiss();
                    if (Utility.validateString(strAns)) {
                        textView.setText(strAns);
                        textView.setTag(spinnerItem);
                    } else {
                        textView.setText(defaultMsg);
                    }


                    Questions question = (Questions) question1;
                    if (question != null && question.getQuestionId().equals(FormEnumKeys.inputType.toString())) {
                        if (Utility.validateString(strAns)) {
                            if (strAns.equalsIgnoreCase("textbox")) {
                                if (questionsMap != null && questionsMap.size() > 0) {
                                    Questions q = questionsMap.get(FormEnumKeys.maxLength.toString());
                                    if (q != null) {
                                        View childView = llFields.findViewWithTag(q);
                                        if (childView != null) {
                                            if (childView.findViewById(R.id.tvChild) != null) {
                                                ((TextView) childView.findViewById(R.id.tvChild)).setText(Html.fromHtml(q.getTitle() + " " + AppConstants.ASTERIK_SIGN));
                                            }
                                            q.setRequired(true);
                                            childView.setVisibility(View.VISIBLE);
                                        }
                                    }

                                    Questions q1 = questionsMap.get(FormEnumKeys.minValue.toString());
                                    if (q1 != null) {
                                        View childView = llFields.findViewWithTag(q1);
                                        if (childView != null) {
                                            if (childView.findViewById(R.id.tvChild) != null) {
                                                ((TextView) childView.findViewById(R.id.tvChild)).setText(Html.fromHtml(q1.getTitle() + " " + AppConstants.ASTERIK_SIGN));
                                            }
                                            q1.setRequired(true);
                                            childView.setVisibility(View.VISIBLE);
                                        }
                                    }
                                    Questions q2 = questionsMap.get(FormEnumKeys.maxValue.toString());
                                    if (q2 != null) {
                                        View childView = llFields.findViewWithTag(q2);
                                        if (childView != null) {
                                            if (childView.findViewById(R.id.tvChild) != null) {
                                                ((TextView) childView.findViewById(R.id.tvChild)).setText(Html.fromHtml(q2.getTitle() + " " + AppConstants.ASTERIK_SIGN));
                                            }
                                            q2.setRequired(true);
                                            childView.setVisibility(View.VISIBLE);
                                        }
                                    }

                                    Questions q4 = questionsMap.get(FormEnumKeys.type.toString());
                                    if (q4 != null) {
                                        View childView = llFields.findViewWithTag(q4);
                                        if (childView != null) {
                                            if (childView.findViewById(R.id.tvChild) != null) {
                                                ((TextView) childView.findViewById(R.id.tvChild)).setText(Html.fromHtml(q4.getTitle() + " " + AppConstants.ASTERIK_SIGN));
                                            }
                                            q4.setRequired(true);
                                            childView.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }
                            } else {
                                if (questionsMap != null && questionsMap.size() > 0) {
                                    Questions q = questionsMap.get(FormEnumKeys.maxLength.toString());
                                    if (q != null) {
                                        View childView = llFields.findViewWithTag(q);
                                        if (childView != null) {
                                            if (childView.findViewById(R.id.tvChild) != null) {
                                                ((TextView) childView.findViewById(R.id.tvChild)).setText(Html.fromHtml(q.getTitle() + " " + AppConstants.ASTERIK_SIGN));
                                            }
                                            q.setRequired(false);
                                            childView.setVisibility(View.GONE);
                                        }
                                    }

                                    Questions q1 = questionsMap.get(FormEnumKeys.minValue.toString());
                                    if (q1 != null) {
                                        View childView = llFields.findViewWithTag(q1);
                                        if (childView != null) {
                                            if (childView.findViewById(R.id.tvChild) != null) {
                                                ((TextView) childView.findViewById(R.id.tvChild)).setText(Html.fromHtml(q1.getTitle() + " " + AppConstants.ASTERIK_SIGN));
                                            }
                                            q1.setRequired(false);
                                            childView.setVisibility(View.GONE);
                                        }
                                    }
                                    Questions q2 = questionsMap.get(FormEnumKeys.maxValue.toString());
                                    if (q2 != null) {
                                        View childView = llFields.findViewWithTag(q2);
                                        if (childView != null) {
                                            if (childView.findViewById(R.id.tvChild) != null) {
                                                ((TextView) childView.findViewById(R.id.tvChild)).setText(Html.fromHtml(q2.getTitle() + " " + AppConstants.ASTERIK_SIGN));
                                            }
                                            q2.setRequired(false);
                                            childView.setVisibility(View.GONE);
                                        }
                                    }
                                    Questions q4 = questionsMap.get(FormEnumKeys.type.toString());
                                    if (q4 != null) {
                                        View childView = llFields.findViewWithTag(q4);
                                        if (childView != null) {
                                            if (childView.findViewById(R.id.tvChild) != null) {
                                                ((TextView) childView.findViewById(R.id.tvChild)).setText(Html.fromHtml(q4.getTitle() + " " + AppConstants.ASTERIK_SIGN));
                                            }
                                            q4.setRequired(false);
                                            childView.setVisibility(View.GONE);
                                        }
                                    }
                                }
                            }
                            if (strAns.equalsIgnoreCase("radio") || strAns.equalsIgnoreCase("checkbox")) {
                                if (questionsMap != null && questionsMap.size() > 0) {
                                    Questions q = questionsMap.get(FormEnumKeys.orientation.toString());
                                    if (q != null) {
                                        View childView = llFields.findViewWithTag(q);
                                        if (childView != null) {
                                            if (childView.findViewById(R.id.tvChild) != null) {
                                                ((TextView) childView.findViewById(R.id.tvChild)).setText(Html.fromHtml(q.getTitle() + " " + AppConstants.ASTERIK_SIGN));
                                            }
                                            q.setRequired(true);
                                            childView.setVisibility(View.VISIBLE);
                                        }
                                    }


                                }
                            } else {
                                if (questionsMap != null && questionsMap.size() > 0) {
                                    Questions q = questionsMap.get(FormEnumKeys.orientation.toString());
                                    if (q != null) {
                                        View childView = llFields.findViewWithTag(q);
                                        if (childView != null) {
                                            if (childView.findViewById(R.id.tvChild) != null) {
                                                ((TextView) childView.findViewById(R.id.tvChild)).setText(Html.fromHtml(q.getTitle() + " " + AppConstants.ASTERIK_SIGN));
                                            }
                                            q.setRequired(false);
                                            childView.setVisibility(View.GONE);
                                        }
                                    }


                                }
                            }

                            if (strAns.equalsIgnoreCase("radio") || strAns.equalsIgnoreCase("checkbox") || strAns.equalsIgnoreCase("rating") || strAns.equalsIgnoreCase("multiedittext") || strAns.equalsIgnoreCase("multiselect") || strAns.equalsIgnoreCase("select")) {
                                if (questionsMap != null && questionsMap.size() > 0) {
                                    llOptionValues.setVisibility(View.VISIBLE);
                                    Questions q2 = questionsMap.get(FormEnumKeys.optionValuesCount.toString());
                                    if (q2 != null) {
                                        View childView = llFields.findViewWithTag(q2);
                                        if (childView != null) {
                                            if (childView.findViewById(R.id.tvChild) != null) {
                                                ((TextView) childView.findViewById(R.id.tvChild)).setText(Html.fromHtml(q2.getTitle() + " " + AppConstants.ASTERIK_SIGN));
                                            }
                                            q2.setRequired(true);
                                            childView.setVisibility(View.VISIBLE);
                                        }
                                    }

                                   /* Questions q1= questionsMap.get(FormEnumKeys.optionValues.toString());
                                    if (q1 != null) {
                                        View childView = llFields.findViewWithTag(q1);
                                        if (childView != null) {
                                            if (childView.findViewById(R.id.tvChild) != null) {
                                                ((TextView) childView.findViewById(R.id.tvChild)).setText(Html.fromHtml(q1.getTitle() + " "  + AppConstants.ASTERIK_SIGN));
                                            }
                                            q1.setRequired(true);
                                            childView.setVisibility(View.VISIBLE);
                                        }
                                    }*/


                                }
                            } else {
                                llOptionValues.setVisibility(View.GONE);
                                if (questionsMap != null && questionsMap.size() > 0) {
                                    Questions q2 = questionsMap.get(FormEnumKeys.optionValuesCount.toString());
                                    if (q2 != null) {
                                        View childView = llFields.findViewWithTag(q2);
                                        if (childView != null) {
                                            if (childView.findViewById(R.id.tvChild) != null) {
                                                ((TextView) childView.findViewById(R.id.tvChild)).setText(Html.fromHtml(q2.getTitle() + " " + AppConstants.ASTERIK_SIGN));
                                            }
                                            q2.setRequired(false);
                                            childView.setVisibility(View.GONE);
                                        }
                                    }

                                   /* Questions q1= questionsMap.get(FormEnumKeys.optionValues.toString());
                                    if (q1 != null) {
                                        View childView = llFields.findViewWithTag(q1);
                                        if (childView != null) {
                                            if (childView.findViewById(R.id.tvChild) != null) {
                                                ((TextView) childView.findViewById(R.id.tvChild)).setText(Html.fromHtml(q1.getTitle() + " "  + AppConstants.ASTERIK_SIGN));
                                            }
                                            q1.setRequired(false);
                                            childView.setVisibility(View.GONE);
                                        }
                                    }*/


                                }
                            }
                        }

                    }
                }
            });
            if (!selectionPickerDialog.isShowing()) {
                selectionPickerDialog.show();
            }
            selectionPickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    isPopupVisible = false;
                }
            });
        } else {
            isPopupVisible = false;
        }

    }

    protected void addRadioButton(String strVal, RadioGroup radioGroup, final Questions questions, int index) {
        final RadioButton rdbtn = new RadioButton(this);
        rdbtn.setPadding(2, 2, 2, 2);
        rdbtn.setId(index);


        if (Utility.validateString(questions.getAnswer()) && questions.getAnswer().equals(strVal)) {

            rdbtn.setChecked(true);
        } else {
            rdbtn.setChecked(false);
          /*  if (questions.getQuestionId().equalsIgnoreCase(AppConstants.ISACTIVE)){
              int isActive= Integer.parseInt(questions.getAnswer());
                if (isActive==1  && strVal.equalsIgnoreCase("Yes")) {
                    rdbtn.setChecked(true);
                } else {
                    rdbtn.setChecked(false);
                }
            }else {
                rdbtn.setChecked(false);
            }*/

        }
        rdbtn.setText(strVal);
        radioGroup.addView(rdbtn);
    }

    protected void addCheckBoxButton(String strVal, LinearLayout llCheck, final Questions questions, int index) {
        final CheckBox rdbtn = new CheckBox(this);
        rdbtn.setPadding(2, 2, 2, 2);
        if (Utility.validateString(questions.getAnswer()) && Utility.validateString(strVal)) {
            try {
                JSONArray jsonArray = new JSONArray(questions.getAnswer());
                if (jsonArray != null && jsonArray.length() > 0) {
                    for (int p = 0; p < jsonArray.length(); p++) {
                        if (strVal.equals(jsonArray.optString(p))) {
                            rdbtn.setChecked(true);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            rdbtn.setChecked(false);
        }
        rdbtn.setText(strVal);
        rdbtn.setTag(questions);

      /*  rdbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Utility.hideSoftKeyboard(FormParentActivity.this);
                if (rdbtn.getTag() instanceof Questions) {
                    Questions question = (Questions) rdbtn.getTag();
                    if (question != null && question.getQuestionId().equals(FormEnumKeys.radio.toString())) {
                        if (compoundButton.isChecked()) {
                            if (FormEnumKeys.Other.equalsName(compoundButton.getText().toString().trim())) {
                                if (questionsMap != null && questionsMap.size() > 0) {
                                    Questions q = questionsMap.get(FormEnumKeys.HEALTH_RISE_OTHER.toString());
                                    if (q != null) {
                                        View childView = llFields.findViewWithTag(q);
                                        if (childView != null) {
                                            if (childView.findViewById(R.id.tvChild) != null) {
                                                ((TextView) childView.findViewById(R.id.tvChild)).setText(Html.fromHtml(q.getTitle() + Constants.ASTERIK_SIGN));
                                            }
                                            q.setRequired(true);
                                            childView.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }
                            }
                        } else {
                            if (FormEnumKeys.Other.equalsName(compoundButton.getText().toString().trim())) {
                                if (questionsMap != null && questionsMap.size() > 0) {
                                    Questions q = questionsMap.get(FormEnumKeys.HEALTH_RISE_OTHER.toString());
                                    if (q != null) {
                                        View childView = llFields.findViewWithTag(q);
                                        if (childView != null && childView.findViewById(R.id.tvChild) != null) {
                                            ((TextView) childView.findViewById(R.id.tvChild)).setText("");
                                            childView.setVisibility(View.GONE);
                                            q.setAnswer("");
                                            q.setRequired(false);
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }
        });*/
        llCheck.addView(rdbtn);
    }

    protected boolean validateData(Map<String, Questions> questionsMap) {
        boolean isValid = true;

        if (formId.equalsIgnoreCase(FormEnum.question.toString())) {
            if (llOptionValues.getVisibility() == View.VISIBLE) {
                if (selectedOptions.isEmpty()) {
                    Utility.setTextView(getString(R.string.option), (TextView) findViewById(R.id.tvOptionValues), true);

                    isValid = false;
                } else {
                    Utility.setTextView(getString(R.string.option), (TextView) findViewById(R.id.tvOptionValues), false);

                }
            }
        }
        if (questionsMap != null && questionsMap.size() > 0) {
            for (Map.Entry<String, Questions> entry : questionsMap.entrySet()) {
                Questions question = (Questions) entry.getValue();
                if (question != null) {
                    View child = llFields.findViewWithTag(question);
                    if (child != null) {
                        TextView tvChild = (TextView) (child).findViewById(R.id.tvChild);
                        if (question.isRequired()) {
                            String questionTitle;

                            questionTitle = question.getTitle();
                            if (!Utility.validateString(question.getAnswer())) {
                                Utility.setTextView(questionTitle, tvChild, true);
                                isValid = false;
                            } else {
                                Utility.setTextView(questionTitle, tvChild, false);
                            }
                        }
                    }
                }
            }
        }
        if (isValid && questionsMap != null && questionsMap.size() > 0) {
            for (Map.Entry<String, Questions> entry : questionsMap.entrySet()) {
                Questions question = (Questions) entry.getValue();
                if (question != null) {
                    View child = llFields.findViewWithTag(question);
                    if (child != null) {
                        TextView tvChild = (TextView) (child).findViewById(R.id.tvChild);
                        float answer = 0;
                        if (question.getMinValue() > 0) {
                            if ((Utility.validateString(question.getAnswer()) && AppConstants.NUMBER.equals(question.getType()))) {
                                answer = Integer.parseInt(question.getAnswer());


                            } else if ((Utility.validateString(question.getAnswer()) && AppConstants.FLOAT.equals(question.getType()))) {
                                answer = Float.parseFloat(question.getAnswer());


                            } else if ((Utility.validateString(question.getAnswer()) && AppConstants.FLOAT.equals(question.getType()))) {
                                answer = Long.parseLong(question.getAnswer());


                            }

                            if (answer < question.getMinValue()) {
                                String questionTitle;

                                questionTitle = question.getTitle();
                                if (question.isRequired()) {
                                    Utility.setTextView(questionTitle, tvChild, true);
                                }

                                showToastMessage(questionTitle + " " + getString(R.string.minimum_must) + " " + question.getMinValue());

                                isValid = false;
                                return isValid;
                            }

                        }
                    }
                }
            }
        }


        if (isValid && questionsMap != null && questionsMap.size() > 0) {
        } else {
            showToastMessage(getString(R.string.fill_required_fields));
        }

        return isValid;
    }

}
