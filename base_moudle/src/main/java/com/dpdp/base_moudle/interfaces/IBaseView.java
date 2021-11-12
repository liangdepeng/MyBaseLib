package com.dpdp.base_moudle.interfaces;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-01-09
 * <p>
 * Summary:
 * <p>
 */
public interface IBaseView {

    /**
     * 显示加载框
     */
    void showLoadingDialog();

    /**
     * 关闭加载框
     */
    void dismissDialog();

    /**
     * 显示一个自定义的 逼格比较高的 加载弹窗
     */
    void showCustomLoadingDialog();

    /**
     * 关闭弹窗
     */
    void dismissCustomLoadingDialog();

    /**
     * 页面标题
     */
    CharSequence getPageTitle();

    /**
     * 初始化
     */
    void initDataView();

}
