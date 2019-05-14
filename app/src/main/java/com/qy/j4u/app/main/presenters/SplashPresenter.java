package com.qy.j4u.app.main.presenters;

import com.qy.j4u.app.main.views.SplashView;
import com.qy.j4u.base.BasePresenter;
import com.qy.j4u.global.User;
import com.qy.j4u.model.http.ApiService;
import com.qy.j4u.model.http.ObserverWrapper;
import com.qy.j4u.utils.JLog;
import com.qy.j4u.utils.ToastUtil;

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
                        ToastUtil.showSuccessShort("登录成功");
                        getView().onLoginSuccess("success");
                        User.init(user);
                        User.save();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showErrorShort(String.format("登录失败:%s", e.getMessage()));
                        getView().onLoginError();
                    }
                });
    }

}
