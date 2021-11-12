package com.dpdp.base_moudle.http;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-01-30
 * <p>
 */
@Deprecated
public interface HttpCallback {

    /**
     * 请求成功 回调
     * @param gsonData 返回数据 如果指定了 实体类 将被 gson 解析为指定类型
     * @param requestTag 多个请求时 可以做区分请求 请求标记
     */
    void onSuccess(Object gsonData, Object requestTag);

    /**
     * 请求失败 回调
     * @param errorMsg 错误消息
     * @param requestTag 请求标记
     */
    void onError(String errorMsg, Object requestTag);

}
