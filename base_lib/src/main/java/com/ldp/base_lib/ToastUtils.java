package com.ldp.base_lib;

import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/11/12
 * <p>
 * Summary: 提示 消息
 */
public class ToastUtils {

    public static void show(String message) {
        if (TextUtils.isEmpty(message))
            return;
        Toast.makeText(ContextHolder.getInstance().getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
