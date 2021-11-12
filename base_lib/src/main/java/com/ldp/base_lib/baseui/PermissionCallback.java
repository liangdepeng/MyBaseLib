package com.ldp.base_lib.baseui;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/11/12
 * <p>
 * Summary: 权限申请回调
 */
public interface PermissionCallback {

    // 申请权限通过
    void onGranted();
    // 申请权限拒绝
    void onDenied();
}
