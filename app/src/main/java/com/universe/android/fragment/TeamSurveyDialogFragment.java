package com.universe.android.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.universe.android.R;
import com.universe.android.adapter.StatusAdapter;
import com.universe.android.adapter.TeamMutiSelctAdapter;
import com.universe.android.adapter.TeamSelectionAdapter;
import com.universe.android.helper.FontClass;
import com.universe.android.model.Number;
import com.universe.android.resource.Login.SurveyDetails.SurverDetailResponse;
import com.universe.android.resource.Login.login.LoginRequest;
import com.universe.android.resource.Login.login.LoginResponse;
import com.universe.android.resource.Login.login.LoginService;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;
import com.universe.android.web.BaseApiCallback;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import in.editsoft.api.exception.APIException;

/**
 * Created by gaurav.pandey on 05-03-2018.
 */

public class TeamSurveyDialogFragment extends DialogFragment {
    private View view;
    private EditText input_period_from, input_period_to, input_period_status;
    private TextView textViewTodayFilter, textViewWeekFilter, textViewMonthFilter, textViewOthersFilter, textViewFilter;
    private TextView textViewPeriodFrom, textViewPeriodTo, textViewPeriodStatus, textViewReset, textViewApplyFilter;
    private TextView textViewChooseStatus;

    private Dialog dialogFilter, dialogStatus, dialogCalendra;
    private ImageView imageViewCancel;

