package com.qy.j4u.app.pccontroller;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.qy.j4u.R;
import com.qy.j4u.utils.coap.Client;

import org.eclipse.californium.core.CoapResponse;

public class CoapControlActivity extends AppCompatActivity implements Client.Callback {

    private static final String TAG = CoapControlActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coap);
        findViewById(R.id.btn).setOnClickListener(v -> {
            Client client = new Client();
            client.setUri("192.168.1.104:5683/mute");
            client.get(CoapControlActivity.this);
        });
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public void onResponse(CoapResponse response) {
        Log.i(TAG, "onResponse: " + response);
    }

    @Override
    public void onError(Exception e) {
        Log.i(TAG, "onError: " + e);
    }
}
