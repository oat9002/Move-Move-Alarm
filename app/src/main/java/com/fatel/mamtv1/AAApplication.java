package com.fatel.mamtv1;
import android.app.Application;
import com.activeandroid.ActiveAndroid;

/**
 * Created by Forunh on 02-Sep-16.
 */
public class AAApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}