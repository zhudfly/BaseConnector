package com.connector.zhudf.netmodule.net.netoperate;

import android.text.TextUtils;

import com.connector.zhudf.netmodule.R;
import com.connector.zhudf.netmodule.net.entity.TokenEntity;
import com.connector.zhudf.netmodule.net.subscribe.ActionSubscriber;
import com.connector.zhudf.netmodule.net.baseclass.BaseNetConnector;
import com.connector.zhudf.netmodule.net.baseclass.BaseNetListener;
import com.connector.zhudf.netmodule.net.entity.BootAdvEntity;
import com.connector.zhudf.netmodule.utils.GsonUtils;
import com.connector.zhudf.netmodule.utils.Logger;
import com.connector.zhudf.netmodule.utils.NetConfig;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zhudf on 2016/10/11.
 * 调用网络请求时，只通过调用该类实现
 */

public class NetConnector extends BaseNetConnector {
    private final NetServiceAction netServiceAction;

    public NetConnector() {
        netServiceAction = new NetServiceAction();

    }

    public void getBootPageAdvert(final String version, BaseNetListener listener) {
        commonDispose(listener, new Func1<Object, Observable<?>>() {
            @Override
            public Observable<BootAdvEntity> call(Object o) {
                return netServiceAction.getBootPageAdvert(version);

            }
        }, "getBootPageAdvert");
    }

    /**
     * 获得app的AccessToken
     *
     * @param logMsg
     * @return
     */
    public Observable<TokenEntity> getAccessToken(final String logMsg) {
        try {
            return netServiceAction.getAccessToken(
                    NetConfig.getInstance().getAppId()
                    , NetConfig.getInstance().getDeviceId()
                    , NetConfig.getInstance().getVersion()
                    , NetConfig.getInstance().getDeviceType()
                    , NetConfig.getInstance().getOsVersion())
                    .subscribeOn(Schedulers.io())
                    .doOnNext(new Action1<TokenEntity>() {
                        @Override
                        public void call(TokenEntity tokenEntity) {
                            Logger.e(logMsg + "getAccessToken --> retryWhen"
                                    , NetConfig.getInstance().getContext().getResources().getString(R.string.get_access_token_success));
                            //将token保存在内存中
                            NetConfig.getInstance().setTokenEntity(tokenEntity);
                        }
                    }).doOnError(new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Logger.e(logMsg + "getAccessToken --> retryWhen"
                                    , NetConfig.getInstance().getContext().getResources().getString(R.string.get_access_token_fail));
                        }
                    });
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
        return null;
    }


    /**
     * 刷新app的AccessToken
     *
     * @param logMsg
     * @return
     */
    public Observable<TokenEntity> refreshAccessToken(final String logMsg) {
        try {
            String refreshToken = "";
            if (NetConfig.getInstance().getTokenEntity() != null
                    && !TextUtils.isEmpty(NetConfig.getInstance().getTokenEntity().getRefresh_token())) {
                refreshToken = NetConfig.getInstance().getTokenEntity().getRefresh_token();
            }
            return netServiceAction.refreshToken(refreshToken,
                    NetConfig.getInstance().getAppId())
                    .subscribeOn(Schedulers.io())
                    .doOnNext(new Action1<TokenEntity>() {
                        @Override
                        public void call(TokenEntity tokenEntity) {
                            Logger.e(logMsg + "refreshAccessToken --> retryWhen"
                                    , NetConfig.getInstance().getContext().getResources().getString(R.string.refresh_access_token_success));
                            Logger.e(logMsg + "refreshAccessToken"
                                    , GsonUtils.gson.toJson(tokenEntity));

                            NetConfig.getInstance().setTokenEntity(tokenEntity);
                        }
                    }).doOnError(new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Logger.e(logMsg + "refreshAccessToken --> retryWhen"
                                    , NetConfig.getInstance().getContext().getResources().getString(R.string.refresh_access_token_fail));
                        }
                    });
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
        return null;
    }

    /**
     * 执行接口的基础操作
     *
     * @param listener 接口的监听器
     * @param fun      访问接口的操作
     */
    private void commonDispose(final BaseNetListener listener
            , Func1<Object, Observable<?>> fun
            , String funcName) {
        if (listener != null) {
            listener.onStart();
        }

        Subscription subscription = Observable.just(null)
                .flatMap(fun)
                .retryWhen(new NetRetryFunction(funcName))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(createActionSubscribe(listener));

        getCompositeSubscription().add(subscription);
    }

    /**
     * 创建一个订阅
     *
     * @param listener 接口的监听器
     * @return
     */
    private ActionSubscriber createActionSubscribe(final BaseNetListener listener) {
        ActionSubscriber actionSubscriber = new ActionSubscriber<Object>(new Action1<Object>() {
            @Override
            public void call(Object data) {
                if (listener != null) {
                    listener.onSuccess("Success", data);
                }
            }
        }, listener);
        return actionSubscriber;
    }
}
