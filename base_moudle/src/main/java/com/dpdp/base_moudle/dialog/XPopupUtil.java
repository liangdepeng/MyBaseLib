package com.dpdp.base_moudle.dialog;

import android.content.Context;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-01-10
 * <p>
 * Summary: 弹窗 工具类
 * <p>
 * api path:
 */
public class XPopupUtil {

    /**
     * 通用提示框
     * <p>
     * 有标题 确定 和 取消 按钮
     *
     * @param context  上下文
     * @param title    提示标题
     * @param message  提示内容
     * @param listener 确定按钮监听回调
     */
    public static void showTipPopup(Context context, CharSequence title, CharSequence message, OnConfirmListener listener) {
        new XPopup.Builder(context).dismissOnTouchOutside(false)
                .asConfirm(title, message, listener)
                .show();
    }

    /**
     * 通用提示框 只有确认选项
     */
    public static void showSinglePopup(Context context, CharSequence title, CharSequence message) {
        new XPopup.Builder(context).dismissOnTouchOutside(false)
                .asConfirm(title, message, "", "确认", null, null, true)
                .show();
    }

    /**
     * @see #showBottomListPopup(Context, CharSequence, String[], int, boolean, OnSelectListener)
     */
    public static void showBottomListPopup(Context context,CharSequence title,String[] data,OnSelectListener selectListener){
        showBottomListPopup(context,title,data,0,true,selectListener);
    }

    /**
     * 底部列表弹窗
     * @param title 标题
     * @param data 列表数据
     * @param defaultSelect 默认选中
     * @param enableDrag 能否拖动
     * @param selectListener 选择监听回调
     */
    public static void showBottomListPopup(Context context, CharSequence title, String[] data, int defaultSelect, boolean enableDrag, OnSelectListener selectListener) {
        new XPopup.Builder(context).dismissOnTouchOutside(true)
                .asBottomList(title, data, null, defaultSelect, enableDrag, selectListener)
                .show();
    }
}
