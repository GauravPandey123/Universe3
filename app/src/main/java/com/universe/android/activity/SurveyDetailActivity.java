package com.universe.android.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.universe.android.R;
import com.universe.android.adapter.StatusAdapter;
import com.universe.android.adapter.SurveyDetailAdapter;
import com.universe.android.adapter.WorkFLowDetailAdapter;
import com.universe.android.helper.FontClass;

import com.universe.android.model.AnswersModal;
import com.universe.android.model.CustomerModal;
import com.universe.android.model.StatusModel;
import com.universe.android.realmbean.RealmAnswers;
import com.universe.android.realmbean.RealmCustomer;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by gaurav.pandey on 24-01-2018.
 */

public class SurveyDetailActivity extends BaseActivity {
    //decalre the Views here
    private RecyclerView recyclerViewSurveyDetail;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recylerViewStatus;
    private SwipeRefreshLayout swipeRefreshLayoutStatus;
    private ImageView imageViewBack, imageViewFilter;
    private FloatingActionMenu floatingActionMenu;

    private FloatingActionButton floatingCrystal, floatingRetailers;
    private TextView textViewToday, textViewtarget, textViewAchievement, textViewInProgress;
    private TextView textViewNewRetailers, textViewCrystalmembers, textViewCompletedQuestionaire;
    private TextView textViewPeriodFrom, textViewPeriodTo, textViewPeriodStatus, textViewReset, textViewApplyFilter;
    private EditText input_period_from, input_period_to, input_period_status;
    private TextView textViewTodayFilter, textViewWeekFilter, textViewMonthFilter, textViewOthersFilter, textViewFilter;
    private TextView textViewChooseStatus;
    private ImageView imageViewCloseStatus;
    private ImageView imageViewCancel;

    private Dialog dialogFilter, dialogStatus, dialogCalendra;
    //    private ArrayList<CustomerModal> stringArrayList;
    private LinearLayoutManager linearLayoutManager;
    private SurveyDetailAdapter surveyDetailAdapter;

    //declare the variables here
    private boolean isMultiSelect = false;
    ArrayList<StatusModel> statusList = new ArrayList<>();
    ArrayList<StatusModel> multiselectSatuslist = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private StatusAdapter statusAdapter;
    private Calendar mcalendar;
    private int day, month, year;
    private TextView textViewtargetCount, textViewCompletedCount, textViewAchievementPercentage, textViewInProgressCount;
    private TextView textViewNewRetailersCount, textViewCrystalMembersCount;
    private RelativeLayout relativelayoutTarget, relativeLayoutSubmitted, realtiveLayoutAchivement, relativelayoutInprogress;
    private RelativeLayout realativeNewRetailers, relativeLayoutCrystalCustomers;

