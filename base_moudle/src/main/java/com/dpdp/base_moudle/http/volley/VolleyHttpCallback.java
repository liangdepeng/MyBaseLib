package com.dpdp.base_moudle.http.volley;

import com.android.volley.VolleyError;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-02-19
 * <p>
 * Summary: 网络请求回调接口
 */
public interface VolleyHttpCallback {

    void onSuccess(Object response, RequestParams requestParams);

    void onError(String tips, VolleyError error, RequestParams requestParams);
}
