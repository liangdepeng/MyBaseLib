package com.dpdp.base_moudle.http.bean;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-01-31
 * <p>
 * Summary: 翻译结果数据结构
 * <p>
 * api path: "https://fanyi-api.baidu.com/api/trans/vip/translate"
 */
public class ItemResultBean {

    // 需要翻译的内容
    public String src;
    // 翻译结果
    public String dst;
}
