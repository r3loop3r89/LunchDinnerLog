package com.shra1.lunchdinnerlog;

import android.app.Application;

import com.shra1.lunchdinnerlog.room.AppDatabase;

public class App extends Application {

    public static AppDatabase adb = null;

    @Override
    public void onCreate() {
        super.onCreate();
        if (adb == null) {
            adb = AppDatabase.getInstance(this);
        }
    }

}
