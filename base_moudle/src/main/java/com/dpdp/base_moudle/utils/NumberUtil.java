package com.dpdp.base_moudle.utils;

import java.math.BigDecimal;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-01-10
 * <p>
 * Summary: 比较大小
 */
public class NumberUtil {

    /**
     * 比较任意2个数的大小 需要转换成 String
     *
     * @return
     *
     * {@code 1} if {@code a > b},
     *
     * {@code -1} if {@code a < b},
     *
     * {@code 0} if {@code this == val}.
     *
     */
    public static int compareTo(String a, String b) {
        BigDecimal aBigDecimal = new BigDecimal(a);
        BigDecimal bBigDecimal = new BigDecimal(b);
        return aBigDecimal.compareTo(bBigDecimal);
    }

    /**
     * 比较 两个 float 数据大小
     */
    public static int compareFloat(float a, float b) {
        return compareTo(String.valueOf(a), String.valueOf(b));
    }

    /**
     * 比较 两个 double 数据大小
     */
    public static int compareDouble(double a, double b) {
        return compareTo(String.valueOf(a), String.valueOf(b));
    }

    public static int compareFloatDouble(float a, double b) {
        return compareTo(String.valueOf(a), String.valueOf(b));
    }

    public static int compareLong(long a, long b) {
        return compareTo(String.valueOf(a), String.valueOf(b));
    }

    public static int compareIntFloat(int a, float b) {
        return compareTo(String.valueOf(a), String.valueOf(b));
    }

    public static int compareIntDouble(int a, double b) {
        return compareTo(String.valueOf(a), String.valueOf(b));
    }

}
