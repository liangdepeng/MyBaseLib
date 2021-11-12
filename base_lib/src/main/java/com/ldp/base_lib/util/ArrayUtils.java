package com.ldp.base_lib.util;

import java.util.List;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-01-10
 * <p>
 * Summary:
 * <p>
 * api path:
 */
public class ArrayUtils {

    /**
     * 判断 集合是否为空
     * @param list
     * @return
     */
    public static boolean isEmpty(List<?> list) {
        return list == null || list.size() == 0;
    }
}
