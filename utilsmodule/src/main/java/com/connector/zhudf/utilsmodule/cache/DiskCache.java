package com.connector.zhudf.utilsmodule.cache;

import android.content.Context;

/**
 * Created by zhangbiao on 2016/5/24.
 */
public class DiskCache{
    public static DiskCache diskCache;
    private ACache mAcache;
    private Context mContext;

    private DiskCache(){

    }

    /**
     * 单例模式，获取instance实例
     */
    public static DiskCache getInstance(){
        if(null== diskCache){
            diskCache = new DiskCache();
        }
        return diskCache;
    }

    public void init(Context context,String cacheName){
        this.mContext = context;
        mAcache=ACache.get(context,cacheName);
    }
    /**
     * 缓存String值
     * @param key
     * @param value
     */
    public void putStringData(String key,String value){
        mAcache.put(key,value);
    }
    /**
     * 缓存String值
     * @param key
     * @param value
     * @param saveTime
     */
    public void putStringData(String key,String value,int saveTime){
        mAcache.put(key,value,saveTime);
    }

    /**
     * 获取String缓存值
     * @param key
     * @return
     */
    public String getStringData(String key){
        return mAcache.getAsString(key);
    }

    public boolean isHaveData(String key){
        return mAcache.getAsString(key)!=null;
    }
}
