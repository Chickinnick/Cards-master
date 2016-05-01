package com.nlt.mobileteam.cards;

import android.app.Application;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;

/**
 * Created by Nick on 01.05.2016.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Hawk.init(this)
                .setStorage(HawkBuilder.newSharedPrefStorage(this))
                .setLogLevel(LogLevel.FULL)
                .build();
    }
}