    private LinearLayoutManager mLayoutManager;
    private StatusAdapter statusAdapter;
    private Calendar mcalendar;
    private int day, month, year;
    private ArrayList<Number> numberArrayList = new ArrayList<>();
    private RecyclerView recylerViewStatus;
    private TeamMutiSelctAdapter teamSelectionAdapter;
    private RelativeLayout relativeLayoutSubmit;
    private ImageView imageViewClose, imageViewCloseStatus;
    private String fromDateString, toDateString, statusString;
    DatePickerDialog dp;
    private ArrayList<LoginResponse.ResponseBean.LoginDetailsBean> loginDetailsBeanArrayList;
    private String id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.filter_dialog, container, false);
        initialization();
        setUpElements();
        setUpListeners();
        return view;
    }

    private void initialization() {
        input_period_from = view.findViewById(R.id.input_period_from);
        input_period_to = view.findViewById(R.id.input_period_to);
        input_period_status = view.findViewById(R.id.input_period_status);
        textViewPeriodFrom = view.findViewById(R.id.textViewPeriodFrom);
        textViewPeriodTo = view.findViewById(R.id.textViewPeriodTo);
        textViewPeriodStatus = view.findViewById(R.id.textViewPeriodStatus);
        textViewReset = view.findViewById(R.id.textViewReset);
        textViewApplyFilter = view.findViewById(R.id.textViewApplyFilter);
        imageViewCancel = view.findViewById(R.id.imageViewClose);
        textViewTodayFilter = view.findViewById(R.id.textViewTodayFilter);
        textViewWeekFilter = view.findViewById(R.id.textViewWeekFilter);
        textViewMonthFilter = view.findViewById(R.id.textViewMonthFilter);
        textViewOthersFilter = view.findViewById(R.id.textViewOthersFilter);

        input_period_status.setHint(R.string.team);
        textViewTodayFilter.setTypeface(FontClass.openSansRegular(getActivity()));
        textViewWeekFilter.setTypeface(FontClass.openSansRegular(getActivity()));
        textViewMonthFilter.setTypeface(FontClass.openSansRegular(getActivity()));
        textViewOthersFilter.setTypeface(FontClass.openSansRegular(getActivity()));
        textViewPeriodFrom.setTypeface(FontClass.openSansRegular(getActivity()));
        textViewPeriodTo.setTypeface(FontClass.openSansRegular(getActivity()));
        textViewPeriodStatus.setTypeface(FontClass.openSansRegular(getActivity()));
        textViewReset.setTypeface(FontClass.openSansRegular(getActivity()));
        textViewApplyFilter.setTypeface(FontClass.openSansLight(getActivity()));
        input_period_from.setTypeface(FontClass.openSansLight(getActivity()));
        input_period_to.setTypeface(FontClass.openSansLight(getActivity()));
        input_period_status.setTypeface(FontClass.openSansLight(getActivity()));

        textViewTodayFilter.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        textViewWeekFilter.setBackgroundColor(getResources().getColor(R.color.transparent_color));
        textViewMonthFilter.setBackgroundColor(getResources().getColor(R.color.transparent_color));
        textViewOthersFilter.setBackgroundColor(getResources().getColor(R.color.transparent_color));
        input_period_from.setText(Utility.getCurrentDate());
        input_period_to.setText(Utility.getCurrentDate());

    }

    public void dateDialogfrom() {
        final Calendar c = Calendar.getInstance();

        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);

        dp = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                       /* String erg = "";
                        erg = String.valueOf(dayOfMonth);
                        erg += "-" + String.valueOf(monthOfYear + 1);
                        erg += "-" + year;
                        input_period_from.setText(erg);*/
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);

                        input_period_from.setText(AppConstants.format2.format(newDate.getTime()));
                    }

                }, y, m, d);
        dp.getDatePicker().setMaxDate(System.currentTimeMillis());
        dp.setTitle("Calender");
        dp.show();
    }

    public void dateDialogto() {
        final Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);

        dp = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        dp.getDatePicker().setMaxDate(System.currentTimeMillis());
                        input_period_to.setText(AppConstants.format2.format(newDate.getTime()));
                    }

                }, y, m, d);
        dp.getDatePicker().setMaxDate(System.currentTimeMillis());
        dp.setTitle("Calender");
        dp.show();

    }

    private void setUpElements() {
        textViewTodayFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_period_from.setText("");
                input_period_to.setText("");
                textViewTodayFilter.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                textViewWeekFilter.setBackgroundColor(getResources().getColor(R.color.transparent_color));
                textViewMonthFilter.setBackgroundColor(getResources().getColor(R.color.transparent_color));
                textViewOthersFilter.setBackgroundColor(getResources().getColor(R.color.transparent_color));

                input_period_from.setText(Utility.getCurrentDate());
                input_period_to.setText(Utility.getCurrentDate());


            }
        });

        textViewWeekFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_period_from.setText("");
                input_period_to.setText("");
                textViewWeekFilter.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                textViewTodayFilter.setBackgroundColor(getResources().getColor(R.color.transparent_color));
                textViewMonthFilter.setBackgroundColor(getResources().getColor(R.color.transparent_color));
                textViewOthersFilter.setBackgroundColor(getResources().getColor(R.color.transparent_color));
                Date date1 = new Date();
                Date newDate = new Date(date1.getTime() - 604800000L); // 7 * 24 * 60 * 60 * 1000
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(newDate);
                Date newDate1 = calendar.getTime();
                String dateFinal = AppConstants.format2.format(newDate1);

                input_period_from.setText(dateFinal);
                input_period_to.setText(Utility.getCurrentDate());
            }


        });

        textViewMonthFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewMonthFilter.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                textViewTodayFilter.setBackgroundColor(getResources().getColor(R.color.transparent_color));
                textViewWeekFilter.setBackgroundColor(getResources().getColor(R.color.transparent_color));
                textViewOthersFilter.setBackgroundColor(getResources().getColor(R.color.transparent_color));
                input_period_from.setText("");
                input_period_to.setText("");
                String date;
                Date date1 = new Date();
                Date newDate = new Date(date1.getTime() - 2592000000L); // 7 * 24 * 60 * 60 * 1000
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(newDate);
                Date newDate1 = calendar.getTime();
                String dateFinal = AppConstants.format2.format(newDate1);
                input_period_from.setText(dateFinal);
                input_period_to.setText(Utility.getCurrentDate());

            }
        });

        textViewOthersFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_period_from.setText("");
                input_period_to.setText("");
                textViewOthersFilter.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                textViewTodayFilter.setBackgroundColor(getResources().getColor(R.color.transparent_color));
                textViewWeekFilter.setBackgroundColor(getResources().getColor(R.color.transparent_color));
                textViewMonthFilter.setBackgroundColor(getResources().getColor(R.color.transparent_color));
                input_period_from.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dateDialogfrom();
                    }
                });
                input_period_to.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dateDialogto();
                    }
                });

            }
        });
        input_period_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusDialog();
            }
        });
    }

    public void statusDialog() {
        dialogStatus = new Dialog(getActivity());
        dialogStatus.setCanceledOnTouchOutside(true);
        dialogStatus.setContentView(R.layout.select_status_dialog);
        dialogStatusInitialization();
        dialogStatusSetUpElements();
        dialogStatus.show();

    }


    private void dialogStatusSetUpElements() {
        teamSelectionAdapter = new TeamMutiSelctAdapter(getActivity(), loginDetailsBeanArrayList);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recylerViewStatus.setLayoutManager(mLayoutManager);
        recylerViewStatus.setItemAnimator(new DefaultItemAnimator());
        recylerViewStatus.setAdapter(teamSelectionAdapter);
        showTeamList();
        imageViewCloseStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogStatus.dismiss();
            }
        });

        relativeLayoutSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder stringBuilder = new StringBuilder();
                for (LoginResponse.ResponseBean.LoginDetailsBean number : loginDetailsBeanArrayList) {
                    for (String fata : getSaveList()) {
                        if (number.getMember().get_id().equals(fata)) {
                            if (stringBuilder.length() > 0)
                                stringBuilder.append(", ");
                            stringBuilder.append(number.getMember().getName());
                        }
                    }
                }

                input_period_status.setText(stringBuilder.toString());

                dialogStatus.dismiss();
            }
        });
    }

    private void dialogStatusInitialization() {
        loginDetailsBeanArrayList = new ArrayList<>();
        textViewChooseStatus = dialogStatus.findViewById(R.id.textViewChooseStatus);
        imageViewCloseStatus = dialogStatus.findViewById(R.id.imageViewCloseStatus);
        recylerViewStatus = dialogStatus.findViewById(R.id.recylerViewStatus);
        relativeLayoutSubmit = dialogStatus.findViewById(R.id.relativeLayoutSubmit);

    }

    private void setUpListeners() {
        textViewReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_period_from.setText(" ");
                input_period_to.setText(" ");
                input_period_status.setText(" ");
            }
        });
        textViewApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromDateString = input_period_from.getText().toString();
                toDateString = input_period_to.getText().toString();
                // id = loginDetailsBeanArrayList.get(0).getMember().get_id();
                StringBuilder stringBuilder = new StringBuilder();
                for (String number : getSaveList()) {
                    if (stringBuilder.length() > 0)
                        stringBuilder.append(", ");
                    stringBuilder.append(number);
                    id = getSaveList().toString();
                }

                if (Utility.validateString(fromDateString) && !fromDateString.equals(AppConstants.DATE_FORMAT)) {
                    if (Utility.validateString(toDateString) && !toDateString.equals(AppConstants.DATE_FORMAT)) {
                        Date date = null;
                        Date toDateTime = null;
                        Date fromDateTime = null;
                        try {
                            date = AppConstants.format2.parse(fromDateString);

                            fromDateTime = AppConstants.format3.parse(AppConstants.format3.format(date));

                            date = AppConstants.format2.parse(toDateString);
                            toDateTime = AppConstants.format3.parse(AppConstants.format3.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (toDateTime.getTime() < fromDateTime.getTime()) {
                            Utility.showToast(getString(R.string.please_select_valid_to_date));
                        } else {
                            TeamSurveyData setDataListListener = (TeamSurveyData) getActivity();
                            setDataListListener.submitTeamSurveyData(id, fromDateString, toDateString);
                            dismiss();

                            //  prepareList();
                        }
                    }
                }


            }
        });
        imageViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


    }

    public void showTeamList() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("Abhishek.LAL@CRYSTALCROP.COM");
        loginRequest.setPassword("pass123456");
        loginRequest.setType("report");
        LoginService loginService = new LoginService();
        loginService.executeService(loginRequest, new BaseApiCallback<LoginResponse>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(@NonNull LoginResponse response) {
                super.onSuccess(response);
                List<LoginResponse.ResponseBean.LoginDetailsBean> crystaDoctorBeans = response.getResponse().getLoginDetails();
                String value = new Gson().toJson(crystaDoctorBeans);
                LoginResponse.ResponseBean.LoginDetailsBean[] surveyDetailsBeans = new Gson().fromJson(value, LoginResponse.ResponseBean.LoginDetailsBean[].class);
                loginDetailsBeanArrayList.clear();
                for (LoginResponse.ResponseBean.LoginDetailsBean loginDetailsBean : surveyDetailsBeans) {

                    loginDetailsBeanArrayList.add(loginDetailsBean);
                }

//                Collections.addAll(loginDetailsBeanArrayList, surveyDetailsBeans);

                teamSelectionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
            }
        });
    }


    public interface TeamSurveyData {
        void submitTeamSurveyData(String id, String fromDate, String toDate);
    }


    public void storeData(String data) {
        ArrayList<String> arrayList;
        Gson gson = new Gson();
        String json = Prefs.getStringPrefs(AppConstants.FilterData);
        if (!json.equals("")) {
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            arrayList = gson.fromJson(json, type);
            arrayList.add(data);
            String json1 = gson.toJson(arrayList);
            Prefs.putStringPrefs(AppConstants.FilterData, json1);

        } else {
            arrayList = new ArrayList<>();
            arrayList.add(data);
            String json1 = gson.toJson(arrayList);
            Prefs.putStringPrefs(AppConstants.FilterData, json1);
        }


    }

    public void removeData(String data) {
        ArrayList<String> arrayList;
        Gson gson = new Gson();
        String json = Prefs.getStringPrefs(AppConstants.FilterData);
        if (!json.equals("")) {
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            arrayList = gson.fromJson(json, type);
            arrayList.remove(data);
            String json1 = gson.toJson(arrayList);
            Prefs.putStringPrefs(AppConstants.FilterData, json1);
        }


    }

    public ArrayList<String> getSaveList() {
        ArrayList<String> arrayList;
        Gson gson = new Gson();
        String json = Prefs.getStringPrefs(AppConstants.FilterData);
        if (!json.equals("")) {
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            arrayList = gson.fromJson(json, type);

        } else {
            arrayList = new ArrayList<>();
        }
        return arrayList;
    }


}
