package com.universe.android.parent;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;


import com.universe.android.R;

import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Utility;

import java.io.File;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;


public class ParentSaveActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public void setHeader() {
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

    public void setTodaysDate(TextView tvDate) {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        final String date1 = AppConstants.format2.format(date);
        tvDate.setText(date1);
    }


    public void showDialog(Activity activity, String msg, final boolean isBack) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog);
        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btnYes);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (isBack) {
                    finish();
                }
            }
        });

        dialog.show();

    }

    protected void showDatePicker(String formattedDate, final TextView tvDate, final boolean isFutureDate) {
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
                Calendar currentDate = Calendar.getInstance();
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                if (isFutureDate) {
                    if (newDate.getTimeInMillis() <= currentDate.getTimeInMillis()) {
                        tvDate.setText(AppConstants.format2.format(newDate.getTime()));
                    } else {
                        showToastMessage(getString(R.string.no_future_date));
                    }
                } else {
                    tvDate.setText(AppConstants.format2.format(newDate.getTime()));
                }


            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.YEAR, -2);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.HOUR_OF_DAY, 23);
        calendar2.set(Calendar.MINUTE, 59);
        calendar2.set(Calendar.SECOND, 59);
        if (dialog.getDatePicker() != null) {
            dialog.getDatePicker().setMinDate(calendar1.getTime().getTime() - 1000);
            if (isFutureDate) {
                dialog.getDatePicker().setMaxDate(calendar2.getTimeInMillis());
            }

        }
    }

    protected void showFollowUpDatePicker(String formattedDate, final TextView tvDate, final boolean isFutureDate, long campDate) {
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
                Calendar currentDate = Calendar.getInstance();
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                if (isFutureDate) {
                    if (newDate.getTimeInMillis() <= currentDate.getTimeInMillis()) {
                        tvDate.setText(AppConstants.format2.format(newDate.getTime()));
                    } else {
                        showToastMessage(getString(R.string.no_future_date));
                    }
                } else {
                    tvDate.setText(AppConstants.format2.format(newDate.getTime()));
                }

            }
        },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.YEAR, -2);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.HOUR_OF_DAY, 23);
        calendar2.set(Calendar.MINUTE, 59);
        calendar2.set(Calendar.SECOND, 59);
        if (dialog.getDatePicker() != null) {
            dialog.getDatePicker().setMinDate(campDate);
            if (isFutureDate) {
//                dialog.getDatePicker().setMaxDate(calendar2.getTimeInMillis());
            }
        }
    }


    protected void showToastMessage(String strMsg) {
        Utility.showToastMessage(this, strMsg);
    }


    @Override
    public void onBackPressed() {
        Utility.hideSoftKeyboard(ParentSaveActivity.this);
        //  showAlert();
        finish();
    }


}
