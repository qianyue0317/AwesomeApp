package com.qy.j4u;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

@Route(path = "/testARouter/activity")
public class TestARouterActivity extends FragmentActivity {

    private TextView mTv;
    @Autowired(name = "passStr")
    String mPassedStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ARouter.getInstance().inject(this);
        mTv = findViewById(R.id.tv_hello);
        mTv.setText(mPassedStr);
    }


}
