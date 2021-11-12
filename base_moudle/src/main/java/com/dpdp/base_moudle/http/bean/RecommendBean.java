package com.dpdp.base_moudle.http.bean;

import java.util.List;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/6/8
 * <p>
 * Summary:
 */
public class RecommendBean {

    public int code;
    public String message;
    public List<ValueDTO> value;

    public static class ValueDTO {
        public int id;
        public UserDTO user;
        public long date;
        public String title;
        public String content;
        public List<ImagesDTO> images;
        public List<CollectUsersDTO> collectUsers;
        public List<CollectUsersDTO> favouriteUsers;
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

    public static class CollectUsersDTO {
        public int id;
        public String username;
        public String role;
        public UserDetailInfoBean userInfo;
    }

}
