package com.universe.android.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
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

import com.universe.android.R;
import com.universe.android.activity.SurveyDetailActivity;
import com.universe.android.adapter.StatusAdapter;
import com.universe.android.adapter.TeamSelectionAdapter;
import com.universe.android.helper.FontClass;
import com.universe.android.model.Number;
import com.universe.android.model.StatusModel;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Utility;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by gaurav.pandey on 27-02-2018.
 */

public class SurveyDetailDialogFragment extends DialogFragment {
    private View view;
    private EditText input_period_from, input_period_to, input_period_status;
    private TextView textViewTodayFilter, textViewWeekFilter, textViewMonthFilter, textViewOthersFilter, textViewFilter;
    private TextView textViewPeriodFrom, textViewPeriodTo, textViewPeriodStatus, textViewReset, textViewApplyFilter;
    private TextView textViewChooseStatus;

    private Dialog dialogFilter, dialogStatus, dialogCalendra;
    private ImageView imageViewCancel;
    ArrayList<StatusModel> statusList = new ArrayList<>();
    ArrayList<StatusModel> multiselectSatuslist = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private StatusAdapter statusAdapter;
    private Calendar mcalendar;
    private int day, month, year;
    private ArrayList<Number> numberArrayList = new ArrayList<>();
    private RecyclerView recylerViewStatus;
    private TeamSelectionAdapter teamSelectionAdapter;
    private RelativeLayout relativeLayoutSubmit;
    private ImageView imageViewClose, imageViewCloseStatus;
    private String fromDateString, toDateString, statusString;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.filter_dialog, container, false);
        initialization();
        setUpElements();
        setUpListenrs();
        return view;
    }

    private void setUpListenrs() {
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
                statusString = input_period_status.getText().toString();

                if (Utility.validateString(fromDateString) && !fromDateString.equals(AppConstants.DATE_FORMAT)) {
                    if (Utility.validateString(toDateString) && !toDateString.equals(AppConstants.DATE_FORMAT)) {
                        Date date = null;
                        Date toDateTime=null;
                        Date fromDateTime=null;
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
                            SetDataListListener setDataListListener = (SetDataListListener) getActivity();
                            setDataListListener.submitData(statusString, fromDateString, toDateString);
                            dismiss();

                            //  prepareList();
                        }
                    }
                }


            }
        });


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

        textViewMonthFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewMonthFilter.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                textViewTodayFilter.setBackgroundColor(getResources().getColor(R.color.transparent_color));
                textViewWeekFilter.setBackgroundColor(getResources().getColor(R.color.transparent_color));
                textViewOthersFilter.setBackgroundColor(getResources().getColor(R.color.transparent_color));
                input_period_from.setText("");
                input_period_to.setText("");
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
        showData();
    }


    private void dialogStatusSetUpElements() {
        teamSelectionAdapter = new TeamSelectionAdapter(numberArrayList, getActivity());
        mLayoutManager = new LinearLayoutManager(getActivity());
        recylerViewStatus.setLayoutManager(mLayoutManager);
        recylerViewStatus.setItemAnimator(new DefaultItemAnimator());
        recylerViewStatus.setAdapter(teamSelectionAdapter);


        relativeLayoutSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder stringBuilder = new StringBuilder();
                for (Number number : numberArrayList) {
                    if (number.isSelected()) {
                        if (stringBuilder.length() > 0)
                            stringBuilder.append(", ");
                        stringBuilder.append(number.getTextONEs());
                    }
                }
                input_period_status.setText(stringBuilder.toString());
                dialogStatus.dismiss();
            }
        });
    }

    private void showData() {

        String ONEs[] = {"Target", "Submitted", "InProgress"};
        for (int i = 0; i <= 2; i++) {
            Number number = new Number();
            number.setONEs(i + "");
            number.setTextONEs(ONEs[i]);
            this.numberArrayList.add(number);
        }

    }

    private void dialogStatusInitialization() {
        textViewChooseStatus = dialogStatus.findViewById(R.id.textViewChooseStatus);
        imageViewCloseStatus = dialogStatus.findViewById(R.id.imageViewCloseStatus);
        recylerViewStatus = dialogStatus.findViewById(R.id.recylerViewStatus);
        relativeLayoutSubmit = dialogStatus.findViewById(R.id.relativeLayoutSubmit);

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

        input_period_status.setHint(R.string.status);
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

        DatePickerDialog dp = new DatePickerDialog(getActivity(),
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
        dp.setTitle("Calender");
        dp.show();
    }

    public void dateDialogto() {
        final Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dp = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                    /*    String erg = "";
                        erg = String.valueOf(dayOfMonth);
                        erg += "-" + String.valueOf(monthOfYear + 1);
                        erg += "-" + year;
                        input_period_to.setText(erg);*/
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        input_period_to.setText(AppConstants.format2.format(newDate.getTime()));
                    }

                }, y, m, d);
        dp.setTitle("Calender");
        dp.show();

    }

    public interface SetDataListListener {
        public void submitData(String statusString, String fromDateString, String toDateString);
    }

}
