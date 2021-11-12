package com.dpdp.base_moudle.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-01-18
 * <p>
 * Summary:
 * <p>
 * api path:
 */
public class FileUtils {

    public static ArrayList<String> readAssetsFile(Context context) {
        long l = System.currentTimeMillis();
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            InputStream inputStream = context.getAssets().open("words_105k.sql");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String sqlLine = "";
            while ((sqlLine = bufferedReader.readLine()) != null) {
                sqlLine = sqlLine.replace(";", "").replace("\\'","");
                arrayList.add(sqlLine);
            }
            long l1 = System.currentTimeMillis();
            Log.e("TIME_SQL_READ_100000---", (l1 - l) / 1000 + "秒");
            inputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
