package com.dpdp.base_moudle.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PermissionsUtil {


    private Context mContext;
    private String packageName;

    public static PermissionsUtil getInstance(Context context, String packageName) {
        return new PermissionsUtil(context, packageName);
    }

    public PermissionsUtil(Context context, String packageName) {
        mContext = context;
        this.packageName = packageName;
    }

    public static boolean checkPermission(Context context, String permission) {
        boolean result;
        /*  if ("Meizu".equals(Build.MANUFACTURER)) {
         *//*魅族手机只有在真正执行相关权限代码时才去申请权限，所以直接返回true*//*
            return true;
        }*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            result = (context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
        } else {
            result = (PermissionChecker.checkSelfPermission(context, permission) == PermissionChecker.PERMISSION_GRANTED);
        }
        return result;
    }

/*    public static int checkPermissionReturnInt(Context context,String permission){
        int result;
        if(context.getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.M){
            result=context.checkSelfPermission(permission);
            if(result!=-1){
                if("Meizu".equals(Build.MANUFACTURER)){
                    *//*魅族手机只有在真正执行相关权限代码时才去申请权限，所以直接返回true*//*
                    return 0;
                }
            }
        }
        else{
            result=PermissionChecker.checkSelfPermission(context,permission);
            if(result!=-2){
                if("Meizu".equals(Build.MANUFACTURER)){
                    *//*魅族手机只有在真正执行相关权限代码时才去申请权限，所以直接返回true*//*
                    return 0;
                }
            }
        }
        return result;
    }*/

    /**
     * 返回需要授权的权限数组
     *
     * @param context
     * @param permission
     * @return
     */
    public static List<String> checkPermissions(Context context, String... permission) {
        List<String> permissionList = new ArrayList<>();
        for (int i = 0; i < permission.length; i++) {
            if (!checkPermission(context, permission[i])) {
                permissionList.add(permission[i]);
            }
        }
        return permissionList;
    }

    /**
     * 是否可以直接启动App，无需权限校验申请
     *
     * @param appContext
     * @return true 已授权，false未授权
     */
    public static boolean shouldStartApplication(Context appContext) {
        List<String> permissionList = checkPermissions(appContext,
                Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return !(null != permissionList && permissionList.size() != 0);
    }

    public static void requestPermissions(Activity context, String[] permissions, int code) {
        ActivityCompat.requestPermissions(context, permissions, code);
    }

    public void jumpPermissionPage() {
        String name = Build.MANUFACTURER;
        switch (name) {
            case "HUAWEI":
                goHuaWeiMainager();
                break;
            case "vivo":
                goVivoMainager();
                break;
            case "OPPO":
                goOppoMainager();
                break;
            case "Coolpad":
                goCoolpadMainager();
                break;
            /*case "Meizu":
                goMeizuMainager();
                break;*/
            case "Xiaomi":
                goXiaoMiMainager();
                break;
            case "Flyme": // 魅族
                gotoMeizuManager();
                break;
            case "samsung":
                goSangXinMainager();
                break;
            case "Sony":
                goSonyMainager();
                break;
            case "LG":
                goLGMainager();
                break;
            default:
                goIntentSetting();
                break;
        }
    }

    private void goLGMainager() {
        try {
            Intent intent = new Intent(packageName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity");
            intent.setComponent(comp);
            mContext.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(mContext, "跳转失败", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            goIntentSetting();
        }
    }

    private void goSonyMainager() {
        try {
            Intent intent = new Intent(packageName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity");
            intent.setComponent(comp);
            mContext.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(mContext, "跳转失败", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            goIntentSetting();
        }
    }


    private void gotoMeizuManager() {
        try {
            Intent intent = new Intent();
            intent.setAction("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("packageName", packageName);
            mContext.startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void goHuaWeiMainager() {
        try {
            Intent intent = new Intent(packageName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
            intent.setComponent(comp);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            goIntentSetting();
        }
    }

    private static String getMiuiVersion() {
        String propName = "ro.miui.ui.version.name";
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(
                    new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return line;
    }

    private void goXiaoMiMainager() {
        /*String rom = getMiuiVersion();
        Intent intent=new Intent();
        if ("V6".equals(rom) || "V7".equals(rom)) {
            intent.setAction("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.putExtra("extra_pkgname", packageName);
        } else if ("V8".equals(rom) || "V9".equals(rom)) {
            intent.setAction("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            intent.putExtra("extra_pkgname", packageName);
        } else {
            goIntentSetting();
        }
        mContext.startActivity(intent);*/
        String rom = getMiuiVersion();
        Intent intent = null;
        if ("V5".equals(rom)) {
            Uri packageURI = Uri.parse("package:" + mContext.getApplicationInfo().packageName);
            intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        } else if ("V6".equals(rom) || "V7".equals(rom)) {
            intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.putExtra("extra_pkgname", mContext.getPackageName());
        } else if ("V8".equals(rom)) {
            intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            intent.putExtra("extra_pkgname", mContext.getPackageName());
        } else {
            goIntentSetting();
            return;
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    private void goMeizuMainager() {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("packageName", packageName);
            mContext.startActivity(intent);
        } catch (ActivityNotFoundException localActivityNotFoundException) {
            localActivityNotFoundException.printStackTrace();
            goIntentSetting();
        }
    }

    private void goSangXinMainager() {
        //三星4.3可以直接跳转
        goIntentSetting();
    }

    private void goIntentSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
        intent.setData(uri);
        try {
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext, "跳转失败", Toast.LENGTH_LONG).show();
        }
    }

    private void goOppoMainager() {
        doStartApplicationWithPackageName("com.coloros.safecenter");
    }

    /**
     * doStartApplicationWithPackageName("com.yulong.android.security:remote")
     * 和Intent open = getPackageManager().getLaunchIntentForPackage("com.yulong.android.security:remote");
     * startActivity(open);
     * 本质上没有什么区别，通过Intent open...打开比调用doStartApplicationWithPackageName方法更快，也是android本身提供的方法
     */
    private void goCoolpadMainager() {
        doStartApplicationWithPackageName("com.yulong.android.security:remote");
      /*  Intent openQQ = getPackageManager().getLaunchIntentForPackage("com.yulong.android.security:remote");
        startActivity(openQQ);*/
    }

    private void goVivoMainager() {
//        doStartApplicationWithPackageName("com.bairenkeji.icaller");
     /*   Intent openQQ = getPackageManager().getLaunchIntentForPackage("com.vivo.securedaemonservice");
        startActivity(openQQ);*/
//        getAppDetailSettingIntent();
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("packageName", packageName);
            intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.PermissionManagerActivity"));
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 此方法在手机各个机型设置中已经失效
     *
     * @return
     */
    private Intent getAppDetailSettingIntent() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", mContext.getPackageName());
        }
        return localIntent;
    }

    private void doStartApplicationWithPackageName(String packagename) {
        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = mContext.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return;
        }
        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);
        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = mContext.getPackageManager()
                .queryIntentActivities(resolveIntent, 0);
        Log.e("PermissionPageManager", "resolveinfoList" + resolveinfoList.size());
       /* for (int i = 0; i < resolveinfoList.size(); i++) {
            Log.e("PermissionPageManager", resolveinfoList.get(i).activityInfo.packageName + resolveinfoList.get(i).activityInfo.name);
        }*/
        Iterator<ResolveInfo> iterator = resolveinfoList.iterator();
        if (!iterator.hasNext()){
            return;
        }
        ResolveInfo resolveinfo = iterator.next();
        if (resolveinfo != null) {
            // packageName参数2 = 参数 packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packageName参数2.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // 设置ComponentName参数1:packageName参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);
            intent.setComponent(cn);
            try {
                mContext.startActivity(intent);
            } catch (Exception e) {
                goIntentSetting();
                e.printStackTrace();
            }
        }
    }

    /**
     * 是否有app使用权限
     *
     * @param context
     * @return
     */
    public static boolean hasAppPermission(Context context) {
        return checkPermission(context, Manifest.permission.READ_PHONE_STATE)
                && checkPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                && checkPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 跳转: 「权限设置」界面
     * <p>
     * 根据各大厂商的不同定制而跳转至其权限设置
     * 目前已测试成功机型: 小米V7V8V9, 华为, 三星, 锤子, 魅族; 测试失败: OPPO
     *
     * @return 成功跳转权限设置, 返回 true; 没有适配该厂商或不能跳转, 则自动默认跳转设置界面, 并返回 false
     * @see #requestNotify
     */
    public static boolean gotoPermissionSetting(Context context) {
        boolean success = true;
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String packageName = context.getPackageName();

        OSUtils.ROM romType = OSUtils.getRomType();
        switch (romType) {
            case EMUI: // 华为
                intent.putExtra("packageName", packageName);
                intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity"));
                break;
            case Flyme: // 魅族
                intent.setAction("com.meizu.safe.security.SHOW_APPSEC");
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.putExtra("packageName", packageName);
                break;
            case MIUI: // 小米
                String rom = getMiuiVersion();
                if ("V6".equals(rom) || "V7".equals(rom)) {
                    intent.setAction("miui.intent.action.APP_PERM_EDITOR");
                    intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                    intent.putExtra("extra_pkgname", packageName);
                } else if ("V8".equals(rom) || "V9".equals(rom)) {
                    intent.setAction("miui.intent.action.APP_PERM_EDITOR");
                    intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
                    intent.putExtra("extra_pkgname", packageName);
                } else {
                    intent = getAppDetailsSettingsIntent(packageName);
                }
                break;
            case Sony: // 索尼
                intent.putExtra("packageName", packageName);
                intent.setComponent(new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity"));
                break;
            case ColorOS: // OPPO
                intent.putExtra("packageName", packageName);
                intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.PermissionManagerActivity"));
                break;
            case EUI: // 乐视
                intent.putExtra("packageName", packageName);
                intent.setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps"));
                break;
            case LG: // LG
                intent.setAction("android.intent.action.MAIN");
                intent.putExtra("packageName", packageName);
                ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity");
                intent.setComponent(comp);
                break;
            case SamSung: // 三星
            case SmartisanOS: // 锤子
                gotoAppDetailSetting(context, packageName);
                break;
            default:
                intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", packageName, null);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(uri);
                Log.i("PermissionUtils", "没有适配该机型, 跳转普通设置界面");
                success = false;
                break;
        }
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            // 跳转失败, 前往普通设置界面
            gotoSetting(context);
            success = false;
            Log.i("PermissionUtils", "无法跳转权限界面, 开始跳转普通设置界面");
        }
        return success;
    }

    /**
     * 跳转:「设置」界面
     */
    public static void gotoSetting(Context context) {
        context.startActivity(getSettingIntent());
    }


    /**
     * 获取跳转「设置界面」的意图
     *
     * @return 意图
     */
    public static Intent getSettingIntent() {
        return new Intent(Settings.ACTION_SETTINGS)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 跳转:「应用详情」界面
     *
     * @param packageName 应用包名
     */
    public static void gotoAppDetailSetting(Context context, String packageName) {
        context.startActivity(getAppDetailsSettingsIntent(packageName));
    }

    /**
     * 获取跳转「应用详情」的意图
     *
     * @param packageName 应用包名
     * @return 意图
     */
    public static Intent getAppDetailsSettingsIntent(String packageName) {
        return new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(Uri.parse("package:" + packageName))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }


    /**
     * 通知权限 设置
     *
     * @param context
     * @see #gotoPermissionSetting
     */
    public static void requestNotify(Context context) {
        /**
         * 跳到通知栏设置界面
         * @param context
         */
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //直接跳转到应用通知设置的代码
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            localIntent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            localIntent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            localIntent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            localIntent.putExtra("app_package", context.getPackageName());
            localIntent.putExtra("app_uid", context.getApplicationInfo().uid);
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            localIntent.addCategory(Intent.CATEGORY_DEFAULT);
            localIntent.setData(Uri.parse("package:" + context.getPackageName()));
        } else {
            //4.4以下没有从app跳转到应用通知设置页面的Action，可考虑跳转到应用详情页面,
            if (Build.VERSION.SDK_INT >= 9) {
                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
            } else if (Build.VERSION.SDK_INT <= 8) {
                localIntent.setAction(Intent.ACTION_VIEW);
                localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
                localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
            }
        }
        context.startActivity(localIntent);
    }

    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean isNotificationEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ///< 8.0手机以上
            if (((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).getImportance() == NotificationManager.IMPORTANCE_NONE) {
                return false;
            }
        }

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
