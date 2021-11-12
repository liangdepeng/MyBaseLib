package com.dpdp.base_moudle.http;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.dpdp.base_moudle.utils.JsonFormatUtil;
import com.dpdp.base_moudle.utils.ToastUtil;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-01-30
 * <p>
 * Summary: 安卓原生异步网络请求封装 单例设计模式 异步任务实现 异步请求
 *
 *          HttpURLConnection  + AsyncTask + Gson 解析实现
 */
@Deprecated
public class HttpHelper {

    private HttpHelper() {

    }

    public static HttpHelper getInstance() {
        return Instance.INSTANCE;
    }

    private static final class Instance {
        private static final HttpHelper INSTANCE = new HttpHelper();
    }

    /**
     * 异步请求
     */
    private static class HttpTask extends AsyncTask<Object, Integer, Object> {

        private final WeakReference<HttpCallback> callback;
        private HttpRequestParams requestParams;

        public HttpTask(HttpCallback callback) {
            this.callback = new WeakReference<>(callback);
        }

        @Override
        protected Object doInBackground(Object... objects) {

            if (objects.length > 0) {
                this.requestParams = (HttpRequestParams) objects[0];
                HttpURLConnection connection = null;
                StringBuilder result = new StringBuilder("");
                try {

                    if (requestParams.getRequestMethod() == HttpRequestMethod.GET) {
                        // get请求

                        // 拼接参数 因为 get请求 参数要拼接在 后面 所以要先拼接
                        if (requestParams.getParamsMap().size() > 0) {
                            String requestUrl = requestParams.getRequestUrl();
                            StringBuilder builder = new StringBuilder(requestUrl);
                            builder.append("?");
                            for (Map.Entry<String, String> item : requestParams.getParamsMap().entrySet()) {
                                if (!builder.toString().endsWith("?")) {
                                    builder.append("&");
                                }
                                builder.append(item.getKey()).append("=")
                                        .append(isContainChinese(item.getValue())
                                                ? URLEncoder.encode(item.getValue(), "UTF-8")
                                                : item.getValue());
                            }
                            requestParams.setRequestUrl(builder.toString());
                        }
                        URL url = new URL(requestParams.getRequestUrl());

                        Log.e("httpUrl", requestParams.getRequestUrl());

                        connection = (HttpURLConnection) url.openConnection();

                        if (!TextUtils.isEmpty(requestParams.getContentType())) {
                            connection.addRequestProperty("Content-Type", requestParams.getContentType());
                        }
                        connection.setConnectTimeout(8000);
                        connection.setReadTimeout(8000);
                        connection.setRequestMethod(HttpRequestMethod.GET.name());
                    } else if (requestParams.getRequestMethod() == HttpRequestMethod.POST) {
                        // post 请求
                        URL url = new URL(requestParams.getRequestUrl());

                        Log.e("httpUrl", requestParams.getRequestUrl());

                        connection = (HttpURLConnection) url.openConnection();

                        if (!TextUtils.isEmpty(requestParams.getContentType())) {
                            // header 设置 Content-Type 有别的需求可以加在这里
                            connection.addRequestProperty("Content-Type", requestParams.getContentType());
                        }
                        connection.setConnectTimeout(8000);
                        connection.setReadTimeout(8000);

                        connection.setRequestMethod(HttpRequestMethod.POST.name());
                        // post 请求要设置
                        connection.setDoInput(true);
                        connection.setDoOutput(true);
                        //Post方式不能缓存,需手动设置为false
                        connection.setUseCaches(false);
                        // 设置请求参数
                        if (requestParams.getParamsMap().size() > 0) {
                            StringBuilder builder = new StringBuilder("");
                            for (Map.Entry<String, String> item : requestParams.getParamsMap().entrySet()) {
                                if (!TextUtils.isEmpty(builder)) {
                                    builder.append("&");
                                }
                                builder.append(item.getKey()).append("=")
                                        .append(isContainChinese(item.getValue())
                                                ? URLEncoder.encode(item.getValue(), "UTF-8")
                                                : item.getValue());
                            }
                            OutputStream connectionOutputStream = connection.getOutputStream();
                            connectionOutputStream.write(builder.toString().getBytes());
                            connectionOutputStream.flush();
                        }
                    } else {

                    }

                    // 请求 结果
                    if (connection.getResponseCode() == 200) {
                        InputStream inputStream = connection.getInputStream();
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            result.append(line);
                        }

                        Log.e("httpUrl", result.toString());

                        inputStream.close();
                        inputStreamReader.close();
                        bufferedReader.close();
                    } else {
                        callback.get().onError("网络请求失败  " + connection.getResponseCode() + "  " + connection.getResponseMessage(), requestParams.getRequestTag());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }

                return result.toString();

            }


            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            String result = String.valueOf(o);
            dealWithResponseInfo(result);
        }

        private final Pattern p = Pattern.compile("[\u4e00-\u9fa5]");

        /**
         * 匹配请求参数 是否有中文 进行 edcode 否则低版本 安卓报错
         */
        public boolean isContainChinese(String str) {
            try {
               // Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
                Matcher m = p.matcher(str);
                return m.find();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        /**
         * 处理 gson 解析数据
         */
        private void dealWithResponseInfo(String response) {
            try {

                if (callback.get() == null) {
                    ToastUtil.showMsg("callback is null");
                    return;
                }

                if (TextUtils.isEmpty(response)) {
                    callback.get().onError("空数据~", requestParams.getRequestTag());
                    return;
                }

                if (requestParams.getParseClass() == null) {
                    callback.get().onSuccess(response, requestParams.getRequestTag());
                } else {
                    Object gsonData = new Gson().fromJson(response, requestParams.getParseClass());
                    callback.get().onSuccess(gsonData, requestParams.getRequestTag());
                }

            } catch (Exception e) {
                e.printStackTrace();
                ToastUtil.showMsg(e.getMessage());
               // callback.get().onError(e.getMessage(),requestParams.getRequestTag());
            }finally {
                Log.e("httpUrl", "response: \n"+JsonFormatUtil.formatDataFromJson(response));
            }

        }
    }

    /**
     * 网络请求
     *
     * @param requestParams 请求参数
     * @param callback      回调
     */
    public void startRequest(HttpRequestParams requestParams, HttpCallback callback) {
        // 异步请求
        new HttpTask(callback).execute(requestParams);
    }


}
