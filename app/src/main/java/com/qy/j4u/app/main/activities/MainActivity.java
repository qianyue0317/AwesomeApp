package com.qy.j4u.app.main.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.qy.j4u.CoreBinder;
import com.qy.j4u.R;
import com.qy.j4u.app.main.fragments.ITCategoryFragment;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = ARouterWrapper.Route.MAIN)
public class MainActivity extends MVPBaseActivity<MainPresenter> {


    @BindView(R.id.fl_container)
    FrameLayout mFrameContainer;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mNavigationBar;


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
        mNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switchFragment(position);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
        mNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.code, "IT"))
                .addItem(new BottomNavigationItem(R.mipmap.hardware, "树莓派"))
                .addItem(new BottomNavigationItem(R.mipmap.mine, "我的"))
                .setFirstSelectedPosition(0)
                .initialise();
        bindRemoteService();
    }


    private void switchFragment(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_container, ITCategoryFragment.getInstance());
        fragmentTransaction.commit();
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




    private MainView mView = new MainView() {
        @Override
        public void showLoading(String msg) {

        }

        @Override
        public void hideLoading() {

        }

        @Override
        public void testProxy(String content) {

        }

    };

}
