package com.qy.j4u;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.qy.j4u.global.ForUApplication;
import com.qy.j4u.pojo.DaoSession;
import com.qy.j4u.pojo.GreenDaoTestPojo;
import com.qy.j4u.utils.JLog;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ScreenStatusBroadCastReceiver mScreenStatusBroadCastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaoSession daoSession = ForUApplication.getInstance().getGreenDaoSession();
        GreenDaoTestPojo pojo = new GreenDaoTestPojo();
        pojo.setText("这是第二次要插入到数据库的");
        pojo.setDate(new Date());
        daoSession.insert(pojo);
        List<GreenDaoTestPojo> greenDaoTestPojos = daoSession.loadAll(GreenDaoTestPojo.class);
//        JLog.i("id为:" + pojo.getId());
        for (int i = 0; i < greenDaoTestPojos.size(); i++) {
            JLog.i(greenDaoTestPojos.get(i));
        }

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView) findViewById(R.id.tv_hello)).setText("变了");
            }
        });
        mScreenStatusBroadCastReceiver = new ScreenStatusBroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mScreenStatusBroadCastReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mScreenStatusBroadCastReceiver);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    static class ScreenStatusBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            JLog.i(Intent.ACTION_SCREEN_OFF.equals(intent.getAction()) ? "屏幕暗了" : "屏幕亮了");
        }
    }

}
