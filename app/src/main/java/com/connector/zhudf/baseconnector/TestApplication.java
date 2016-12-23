package com.connector.zhudf.baseconnector;

import android.app.Application;

import com.connector.zhudf.netmodule.utils.NetConfig;

/**
 * Created by zhudf on 2016/12/13.
 */

public class TestApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        NetConfig netConfig = NetConfig.getInstance();
        netConfig.init(this);
    }
}
