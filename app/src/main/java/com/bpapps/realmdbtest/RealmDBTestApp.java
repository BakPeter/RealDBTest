package com.bpapps.realmdbtest;

import android.app.Application;
import android.content.Context;

import com.bpapps.realmdbtest.repository.localdata.RealmDBHandler;

public class RealmDBTestApp extends Application {
    private static final String TAG = "TAG.RealmDBTestApp";


    private static Application sInstance = null;

    public static Context getAppContext() throws IllegalStateException {
        if (sInstance == null) {
            throw new IllegalStateException("RealmDBTestApp noy initialized yet!");
        }

        return sInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
