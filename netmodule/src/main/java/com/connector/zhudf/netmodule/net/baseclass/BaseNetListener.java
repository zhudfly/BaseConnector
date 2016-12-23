package com.connector.zhudf.netmodule.net.baseclass;

/**
 * Created by zhudf on 2016/12/16.
 */

public interface BaseNetListener<T> {

    /**
     * 网络请求开始
     */
    void onStart();

    /**
     * 网络请求成功
     * @param message 提示信息
     * @param data 网络返回的数据
     */
    void onSuccess(String message,T data);

    /**
     * 网络请求错误
     * @param apiException
     */
    void onError(BaseApiException apiException);

    /**
     * 网路请求结束
     */
    void onFinish();
}
