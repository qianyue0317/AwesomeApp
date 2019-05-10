package com.qy.j4u.global;

/**
 * 全局用户信息类
 * Created by abc on 2016/11/18.
 */

public class User {

    private String name;

    private User() {

    }

    public static User getUser() {
        User instance = NewInstance.instance;
        return instance;
    }

    private static class NewInstance {
        private static final User instance= new User();
    }

}
