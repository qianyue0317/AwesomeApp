package com.qy.j4u.global;

import android.content.Context;

/**
 * 和用户登录状态相关的操作接口,使用状态模式
 * Created by abc on 2016/11/21.
 */

public interface UserState {
    /**
     * 评论
     */
    void comment(Context context);
}
