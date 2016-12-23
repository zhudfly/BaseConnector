package com.connector.zhudf.netmodule.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by zhangbiao on 2016/4/15.
 */
public class GsonUtils {
    public static Gson gson = null; // 声明gson对象
    static {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create(); // 创建gson对象，并设置日期格式
    }
}
