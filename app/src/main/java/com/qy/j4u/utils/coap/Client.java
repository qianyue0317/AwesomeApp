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
    private ExecutorService mExecutor;
    private Handler mHandler;

    public interface Callback{
        void onResponse(CoapResponse response);

        void onError(Exception e);
    }

    private Client() {
        mClient = new CoapClient();
        mExecutor = Executors.newFixedThreadPool(5);
        mHandler = new Handler(Looper.getMainLooper());
    }

    private static class InsHolder {
        private static Client ins = new Client();
    }

    public static Client getIns() {
        return InsHolder.ins;
    }

    public void setUri(String uri) {
        if (mClient != null) {
            mClient.setURI(uri);
        }
    }

    public void get(final Callback callback) {
        if (mClient != null) {
            mExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {

                        final CoapResponse coapResponse = mClient.get();
                        if (callback != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onResponse(coapResponse);
                                }
                            });
                        }
                    } catch (final Exception e) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (callback != null) {
                                    callback.onError(e);
                                }
                            }
                        });
                    }
                }
            });
        }
    }

}
