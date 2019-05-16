package com.qy.j4u.model.http;

import com.qy.j4u.global.User;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder()
                .addHeader("test_header", "test header")
                .addHeader("token", User.getUser().getToken())
                .addHeader("user_id", String.valueOf(User.getUser().getId()));
        Request request = builder.build();
        return chain.proceed(request);
    }
}
