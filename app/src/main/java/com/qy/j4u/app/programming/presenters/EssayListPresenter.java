package com.qy.j4u.app.programming.presenters;

import com.qy.j4u.app.programming.views.EssayListView;
import com.qy.j4u.base.BasePresenter;
import com.qy.j4u.model.entity.ITEssayItem;
import com.qy.j4u.model.http.ApiService;
import com.qy.j4u.model.http.ObserverWrapper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EssayListPresenter extends BasePresenter<EssayListView> {

    @Inject
    public EssayListPresenter(EssayListView essayListView, ApiService apiService) {
        super(essayListView, apiService);
    }

    public void getEssayList(int itCategoryId) {
        getApiService().getEssayList(itCategoryId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .as(bindLifecycle())
                        .subscribe(new ObserverWrapper<List<ITEssayItem>>() {
                            @Override
                            public void onSuccess(List<ITEssayItem> itEssayItems) {
                                getView().onEssayList(itEssayItems);
                            }
                        });
    }

}
