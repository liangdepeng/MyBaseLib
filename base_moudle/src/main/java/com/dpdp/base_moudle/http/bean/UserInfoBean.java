package com.dpdp.base_moudle.http.bean;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/6/10
 * <p>
 * Summary:
 */
public class UserInfoBean {

    /**
     * 返回code 0 成功 -1 失败
     */
    public int code;
    /**
     * 成功或者失败消息
     */
    public String message;

    public UserDetailInfoBean value;

}
