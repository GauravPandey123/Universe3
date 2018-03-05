package com.universe.android.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;


import com.cocosw.bottomsheet.BottomSheet;
import com.universe.android.R;
import com.universe.android.model.AnswersModal;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Utility;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.universe.android.utility.Utility.getStringRes;


/**
 * Created by gaurav.pandey on 02-01-2018.
 */

public class BaseActivity extends AppCompatActivity {
    protected static File folder = new File(Environment.getExternalStorageDirectory().toString() + AppConstants.DIR_UNIVERSE);
    //Save the path as a string value
    protected static String FILE = folder.toString();
    protected Context mContext;
    private ProgressDialog mProgressDialog;
    public boolean isReplaced = false;
    public int CAMERA_REQUEST = 2121;
    public int GALLERY_REQUEST = 2221;
    private Activity mActivity;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        mContext = this;
        mActivity=this;
    }

    public int setLayoutId() {
        return R.layout.activity_main;
    }

    public void addFragment(Fragment fragment, int containerId) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerId, fragment)
                .commitAllowingStateLoss();
    }

    public void replaceFragment(Fragment fragment, int containerId) {
        isReplaced = true;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment)
                .commitAllowingStateLoss();
    }

    public void showProgress() {
        showProgress(getStringRes(R.string.msg_load_default));
    }

    public void showProgress(String msg) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            //  dismissProgress();
            mProgressDialog.setMessage(msg);
        } else {


        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }
    }

    public void showProgress(int msgId) {
        String message = getStringRes(msgId);
        showProgress(message);
    }

    public void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }


    public void showImageOptions() {
        String title = Utility.getStringRes(R.string.dialog_image_title);
        new BottomSheet.Builder(mActivity)
                .title(Html.fromHtml("<font color=#4A4E55>" + title + "</font>"))
                .sheet(R.menu.image_options)
                .listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case R.id.image_camera:
                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                                break;
                            case R.id.image_gallery:
                                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                                startActivityForResult(i, GALLERY_REQUEST);
                                break;
                        }
                    }
                }).show();
    }
    protected boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }
    /**
     * Show progress bar to download the file
     */
    protected void showProgressBar(final String filename, final String subject, final String text) {
        final ProgressDialog progDailog = new ProgressDialog(BaseActivity.this);
        progDailog.setIndeterminate(false);
        progDailog.setMessage(getString(R.string.file_downloading));
        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDailog.setCancelable(true);
        progDailog.show();
        Handler progressBarbHandler = new Handler();
        progressBarbHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                shareFile(true, filename, subject, text);

                progDailog.dismiss();
            }
        }, 2000);
    }


    public void shareFile(boolean isImage, String fileName, String subject, String text) {
        List<Intent> targetedShareIntents = new ArrayList<>();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        if (isImage) {
            shareIntent.setType(AppConstants.PNG_FILE_TYPE);
        } else {
            shareIntent.setType(AppConstants.EXCEL_FILE_TYPE);
        }
        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(shareIntent, 0);

        if (!resInfo.isEmpty()) {
            for (ResolveInfo resolveInfo : resInfo) {
                String packageName = resolveInfo.activityInfo.packageName;
                Intent targetedShareIntent = new Intent(Intent.ACTION_SEND);
                if (isImage) {
                    targetedShareIntent.setType(AppConstants.PNG_FILE_TYPE);
                } else {
                    targetedShareIntent.setType(AppConstants.EXCEL_FILE_TYPE);
                }
                File root = new File(FILE + "/" + fileName);
                Uri uri = Uri.fromFile(root);
                targetedShareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                targetedShareIntent.putExtra(Intent.EXTRA_TEXT, text);
                targetedShareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                targetedShareIntent.setPackage(packageName);
                targetedShareIntents.add(targetedShareIntent);
            }
            Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), getString(R.string.select_app_to_share));
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.
                    toArray(new Parcelable[]{}));
            startActivity(chooserIntent);
        }
    }


    /**
     * Create excel file of report
     *
     * @return isFileCreated
     */
    protected boolean createExcelFileReport(List<String> headerList, List<AnswersModal> snapshotList, String title, String filename, String subject, String text) {
        if (!Utility.isExternalStorageAvailable() || Utility.isExternalStorageReadOnly()) {
            return false;
        }
        boolean success = false;

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = null;
        if (title != null) {
            sheet = workbook.createSheet(subject);
        }
        int idxr = 0;

        Cell cell;
        Row row = sheet.createRow(idxr);

        cell = row.createCell(1);
        cell.setCellValue(subject);
        cell.setCellStyle(Utility.getHeaderCellStyle(workbook));

        idxr = +2;

        row = sheet.createRow(idxr);
        // Generate column headings
        for (int i = 0; i < headerList.size(); i++) {
            cell = row.createCell(i);
            cell.setCellValue(headerList.get(i));
            cell.setCellStyle(Utility.getColumnHeaderCellStyle(workbook));
            sheet.setColumnWidth(i, (10 * 900));

        }
        idxr = idxr + 1;
        for (int j = 0; j < snapshotList.size(); j++) {
            int idyc = 0;
            row = sheet.createRow(idxr);
            AnswersModal answersModal = snapshotList.get(j);
            cell = row.createCell(idyc);
            cell.setCellValue(answersModal.get_id());
            cell.setCellStyle(Utility.getContentCellStyle(workbook));
            cell = row.createCell(++idyc);
            cell.setCellValue(answersModal.getTitle());
            cell.setCellStyle(Utility.getContentCellStyle(workbook));
            cell = row.createCell(++idyc);
            cell.setCellValue(answersModal.getTerritory());
            cell.setCellStyle(Utility.getContentCellStyle(workbook));
            cell = row.createCell(++idyc);
            cell.setCellValue(answersModal.getState());
            cell.setCellStyle(Utility.getContentCellStyle(workbook));
            cell = row.createCell(++idyc);
            cell.setCellValue(answersModal.getPincode());
            cell.setCellStyle(Utility.getContentCellStyle(workbook));
            cell = row.createCell(++idyc);
            cell.setCellValue(answersModal.getContactNo());
            cell.setCellStyle(Utility.getContentCellStyle(workbook));
            cell = row.createCell(++idyc);
            cell.setCellValue(answersModal.getDate());
            cell.setCellStyle(Utility.getContentCellStyle(workbook));
            cell = row.createCell(++idyc);
            if (answersModal.getStatus().equalsIgnoreCase("0")){
                cell.setCellValue("Pending");
            }else   if (answersModal.getStatus().equalsIgnoreCase("1")){
                cell.setCellValue("Submitted");
            }else   if (answersModal.getStatus().equalsIgnoreCase("5")){
                cell.setCellValue("in Progress");
            }

            cell.setCellStyle(Utility.getContentCellStyle(workbook));


            idxr++;
        }

        success = Utility.writeExcelFile(workbook, filename);
        if (!success) {
            Utility.showToast(getString(R.string.error_file_downloading));
        } else {
            showProgressBar(filename, subject, text);
        }


        return success;
    }
}
