package com.universe.android.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.adapter.CrystalDoctorAdapter;
import com.universe.android.adapter.StatusAdapter;

import com.universe.android.helper.FontClass;
import com.universe.android.helper.RecyclerTouchListener;
import com.universe.android.model.DataModel;
import com.universe.android.model.StatusModel;
import com.universe.android.utility.Utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by gaurav.pandey on 12-02-2018.
 */

public class TeamSurveyDeatilActivity extends BaseActivity {

    private CardView carView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView reyclerViewCategory;
    private ImageView imageViewBack;
    private Dialog dialogFilter, dialogStatus, dialogCalendra;

    private EditText input_period_from, input_period_to, input_period_status;
    private TextView textViewTodayFilter, textViewWeekFilter, textViewMonthFilter, textViewOthersFilter, textViewFilter;
    private TextView textViewChooseStatus;
    private ImageView imageViewCloseStatus;
    private ImageView imageViewCancel, imageviewfilter;
    private StatusAdapter statusAdapter;
    private LinearLayoutManager mLayoutManager;
    private RelativeLayout relativeLayoutTeamSubmit;

    private ArrayList<StatusModel> statusList = new ArrayList<>();
    private ArrayList<StatusModel> multiselectSatuslist = new ArrayList<>();
    private ArrayList<Number> numberArrayList = new ArrayList<>();

    private RecyclerView recylerViewStatus;
    private TextView textViewPeriodFrom, textViewPeriodTo, textViewPeriodStatus, textViewReset, textViewApplyFilter;

