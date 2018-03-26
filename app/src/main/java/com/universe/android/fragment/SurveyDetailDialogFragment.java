package com.universe.android.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
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
import com.universe.android.adapter.SurveyStatusSelectionAdapter;
import com.universe.android.adapter.TeamSelectionAdapter;
import com.universe.android.helper.FontClass;
import com.universe.android.helper.RecyclerTouchListener;
import com.universe.android.model.Number;
import com.universe.android.model.StatusModel;
import com.universe.android.model.SurveyReportModel;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    DatePickerDialog dp;
    private Dialog dialogFilter, dialogStatus, dialogCalendra;
    private ImageView imageViewCancel;
    private SurveyStatusSelectionAdapter surveyStatusSelectionAdapter;
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
    private ArrayList<SurveyReportModel> surveyReportModelArrayList;
    String type, teamTitleString;

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
                teamTitleString = input_period_status.getText().toString();


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
                            SetDataListListener setDataListListener = (SetDataListListener) getActivity();
                            setDataListListener.submitData(statusString, fromDateString, toDateString, type);
                            dismiss();

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

    private void setUpElements() {
        textViewTodayFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_period_from.setText("");
                input_period_to.setText("");
                textViewTodayFilter.setBackgroundColor(getResources().getColor(R.color.buttoncolor));
                textViewTodayFilter.setTextColor(getResources().getColor(R.color.white));
                textViewWeekFilter.setTextColor(getResources().getColor(R.color.filter_text_color));
                textViewMonthFilter.setTextColor(getResources().getColor(R.color.filter_text_color));
                textViewOthersFilter.setTextColor(getResources().getColor(R.color.filter_text_color));
                textViewWeekFilter.setBackgroundColor(getResources().getColor(R.color.filter_color));
                textViewMonthFilter.setBackgroundColor(getResources().getColor(R.color.filter_color));
                textViewOthersFilter.setBackgroundColor(getResources().getColor(R.color.filter_color));

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

                type = ":" + AppConstants.ToDay;
                input_period_from.setText(Utility.getCurrentDate());
                input_period_to.setText(Utility.getCurrentDate());

            }
        });

        textViewWeekFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = ":" + AppConstants.CurrentWeek;
                input_period_from.setText("");
                input_period_to.setText("");
                textViewWeekFilter.setBackgroundColor(getResources().getColor(R.color.buttoncolor));
                textViewTodayFilter.setTextColor(getResources().getColor(R.color.filter_text_color));
                textViewWeekFilter.setTextColor(getResources().getColor(R.color.white));
                textViewMonthFilter.setTextColor(getResources().getColor(R.color.filter_text_color));
                textViewOthersFilter.setTextColor(getResources().getColor(R.color.filter_text_color));
                textViewTodayFilter.setBackgroundColor(getResources().getColor(R.color.filter_color));
                textViewMonthFilter.setBackgroundColor(getResources().getColor(R.color.filter_color));
                textViewOthersFilter.setBackgroundColor(getResources().getColor(R.color.filter_color));
                // Get calendar set to current date and time
                Calendar c = Calendar.getInstance();

                // Set the calendar to Sunday of the current week
                c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

                // Print dates of the current week starting on Sunday
                DateFormat df = new SimpleDateFormat("dd MMM yyyy");

                input_period_from.setText(df.format(c.getTime()));

                input_period_to.setText(Utility.getCurrentDate());
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
                type = ":" + AppConstants.CurrentMonth;
                textViewMonthFilter.setBackgroundColor(getResources().getColor(R.color.buttoncolor));
                textViewTodayFilter.setTextColor(getResources().getColor(R.color.filter_text_color));
                textViewMonthFilter.setTextColor(getResources().getColor(R.color.white));
                textViewWeekFilter.setTextColor(getResources().getColor(R.color.filter_text_color));
                textViewOthersFilter.setTextColor(getResources().getColor(R.color.filter_text_color));
                textViewTodayFilter.setBackgroundColor(getResources().getColor(R.color.filter_color));
                textViewWeekFilter.setBackgroundColor(getResources().getColor(R.color.filter_color));
                textViewOthersFilter.setBackgroundColor(getResources().getColor(R.color.filter_color));
                input_period_from.setText("");
                input_period_to.setText("");
                // Get calendar set to current date and time
                Calendar c = Calendar.getInstance();

                // Set the calendar to Sunday of the current week
                c.set(Calendar.DAY_OF_MONTH, 1);

                // Print dates of the current week starting on Sunday
                DateFormat df = new SimpleDateFormat("dd MMM yyyy");
                input_period_from.setText(df.format(c.getTime()));
                input_period_to.setText(Utility.getCurrentDate());
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
                type = ":" + AppConstants.Others;
                input_period_from.setText("");
                input_period_to.setText("");
                textViewOthersFilter.setBackgroundColor(getResources().getColor(R.color.buttoncolor));
                textViewTodayFilter.setTextColor(getResources().getColor(R.color.filter_text_color));
                textViewWeekFilter.setTextColor(getResources().getColor(R.color.filter_text_color));
                textViewMonthFilter.setTextColor(getResources().getColor(R.color.filter_text_color));
                textViewOthersFilter.setTextColor(getResources().getColor(R.color.white));
                textViewTodayFilter.setBackgroundColor(getResources().getColor(R.color.filter_color));
                textViewWeekFilter.setBackgroundColor(getResources().getColor(R.color.filter_color));
                textViewMonthFilter.setBackgroundColor(getResources().getColor(R.color.filter_color));
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
        surveyReportModelArrayList = new ArrayList<>();
        surveyStatusSelectionAdapter = new SurveyStatusSelectionAdapter(getActivity(), surveyReportModelArrayList);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recylerViewStatus.setLayoutManager(mLayoutManager);
        recylerViewStatus.setItemAnimator(new DefaultItemAnimator());
        recylerViewStatus.setAdapter(surveyStatusSelectionAdapter);
        prepareMovieData();

        imageViewCloseStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogStatus.dismiss();
            }
        });
        recylerViewStatus.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recylerViewStatus, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                input_period_status.setText(surveyReportModelArrayList.get(position).getTitleString());
                dialogStatus.dismiss();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void dialogStatusInitialization() {
        textViewChooseStatus = dialogStatus.findViewById(R.id.textViewChooseStatus);
        imageViewCloseStatus = dialogStatus.findViewById(R.id.imageViewCloseStatus);
        recylerViewStatus = dialogStatus.findViewById(R.id.recylerViewStatus);
        relativeLayoutSubmit = dialogStatus.findViewById(R.id.relativeLayoutSubmit);
        relativeLayoutSubmit.setVisibility(View.GONE);

    }

    private void initialization() {
        type = ":" + AppConstants.ToDay;
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

        textViewTodayFilter.setBackgroundColor(getResources().getColor(R.color.buttoncolor));
        textViewTodayFilter.setTextColor(getResources().getColor(R.color.white));
        textViewWeekFilter.setTextColor(getResources().getColor(R.color.filter_text_color));
        textViewMonthFilter.setTextColor(getResources().getColor(R.color.filter_text_color));
        textViewOthersFilter.setTextColor(getResources().getColor(R.color.filter_text_color));
        textViewWeekFilter.setBackgroundColor(getResources().getColor(R.color.filter_color));
        textViewMonthFilter.setBackgroundColor(getResources().getColor(R.color.filter_color));
        textViewOthersFilter.setBackgroundColor(getResources().getColor(R.color.filter_color));
        input_period_from.setText(Utility.getCurrentDate());
        input_period_to.setText(Utility.getCurrentDate());
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

    private void prepareMovieData() {
        SurveyReportModel movie = new SurveyReportModel("Target");
        surveyReportModelArrayList.add(movie);

        movie = new SurveyReportModel(getResources().getString(R.string.completed));
        surveyReportModelArrayList.add(movie);

        movie = new SurveyReportModel(getResources().getString(R.string.inprogress));
        surveyReportModelArrayList.add(movie);

        surveyStatusSelectionAdapter.notifyDataSetChanged();
    }


    public interface SetDataListListener {
        public void submitData(String statusString, String fromDateString, String toDateString, String type);
    }

}
