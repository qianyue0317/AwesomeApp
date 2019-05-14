package com.qy.j4u.app.programming.activities;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qy.j4u.R;
import com.qy.j4u.app.programming.presenters.EssayListPresenter;
import com.qy.j4u.app.programming.views.EssayListView;
import com.qy.j4u.base.MVPBaseActivity;
import com.qy.j4u.di.components.DaggerEssayListComponent;
import com.qy.j4u.di.modules.EssayListModule;
import com.qy.j4u.global.ForUApplication;
import com.qy.j4u.global.constants.TransferKeys;
import com.qy.j4u.model.entity.ITEssayItem;
import com.qy.j4u.utils.ARouterWrapper;
import com.qy.j4u.utils.collectionutil.CollectionKit;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

@Route(path = ARouterWrapper.Route.ESSAY_LIST)
public class EssayListActivity extends MVPBaseActivity<EssayListPresenter> {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty_view)
    QMUIEmptyView mEmptyView;
    private List<ITEssayItem> mData;
    private BaseQuickAdapter<ITEssayItem, BaseViewHolder> mAdapter;
    @Autowired(name = TransferKeys.CATEGORY_ID)
    int mCategoryId;

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
        mData = new ArrayList<>();
        mAdapter =
                new BaseQuickAdapter<ITEssayItem, BaseViewHolder>(android.R.layout.simple_list_item_1, mData) {
            @Override
            protected void convert(BaseViewHolder helper, ITEssayItem item) {
                helper.setText(android.R.id.text1, item.getTitle());
            }
        };
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            ITEssayItem itEssayItem = mData.get(position);
            ARouterWrapper.build(ARouterWrapper.Route.ESSAY_DETAIL)
                    .withString(TransferKeys.ESSAY_TITLE, itEssayItem.getTitle())
                    .withString(TransferKeys.ESSAY_URL, itEssayItem.getUrl())
                    .navigation(EssayListActivity.this);
        });
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,
                false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mEmptyView.setOnClickListener(v -> loadData());
    }

    @Override
    protected void loadData() {
        getPresenter().getEssayList(mCategoryId);
    }

    private EssayListView mView = new EssayListView() {
        @Override
        public void onEssayList(List<ITEssayItem> itEssayItems) {
            mData.clear();
            mData.addAll(itEssayItems);
            mAdapter.notifyDataSetChanged();
            mEmptyView.hide();
            if (CollectionKit.isEmpty(mData)) {
                mEmptyView.show("", getString(R.string.str_empty_tip_no_data));
            }
        }

        @Override
        public void onError() {
            mEmptyView.setDetailText(getString(R.string.str_empty_tip_retry));
            mEmptyView.show();
        }

        @Override
        public void showLoading(String msg) {
            mEmptyView.show(true);
        }

        @Override
        public void hideLoading() {
            mEmptyView.hide();
        }
    };


}
