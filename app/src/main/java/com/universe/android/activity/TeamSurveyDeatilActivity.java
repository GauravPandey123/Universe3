package com.universe.android.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
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

import com.google.gson.Gson;
import com.universe.android.R;
import com.universe.android.adapter.CrystalDoctorAdapter;
import com.universe.android.adapter.StatusAdapter;

import com.universe.android.adapter.TeamSurveyAdapter;
import com.universe.android.fragment.SurveyDetailDialogFragment;
import com.universe.android.fragment.TeamSurveyDialogFragment;
import com.universe.android.helper.FontClass;
import com.universe.android.helper.RecyclerTouchListener;
import com.universe.android.model.AnswersModal;
import com.universe.android.model.DataModel;
import com.universe.android.model.StatusModel;
import com.universe.android.resource.Login.SurveyDetails.SurverDetailResponse;
import com.universe.android.resource.Login.SurveyDetails.SurveyDeatailRequest;
import com.universe.android.resource.Login.SurveyDetails.SurveyDetailService;
import com.universe.android.resource.Login.TeamSurveyFIlter.TeamSurveyFilterRequest;
import com.universe.android.resource.Login.login.LoginRequest;
import com.universe.android.resource.Login.login.LoginResponse;
import com.universe.android.resource.Login.login.LoginService;
import com.universe.android.resource.Login.survey.SurveyResponse;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;
import com.universe.android.web.BaseApiCallback;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import in.editsoft.api.exception.APIException;
import in.editsoft.api.util.App;

/**
 * Created by gaurav.pandey on 12-02-2018.
 */

public class TeamSurveyDeatilActivity extends BaseActivity implements TeamSurveyDialogFragment.TeamSurveyData {

    private CardView carView;
    private RecyclerView reyclerViewCategory;
    private ImageView imageViewBack;
    private TextView textViewtargetCount, textViewCompletedCount, textViewAchievementPercentage, textViewInProgressCount, textViewNewRetailersCount, textViewCrystalMembersCount;
    int percent;
    private ImageView imageViewCloseStatus;
    private ImageView imageviewfilter;

    private ArrayList<SurverDetailResponse.ResponseBean.CrystaDoctorBean> surveyDetailsBeanArrayList;
    private TeamSurveyAdapter teamSurveyAdapter;

    FragmentManager fm = getSupportFragmentManager();
    String teamId;
    String fromDateString, toDateString;
    Date fromDateTime = null;
    Date toDates = null;
    RelativeLayout relativeLayout;
    private RelativeLayout linearLayoutSurvey;
    private ArrayList<String> headerList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_survey_activity);
        initialization();
        setUpElements();
    }

    private void setUpElements() {
        carView.setVisibility(View.GONE);
        surveyDetailsBeanArrayList = new ArrayList<>();
        reyclerViewCategory.setLayoutManager(new LinearLayoutManager(mContext));
        teamSurveyAdapter = new TeamSurveyAdapter(mContext, surveyDetailsBeanArrayList);
        reyclerViewCategory.setAdapter(teamSurveyAdapter);
        setWebService();

        reyclerViewCategory.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), reyclerViewCategory, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

//        relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Utility.animateView(view);
//                if (isStoragePermissionGranted()) {
//                    String title = ((TextView) findViewById(R.id.textViewSurveyDetailActivity)).getText().toString();
//                    createExcelFileReport(headerList, surveyDetailsBeanArrayList, title.replace(" ", "_"), title.replace(" ", "_") + ".xls", title, getResources().getString(R.string.sharetitle) + " of " + title + "\n\n" + getResources().getString(R.string.thankyou));
//                } else {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
//                    }
//                }
//            }
//        });
//    }

    private void initialization() {
        carView = findViewById(R.id.carView);
        reyclerViewCategory = findViewById(R.id.reyclerViewCategory);
        imageViewBack = findViewById(R.id.imageviewback);
        imageviewfilter = findViewById(R.id.imageviewfilter);
        textViewtargetCount = findViewById(R.id.textViewtargetCount);
        textViewCompletedCount = findViewById(R.id.textViewCompletedCount);
        textViewAchievementPercentage = findViewById(R.id.textViewAchievementPercentage);
        textViewInProgressCount = findViewById(R.id.textViewInProgressCount);
        textViewNewRetailersCount = findViewById(R.id.textViewNewRetailersCount);
        textViewCrystalMembersCount = findViewById(R.id.textViewCrystalMembersCount);
        linearLayoutSurvey=findViewById(R.id.linearLayoutSurvey);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageviewfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamSurveyDialogFragment dFragment = new TeamSurveyDialogFragment();
                dFragment.show(fm, "Dialog Fragment");
            }
        });


    }


