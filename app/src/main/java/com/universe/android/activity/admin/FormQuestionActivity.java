package com.universe.android.activity.admin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.universe.android.R;
import com.universe.android.enums.FormEnum;
import com.universe.android.model.Questions;
import com.universe.android.okkhttp.APIClient;
import com.universe.android.okkhttp.UniverseAPI;
import com.universe.android.realmbean.RealmController;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_form_question);
        setHeader();
        Intent intent=getIntent();
        if (intent!=null){
            title=intent.getStringExtra(AppConstants.STR_TITLE);
            formId=intent.getStringExtra(AppConstants.FORM_ID);
            formAnsId = intent.getStringExtra(AppConstants.FORM_ANS_ID);
        }
        ((TextView) findViewById(R.id.tvName)).setText(title);


        setScreenDetails();
        prepareQuestionList();

    }


    private void prepareQuestionList() {
        List<String> noDisplayKeys = new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.question)));
        if (formId.equalsIgnoreCase(FormEnum.survey.toString())) {
            noDisplayKeys = new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.surveys)));
        } else if (formId.equalsIgnoreCase(FormEnum.client.toString())) {
            noDisplayKeys = new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.client)));
        } else if (formId.equalsIgnoreCase(FormEnum.customer.toString())) {
            noDisplayKeys = new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.customer)));
        }else if (formId.equalsIgnoreCase(FormEnum.category.toString())) {
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
        } else {
            if (jsonSubmitReq != null && !jsonSubmitReq.has(AppConstants.ID)) {
                UUID randomId = UUID.randomUUID();
                String id = String.valueOf(randomId);
                try {
                    jsonSubmitReq.put(AppConstants.ID, id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        if (jsonSubmitReq.has(AppConstants.ISSYNC)) {
            jsonSubmitReq.remove(AppConstants.ISSYNC);
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.submittingAnswers));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        OkHttpClient okHttpClient = APIClient.getHttpClient();
        RequestBody requestBody = RequestBody.create(UniverseAPI.JSON, jsonSubmitReq.toString());
        String url = UniverseAPI.WEB_SERVICE_CREATE_SURVEY_METHOD;


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
                            String responses = new Gson().toJson(jsonResponse);
                            new RealmController().saveFormInputFromSubmit(responses, isUpdateId, formId);
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
        String strMsg = getResources().getString(R.string.save_data_msg) ;
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
                }  else {
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
        dialog.show();
    }

}
