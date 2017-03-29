package com.qy.j4u.global;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * 全局用户信息类
 * Created by abc on 2016/11/18.
 */

public class User {

    private String name;
    private UserState mState;

    private User() {

    }

    public static User getUser() {
        User instance = NewInstance.instance;
        instance.setState(new LogoutUser());
        return instance;
    }

    private static class NewInstance {
        private static final User instance= new User();
    }

    /**
     * 在用户登录状态改变时候设置
     * @param state 用户状态实现类
     */
    private void setState(@NonNull UserState state) {
        this.mState = state;
    }

    /**
     * 登录成功
     */
    public void login(){
        setState(new LoginUser());
    }

    /**
     * 退出
     */
    public void logout() {
        setState(new LogoutUser());
    }

    /**
     * 评论
     */
    public void comment(Context context) {
        mState.comment(context);
    }

}
