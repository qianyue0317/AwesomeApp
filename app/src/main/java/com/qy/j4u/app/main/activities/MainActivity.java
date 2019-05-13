package com.qy.j4u.app.main.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.qy.j4u.CoreBinder;
import com.qy.j4u.R;
import com.qy.j4u.app.main.presenters.MainPresenter;
import com.qy.j4u.app.main.views.MainView;
import com.qy.j4u.base.MVPBaseActivity;
import com.qy.j4u.di.components.DaggerMainComponent;
import com.qy.j4u.di.modules.MainModule;
import com.qy.j4u.eventmessages.RaspberryIp;
import com.qy.j4u.global.ForUApplication;
import com.qy.j4u.services.RemoteCoreService;
import com.qy.j4u.utils.ARouterWrapper;
import com.qy.j4u.utils.JLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import butterknife.BindView;
@Route(path = ARouterWrapper.Route.MAIN)
public class MainActivity extends MVPBaseActivity<MainPresenter> {

    @BindView(R.id.tv_raspberry_ip)
    TextView mTvIp;
    private MainView mView = new MainView() {
        @Override
        public void testProxy(String content) {

        }
    };

    @Override
    protected void daggerInject() {
        DaggerMainComponent.builder().mainModule(new MainModule(mView))
                .netComponent(ForUApplication.getInstance().getNetComponent())
                .build().inject(this);
    }

    @Override
    protected String getToolBarTitle() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onRaspIp(RaspberryIp raspberryIp) {
        mTvIp.setText(raspberryIp.getIp());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        bindRemoteService();
    }

    @Override
    protected void loadData() {

    }

    private void bindRemoteService() {
        bindService(new Intent(this, RemoteCoreService.class), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                CoreBinder coreBinder = CoreBinder.Stub.asInterface(service);
                try {
                    JLog.d(coreBinder.getContent());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, BIND_AUTO_CREATE);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}
