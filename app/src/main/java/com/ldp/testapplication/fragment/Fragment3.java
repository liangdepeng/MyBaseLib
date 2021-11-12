package com.ldp.testapplication.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/11/12
 * <p>
 * Summary: 测试11111 不要写代码在里面 自己去新建
 */
public class Fragment3 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // todo  测试11111 不要写代码在里面 自己去新建
        // todo  测试11111 不要写代码在里面 自己去新建
        // todo  测试11111 不要写代码在里面 自己去新建
        // todo  测试11111 不要写代码在里面 自己去新建

        TextView textView = new TextView(getContext());
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);
        textView.setText("我是 Fragment333333");

        ViewGroup.LayoutParams layoutParams =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);

        textView.setLayoutParams(layoutParams);

        return textView;
    }
}
