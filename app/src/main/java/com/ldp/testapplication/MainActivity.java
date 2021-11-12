package com.ldp.testapplication;

import android.Manifest;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ldp.base_lib.baseui.BaseActivity;
import com.ldp.base_lib.baseui.PermissionCallback;
import com.ldp.testapplication.fragment.Fragment1;
import com.ldp.testapplication.fragment.Fragment2;
import com.ldp.testapplication.fragment.Fragment3;
import com.ldp.testapplication.fragment.Fragment4;

import java.util.ArrayList;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/11/12
 * <p>
 * Summary: 主页面 简单架构  tablayout + viewpager + fragment  可左右滑动 视图自动绑定
 */
public class MainActivity extends BaseActivity implements TabLayout.OnTabSelectedListener{

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tab_layout);

        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();

        // 主页面添加4个子页面
        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
        fragments.add(new Fragment3());
        fragments.add(new Fragment4());

        // 每个页面的标题 一一对应
        titles.add("首页");
        titles.add("二页");
        titles.add("三页");
        titles.add("四页");

        viewPager.setOffscreenPageLimit(fragments.size());
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),fragments,titles));

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);

        // 动态请求权限 用这个 简易版
        requestMyPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                new PermissionCallback() {
            @Override
            public void onGranted() {
               // ToastUtils.show("已授权");
            }

            @Override
            public void onDenied() {
               // ToastUtils.show("已拒绝");
            }
        });
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        // 下面的tab 被选中
        //tab.getPosition();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        // 下面的tab 由选中变为不选中
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        // 下面的tab 由不选中变为选中
    }
}