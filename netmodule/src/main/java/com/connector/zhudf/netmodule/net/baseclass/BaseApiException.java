package com.connector.zhudf.netmodule.net.baseclass;

/**
 * Created by zhudf on 2016/12/16.
 * 网络请求的异常类
 */
public class BaseApiException extends Exception{
    //错误码
    public int code;
    //错误信息
    public String message;

    public BaseApiException(int code, String message){
        this.code = code;
        this.message = message;
    }
}
