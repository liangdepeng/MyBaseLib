package com.dpdp.base_moudle.http.bean;

import java.util.List;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/6/12
 * <p>
 * Summary:
 */
public class QueryLogcatBean {

    public int code;
    public String message;
    public ValueDTO value;

    public static class ValueDTO {
        public int id;
        public UserDTO user;
        public long date;
        public String title;
        public String content;
        public List<ImagesDTO> images;
        public List<UsersDTO> collectUsers;
        public List<UsersDTO> favouriteUsers;

    }

    public static class UserDTO {
        public int id;
        public String username;
        public String role;
        public UserDetailInfoBean userInfo;
    }

    public static class ImagesDTO {
        public int id;
        public String imagePath;
    }

    public static class UsersDTO {
        public int id;
        public String username;
        public String role;
        public UserDetailInfoBean userInfo;
    }
}
