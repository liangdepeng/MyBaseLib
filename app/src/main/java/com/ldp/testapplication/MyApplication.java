package com.ldp.testapplication;

import android.app.Application;

import com.ldp.base_lib.ContextHolder;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/11/12
 * <p>
 * Summary:
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ContextHolder.getInstance().init(this);
    }
}
