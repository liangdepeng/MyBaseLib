package com.dpdp.base_moudle.utils;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-02-19
 * <p>
 * Summary: 多个对象 判空
 */
public class ObjectNullUtils {

    public static boolean objectsIsNull(Object... objects) {
        if (objects == null) return true;
        for (Object object : objects) {
            if (object == null)
                return true;
        }
        return false;
    }
}
