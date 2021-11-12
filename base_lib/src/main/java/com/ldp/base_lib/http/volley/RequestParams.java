package com.ldp.base_lib.http.volley;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-02-19
 * <p>
 * Summary: 设置请求参数
 */
public class RequestParams {


    /**
     * Supported request methods.
     */
    public interface Method {
        int DEPRECATED_GET_OR_POST = -1;
        int GET = 0;
        int POST = 1;
        int PUT = 2;
        int DELETE = 3;
        int HEAD = 4;
        int OPTIONS = 5;
        int TRACE = 6;
        int PATCH = 7;
    }

    /**
     * 请求url
     */
    private final String requestUrl;
    /**
     * 请求参数
     */
    private HashMap<String, String> paramsMap;
    /**
     * 请求 方式
     * @see Method
     */
    private final int requestMethod;
    /**
     * gson 解析的实体类
     */
    private final Class<?> parseClass;
    /**
     * 自定义请求标识
     */
    private Object requestTag;

    /**
     * 默认 get 请求
     *
     * @param requestUrl url
     */
    public RequestParams(String requestUrl, Class<?> parseClass) {
        this(requestUrl, Method.GET, parseClass);
    }

    /**
     * post/get 请求
     *
     * @param requestUrl    url
     * @param requestMethod post/get
     */
    public RequestParams(String requestUrl, int requestMethod, Class<?> parseClass) {
        this.requestUrl = requestUrl;
        this.requestMethod = requestMethod;
        this.parseClass = parseClass;
    }

    /**
     * 添加请求参数 key-value 一一对应
     */
    public RequestParams addParams(String key, Object value) {
        if (paramsMap == null) {
            paramsMap = new HashMap<>();
        }
        paramsMap.put(key, String.valueOf(value));
        return this;
    }

    public String getRequestUrl() {
        // get 请求 需要拼接参数到url里面
        if (requestMethod == Request.Method.GET && paramsMap != null) {
            StringBuilder str = new StringBuilder(requestUrl);
            str.append("?");
            for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
                str.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            return str.toString().substring(0, str.length() - 1);
        }
        return requestUrl;
    }

    public HashMap<String, String> getParamsMap() {
        return paramsMap;
    }

    public int getRequestMethod() {
        return requestMethod;
    }

    public Class<?> getParseClass() {
        return parseClass;
    }

    public Object getRequestTag() {
        return requestTag;
    }

    public void setRequestTag(Object requestTag) {
        this.requestTag = requestTag;
    }
}
