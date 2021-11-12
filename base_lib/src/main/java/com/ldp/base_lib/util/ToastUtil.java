package com.ldp.base_lib.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ldp.base_lib.R;


/**
 * 弱提示
 */

public class ToastUtil {

    static Context appContext;
    static long firstClickTime = 0;
    static final long SHORT_DURATION_TIMEOUT = 4000;
    static final long LONG_DURATION_TIMEOUT = 7000;
    static Toast customToast;
    static TextView tipsTv;

    public static void init(Context appCtx) {
        appContext = appCtx;
    }

    public static void showMsg(CharSequence msg) {
        showMsg(null, msg);
    }

    @Deprecated
    public static void showMsg(@Nullable Context context, CharSequence msg) { //显示信息的方法，传参：界面信息，要显示的信息

        // 检测防止重复点击
//        long currentClickTime = SystemClock.elapsedRealtime();
//        if (currentClickTime - firstClickTime <= 500) {
//            firstClickTime = currentClickTime;
//            return;
//        }

        // 检测是否是主线程 UI线程 不是要到主线程更新UI
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (customToast == null) {
                        // 自定义 通用 toast
                        customToast = new Toast(appContext);
                        View v = layoutInflaterFrom(appContext).inflate(R.layout.base_toast_tip_layout, null);
                        tipsTv = v.findViewById(R.id.tip_tv);
                        customToast.setView(v);
                        customToast.setGravity(Gravity.CENTER, 0, 0);
                        customToast.setDuration(Toast.LENGTH_SHORT);
                    }
                    tipsTv.setText(msg);
                    customToast.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Android source
     * <p>
     * Obtains the LayoutInflater from the given context.
     */
    static LayoutInflater layoutInflaterFrom(Context context) {
        LayoutInflater LayoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (LayoutInflater == null) {
            throw new AssertionError("LayoutInflater not found.");
        }
        return LayoutInflater;
    }
}