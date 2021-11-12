package com.ldp.base_lib.http;

import java.util.HashMap;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-01-30
 * <p>
 * Summary:
 * <p>
 * api path:
 */
//@Deprecated
public class HttpRequestParams {

    // 默认 get 方式 请求数据
    private HttpRequestMethod requestMethod = HttpRequestMethod.GET;
    // 请求 参数 map
    private HashMap<String, String> paramsMap = new HashMap<>();
    // header contentType
    private String contentType;
    // 设置 json 解析的实体类
    private Class<?> parseClass;
    // 请求链接
    private String requestUrl;
    // 请求 标记
    private Object requestTag;

    public HttpRequestParams(){
        paramsMap.clear();
    }

    @Deprecated
    public HttpRequestParams(String requestUrl) {
        this(requestUrl, null);
    }

    /**
     * 常规 get 请求
     */
    public HttpRequestParams(String requestUrl, Class<?> parseClass) {
        this(requestUrl, HttpRequestMethod.GET, parseClass);
    }

    /**
     * 指定 get或者post请求
     *
     */
    public HttpRequestParams(String requestUrl, HttpRequestMethod requestMethod, Class<?> parseClass) {
        this.requestUrl = requestUrl;
        this.requestMethod = requestMethod;
        this.parseClass = parseClass;
        this.paramsMap.clear();
    }

    public void put(String key, Object value) {
        paramsMap.put(key, String.valueOf(value));
    }

    public HashMap<String, String> getParamsMap() {
        return paramsMap;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Class<?> getParseClass() {
        return parseClass;
    }

    public HttpRequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(HttpRequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public void setParamsMap(HashMap<String, String> paramsMap) {
        this.paramsMap = paramsMap;
    }

    public void setParseClass(Class<?> parseClass) {
        this.parseClass = parseClass;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public Object getRequestTag() {
        return requestTag;
    }

    public void setRequestTag(Object requestTag) {
        this.requestTag = requestTag;
    }
}
