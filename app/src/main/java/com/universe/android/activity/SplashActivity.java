package com.universe.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.universe.android.R;
import com.universe.android.helper.FontClass;

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
        mRunnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(mContext, MainActivity.class));
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        };
        mHandler.postDelayed(mRunnable, SPLASH_TIME_OUT);
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
