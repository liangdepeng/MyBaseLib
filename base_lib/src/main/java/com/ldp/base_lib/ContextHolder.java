package com.ldp.base_lib;

import android.annotation.SuppressLint;
import android.content.Context;


/**
 * 获取全局 上下文
 */
public class ContextHolder {

    private Context sContext = null;

    @SuppressLint("StaticFieldLeak")
    private static volatile ContextHolder instance;

    public static synchronized ContextHolder getInstance() {
        if (instance == null) {
            synchronized (ContextHolder.class) {
                if (instance == null) {
                    instance = new ContextHolder();
                }
            }
        }
        return instance;
    }

    public void init(Context ctx) {
        sContext = ctx;
    }

    public Context getContext() {
        return sContext;
    }

}
