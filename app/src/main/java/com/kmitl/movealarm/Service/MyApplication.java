package com.kmitl.movealarm.Service;

import android.app.Application;
import android.content.Context;

/**
 * Created by Forunh on 27-Sep-16.
 */
public class MyApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
