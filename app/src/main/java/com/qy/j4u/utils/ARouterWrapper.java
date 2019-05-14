package com.qy.j4u.utils;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.qy.j4u.R;

public class ARouterWrapper {

    /**
     * 简单封装
     * @param uri 目标 uri
     * @return 返回postcard 继续添加携带的参数等
     */
    public static Postcard build(String uri) {
        return ARouter.getInstance().build(uri).withTransition(R.animator.transition_in,
        R.animator.transition_out);
    }

    /**
     * 存放所有路由
     */
    public interface Route{
        String MAIN = "/main/MainActivity";
        String ESSAY_LIST = "/IT/EssayListActivity";
    }

}
