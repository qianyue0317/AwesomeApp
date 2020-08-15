package com.qy.j4u.utils.coap;

import android.os.Handler;
import android.os.Looper;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by qianyue on 2018/7/12.
 */

public class Client {

    private CoapClient mClient;
    private static ExecutorService mExecutor;
    private static Handler mHandler;

    public Client() {
        mClient = new CoapClient();
    }

    static {
        mExecutor = Executors.newFixedThreadPool(5);
        mHandler = new Handler(Looper.getMainLooper());
    }

    public interface Callback{
        void onResponse(CoapResponse response);

        void onError(Exception e);
    }


    public void setUri(String uri) {
        if (mClient != null) {
            mClient.setURI(uri);
        }
    }

    public void get(final Callback callback) {
        if (mClient != null) {
            mExecutor.execute(() -> {
                try {
                    final CoapResponse coapResponse = mClient.get();
                    if (callback != null) {
                        mHandler.post(() -> callback.onResponse(coapResponse));
                    }
                } catch (final Exception e) {
                    mHandler.post(() -> {
                        if (callback != null) {
                            callback.onError(e);
                        }
                    });
                }
            });
        }
    }

}
