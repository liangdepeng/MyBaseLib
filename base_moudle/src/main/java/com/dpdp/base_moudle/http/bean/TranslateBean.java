package com.dpdp.base_moudle.http.bean;

import java.util.List;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-01-31
 * <p>
 * Summary: 翻译结果数据结构
 * <p>
 * api path: "https://fanyi-api.baidu.com/api/trans/vip/translate"
 */
public class TranslateBean {

    // 原内容 的语言类别
    public String from;

    // 翻译结果的语言类别
    public String to;

    // 翻译结果
    public List<ItemResultBean> trans_result;
}