    private List<DataModel> allSampleData;
    private CrystalDoctorAdapter crystalDoctorAdapter;
//    private TeamSelectionAdapter teamSelectionAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_survey_activity);
        initialization();
        setUpElements();
    }

    private void setUpElements() {
        carView.setVisibility(View.GONE);
        populateSampleData();
        reyclerViewCategory.setLayoutManager(new LinearLayoutManager(mContext));
        crystalDoctorAdapter = new CrystalDoctorAdapter(allSampleData, mContext);
        reyclerViewCategory.setAdapter(crystalDoctorAdapter);

        reyclerViewCategory.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), reyclerViewCategory, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(mContext, TeamSurveyDetailReport.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }

    private void initialization() {
        allSampleData = new ArrayList<DataModel>();
        carView = findViewById(R.id.carView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayoutCategory);
        reyclerViewCategory = findViewById(R.id.reyclerViewCategory);
        imageViewBack = findViewById(R.id.imageviewback);
        imageviewfilter = findViewById(R.id.imageviewfilter);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageviewfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });


    }

    private void populateSampleData() {
        for (int i = 1; i <= 1; i++) {
            DataModel dm = new DataModel();
            dm.setHeaderTitle("Parth Rawal");
            ArrayList<String> singleItem = new ArrayList<>();
            for (int j = 1; j <= 1; j++) {
                singleItem.add("10 ");
            }
            dm.setAllItemsInSection(singleItem);
            allSampleData.add(dm);
        }
    }

    public void showDialog() {
        // Create custom dialog object
        dialogFilter = new Dialog(mContext);
        // Include dialog.xml file
        dialogFilter.setContentView(R.layout.filter_dialog);
        // Set dialog title
        dialoginitialization();
        dialogSetUpElements();
        dialogFilter.show();


    }

    private void dialogStatusSetUpElements() {
//        teamSelectionAdapter = new TeamSelectionAdapter(numberArrayList, mContext);
//        mLayoutManager = new LinearLayoutManager(mContext);
//        recylerViewStatus.setLayoutManager(mLayoutManager);
//        recylerViewStatus.setItemAnimator(new DefaultItemAnimator());
//        recylerViewStatus.setAdapter(teamSelectionAdapter);
//        relativeLayoutTeamSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                StringBuilder stringBuilder = new StringBuilder();
//                for (Number number : numberArrayList) {
//                    if (number.isSelected()) {
//                        if (stringBuilder.length() > 0)
//                            stringBuilder.append(", ");
//                        stringBuilder.append(number.getTextONEs());
//                    }
//                }
//                input_period_status.setText(stringBuilder.toString());
//                dialogStatus.dismiss();
//            }
//        });
//
//        imageViewCloseStatus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialogStatus.dismiss();
//            }
//        });


    }

    private void dialogStatusInitialization() {
        textViewChooseStatus = dialogStatus.findViewById(R.id.textViewChooseStatus);
        imageViewCloseStatus = dialogStatus.findViewById(R.id.imageViewCloseStatus);
        recylerViewStatus = dialogStatus.findViewById(R.id.recylerViewStatus);
//        relativeLayoutTeamSubmit = dialogStatus.findViewById(R.id.relativeLayoutTeamSubmit);
    }

    private void dialogSetUpElements() {
        imageViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFilter.dismiss();
            }
        });
    }

    private void dialoginitialization() {
        dialogFilter.setCanceledOnTouchOutside(true);
        textViewPeriodFrom = dialogFilter.findViewById(R.id.textViewPeriodFrom);
        textViewPeriodTo = dialogFilter.findViewById(R.id.textViewPeriodTo);
        textViewPeriodStatus = dialogFilter.findViewById(R.id.textViewPeriodStatus);
        textViewReset = dialogFilter.findViewById(R.id.textViewReset);
        textViewApplyFilter = dialogFilter.findViewById(R.id.textViewApplyFilter);
        input_period_from = dialogFilter.findViewById(R.id.input_period_from);
        input_period_to = dialogFilter.findViewById(R.id.input_period_to);
        input_period_status = dialogFilter.findViewById(R.id.input_period_status);
        imageViewCancel = dialogFilter.findViewById(R.id.imageViewClose);
        textViewTodayFilter = dialogFilter.findViewById(R.id.textViewTodayFilter);
        textViewWeekFilter = dialogFilter.findViewById(R.id.textViewWeekFilter);
        textViewMonthFilter = dialogFilter.findViewById(R.id.textViewMonthFilter);
        textViewOthersFilter = dialogFilter.findViewById(R.id.textViewOthersFilter);
        input_period_status.setHint(R.string.hintTeam);

        textViewTodayFilter.setTypeface(FontClass.openSansRegular(mContext));
        textViewWeekFilter.setTypeface(FontClass.openSansRegular(mContext));
        textViewMonthFilter.setTypeface(FontClass.openSansRegular(mContext));
        textViewOthersFilter.setTypeface(FontClass.openSansRegular(mContext));
        textViewPeriodFrom.setTypeface(FontClass.openSansRegular(mContext));
        textViewPeriodTo.setTypeface(FontClass.openSansRegular(mContext));
        textViewPeriodStatus.setTypeface(FontClass.openSansRegular(mContext));
        textViewReset.setTypeface(FontClass.openSansRegular(mContext));
        textViewApplyFilter.setTypeface(FontClass.openSansLight(mContext));
        input_period_from.setTypeface(FontClass.openSansLight(mContext));
        input_period_to.setTypeface(FontClass.openSansLight(mContext));
        input_period_status.setTypeface(FontClass.openSansLight(mContext));

        textViewTodayFilter.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        textViewWeekFilter.setBackgroundColor(getResources().getColor(R.color.transparent_color));
        textViewMonthFilter.setBackgroundColor(getResources().getColor(R.color.transparent_color));
        textViewOthersFilter.setBackgroundColor(getResources().getColor(R.color.transparent_color));
        input_period_from.setText(Utility.getCurrentDate());
        input_period_to.setText(Utility.getCurrentDate());

        dialogClickListeners();
    }

    private void dialogClickListeners() {
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

        textViewReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_period_from.setText("");
                input_period_to.setText(" ");
                input_period_status.setText(" ");
            }
        });

        textViewApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFilter.dismiss();
            }
        });

    }

    public void statusDialog() {
        dialogStatus = new Dialog(mContext);
        dialogStatus.setCanceledOnTouchOutside(true);
        dialogStatus.setContentView(R.layout.select_status_dialog);
        dialogStatusInitialization();
        dialogStatusSetUpElements();
        dialogStatus.show();
        //showData();
    }

//    private void showData() {
//        String ONEs[] = {"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN",
//                "ELEVEN", "TWELVE", "THIRTEEN", "FOURTEEN", "FIFTEEN", "SIXTEEN", "SEVENTEEN", "EIGHTEEN", "NINETEEN", "TWENTY"};
//        for (int i = 0; i <= 20; i++) {
//            Number number = new Number();
//            number.setONEs(i + "");
//            number.setTextONEs(ONEs[i]);
//            this.numberArrayList.add(number);
//        }
//
//    }

    private void showWeekCalendra() {
    }

    private void dialogCalendraSetUpElements() {
    }

    private void dialogCalerndraInitialization() {
    }

    public void dateDialogfrom() {
        final Calendar c = Calendar.getInstance();

        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dp = new DatePickerDialog(mContext,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String erg = "";
                        erg = String.valueOf(dayOfMonth);
                        erg += "-" + String.valueOf(monthOfYear + 1);
                        erg += "-" + year;
                        input_period_from.setText(erg);

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

        DatePickerDialog dp = new DatePickerDialog(mContext,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String erg = "";
                        erg = String.valueOf(dayOfMonth);
                        erg += "-" + String.valueOf(monthOfYear + 1);
                        erg += "-" + year;
                        input_period_to.setText(erg);
                    }

                }, y, m, d);
        dp.setTitle("Calender");
        dp.show();

    }

}
