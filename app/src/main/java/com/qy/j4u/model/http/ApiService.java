package com.qy.j4u.model.http;


import com.qy.j4u.global.User;
import com.qy.j4u.global.constants.Urls;
import com.qy.j4u.model.entity.ITEssayItem;
import com.qy.j4u.model.entity.Result;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;


/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/7/21
 * Owspace
 */
public interface ApiService {
    /**
     * 用uuid 登录
     * @param uuid 设备唯一标识
     */
    @Headers("test_annotation_header:value")
    @POST(Urls.Account.LOGIN)
    @FormUrlEncoded
    Observable<Result<User>> loginWithUUid(@Field("uuid")String uuid);
    /**
     * 文章列表
     * @param categoryId 文章类别的id
     */
    @POST(Urls.IT.ESSAY_LIST)
    @FormUrlEncoded
    Observable<Result<List<ITEssayItem>>> getEssayList(@Field("it_category_id")int categoryId);
}
