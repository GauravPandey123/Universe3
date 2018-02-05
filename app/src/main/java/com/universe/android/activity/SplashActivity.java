package com.universe.android.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.universe.android.R;
import com.universe.android.helper.FontClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav.pandey on 23-01-2018.
 */

public class SplashActivity extends BaseActivity {
    private Runnable mRunnable;
    private Handler mHandler = new Handler();
    private static int SPLASH_TIME_OUT = 1000;
    private TextView textViewUniverse;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialization();



        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissionList = new ArrayList<>();

            int permissionExternalRead = checkCallingOrSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permissionExternalRead != PackageManager.PERMISSION_GRANTED )
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);


            if (permissionList.size() > 0) {
                ActivityCompat.requestPermissions(SplashActivity.this, permissionList.toArray(new String[]{}), 1);
            } else {
                loadContent();
            }
        } else {
            loadContent();
        }

    }

    private void loadContent() {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(mContext, LoginActivity.class));
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        };
        mHandler.postDelayed(mRunnable, SPLASH_TIME_OUT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if ((grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        || checkCallingOrSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    loadContent();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.allow_permission), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void initialization() {
        textViewUniverse=findViewById(R.id.textViewUniverse);
        textViewUniverse.setTypeface(FontClass.openSansBold(mContext));
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_splash;
    }
}
