package com.connector.zhudf.netmodule.net.netoperate;

import com.connector.zhudf.netmodule.net.exception.RegisterException;
import com.connector.zhudf.netmodule.net.exception.RefreshAccessException;
import com.connector.zhudf.netmodule.utils.Logger;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by zhudf on 2016/12/22.
 */

public class NetRetryFunction implements Func1<Observable<? extends Throwable>,Observable<?>> {
    public String logMsg = "";
    public NetRetryFunction(String logMsg){

    }

    //处理重试时所需要执行的内容，比如注册，刷新accessToken
    @Override
    public Observable<?> call(Observable<? extends Throwable> observable) {
        return observable.flatMap(new Func1<Throwable, Observable<?>>() {
            @Override
            public Observable<?> call(Throwable throwable) {
                Logger.e(logMsg + " --> retryWhen " + throwable.getMessage());
                if(throwable instanceof RegisterException){
                    //执行注册操作
                    Logger.e(logMsg + " --> RegisterException register");
                    return new NetConnector().getAccessToken(logMsg);
                }
                else if(throwable instanceof RefreshAccessException){
                    //执行刷新操作
                    Logger.e(logMsg + " --> RefreshAccessException refreshAccess");
                    return new NetConnector().refreshAccessToken(logMsg);
                }
                return null;
            }
        });
    }
}
