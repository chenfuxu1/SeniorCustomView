package com.example.seniorcustomview;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/18 14:58
 **/
public class CustomApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context sContext = null;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }
}
