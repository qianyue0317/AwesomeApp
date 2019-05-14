package com.qy.j4u.app.programming.presenters;

import com.qy.j4u.app.programming.views.EssayListView;
import com.qy.j4u.base.BasePresenter;
import com.qy.j4u.model.http.ApiService;

import javax.inject.Inject;

public class EssayListPresenter extends BasePresenter<EssayListView> {

    @Inject
    public EssayListPresenter(EssayListView essayListView, ApiService apiService) {
        super(essayListView, apiService);
    }



}
