package com.universe.android;

import android.content.Context;
import android.support.multidex.MultiDex;

import in.editsoft.api.util.App;
import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by gaurav.pandey on 22-01-2018.
 */

public class UniverseApplication extends App {
    private static Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(getString(R.string.app_name))
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }
    public static Context getContext() {
        return mContext;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}


