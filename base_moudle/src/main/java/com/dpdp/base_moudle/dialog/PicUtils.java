package com.dpdp.base_moudle.dialog;

import android.Manifest;
import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;

import com.dpdp.base_moudle.utils.ToastUtil;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.XXPermissions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/6/9
 * <p>
 * Summary: 选择图片
 */
public class PicUtils {

    public static void chooseImg(Activity activity, PicSelectCallback callback) {

        XXPermissions.with(activity)
                .permission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE})
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all){
                            openGally(activity, callback);
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        ToastUtil.showMsg("权限被拒绝");
                    }
                });

//        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)==0&&
//                ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)==0){
//            openGally(activity, callback);
//        }else {
//            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},123321);
//        }

    }

    private static void openGally(Activity activity,PicSelectCallback callback){

        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .isAndroidQTransform(Build.VERSION.SDK_INT>=29)
                .minimumCompressSize(200)
                .isCompress(true)
                .compressQuality(80)
                .selectionMode(PictureConfig.SINGLE)// PictureConfig.MULTIPLE
                .maxSelectNum(1)
                .imageEngine(GlideEngine.createGlideEngine())
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        ArrayList<String> list = new ArrayList<>();
                        for (int i = 0;i<result.size();i++){
                            String url;
                            if (Build.VERSION.SDK_INT>=29){
                                url = result.get(i).getAndroidQToPath();
                            }else {
                                if (result.get(i).isCompressed()){
                                    url=result.get(i).getCompressPath();
                                }else {
                                    url=result.get(i).getPath();
                                }
                            }

                            if (!TextUtils.isEmpty(url)){
                                list.add(url);
                            }
                        }

                        if (callback!=null){
                            callback.onResult(list);
                        }
                    }

                    @Override
                    public void onCancel() {
                        if (callback!=null){
                            callback.onCancel();
                        }
                    }
                });
    }

    public interface PicSelectCallback{

        void onResult(List<String> list);

        void onCancel();
    }


}
