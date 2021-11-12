package com.dpdp.base_moudle.http.url;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/6/8
 * <p>
 * Summary:
 */
public class HttpUrl {

    public static String DELICIOUS_LOGIN_REQUEST_URL = "http://120.26.8.235:88/user/login";

    public static String DELICIOUS_REGIS_REQUEST_URL = " http://120.26.8.235:88/user/register";

    public static String DELICIOUS_LOGCAT_REQUEST_URL = "http://120.26.8.235:88/foodLog/all";

    public static String DELICIOUS_MY_LOGCAT_REQUEST_URL = "http://120.26.8.235:88/foodLog/myFoodLog";

    public static String DELICIOUS_USERINFO_REQUEST_URL = "http://120.26.8.235:98/user/member";

    public static String DELICIOUS_UPLOAD_FILE_REQUEST_URL = "http://120.26.8.235:88/fileUpload";

    public static String DELICIOUS_COLLECT_LOG_REQUEST_URL = "http://120.26.8.235:88/foodLog/myCollectFoodLog";

    public static String DELICIOUS_LIKE_LOG_REQUEST_URL = "http://120.26.8.235:88/foodLog/myFavouriteFoodLog";

    /**
     * http://120.26.8.235:88/user/changePassword
     * Post方式
     * 附加参数：
     * oldPassword：旧密码。
     * newPassword：新密码。
     */
    public static String DELICIOUS_UPDATE_PASSWORD_REQUEST_URL = "http://120.26.8.235:88/user/changePassword";

    /**
     * http://120.26.8.235:88/user/update
     * Post方式
     * 附加参数：
     * nickname：昵称。
     * sign：签名。
     * sex：性别，0 未知 1 男 2女
     * avatar：头像在服务端的资源。
     * 这4个参数不是全部必须的，需要修改哪些信息就提供哪些参数。
     * 如果要修改头像，先要访问上传图片接口把头像图片上传到服务端，再将返回的服务端路径放到avatar参数中
     */
    public static String DELICIOUS_UPDATE_USERINFO_REQUEST_URL = "http://120.26.8.235:88/user/update";

    /**
     * http://120.26.8.235:88/foodLog/create
     * Post方式
     * 附加参数：
     * title: 日志标题；
     * content：日志内容；
     * images：日志中的图片资源。
     * 先要访问上传图片接口把日志中的图片资源上传到服务端，在将返回的服务端路径放到images参数中。
     * 如果有多张图片，在images参数用英文的逗号分开”247bb0ae-ac6d-4f14-9567-ac699999cc4e.png,ad967644-f4c3-4789-82f7-77b111009135.png”。
     */
    public static String DELICIOUS_CREATE_LOGCAT_REQUEST_URL = "http://120.26.8.235:88/foodLog/create";

    /**
     * http://120.26.8.235:88/foodLog/collect
     * Post方式
     * 附加参数：
     * foodLogId: 美食日志的id。
     * 如果当前用户未收藏过美食日志，则收藏此美食日志
     */
    public static String DELICIOUS_COLLECT_LOGCAT_REQUEST_URL="http://120.26.8.235:88/foodLog/collect";

    /**
     * http://120.26.8.235:88/foodLog/favourite
     * Post方式
     * 附加参数：
     * foodLogId: 美食日志的id。
     * 如果当前用户未点赞过美食日志，则点赞此美食日志
     */
    public static String DELICIOUS_LIKE_LOGCAT_REQUEST_URL="http://120.26.8.235:88/foodLog/favourite";

    /**
     * http://120.26.8.235:88/foodLog
     * Post方式
     * 附加参数：
     * id: 美食日志的id。
     * 返回的value中为根据id查找到的美食日志的数据 如果根据id找不到美食日志，value为null
     */
    public static String DELICIOUS_QUERY_LOGCAT_REQUEST_URL="http://120.26.8.235:88/foodLog/findOne";
}
