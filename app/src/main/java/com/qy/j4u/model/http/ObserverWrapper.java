package com.qy.j4u.model.http;

import com.qy.j4u.model.entity.Result;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class ObserverWrapper<T> implements Observer<Result<T>> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Result<T> tResult) {
        if (tResult.getCode() == ResponseCode.SUCCESS) {
            onSuccess(tResult.getData());
        }else {
            onError(new Exception(tResult.getMsg()));
        }
    }

    public abstract void onSuccess(T t);

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

}
