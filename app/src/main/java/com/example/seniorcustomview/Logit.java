package com.example.seniorcustomview;

import android.util.Log;

/**
 * Create By: ChenFuXu
 * DateTime: 2022/8/31 19:54
 **/
public  class Logit {
    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void d(String tag, String msg, Throwable throwable) {
        Log.d(tag, msg, throwable);
    }
}
