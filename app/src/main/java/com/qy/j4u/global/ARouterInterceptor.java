package com.qy.j4u.global;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

/**
 * AROUTER 的拦截器
 */
@Interceptor(priority = 8,name = "测试拦截器")
public class ARouterInterceptor implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
//        JLog.i("postcard:" + postcard);
        callback.onContinue(postcard);
    }

    @Override
    public void init(Context context) {
//        JLog.i("init");
    }
}
