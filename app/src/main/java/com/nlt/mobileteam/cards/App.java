package com.nlt.mobileteam.cards;

import android.app.Application;
import android.content.Context;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;

import io.paperdb.Paper;

/**
 * Created by Nick on 01.05.2016.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        Hawk.init(this)
                .setStorage(HawkBuilder.newSharedPrefStorage(this))
                .setLogLevel(LogLevel.FULL)
                .build();
        Paper.init(this);
    }

    private static Context appContext;


    public static Context getAppContext() {
        return appContext;
    }
}
