package com.qy.j4u.app.programming.activities;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.qy.j4u.R;
import com.qy.j4u.app.programming.presenters.EssayListPresenter;
import com.qy.j4u.app.programming.views.EssayListView;
import com.qy.j4u.base.MVPBaseActivity;
import com.qy.j4u.di.components.DaggerEssayListComponent;
import com.qy.j4u.di.modules.EssayListModule;
import com.qy.j4u.global.ForUApplication;
import com.qy.j4u.utils.ARouterWrapper;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

@Route(path = ARouterWrapper.Route.ESSAY_LIST)
public class EssayListActivity extends MVPBaseActivity<EssayListPresenter> {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void daggerInject() {
        DaggerEssayListComponent.builder().essayListModule(new EssayListModule(mView))
                .netComponent(ForUApplication.getInstance().getNetComponent())
                .build().inject(this);
    }

    @Override
    protected String getToolBarTitle() {
        return "文章列表";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_essay_list;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
    }

    private EssayListView mView = new EssayListView() {
        @Override
        public void showLoading(String msg) {

        }

        @Override
        public void hideLoading() {

        }
    };


}
