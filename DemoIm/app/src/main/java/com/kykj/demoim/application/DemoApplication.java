package com.kykj.demoim.application;

import android.app.Application;

import com.netease.nimlib.sdk.NIMClient;

/**
 * Created by vectoria on 2017/6/27.
 */

public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //SDK初始化，如果已经存在登录用户，则SDK完成自动登录
        /**
         * 1.this
         * 2.自动登录的参数
         * 3.一些config
         */
        NIMClient.init(this, null, null);
    }

}