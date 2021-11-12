package com.ldp.base_lib.baseui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/11/12
 * <p>
 * Summary: 基类activity 包括 加载框 生命周期 Logcat 和 动态权限申请
 */
public class BaseActivity extends BasePermissionActivity {

    private final String tag = "lifecycle_for_activity";
    private ProgressDialog progressDialog;
    private Runnable runnable;
    private final ActivityStack activityStack = new ActivityStack();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityStack.addActivity(this);
        Log.e(tag, "---onCreate " + getClass().getSimpleName() + " ---");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(tag, "---onStart " + getClass().getSimpleName() + " ---");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(tag, "---onResume " + getClass().getSimpleName() + " ---");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(tag, "---onPause " + getClass().getSimpleName() + " ---");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(tag, "---onStop " + getClass().getSimpleName() + " ---");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityStack.removeActivity(this);
        Log.e(tag, "---onDestroy " + getClass().getSimpleName() + " ---");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(tag, "---onRestart " + getClass().getSimpleName() + " ---");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(tag, "---onSaveInstanceState " + getClass().getSimpleName() + " ---");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(tag, "---onRestoreInstanceState " + getClass().getSimpleName() + " ---");
    }

    /**
     * 显示加载弹窗
     */
    public final void showLoadingDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在玩命加载中...");
        }
        if (progressDialog.isShowing()) {
            return;
        }
        if (runnable == null) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    progressDialog.show();
                }
            };
        }
        runOnUiThread(runnable);
    }

    /**
     * 关闭加载弹窗
     */
    public final void dismissLoadingDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * @return 判断是否是主线程 子线程不能进行耗时操作和UI修改
     */
    public final boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
