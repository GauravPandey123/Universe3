package com.universe.android.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;


import com.cocosw.bottomsheet.BottomSheet;
import com.universe.android.R;
import com.universe.android.utility.Utility;

import static com.universe.android.utility.Utility.getStringRes;


/**
 * Created by gaurav.pandey on 02-01-2018.
 */

public class BaseActivity extends AppCompatActivity {

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

}