//    /**
//     * Create excel file of report
//     *
//     * @return isFileCreated
//     */
//    protected boolean createExcelFileReport(List<String> headerList, List<SurverDetailResponse.ResponseBean.CrystaDoctorBean> snapshotList, String title, String filename, String subject, String text) {
//        if (!Utility.isExternalStorageAvailable() || Utility.isExternalStorageReadOnly()) {
//            return false;
//        }
//        boolean success = false;
//
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFSheet sheet = null;
//        if (title != null) {
//            sheet = workbook.createSheet(subject);
//        }
//        int idxr = 0;
//
//        Cell cell;
//        Row row = sheet.createRow(idxr);
//
//        cell = row.createCell(1);
//        cell.setCellValue(subject);
//        cell.setCellStyle(Utility.getHeaderCellStyle(workbook));
//
//        idxr = +2;
//
//        row = sheet.createRow(idxr);
//        // Generate column headings
//        for (int i = 0; i < headerList.size(); i++) {
//            cell = row.createCell(i);
//            cell.setCellValue(headerList.get(i));
//            cell.setCellStyle(Utility.getColumnHeaderCellStyle(workbook));
//            sheet.setColumnWidth(i, (10 * 900));
//
//        }
//        idxr = idxr + 1;
//        for (int j = 0; j < snapshotList.size(); j++) {
//            int idyc = 0;
//            row = sheet.createRow(idxr);
//            AnswersModal answersModal = snapshotList.get(j);
//            cell = row.createCell(idyc);
//            cell.setCellValue(answersModal.get_id());
//            cell.setCellStyle(Utility.getContentCellStyle(workbook));
//            cell = row.createCell(++idyc);
//            cell.setCellValue(answersModal.getTitle());
//            cell.setCellStyle(Utility.getContentCellStyle(workbook));
//            cell = row.createCell(++idyc);
//            cell.setCellValue(answersModal.getTerritory());
//            cell.setCellStyle(Utility.getContentCellStyle(workbook));
//            cell = row.createCell(++idyc);
//            cell.setCellValue(answersModal.getState());
//            cell.setCellStyle(Utility.getContentCellStyle(workbook));
//            cell = row.createCell(++idyc);
//            cell.setCellValue(answersModal.getPincode());
//            cell.setCellStyle(Utility.getContentCellStyle(workbook));
//            cell = row.createCell(++idyc);
//            cell.setCellValue(answersModal.getContactNo());
//            cell.setCellStyle(Utility.getContentCellStyle(workbook));
//            cell = row.createCell(++idyc);
//            cell.setCellValue(answersModal.getDate());
//            cell.setCellStyle(Utility.getContentCellStyle(workbook));
//            cell = row.createCell(++idyc);
//            if (answersModal.getStatus() != null) {
//                if (answersModal.getStatus().equalsIgnoreCase("0")) {
//                    cell.setCellValue("Pending");
//                } else if (answersModal.getStatus().equalsIgnoreCase("1")) {
//                    cell.setCellValue("Submitted");
//                } else if (answersModal.getStatus().equalsIgnoreCase("5")) {
//                    cell.setCellValue("in Progress");
//                }
//            }else
//            {
//
//            }
//
//            cell.setCellStyle(Utility.getContentCellStyle(workbook));
//
//
//            idxr++;
//        }
//
//        success = Utility.writeExcelFile(workbook, filename);
//        if (!success) {
//            Utility.showToast(getString(R.string.error_file_downloading));
//        } else {
//            showProgressBar(filename, subject, text);
//        }
//
//
//        return success;
//    }

    private void setWebService() {
        if (!Utility.isConnected()) {
            Utility.showToast(R.string.msg_disconnected);
        } else {
            teamSurveyDetail();
        }
    }

    public void teamSurveyDetail() {
        showProgress();
        SurveyDeatailRequest surveyDeatailRequest = new SurveyDeatailRequest();
        surveyDeatailRequest.setSurveyId(Prefs.getStringPrefs(AppConstants.TeamSurveyId));
        surveyDeatailRequest.setEmployee_code("10000297");
        surveyDeatailRequest.setType("rsm");
        SurveyDetailService surveyDetailService = new SurveyDetailService();
        surveyDetailService.executeService(surveyDeatailRequest, new BaseApiCallback<SurverDetailResponse>() {
            @Override
            public void onComplete() {
                dismissProgress();
            }

            @Override
            public void onSuccess(@NonNull SurverDetailResponse response) {
                super.onSuccess(response);
                SurverDetailResponse.ResponseBean responseBeans = response.getResponse();

                String totalString = String.valueOf(responseBeans.getTarget());
                String completedString = String.valueOf(responseBeans.getSubmitted());
                int n = Integer.parseInt(totalString);
                int v = Integer.parseInt(completedString);
                try {
                    percent = v * 100 / n;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                textViewtargetCount.setText(totalString);
                textViewCompletedCount.setText(completedString);
                textViewInProgressCount.setText(String.valueOf(responseBeans.getInprogress()));
                textViewNewRetailersCount.setText(String.valueOf(responseBeans.getNewRetailer()));
                textViewCrystalMembersCount.setText(String.valueOf(responseBeans.getCrystalCustomer()));
                textViewAchievementPercentage.setText(String.valueOf(percent).concat("%"));
                List<SurverDetailResponse.ResponseBean.CrystaDoctorBean> crystaDoctorBeans = responseBeans.getCrystaDoctor();
                String value = new Gson().toJson(crystaDoctorBeans);
                SurverDetailResponse.ResponseBean.CrystaDoctorBean[] surveyDetailsBeans = new Gson().fromJson(value, SurverDetailResponse.ResponseBean.CrystaDoctorBean[].class);
                Collections.addAll(surveyDetailsBeanArrayList, surveyDetailsBeans);
                teamSurveyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
            }
        });

    }

    private void setFilter(String type, String id, String fromate, String toDate) {
        SurveyDeatailRequest teamSurveyFilterRequest = new SurveyDeatailRequest();
        teamSurveyFilterRequest.setSurveyId((Prefs.getStringPrefs(AppConstants.TeamSurveyId)));
        teamSurveyFilterRequest.setEmployee_code(Prefs.getStringPrefs(AppConstants.employee_code));
        teamSurveyFilterRequest.setType("rsm");
        teamSurveyFilterRequest.setFilter("filter");
        teamSurveyFilterRequest.setEmployeeId(id);
        teamSurveyFilterRequest.setStartDate(fromate);
        teamSurveyFilterRequest.setEndDate(toDate);
        SurveyDetailService surveyDetailService = new SurveyDetailService();
        surveyDetailService.executeService(teamSurveyFilterRequest, new BaseApiCallback<SurverDetailResponse>() {
            @Override
            public void onComplete() {
                dismissProgress();
            }

            @Override
            public void onSuccess(@NonNull SurverDetailResponse response) {
                super.onSuccess(response);
                SurverDetailResponse.ResponseBean responseBeans = response.getResponse();
                surveyDetailsBeanArrayList.clear();
                String totalString = String.valueOf(responseBeans.getTarget());
                String completedString = String.valueOf(responseBeans.getSubmitted());
                int n = Integer.parseInt(totalString);
                int v = Integer.parseInt(completedString);
                try {
                    percent = v * 100 / n;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                textViewtargetCount.setText(totalString);
                textViewCompletedCount.setText(completedString);
                textViewInProgressCount.setText(String.valueOf(responseBeans.getInprogress()));
                textViewNewRetailersCount.setText(String.valueOf(responseBeans.getNewRetailer()));
                textViewCrystalMembersCount.setText(String.valueOf(responseBeans.getCrystalCustomer()));
                textViewAchievementPercentage.setText(String.valueOf(percent).concat("%"));

                List<SurverDetailResponse.ResponseBean.CrystaDoctorBean> crystaDoctorBeans = responseBeans.getCrystaDoctor();
                String value = new Gson().toJson(crystaDoctorBeans);
                SurverDetailResponse.ResponseBean.CrystaDoctorBean[] surveyDetailsBeans = new Gson().fromJson(value, SurverDetailResponse.ResponseBean.CrystaDoctorBean[].class);
                Collections.addAll(surveyDetailsBeanArrayList, surveyDetailsBeans);
                teamSurveyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(APIException e) {
                super.onFailure(e);
            }
        });

    }


    @Override
    public void submitTeamSurveyData(String id, String fromDate, String toDate) {
        teamId = id;
        fromDateString = fromDate;
        toDateString = toDate;
        Date date = null;
        Date toDateTime = null;
        try {
            date = AppConstants.format2.parse(fromDate);
            fromDateTime = AppConstants.format3.parse(AppConstants.format3.format(date));
            fromDateTime = AppConstants.format2.parse(toDate);
            toDateTime = AppConstants.format3.parse(AppConstants.format3.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(toDateTime);
        cal.add(Calendar.DATE, 1);
        toDates = cal.getTime();
        setFilter("rsm", teamId, fromDateString, toDateString);
    }
}
