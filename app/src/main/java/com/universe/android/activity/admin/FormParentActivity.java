package com.universe.android.activity.admin;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
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
import android.widget.CompoundButton;
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
import com.universe.android.enums.FormEnumKeys;
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

import static in.editsoft.api.util.App.context;


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

    public void showDatePicker(String formattedDate, final TextView tvDate) {
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
