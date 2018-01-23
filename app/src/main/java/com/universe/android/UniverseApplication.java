package com.universe.android;

import android.content.Context;

import in.editsoft.api.util.App;

/**
 * Created by gaurav.pandey on 22-01-2018.
 */

public class UniverseApplication extends App {
    private static Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
    public static Context getContext() {
        return mContext;
    }

}


