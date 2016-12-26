package com.connector.zhudf.utilsmodule.utils;

import android.content.Context;

import com.connector.zhudf.utilsmodule.cache.DiskCache;

/**
 * Created by zhudf on 2016/12/23.
 */

public class BaseConfig {
    private static BaseConfig baseConfig;

    private BaseConfig(){}

    public static BaseConfig getInstance(){
        if(baseConfig == null){
            baseConfig = new BaseConfig();
        }
        return baseConfig;
    }

    private boolean isDebug = true;
    private Context mContext;

    public void init(Context context){
        //初始化上下文，一般为Application的context
        this.mContext = context;

        //获得缓存的文件名
        String cacheName = AppInfoUtil.getLastPackageName() + "-" +AppInfoUtil.getVersionName();
        //初始化缓存的类
        DiskCache.getInstance().init(context,cacheName);
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }
}
