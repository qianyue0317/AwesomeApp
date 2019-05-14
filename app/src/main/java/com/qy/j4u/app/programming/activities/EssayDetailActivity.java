package com.qy.j4u.app.programming.activities;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.qmuiteam.qmui.widget.webview.QMUIWebView;
import com.qy.j4u.R;
import com.qy.j4u.base.BaseActivity;
import com.qy.j4u.global.constants.TransferKeys;
import com.qy.j4u.utils.ARouterWrapper;

import androidx.annotation.Nullable;
import butterknife.BindView;

@Route(path = ARouterWrapper.Route.ESSAY_DETAIL)
public class EssayDetailActivity extends BaseActivity {

    @Autowired(name = TransferKeys.ESSAY_URL)
    String mUrl;
    @Autowired(name = TransferKeys.ESSAY_TITLE)
    String mTitle;
    @BindView(R.id.web_view)
    QMUIWebView mQMUIWebView;

    @Override
    protected void daggerInject() {

    }

    @Override
    protected String getToolBarTitle() {
        return mTitle;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_essay_detail;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        mQMUIWebView.loadUrl(mUrl);
    }

    @Override
    protected void loadData() {

    }
}
