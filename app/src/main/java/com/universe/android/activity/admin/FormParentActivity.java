package com.universe.android.activity.admin;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.collect.Collections2;
import com.universe.android.R;
import com.universe.android.component.FilterPredicate;
import com.universe.android.component.QuestionItemListDialog;
import com.universe.android.component.QuestionMapComparator;
import com.universe.android.enums.FormEnum;
import com.universe.android.model.Questions;
import com.universe.android.model.SpinnerList;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;


public class FormParentActivity extends AppCompatActivity {

    protected String formId = null;
    protected String formAnsId = null;
    protected LinearLayout llFields;
    protected Map<String, Questions> questionsMap;
    protected boolean isPopupVisible = false;
    protected boolean isSync;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       
    }


    protected void setScreenDetails() {
     //   final Realm realm = Realm.getDefaultInstance();
        llFields = (LinearLayout) findViewById(R.id.parent);






    }

    public void setHeader() {
//
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
            getSupportActionBar().setCustomView(R.layout.act_main_header);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void showAlert() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);

        TextView text_dialog = (TextView) dialog.findViewById(R.id.text_dialog);
        text_dialog.setText(getResources().getString(R.string.alert_clear_form));
        Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
        btnYes.setText(getResources().getString(R.string.yes));

        Button btnNo = (Button) dialog.findViewById(R.id.btnNo);
        btnNo.setVisibility(View.VISIBLE);
        btnNo.setText(getResources().getString(R.string.no));
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utility.animateView(v);
                dialog.dismiss();
                setResult(RESULT_OK);
                finish();

            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utility.animateView(v);
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    protected void showToastMessage(String strMsg) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_show,
                (ViewGroup) findViewById(R.id.toast_layout_root));


        TextView text = (TextView) layout.findViewById(R.id.tvToast);
        text.setText(strMsg);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void setTodaysDate(TextView tvDate) {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        final String date1 = AppConstants.format2.format(date);
        tvDate.setText(date1);
    }

    protected Map<String, Questions> prepareFormQuestions(String stationFormId, List<String> noDisplayKeys) {
        Realm realm = Realm.getDefaultInstance();
        Map<String, Questions> questionsMap = new LinkedHashMap<>();
        try {
            formId = stationFormId;
            RealmResults<RealmQuestions> realmFormQuestions = null;
            JSONObject jsonAnswers = null;
            realmFormQuestions = realm.where(RealmQuestions.class).equalTo(AppConstants.FORM_ID, stationFormId).findAll();
            if (Utility.validateString(formAnsId)) {

                if (formId.equalsIgnoreCase(FormEnum.survey.toString())){
                    RealmResults<RealmSurveys> realmSurveys = realm.where(RealmSurveys.class).equalTo(AppConstants.ID, formAnsId).findAll();

                    if (realmSurveys != null && realmSurveys.size() > 0) {

                    //      RealmQuestion realmQuestion = realmSurveys.get(0);
                        ((Button) findViewById(R.id.btnSaveNext)).setTag(realmSurveys.get(0).getId());
                        //  isSync = realmQuestions.get(0).isSync();
                        jsonAnswers = new JSONObject(realmSurveys.get(0).getResponses());

                    }
                }else if (formId.equalsIgnoreCase(FormEnum.client.toString())){
                    RealmResults<RealmClient> realmClients = realm.where(RealmClient.class).equalTo(AppConstants.ID, formAnsId).findAll();

                    if (realmClients != null && realmClients.size() > 0) {

                        //  RealmQuestion realmQuestion = realmQuestions.get(0);
                        ((Button) findViewById(R.id.btnSaveNext)).setTag(realmClients.get(0).getId());
                        //  isSync = realmQuestions.get(0).isSync();
                        jsonAnswers = new JSONObject(realmClients.get(0).getResponses());

                    }
                }else if (formId.equalsIgnoreCase(FormEnum.customer.toString())){
                    RealmResults<RealmCustomer> realmCustomers = realm.where(RealmCustomer.class).equalTo(AppConstants.ID, formAnsId).findAll();

                    if (realmCustomers != null && realmCustomers.size() > 0) {

                        //  RealmQuestion realmQuestion = realmQuestions.get(0);
                        ((Button) findViewById(R.id.btnSaveNext)).setTag(realmCustomers.get(0).getId());
                        //  isSync = realmQuestions.get(0).isSync();
                        jsonAnswers = new JSONObject(realmCustomers.get(0).getResponses());

                    }
                }else if (formId.equalsIgnoreCase(FormEnum.category.toString())){
                    RealmResults<RealmCategory> realmCategories = realm.where(RealmCategory.class).equalTo(AppConstants.ID, formAnsId).findAll();

                    if (realmCategories != null && realmCategories.size() > 0) {

                        //  RealmQuestion realmQuestion = realmQuestions.get(0);
                        ((Button) findViewById(R.id.btnSaveNext)).setTag(realmCategories.get(0).getId());
                        //  isSync = realmQuestions.get(0).isSync();
                        jsonAnswers = new JSONObject(realmCategories.get(0).getResponses());

                    }
                }else if (formId.equalsIgnoreCase(FormEnum.question.toString())){
                    RealmResults<RealmQuestion> realmQuestions = realm.where(RealmQuestion.class).equalTo(AppConstants.ID, formAnsId).findAll();

                    if (realmQuestions != null && realmQuestions.size() > 0) {

                      //  RealmQuestion realmQuestion = realmQuestions.get(0);
                        ((Button) findViewById(R.id.btnSaveNext)).setTag(realmQuestions.get(0).getId());
                      //  isSync = realmQuestions.get(0).isSync();
                        jsonAnswers = new JSONObject(realmQuestions.get(0).getResponses());

                    }
                }

                ((Button) findViewById(R.id.btnSave)).setVisibility(View.GONE);
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

    private void addQuestionsForm(Map<String, Questions> questionsMap) {
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

    protected void addSelectTextView(final TextView tvSelect, final Questions question) {
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
            }

            tvSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  /*  if (view.getTag() != null) {
                        List<SpinnerList> spnList = (List<SpinnerList>) view.getTag();
                        if (spnList != null && spnList.size() > 0) {


                        }
                    } else {
                        isPopupVisible = false;
                    }*/
                }
            });

        } catch (Exception e) {

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
        llCheck.addView(rdbtn);
    }

    private void showDatePicker(String formattedDate, final TextView tvDate) {
        Date date = null;
        Calendar calendar = Calendar.getInstance();
        try {
            if (!formattedDate.equals(AppConstants.DATE_FORMAT)) {
                date = AppConstants.format2.parse(formattedDate);
            } else {
                date = calendar.getTime();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            calendar.setTime(date);
        }

        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tvDate.setText(AppConstants.format2.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    protected void showSelectionList(Context context, final TextView textView, final List<SpinnerList> list, final String defaultMsg) {
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

    protected JSONObject prepareJsonRequest(Map<String, Questions> questionsMap) {
        JSONObject jsonSubmitReq = new JSONObject();
        JSONObject jsonAnswers = new JSONObject();
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
                                            jsonSubmitReq.put(question.getQuestionId(), Integer.parseInt(question.getAnswer()));
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
                                    jsonSubmitReq.put(question.getQuestionId(), question.getAnswer());
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


                jsonSubmitReq.put(AppConstants.RESPONSES,jsonSubmitReq.toString());
                jsonSubmitReq.put(AppConstants.FORM_ID, formId);

             //   jsonSubmitReq.put(AppConstants.CREATED_BY, userId);
                if (Utility.validateString(formAnsId)){
                  //  jsonSubmitReq.put(AppConstants.UPDATEDAT, Utility.get);
                }else{
                 //   jsonSubmitReq.put(AppConstants.CREATEDAT, userId);
                }

           }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("json", jsonSubmitReq.toString());
        return jsonSubmitReq;
    }

    protected boolean validateData(Map<String, Questions> questionsMap) {
        boolean isValid = true;
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

    protected void clearForm(Map<String, Questions> questionsMap, String formName) {
        try {
            Prefs.clearValue( formName);
            llFields = (LinearLayout) findViewById(R.id.parent);
            if (llFields != null && llFields.getChildCount() > 0) {
                for (Map.Entry<String, Questions> entry : questionsMap.entrySet()) {
                    Questions question = (Questions) entry.getValue();
                    if (question != null && (question.getInputType().equals(AppConstants.TEXTBOX) || (question.getInputType().equals(AppConstants.TEXTAREA)))) {
                        View childView = llFields.findViewWithTag(question);
                        if (childView != null) {
                            EditText edtChild = (EditText) childView.findViewById(R.id.edtChild);
                            if (edtChild != null) {
                                edtChild.getText().clear();
                                question.setAnswer(edtChild.getText().toString().trim());
                            }
                        }
                    } else if (question != null && (question.getInputType().equals(AppConstants.RADIO))) {
                        View childView = llFields.findViewWithTag(question);
                        if (childView != null) {
                            RadioGroup radChild = (RadioGroup) childView.findViewById(R.id.radChild);
                            if (radChild != null) {
                                radChild.clearCheck();
                                question.setAnswer("");
                            }
                        }
                    } else if (question != null && (question.getInputType().equals(AppConstants.SELECT))) {
                        View childView = llFields.findViewWithTag(question);
                        if (childView != null) {
                            TextView textView = (TextView) childView.findViewById(R.id.spnSelect);
                            if (textView != null && textView.getTag() != null) {
                                textView.setText(question.getPlaceholder());
                                List<SpinnerList> spinnerList = (List<SpinnerList>) textView.getTag();
                                for (SpinnerList s : spinnerList) {
                                    s.setChecked(false);
                                }
                                textView.setTag(spinnerList);
                            }
                        }
                    } else if (question != null && (question.getInputType().equals(AppConstants.DATE))) {
                        View childView = llFields.findViewWithTag(question);
                        if (childView != null) {
                            TextView textView = (TextView) childView.findViewById(R.id.tvFormDate);
                            if (textView != null) {
                                setTodaysDate(textView);
                                question.setAnswer("");
                            }
                        }
                    } else if (question != null && (question.getInputType().equals(AppConstants.CHECKBOX))) {
                        View childView = llFields.findViewWithTag(question);
                        if (childView != null) {
                            LinearLayout llCheckChild = (LinearLayout) childView.findViewById(R.id.llCheckChild);
                            if (llCheckChild != null) {
                                for (int j = 0; j < llCheckChild.getChildCount(); j++) {
                                    View child = llCheckChild.getChildAt(j);
                                    if (llCheckChild.getChildAt(j) instanceof CheckBox) {
                                        CheckBox cb = (CheckBox) child;
                                        cb.setChecked(false);
                                    }
                                }
                                question.setAnswer("");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                if (isSync) {
                    jsonSubmitReq.put(AppConstants.ISUPDATE, false);
                } else {
                    jsonSubmitReq.put(AppConstants.ISSYNC, false);
                }


                if (formId.equalsIgnoreCase(FormEnum.survey.toString())) {
                    realm.createOrUpdateObjectFromJson(RealmSurveys.class, jsonSubmitReq);
                } else if (formId.equalsIgnoreCase(FormEnum.client.toString())) {
                    realm.createOrUpdateObjectFromJson(RealmClient.class, jsonSubmitReq);
                } else if (formId.equalsIgnoreCase(FormEnum.customer.toString())) {
                    realm.createOrUpdateObjectFromJson(RealmCustomer.class, jsonSubmitReq);
                }else if (formId.equalsIgnoreCase(FormEnum.category.toString())) {
                    realm.createOrUpdateObjectFromJson(RealmCategory.class, jsonSubmitReq);
                }else if (formId.equalsIgnoreCase(FormEnum.question.toString())){
                    realm.createOrUpdateObjectFromJson(RealmQuestion.class, jsonSubmitReq);
                }

            } catch (Exception e) {
                realm.cancelTransaction();
                realm.close();
            } finally {
                realm.commitTransaction();
                realm.close();
            }
        }
    }




    @Override
    public void onBackPressed() {
        Utility.hideSoftKeyboard(FormParentActivity.this);
        showAlert();
    }

}
