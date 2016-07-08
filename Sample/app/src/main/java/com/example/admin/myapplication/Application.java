package com.example.admin.myapplication;

import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;

/**
 * Created by admin on 2016/07/08.
 */
public class Application extends android.app.Application {

    private static Application _instance;

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;

        NoHttp.initialize(this);

        Logger.setTag("NoHttpSample");
        Logger.setDebug(true);// 开始NoHttp的调试模式, 这样就能看到请求过程和日志

//        AppConfig.getInstance();
    }

    public static Application getInstance() {
        return _instance;
    }

}
