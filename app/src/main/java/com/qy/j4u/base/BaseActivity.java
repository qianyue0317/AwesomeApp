package com.qy.j4u.base;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.j4u.j4uLib.InstanceStateInjector;
import com.jakewharton.rxbinding3.view.RxView;
import com.qy.j4u.R;
import com.qy.j4u.app.main.activities.MainActivity;
import com.qy.j4u.app.main.activities.SplashActivity;
import com.qy.j4u.global.User;
import com.qy.j4u.utils.KeyboardTool;
import com.qy.j4u.utils.RxLifecycleUtils;
import com.qy.j4u.widget.dialog.DefaultLoading;
import com.qy.j4u.widget.dialog.LoadingInterface;
import com.uber.autodispose.AutoDisposeConverter;

import java.util.concurrent.TimeUnit;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

/**
 * Activity的基类
 * Created by qy on 2016/11/2. modified by qy on 2019/05/10
 */
@SuppressWarnings("unused")
public abstract class BaseActivity extends AppCompatActivity {

    private boolean mNeedHideKeyboard = true;
    private LoadingInterface mLoadingInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InstanceStateInjector.restore(this, savedInstanceState);
        mLoadingInterface = new DefaultLoading();
        if (savedInstanceState != null) {
            User.initFromLocal();
        }
        /* ***************注入传递过来的参数************** */
        ARouter.getInstance().inject(this);
        daggerInject();
        if (TextUtils.isEmpty(getToolBarTitle())) {
            setContentView(provideContentViewId());
        } else {
            setContentView(R.layout.activity_base);
            FrameLayout content = findViewById(R.id.fl_content);
            content.addView(getLayoutInflater().inflate(provideContentViewId(), content, false));
            TextView tvTitle = findViewById(R.id.tv_base_title);
            TextView tvSecondTitle = findViewById(R.id.tv_base_second_title);
            ImageView ivBack = findViewById(R.id.iv_base_back);
            ImageView ivSecondIcon = findViewById(R.id.iv_base_second_icon);
            if (!TextUtils.isEmpty(getToolBarSecondTitle())) {
                tvSecondTitle.setVisibility(View.VISIBLE);
                tvSecondTitle.setText(getToolBarSecondTitle());
                setOnClickSolveShake(tvSecondTitle, this::onSecondTitleClick);
            }
            if (getToolBarSecondIcon() != 0) {
                ivSecondIcon.setVisibility(View.VISIBLE);
                ivSecondIcon.setImageResource(getToolBarSecondIcon());
                setOnClickSolveShake(ivSecondIcon, this::onSecondIconClick);
            }
            tvTitle.setText(getToolBarTitle());
            ivBack.setOnClickListener(v -> finish());
        }
        /* ****************注入控件***************** */
        ButterKnife.bind(this);
        initVariables();
        initView(savedInstanceState);
    }

    public void showLoading(String content,boolean cancelable) {
        mLoadingInterface.show(this, content, cancelable);
    }

    public void hide() {
        mLoadingInterface.hide();
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            /* 由于在BasePresenter中mLifecycleOwner变量的赋值是在onCreate中进行的,而如果
             presenter加载数据在Activity的onCreate中调用BasePresenter中的mLifecycleOwner
              是还没有赋值的 故将加载数据放到了Activity的onStart方法中了 */
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * rxJava observer绑定生命周期
     */
    protected <T> AutoDisposeConverter<T> bindLifecycle() {
        return RxLifecycleUtils.bindLifecycle(this);
    }

    /**
     * 用daggerComponent注入依赖 如果不是用dagger注入则方法内部不用写任何东西
     */
    protected abstract void daggerInject();

    /**
     * 点击右侧图标的回调方法
     */
    protected void onSecondIconClick(View view) {

    }

    /**
     * 点击右侧的选项的回调方法,子类如果需要定制就要重写
     */
    protected void onSecondTitleClick(View v) {

    }


    /**
     * @return 返回toolbar标题 如果返回null或""则不设置标题栏
     */
    protected abstract String getToolBarTitle();


    protected String getToolBarSecondTitle() {
        return "";
    }

    //是否创建ToolBar副标题图标设置
    protected int getToolBarSecondIcon() {
        return 0;
    }


    /**
     * @return 返回布局id
     */
    protected abstract @LayoutRes
    int provideContentViewId();

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void finish() {
        super.finish();
        if (needTransition()) {
            // 如果当前页面不是主页 才是这样的动画
            overridePendingTransition(R.animator.push_right_out, R.animator.push_right_in);
        }
    }


    public boolean needTransition() {
        Class<? extends BaseActivity> aClass = getClass();
        return MainActivity.class != aClass && SplashActivity.class != aClass;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoadingInterface.dismiss();
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        InstanceStateInjector.save(this, outState);
        super.onSaveInstanceState(outState);
        // 异常切换的时候保存本地信息
        User.save();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (mNeedHideKeyboard) {// 如果需要点击空白隐藏键盘的操作,默认可以开启
                View view = getCurrentFocus();
                KeyboardTool.hideKeyboard(ev, view, BaseActivity.this);//调用方法判断是否需要隐藏键盘
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    public void needHideKeyboard(boolean needHideKeyboard) {
        mNeedHideKeyboard = needHideKeyboard;
    }


    protected void immersion() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = /*View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    |*/ View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 透明状态栏和导航栏
     */
    protected void transparentNavAndStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    protected void transparentNav() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    protected void transparentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 避免按键抖动的设置监听器 两秒内的点击只取其中一次
     *
     * @param view            被点击的view
     * @param onClickListener 监听器
     */
    @SuppressLint({"CheckResult", "AutoDispose"})
    protected void setOnClickSolveShake(final View view, final View.OnClickListener
            onClickListener) {
        //noinspection ResultOfMethodCallIgnored
        RxView.clicks(view).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(unit -> onClickListener.onClick(view));
    }


    /**
     * 请求全屏
     */
    protected void requestFullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    /**
     * 隐藏导航栏
     */
    protected void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }


    /**
     * 初始化一些变量 例如从上个页面传递过来的(此项目采用ARouter,采用注解获取传递参数) 或者其他成员变量
     */
    protected abstract void initVariables();

    /**
     * 初始化控件
     *
     * @param savedInstanceState onCreate的参数
     */
    protected abstract void initView(@Nullable Bundle savedInstanceState);

    /**
     * 加载数据,需要presenter监听activity和fragment生命周期,loadData放在onCreate中的话BasePresenter中
     * 的mLifecycleOwner初始化要滞后于loadData,会出现异常.故放在onStart中
     */
    protected abstract void loadData();


}
