package com.connector.zhudf.netmodule.net.subscribe;

import com.connector.zhudf.netmodule.net.baseclass.BaseApiException;
import com.connector.zhudf.netmodule.net.baseclass.BaseNetListener;
import com.connector.zhudf.netmodule.utils.Logger;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.ParseException;

import rx.Subscriber;
import rx.functions.Action1;

/**
 * 泛型rxjava观察者
 * Created by zhangbiao on 2016/4/5.
 */
public class ActionSubscriber<T> extends Subscriber<T> {
    public static final int UNLOGIN_CODE=1313;
    public static final int FREQUENT_CODE=1515;

    private Action1<? super T> onNext;
    private BaseNetListener<T> baseNetListener;

    public ActionSubscriber(Action1<? super T> onNext, BaseNetListener<T> actionListener) {
        this.onNext = onNext;
        this.baseNetListener = actionListener;
    }
    @Override
    public void onCompleted() {
        Logger.e("ActionSubscriber","onCompleted");
        if(baseNetListener!=null)
            baseNetListener.onFinish();
    }

    @Override
    public void onError(Throwable e) {
        Logger.e("ActionSubscriber","onError");
        Logger.e("ActionSubscriber",e);
        Throwable throwable = e;
        //获取最根源的异常
        while(throwable.getCause() != null){
            e = throwable;
            throwable = throwable.getCause();
        }
        BaseApiException exception=null;
        if (e instanceof BaseApiException) {
            exception = (BaseApiException) e;
        }else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException){
            exception = new BaseApiException(1000,"解析错误");
        } else if (e instanceof SocketTimeoutException) {
            exception=new BaseApiException(1000,"当前网络较差！");
        } else if (e instanceof ConnectException) {
            exception=new BaseApiException(404,"请检查网络后重试！");
        }else{
            exception=new BaseApiException(1000,"网络异常！");
        }

        if(baseNetListener!=null)
            baseNetListener.onError(exception);
        if(baseNetListener!=null)
            baseNetListener.onFinish();
    }
    @Override
    public void onNext(T t) {
        Logger.e("ActionSubscriber","onNext");
        if(!this.isUnsubscribed())
            onNext.call(t);
    }
}
