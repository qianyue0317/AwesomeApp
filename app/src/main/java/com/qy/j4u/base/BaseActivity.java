package com.qy.j4u.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.qy.j4u.R;
import com.qy.j4u.app.main.SplashActivity;
import com.qy.j4u.global.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Activity的基类
 * Created by abc on 2016/11/2.
 */
public abstract class BaseActivity extends AppCompatActivity {

//    /**
//     * 进度条
//     */
//    private ProgressInterface mProgress;
//
//    private boolean mNeedHideKeyboard = true;
//
//    // 不需要进行友盟统计的页面集合
//    private static List<String> sNotUm = new ArrayList<>();
//
//    static {
//        sNotUm.add(SubmitAnswerActivity.class.getName());
//        sNotUm.add(TopicChooseActivity.class.getName());
//        sNotUm.add(ReadOverActivity.class.getName());
//        sNotUm.add(GuideActivity.class.getName());
//        sNotUm.add(LoginActivity.class.getName());
//    }
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        if (savedInstanceState != null) {
//            // 恢复之后将用户信息也要恢复保存起来
//            User user = (User) savedInstanceState.getSerializable("user");
//            User.updateUser(user);
//            User.setupLocalUser();
//            // 可能是切换权限的恢复 应该是再初始化一遍
//            Constant.initConstant(this);
//        }
//
//
//        PrimaryZSDApplication.sActivities.put(getClass().getName(), this);
//        LoLog.e("---添加进来的Activity名称:" + getClass().getSimpleName());
//        initProgressDialog();
//
//        initVariables();
//        initView(savedInstanceState);
//        try {
//            loadData();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Constant.initConstant(this);
//
//        String name = getClass().getName();
//
//        if (sNotUm.contains(name)) {
//            return;
//        }
//
//
//        FragmentManager supportFragmentManager = getSupportFragmentManager();
//        List<Fragment> fragments = supportFragmentManager.getFragments();
//        if (!SplashActivity.class.getName().equals(getClass().getName())) {// 不统计闪屏
//            // 如果不是闪屏
//            if (fragments == null || fragments.size() == 0) {// 说明当前Activity没有Fragment
//                MobclickAgent.onPageStart(getClass().getName());
//            }
//            MobclickAgent.onResume(this);
//        }
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//
//        String name = getClass().getName();
//        if (sNotUm.contains(name)) {
//            return;
//        }
//
//        List<Fragment> fragments = getSupportFragmentManager().getFragments();
//        if (!SplashActivity.class.getName().equals(getClass().getName())) {// 不统计闪屏
//            if (fragments == null || fragments.size() == 0) {// 说明当前Activity没有Fragment
//                MobclickAgent.onPageEnd(getClass().getName());
//            }
//            MobclickAgent.onPause(this);
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//
//    }
//
//
//    @Override
//    public void finish() {
//        super.finish();
//        if (needTransition()) {
//            // 如果当前页面不是主页 才是这样的动画
//
//            overridePendingTransition(R.animator.push_right_out, R.animator.push_right_in);
//        }
//    }
//
//
//    public boolean needTransition() {
//        Class<? extends BaseActivity> aClass = getClass();
//        return MainActivity.class != aClass && SplashActivity.class != aClass &&
//                GuideActivity.class != aClass && LoginActivity.class != aClass;
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // 移除Activity
//        LoLog.e("-----移除Activity:" + getClass().getSimpleName());
//        PrimaryZSDApplication.sActivities.remove(getClass().getName());
//        dismissProgress();
//
//    }
//
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        // 异常切换的时候保存本地信息
//        User.setupLocalUser();
//        if (outState != null) {
//            // 保存到savedInstance中
//            outState.putSerializable("user", User.getInstance());
//        }
//    }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                if (mNeedHideKeyboard) {// 如果需要点击空白隐藏键盘的操作,默认可以开启
//                    View view = getCurrentFocus();
//                    KeyboardTool.hideKeyboard(ev, view, BaseActivity.this);//调用方法判断是否需要隐藏键盘
//                }
//                break;
//
//            default:
//                break;
//        }
//        return super.dispatchTouchEvent(ev);
//    }
//
//
//    public void needHideKeyboard(boolean needHideKeyboard) {
//        mNeedHideKeyboard = needHideKeyboard;
//    }
//
//
//    /**
//     * 初始化进度对话框  对象只要是实现了ProgressInterface接口就行
//     */
//    protected void initProgressDialog() {
//        mProgress = new ZsdProgressDialog(this, R.style.ConfirmDialog);
//        // 实例化一个ColorDrawable颜色为透明
//
//    }
//
//    /**
//     * 设置loading框消失监听
//     *
//     * @param dismissListener
//     */
//    public void setOnLoadingDismissListener(DialogInterface.OnDismissListener dismissListener) {
//        if (mProgress != null) {
//            mProgress.setDismissListener(dismissListener);
//        }
//    }
//
//
//    public void startActivity(Class<?> clz, Bundle bundle) {
//        Intent intent = new Intent(this, clz);
//        if (bundle != null) {
//            intent.putExtras(bundle);
//        }
//        startActivity(intent);
//        overridePendingTransition(R.animator.transition_in, R.animator.transition_out);
//    }
//
//    public void startActivity(Class<?> clz) {
//        startActivity(clz, null);
//    }
//
//    public void startActivityForResult(Class<?> clz, Bundle bundle, int requestCode) {
//        Intent intent = new Intent(this, clz);
//        if (bundle != null) {
//            intent.putExtras(bundle);
//        }
//        startActivityForResult(intent, requestCode);
//        if (needTransition()) {
//            overridePendingTransition(R.animator.transition_in, R.animator.transition_out);
//        }
//    }
//
//    public void startActivityForResult(Class<?> clz, int requestCode) {
//        startActivityForResult(clz, null, requestCode);
//    }
//
//    /**
//     * 带转场动画的跳转页面
//     *
//     * @param clz  目标页面的class对象
//     * @param view 跳转动画所依赖的控件
//     */
//    public void startAniActivity(Class<?> clz, View view) {
//        Intent intent = new Intent(this, clz);
//    }
//
//
//    /**
//     *
//     */
//    protected void immersion() {
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = /*View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    |*/ View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    | View.SYSTEM_UI_FLAG_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LOW_PROFILE
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//
//            decorView.setSystemUiVisibility(option);
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
//    }
//
//    /**
//     * 透明状态栏和导航栏
//     */
//    protected void transparentNaviAndStatus() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
//    }
//
//    protected void transparentNavi() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
//    }
//
//    protected void transparentStatus() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//    }
//
//    /**
//     * 避免按键抖动的设置监听器 两秒内的点击只取其中一次
//     *
//     * @param view            被点击的view
//     * @param onClickListener 监听器
//     */
//    protected void setOnClickSolveShake(final View view, final View.OnClickListener
//            onClickListener) {
//        RxView.clicks(view).throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
//            @Override
//            public void accept(Object o) throws Exception {
//                onClickListener.onClick(view);
//            }
//        });
//    }
//
//
//    /**
//     * 请求全屏
//     */
//    protected void requestFullScreen() {
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//    }
//
//
//    /**
//     * 隐藏导航栏
//     */
//    protected void hideNavigationBar() {
//        if (Build.VERSION.SDK_INT < 15) {
//            return;
//        }
//        View decorView = getWindow().getDecorView();
//        // Hide both the navigation bar and the status bar.
//        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
//        // a general rule, you should design your app to hide the status bar whenever you
//        // hide the navigation bar.
//        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);
//    }
//
//
//    /**
//     * 初始化一些变量 例如从上个页面传递过来的
//     */
//    protected abstract void initVariables();
//
//    /**
//     * 初始化控件
//     *
//     * @param savedInstanceState onCreate的参数
//     */
//    protected abstract void initView(@Nullable Bundle savedInstanceState);
//
//    /**
//     * 加载数据
//     */
//    protected abstract void loadData();
//
//
//    /**
//     * 显示进度框
//     *
//     * @param cancelable 是否可取消
//     */
//    public void showProgress(boolean cancelable) {
//        if (mProgress == null) {
//            return;
//        }
//        mProgress.setProCancelable(cancelable);
//        mProgress.showProgress();
//    }
//
//    /**
//     * 显示指定信息的loading框
//     *
//     * @param cancelable 是否可取消
//     * @param message    显示的信息
//     */
//    public void showProgress(boolean cancelable, String message) {
//        if (mProgress != null) {
//            mProgress.setMessage(message);
//            showProgress(cancelable);
//        }
//    }
//
//
//    /**
//     * 隐藏进度条
//     */
//    public void hideProgress() {
//        if (mProgress == null) {
//            return;
//        }
//        mProgress.hideProgress();
//    }
//
//    /**
//     * 修改loading信息
//     *
//     * @param newMessage
//     */
//    public void changeProgressMsg(String newMessage) {
//        if (mProgress == null) {
//            return;
//        }
//        mProgress.changeMessage(newMessage);
//    }
//
//    /**
//     * 销毁进度条,, 在onDestroy方法中调用
//     */
//    private void dismissProgress() {
//        if (mProgress == null) {
//            return;
//        }
//        mProgress.dismissProgress();
//    }
//
//
//    public void showAlertDialog() {
//
//    }
//
//
//    /**
//     * 显示时间短的toast 在ui线程用
//     *
//     * @param content 内容
//     */
//    @UiThread
//    public void showShortToast(String content) {
//        ToastUtil.showShortToast(content);
//    }
//
//    @UiThread
//    public void showShortToast(int resId) {
//        ToastUtil.showShortToast(resId);
//    }
//
//    /**
//     * 显示时间长的toast,在ui线程用到
//     *
//     * @param content 内容
//     */
//    @UiThread
//    public void showLongToast(String content) {
//        ToastUtil.showLongToast(content);
//    }
//
//    @UiThread
//    public void showLongToast(int resId) {
//        ToastUtil.showLongToast(resId);
//    }
//
//    /**
//     * 在子线程可用的toast
//     *
//     * @param content
//     */
//    public void showShorToastOnSubThread(String content) {
//        ToastUtil.showShortToastSafe(content);
//    }
//
//    public void showShorToastOnSubThread(int resId) {
//        ToastUtil.showShortToastSafe(resId);
//    }
//
//
//    /**
//     * 在子线程可用的toast
//     *
//     * @param content
//     */
//    public void showLongToastOnSubThread(String content) {
//        ToastUtil.showLongToastSafe(content);
//    }
//
//    public void showLongToastOnSubThread(int resId) {
//        ToastUtil.showLongToastSafe(resId);
//    }


}
