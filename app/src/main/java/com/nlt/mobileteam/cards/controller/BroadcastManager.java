package com.nlt.mobileteam.cards.controller;

import android.content.Context;
import android.content.Intent;

import com.nlt.mobileteam.cards.App;

import java.io.Serializable;

/**
 * Created by Nick on 15.05.2016.
 */
public class BroadcastManager {
    private static BroadcastManager ourInstance = new BroadcastManager();

    public static BroadcastManager getInstance() {
        return ourInstance;
    }

    private Context context;


    public static final String EXTRA_DATA = "_EXTRA_DATA";
    public static final String EXTRA_STRING = "_EXTRA_STRING";
    public static final String EXTRA_INT = "_EXTRA_INT";

    private BroadcastManager() {
        context = App.getAppContext();
    }

    public void sendBroadcast(String action) {
        context.sendBroadcast(new Intent(action));
    }

    public void sendBroadcastWithString(String action, String data) {
        Intent intent = new Intent(action);
        intent.putExtra(EXTRA_DATA, data);
        context.sendBroadcast(intent);
    }

    public void sendBroadcastWithStringAndInt(String action, String data, int intData) {
        Intent intent = new Intent(action);
        intent.putExtra(EXTRA_STRING, data);
        intent.putExtra(EXTRA_INT, intData);
        context.sendBroadcast(intent);
    }

    public void sendBroadcastWithInt(String action, int data) {
        Intent intent = new Intent(action);
        intent.putExtra(EXTRA_DATA, data);
        context.sendBroadcast(intent);
    }

    public void sendBroadcastWithSerializable(String action, Serializable data) {
        Intent intent = new Intent(action);
        intent.putExtra(EXTRA_DATA, data);
        context.sendBroadcast(intent);
    }
}
