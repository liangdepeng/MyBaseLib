package com.dpdp.base_moudle.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-01-10
 * <p>
 * Summary: 状态栏工具
 * <p>
 * api path:
 */
public class StatusBarUtil {

    public static int getHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static float dip2px(float dipValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, Resources.getSystem().getDisplayMetrics());
    }
}
