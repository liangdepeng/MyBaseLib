package com.ldp.base_lib.util;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-02-07
 * <p>
 * Summary: json 数据格式化显示
 */
public class JsonFormatUtil {


    /**
     * json 数据格式化输出
     * @param response
     * @return
     */
    public static String formatDataFromJson(String response) {
        try {
            if (response.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(response);
                return jsonObject.toString(4);
            } else if (response.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(response);
                return jsonArray.toString(4);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

//    /**
//     * 忽略
//     * @param str
//     * @return
//     */
//    @Deprecated
//    public static String formatFromJson(String str) {
//        char[] array = str.toCharArray();
//        StringBuilder strBuilder = new StringBuilder();
//        for (int i = 0; i < array.length; i++) {
//            if (i == array.length - 1) {
//                strBuilder.append("\n").append(array[i]);
//                break;
//            }
//            strBuilder.append(array[i]);
//            if (matchStr(array[i])) {
//                strBuilder.append("\n");
//            }
//        }
//        return strBuilder.toString().replace("\n\n", "\n");
//    }
//
//    @Deprecated
//    private static boolean matchStr(char c) {
//        return c == '{' || c == '}' || c == '[' || c == ']' || c == ',';
//    }
}
