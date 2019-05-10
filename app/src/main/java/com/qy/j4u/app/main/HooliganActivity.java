package com.qy.j4u.app.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.qy.j4u.global.ForUApplication;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 1像素activity 用于锁屏保活
 */
public class HooliganActivity extends AppCompatActivity {
    private static HooliganActivity instance;

    @SuppressLint("RtlHardcoded")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        Window window = getWindow();
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.height = 1;
        params.width = 1;
        window.setAttributes(params);
    }

    /**
     * 开启保活页面
     */
    public static void startHooligan() {
        Intent intent = new Intent(ForUApplication.getInstance(), HooliganActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ForUApplication.getInstance().startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
    }

    /**
     * 关闭保活页面
     */
    public static void killHooligan() {
        if(instance != null) {
            instance.finish();
        }
    }
}
