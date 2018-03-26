package com.universe.android.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.cocosw.bottomsheet.BottomSheet;
import com.soundcloud.android.crop.Crop;
import com.universe.android.R;
import com.universe.android.model.AnswersModal;
import com.universe.android.resource.Login.CutomerPictureChange.CustomerPictureRequest;
import com.universe.android.resource.Login.CutomerPictureChange.CustomerPictureResponse;
import com.universe.android.resource.Login.CutomerPictureChange.CustomerPictureService;
import com.universe.android.resource.Login.Profile.ImageUploadService;
import com.universe.android.resource.Login.Profile.ProfileRequest;
import com.universe.android.resource.Login.Profile.ProfileResponse;
import com.universe.android.resource.Login.SurveyDetails.SurverDetailResponse;
import com.universe.android.utility.AppConstants;
import com.universe.android.utility.Prefs;
import com.universe.android.utility.Utility;
import com.universe.android.web.BaseApiCallback;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import in.editsoft.api.exception.APIException;

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
    protected Activity mActivity;
    protected Fragment fragmentCurrent;

    private String mImageUrl;
    private boolean isUpdateImage = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questoionaire_header);
        mContext = this;
        mActivity = this;

    }


    public int setLayoutId() {
        return R.layout.questoionaire_header;
    }

    public void addFragment(Fragment fragment, int containerId) {
        fragmentCurrent = fragment;
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerId, fragment)
                .commitAllowingStateLoss();
    }

    public void replaceFragment(Fragment fragment, int containerId) {
        isReplaced = true;
        fragmentCurrent = fragment;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void showProgress() {
        showProgress(getStringRes(R.string.msg_load_default));
    }

    public void showProgress(String msg) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
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


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(mActivity.getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(mActivity);
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

                shareFile(false, filename, subject, text);

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

        File root = new File(FILE + "/" + fileName);
        Uri uri = Uri.fromFile(root);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
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
//                File root = new File(FILE + "/" + fileName);
//                Uri uri = Uri.fromFile(root);
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
            if (answersModal.getStatus() != null) {
                if (answersModal.getStatus().equalsIgnoreCase("0")) {
                    cell.setCellValue("Pending");
                } else if (answersModal.getStatus().equalsIgnoreCase("1")) {
                    cell.setCellValue("Submitted");
                } else if (answersModal.getStatus().equalsIgnoreCase("5")) {
                    cell.setCellValue("in Progress");
                }
            } else {

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


    /**
     * Create excel file of report
     *
     * @return isFileCreated
     */
    protected boolean createExcelFileTeamSurveyReport(List<String> headerList, List<SurverDetailResponse.ResponseBean.CrystaDoctorBean> snapshotList, String title, String filename, String subject, String text) {
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
            SurverDetailResponse.ResponseBean.CrystaDoctorBean answersModal = snapshotList.get(j);
            cell = row.createCell(idyc);
            cell.setCellValue(answersModal.getDetail().get_id());
            cell.setCellStyle(Utility.getContentCellStyle(workbook));
            cell = row.createCell(++idyc);
            cell.setCellValue(answersModal.getDetail().getName());
            cell.setCellStyle(Utility.getContentCellStyle(workbook));
            cell = row.createCell(++idyc);
            cell.setCellValue(answersModal.getTotalAssign());
            cell.setCellStyle(Utility.getContentCellStyle(workbook));
            cell = row.createCell(++idyc);
            cell.setCellValue(answersModal.getSubmittedCount());
            cell.setCellStyle(Utility.getContentCellStyle(workbook));
            cell = row.createCell(++idyc);
            String totalString = "" + answersModal.getTotalAssign();
            String completedString = "" + answersModal.getSubmittedCount();
            int n = Integer.parseInt(totalString);
            int v = Integer.parseInt(completedString);
            int percent = 0;
            try {
                percent = v * 100 / n;
            } catch (Exception e) {
                e.printStackTrace();
            }
            cell.setCellValue(String.valueOf(percent).concat("%"));
            cell.setCellStyle(Utility.getContentCellStyle(workbook));
            cell = row.createCell(++idyc);
            cell.setCellValue(answersModal.getProgress());
            cell.setCellStyle(Utility.getContentCellStyle(workbook));
            cell = row.createCell(++idyc);
            cell.setCellValue(answersModal.getRetailorCount());
            cell.setCellStyle(Utility.getContentCellStyle(workbook));
            cell = row.createCell(++idyc);
            cell.setCellValue(answersModal.getCrystalCustomer());


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
