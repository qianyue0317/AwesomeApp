package com.qy.j4u.global.constants;

/**
 * 存放Url常量的类
 * Created by abc on 2016/11/14.
 * modified by qy on 2019/05/13 以内部类的形式分模块
 */

@SuppressWarnings("ALL")
public class Urls {

    private static final String RELEASE_BASE_URL = "http://106.12.129.87:5000/j4u/";
    private static final String TEST_BASE_URL = "http://172.16.0.29:5000/j4u/";
    public static String BASE_URL = RELEASE_BASE_URL;

    static {
        BASE_URL = Constants.DEBUG ? TEST_BASE_URL : RELEASE_BASE_URL;
    }

    public static final class Raspberry {
        public static final String GET_IP = "raspberry/localIp";
    }

    public static final class Account{
        public static final String LOGIN = "account/login";
    }

    public static final class IT{
        public static final String ESSAY_LIST = "IT/essayList";
    }

}
