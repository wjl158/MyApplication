package com.example.admin.myapplication.base;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.admin.myapplication.unity.ActivityCollector;

/**
 * Created by admin on 2016/05/18.
 */
public class BaseActivity extends AppCompatActivity {


    // 定义一个变量，来标识是否退出

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        ActivityCollector.removeActivity(this);
        super.onDestroy();
    }

    /**
     * 退出应用程序 需要权限<android:uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES"/>
     */
    public void AppExit(Context context) {
//        try {
            ActivityCollector.finishAll();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
//        } catch (Exception ignored) {}
    }
}