    private ArrayList<CustomerModal> stringArrayList;
    private ArrayList<AnswersModal> answersModalArrayList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surveyreportfragment);
        initialization();
        setUpElements();
        setUpListeners();
        prepareList();
        setCounts();
    }

    private void setCounts() {
        Realm realm = Realm.getDefaultInstance();
        try {
            long realmPending = realm.where(RealmAnswers.class).equalTo(AppConstants.CD_STATUS, "1").equalTo(AppConstants.RM_STATUS, "0").equalTo(AppConstants.ZM_STATUS, "4").count();
            long realmInprogress = realm.where(RealmAnswers.class).equalTo(AppConstants.CD_STATUS, "1").equalTo(AppConstants.RM_STATUS, "2").equalTo(AppConstants.ZM_STATUS, "0").count();
            long realmCompleted = realm.where(RealmAnswers.class).equalTo(AppConstants.CD_STATUS, "2").equalTo(AppConstants.RM_STATUS, "2").equalTo(AppConstants.ZM_STATUS, "2").count();
            long realmRejected = realm.where(RealmAnswers.class).equalTo(AppConstants.CD_STATUS, "3").equalTo(AppConstants.RM_STATUS, "3").equalTo(AppConstants.ZM_STATUS, "3").count();
//            textViewInProgressCount.setText(realmPending + "");
            textViewInProgressCount.setText(realmInprogress + "");
            textViewCompletedCount.setText(realmCompleted + "");
//            tvRejected.setText(realmRejected + "");

        } catch (Exception e) {
            realm.close();
            e.printStackTrace();
        } finally {
            realm.close();
        }


    }

    private void setUpListeners() {
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageViewFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        floatingActionMenu.setClosedOnTouchOutside(true);
        floatingCrystal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, SearchCustomersActivity.class));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                floatingActionMenu.close(true);

            }
        });

        floatingRetailers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, SearchCustomersActivity.class));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                floatingActionMenu.close(true);

            }
        });
        relativelayoutTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        relativeLayoutSubmitted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        realtiveLayoutAchivement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        relativelayoutInprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        realativeNewRetailers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        relativeLayoutCrystalCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setUpElements() {
        surveyDetailAdapter = new SurveyDetailAdapter(mContext, stringArrayList);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewSurveyDetail.setLayoutManager(linearLayoutManager);
        recyclerViewSurveyDetail.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSurveyDetail.setAdapter(surveyDetailAdapter);
    }

    private void initialization() {
        stringArrayList = new ArrayList<>();
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerViewSurveyDetail = findViewById(R.id.recylerViewSurveyDetail);
        imageViewBack = findViewById(R.id.imageviewback);
        imageViewFilter = findViewById(R.id.imageviewfilter);
        textViewToday = findViewById(R.id.textViewToday);
        textViewtarget = findViewById(R.id.textViewtarget);
        textViewAchievement = findViewById(R.id.textViewAchievement);
        textViewInProgress = findViewById(R.id.textViewInProgress);
        textViewNewRetailers = findViewById(R.id.textViewNewRetailers);
        textViewCrystalmembers = findViewById(R.id.textViewCrystalmembers);
        textViewCompletedQuestionaire = findViewById(R.id.textViewCompletedQuestionaire);
        floatingActionMenu = findViewById(R.id.floating_action_menu_customers);
        floatingCrystal = findViewById(R.id.floatingCrystal);
        floatingRetailers = findViewById(R.id.floatingRetailers);
        textViewFilter = findViewById(R.id.textViewFilter);
        textViewtargetCount = findViewById(R.id.textViewtargetCount);
        textViewAchievementPercentage = findViewById(R.id.textViewAchievementPercentage);
        textViewCompletedCount = findViewById(R.id.textViewCompletedCount);
        textViewInProgressCount = findViewById(R.id.textViewInProgressCount);
        textViewNewRetailersCount = findViewById(R.id.textViewNewRetailersCount);
        textViewCrystalMembersCount = findViewById(R.id.textViewCrystalMembersCount);
        relativelayoutTarget = findViewById(R.id.relativelayoutTarget);
        relativeLayoutSubmitted = findViewById(R.id.relativeLayoutSubmitted);
        realtiveLayoutAchivement = findViewById(R.id.realtiveLayoutAchivement);
        relativelayoutInprogress = findViewById(R.id.relativelayoutInprogress);
        realativeNewRetailers = findViewById(R.id.realativeNewRetailers);
        relativeLayoutCrystalCustomers = findViewById(R.id.relativeLayoutCrystalCustomers);

        textViewToday.setTypeface(FontClass.openSemiBold(mContext));
        textViewtarget.setTypeface(FontClass.openSansRegular(mContext));
//        textViewAchievement.setTypeface(FontClass.openSansRegular(mContext));
//        textViewInProgress.setTypeface(FontClass.openSansRegular(mContext));
//        textViewNewRetailers.setTypeface(FontClass.openSansRegular(mContext));
//        textViewCrystalmembers.setTypeface(FontClass.openSansRegular(mContext));
//        textViewtargetCount.setTypeface(FontClass.openSansRegular(mContext));
//        textViewAchievementPercentage.setTypeface(FontClass.openSansRegular(mContext));
//        textViewCompletedCount.setTypeface(FontClass.openSansRegular(mContext));
//        textViewInProgressCount.setTypeface(FontClass.openSansRegular(mContext));
//        textViewNewRetailersCount.setTypeface(FontClass.openSansRegular(mContext));
//        textViewCrystalMembersCount.setTypeface(FontClass.openSansRegular(mContext));
//        textViewCompletedQuestionaire.setTypeface(FontClass.openSansRegular(mContext));
//        textViewFilter.setTypeface(FontClass.openSemiBold(mContext));

    }

    private void prepareList() {
        if (stringArrayList == null) stringArrayList = new ArrayList<>();
        stringArrayList.clear();
        Realm realm = Realm.getDefaultInstance();

        try {
            RealmResults<RealmCustomer> realmCustomers = realm.where(RealmCustomer.class).findAll();
            if (realmCustomers != null && realmCustomers.size() > 0) {
                for (int i = 0; i < realmCustomers.size(); i++) {
                    CustomerModal modal = new CustomerModal();
                    modal.setId(realmCustomers.get(i).get_id());
                    RealmAnswers realmAnswers1 = realm.where(RealmAnswers.class).equalTo(AppConstants.CUSTOMERID, realmCustomers.get(i).get_id()).findFirst();
                    if (realmAnswers1 != null) {
                        String status = realmAnswers1.getCd_Status();
                        modal.setStatus(status);
                    } else {
                        modal.setStatus("");
                    }
                    modal.setTitle(realmCustomers.get(i).getName());
                    modal.setState(realmCustomers.get(i).getState());
                    modal.setTerritory(realmCustomers.get(i).getTerritory());
                    modal.setPincode(realmCustomers.get(i).getPincode());
                    modal.setContactNo(realmCustomers.get(i).getContactNo());
                    //modal.setStatus(type);
                    modal.setDate(AppConstants.format2.format(realmCustomers.get(i).getCreatedAt()));
                    stringArrayList.add(modal);
                }
            }
        } catch (Exception e) {
            realm.close();
            e.printStackTrace();
        } finally {
            realm.close();
        }

        if (surveyDetailAdapter != null) {
            surveyDetailAdapter.notifyDataSetChanged();
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

    public void statusDialog() {
        dialogStatus = new Dialog(mContext);
        dialogStatus.setCanceledOnTouchOutside(true);
        dialogStatus.setContentView(R.layout.select_status_dialog);
        dialogStatusInitialization();
        dialogStatusSetUpElements();
        dialogStatus.show();
        showData();
    }

    private void showData() {
        String name[] = {"Target", "Completed", "Achievement", "InProgress", "New Retailers", "Crystal Members"};
        int imageId[] = {R.drawable.ic_target, R.drawable.ic_completed, R.drawable.ic_progress, R.drawable.ic_customer, R.drawable.ic_customer};
        for (int i = 0; i < name.length - 1; i++) {
            StatusModel mSample = new StatusModel(name[i], imageId[i]);
            statusList.add(mSample);
        }

    }

    private void dialogStatusSetUpElements() {
        statusAdapter = new StatusAdapter(this, statusList, multiselectSatuslist);
        mLayoutManager = new LinearLayoutManager(mContext);
        recylerViewStatus.setLayoutManager(mLayoutManager);
        recylerViewStatus.setItemAnimator(new DefaultItemAnimator());

        recylerViewStatus.setAdapter(statusAdapter);
//        recylerViewStatus.addOnItemTouchListener(new RecyclerItemClickListener(this, recylerViewStatus, new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                if (!isMultiSelect) {
//                    multiselect_list = new ArrayList<SampleModel>();
//                    isMultiSelect = true;
//
//                    if (mActionMode == null) {
//                        mActionMode = startActionMode(mActionModeCallback);
//                    }
//                }
//
//                multi_select(position);
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position) {
//
//
//            }
//        }));
    }

    private void dialogStatusInitialization() {
        textViewChooseStatus = dialogStatus.findViewById(R.id.textViewChooseStatus);
        imageViewCloseStatus = dialogStatus.findViewById(R.id.imageViewCloseStatus);
        recylerViewStatus = dialogStatus.findViewById(R.id.recylerViewStatus);
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

        input_period_status.setHint(R.string.status);
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
    }

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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
