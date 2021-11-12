package com.dpdp.base_moudle.utils;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-02-18
 * <p>
 * Summary: 字符/字符串 工具类
 */
public class StringUtils {

    /**
     * 判断 一个或者多个字符 是否有 空  ，有一个即为 true
     * @param charSequences 可变长参数 一个或者多个 字符/字符串参数
     * @return 是否有空的 字符/字符串
     */
    public static boolean contentIsNullOrEmpty(CharSequence... charSequences) {
        if (charSequences == null) return true;
        for (CharSequence aChar : charSequences) {
            if (aChar == null || aChar.length() == 0)
                return true;
        }
        return false;
    }

    /**
     * 判断 一个或者多个字符 是否都不为空 全部都不为空才是 true
     * @param charSequences 可变长参数 一个或者多个 字符/字符串参数
     * @return 是否都不为空
     */
    public static boolean contentIsNotNullOrEmpty(CharSequence... charSequences) {
        if (charSequences == null) return false;
        for (CharSequence aChar : charSequences) {
            if (aChar == null || aChar.length() == 0)
                return false;
        }
        return true;
    }
}
