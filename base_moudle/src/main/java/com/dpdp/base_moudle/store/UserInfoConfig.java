package com.dpdp.base_moudle.store;

import android.text.TextUtils;

import com.dpdp.base_moudle.http.bean.LoginBean;
import com.dpdp.base_moudle.http.bean.UserDetailInfoBean;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/6/8
 * <p>
 * Summary:
 */
public class UserInfoConfig {

    private final static class UserInfoInstance {
        private final static UserInfoConfig INSTANCE = new UserInfoConfig();
    }

    private UserInfoConfig() {
    }

    public synchronized static UserInfoConfig getInstance() {
        return UserInfoInstance.INSTANCE;
    }

    private LoginBean userLoginInfo;

    public LoginBean getUserLoginInfo() {
        if (userLoginInfo == null)
            userLoginInfo = getDefaultLoginNoNullBean();
        return userLoginInfo;
    }

    private LoginBean getDefaultLoginNoNullBean() {
        LoginBean loginBean = new LoginBean();
        LoginBean.ValueBean valueBean = new LoginBean.ValueBean();
        valueBean.userInfo = new UserDetailInfoBean();
        loginBean.value = valueBean;
        return loginBean;
    }

    public void setUserLoginInfo(LoginBean userLoginInfo) {
        this.userLoginInfo = userLoginInfo;
    }

    public String getToken() {
        return getUserLoginInfo().value.token;
    }

    public int getUserId() {
        return getUserLoginInfo().value.userInfo.id;
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(getUserLoginInfo().value.token);
    }
}
