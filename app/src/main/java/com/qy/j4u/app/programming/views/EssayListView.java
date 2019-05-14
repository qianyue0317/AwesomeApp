package com.qy.j4u.app.programming.views;

import com.qy.j4u.base.BaseView;
import com.qy.j4u.model.entity.ITEssayItem;

import java.util.List;

public interface EssayListView extends BaseView {
    void onEssayList(List<ITEssayItem> itEssayItems);

    void onError();
}
