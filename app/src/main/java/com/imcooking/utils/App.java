package com.imcooking.utils;

import android.app.Application;

import co.paystack.android.PaystackSdk;

/**
 * Created by Rakhi on 8/17/2018.
 */
public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        PaystackSdk.initialize(getApplicationContext());
    }
}
