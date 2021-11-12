package com.ldp.base_lib.baseui;

import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/11/12
 * <p>
 * Summary: 动态权限申请简易封装 6.0 以上
 */
class BasePermissionActivity extends AppCompatActivity {

    private PermissionCallback permissionCallback;

    public final void requestMyPermissions(@NonNull String[] permissions,
                                         PermissionCallback callback) {
        this.permissionCallback = callback;
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, permissions, 666);
                break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != 666 || permissionCallback == null)
            return;
        for (int result : grantResults) {
            if (PackageManager.PERMISSION_DENIED == result) {
                permissionCallback.onDenied();
                return;
            }
        }
        permissionCallback.onGranted();
    }
}
