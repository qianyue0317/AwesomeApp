package com.qy.j4u.utils.coap;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.server.resources.CoapExchange;

/**
 * Created by qianyue on 2018/7/12.
 */

public class Server extends Service {

    CoapServer mServer;

    @Override
    public void onCreate() {
        this.mServer = new CoapServer();
        mServer.add(new HelloWorldResource());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mServer.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mServer.destroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class HelloWorldResource extends CoapResource {

        HelloWorldResource() {

            // set resource identifier
            super("hello");

            // set display name
            getAttributes().setTitle("Hello-World Resource");
        }

        @Override
        public void handleGET(CoapExchange exchange) {

            // respond to the request
            exchange.respond("Hello Android!");
        }
    }
}
