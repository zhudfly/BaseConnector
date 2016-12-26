package com.connector.zhudf.utilsmodule.cache;

import android.support.v4.util.LruCache;

/**
 * Created by zhangbiao on 2016/5/24.
 */
public class MemoryCache{
    public static MemoryCache memoryCache;
    int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

    /**
    * 缓存json数据
    */
    private LruCache<String, String> mJsonCache;

    private MemoryCache(){
        mJsonCache = new LruCache<String, String>(maxMemory/10);
    }
    /**
     * 单例模式，获取instance实例
     */
    public static MemoryCache getInstance(){
        if(null== memoryCache){
            memoryCache=new MemoryCache();
        }
        return memoryCache;
    }

    /**
     * 添加进入缓存列表
     * @param key
     * @param value
     */
    public void putStringCache(String key, String value){
        mJsonCache.put(key, value);
    }

    /**
     * 从缓存列表中拿出来
     * @param key
     * @return
     */
    public String getStringCache(String key) {
        return mJsonCache.get(key);
    }


    public boolean isHaveData(String key){
        return getStringCache(key)!=null;
    }
    /**
     * 清除内存缓存
     */
    public void clearCache(){
        if(memoryCache!=null)
            memoryCache.clearCache();
    }
}
