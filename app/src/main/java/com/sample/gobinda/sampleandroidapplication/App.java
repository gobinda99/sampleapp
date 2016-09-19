package com.sample.gobinda.sampleandroidapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDexApplication;


import timber.log.Timber;

public class App extends MultiDexApplication {
    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static SharedPreferences getPrefs() {
        return sContext.getSharedPreferences("sample", 0);

    }
}
