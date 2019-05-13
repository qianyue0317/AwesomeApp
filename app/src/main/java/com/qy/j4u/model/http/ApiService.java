package com.qy.j4u.model.http;


import com.qy.j4u.global.User;
import com.qy.j4u.global.constants.Urls;
import com.qy.j4u.model.entity.Result;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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
    @POST(Urls.Account.LOGIN)
    @FormUrlEncoded
    Observable<Result<User>> loginWithUUid(@Field("uuid")String uuid);
}
