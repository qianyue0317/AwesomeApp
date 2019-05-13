package com.qy.j4u.app.main.presenters;

import com.qy.j4u.app.main.views.SplashView;
import com.qy.j4u.base.BasePresenter;
import com.qy.j4u.global.User;
import com.qy.j4u.model.http.ApiService;
import com.qy.j4u.model.http.ObserverWrapper;
import com.qy.j4u.utils.JLog;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SplashPresenter extends BasePresenter<SplashView> {


    @Inject
    public SplashPresenter(SplashView view, ApiService apiService) {
        super(view, apiService);
    }

    /**
     * 通过uuid实现登录
     */
    public void loginWithUUid(String uuid) {
        JLog.i("BasePresenter", "loginWithUUid");
        getApiService().loginWithUUid(uuid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(bindLifecycle())
                .subscribe(new ObserverWrapper<User>() {
                    @Override
                    public void onSuccess(User user) {
                        JLog.i("返回结果:" + user);
                    }
                });
    }

}
