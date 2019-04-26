package com.qy.j4u;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qy.j4u.global.ForUApplication;
import com.qy.j4u.pojo.DaoSession;
import com.qy.j4u.pojo.GreenDaoTestPojo;
import com.qy.j4u.utils.JLog;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
    }
}
