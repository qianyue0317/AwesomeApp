package com.qy.j4u.model.http;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public abstract class SubscriberWrapper<T> implements Subscriber<T> {
    @Override
    public void onSubscribe(Subscription s) {

    }


    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onComplete() {

    }
}
