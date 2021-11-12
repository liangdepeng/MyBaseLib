package com.ldp.base_lib.http.volley;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.gson.Gson;
import com.ldp.base_lib.http.HttpCallback;
import com.ldp.base_lib.util.JsonFormatUtil;
import com.ldp.base_lib.util.ObjectNullUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-02-19
 * <p>
 * Summary: 封装 Volley 网络请求 + Gson 数据解析
 * https://developer.android.google.cn/training/volley
 *
 * @see com.android.volley.toolbox.HurlStack
 * <p>
 * {@link com.android.volley.toolbox.BaseHttpStack}
 * based on {@link java.net.HttpURLConnection}
 **/
public class HttpHelper {

    private RequestQueue requestQueue;
    private final String tag = HttpHelper.class.getSimpleName();

    private final static class VolleyClient {
        private final static HttpHelper INSTANCE = new HttpHelper();
        private final static OkHttpClient okHttpClient = new OkHttpClient();
    }

    /**
     * 构造方法 初始化
     */
    private HttpHelper() {
    }

    public static HttpHelper getInstance() {
        return VolleyClient.INSTANCE;
    }


    public void initLib(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    /**
     * 发起请求
     *
     * @param requestParams {@link RequestParams}
     * @param callback      请求结果回调
     */
    public void startRequest(@NonNull RequestParams requestParams,
                             @NonNull VolleyHttpCallback callback) {
        if (ObjectNullUtils.objectsIsNull(requestParams, callback)) {
            throw new NullPointerException("requestParams / callback 不能为空");
        }

        Log.e(tag, requestParams.getRequestUrl());

        StringRequest stringRequest = new StringRequest(requestParams.getRequestMethod(),
                requestParams.getRequestUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (response.startsWith("\n")) {
                        response = response.replaceFirst("\n", "");
                    }
                    Object data = new Gson().fromJson(response, requestParams.getParseClass());
                    callback.onSuccess(data, requestParams);
                    Log.e(tag, "response1 :" + response);
                    Log.e(tag, "response2 :\n" + JsonFormatUtil.formatDataFromJson(unicodeToString(response)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError("请求失败", error, requestParams);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // post 请求时 提交的参数
                if (requestParams.getRequestMethod() == Method.POST && requestParams.getParamsMap() != null) {
                    return requestParams.getParamsMap();
                }
                return super.getParams();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // 可根据需求 设置项目的通用请求头 token 等等
              //  HashMap<String, String> map = new HashMap<>(4);
                //  map.put("Content-Type", "application/x-www-form-urlencoded");
//                if (!TextUtils.isEmpty(UserInfoConfig.getInstance().getToken()))
//                    map.put("authorization", UserInfoConfig.getInstance().getToken());

                return headMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    private HashMap<String, String> headMap = new HashMap<>(4);

    public HttpHelper addHeader(HashMap<String, String> hashMap) {
        hashMap.clear();
        headMap = hashMap;
        return this;
    }

    private final Handler handler = new Handler(Looper.getMainLooper());

//    @Deprecated // 无法使用
//    public void uploadImg(String fileUploadUrl, File file, HttpCallback callback) {
//        OkHttpClient okHttpClient = VolleyClient.okHttpClient;
//        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data;" +
//                "charset=UTF-8"), file);
//        MultipartBody multipartBody =
//                new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file",
//                        file.getName(), requestBody).build();
//        Request request = new Request.Builder()
//                .url(fileUploadUrl)
//                .post(multipartBody)
////                .addHeader("authorization", UserInfoConfig.getInstance().getToken())
//                .build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (callback != null) {
//                            callback.onError(e.getMessage(), "");
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) {
//                try {
//                    String string = response.body().string();
//                    FileUploadBean baseBean = new Gson().fromJson(string, FileUploadBean.class);
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (baseBean.code == 0 && callback != null) {
//                                callback.onSuccess(baseBean.value, "success");
//                                // ToastUtil.showMsg("图片上传成功");
//                            } else {
//                                if (callback != null) {
//                                    callback.onError(baseBean.message, "error");
//                                }
//                            }
//                        }
//                    });
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            callback.onError(e.getMessage(), "error");
//                        }
//                    });
//                }
//
//            }
//        });
//    }


    /**
     * Unicode转 汉字字符串
     *
     * @param str \u6728
     * @return '木' 26408
     */
    public static String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            //group 6728
            String group = matcher.group(2);
            //ch:'木' 26408
            ch = (char) Integer.parseInt(group, 16);
            //group1 \u6728
            String group1 = matcher.group(1);
            str = str.replace(group1, ch + "");
        }
        return str;
    }
}
