package com.connector.zhudf.netmodule.net.netoperate;

import com.connector.zhudf.netmodule.net.entity.BootAdvEntity;
import com.connector.zhudf.netmodule.net.entity.TokenEntity;
import com.connector.zhudf.netmodule.utils.RetrofitHelper;

import rx.Observable;

/**
 * Created by zhudf on 2016/12/16.
 */

public class NetServiceAction {
    public Observable<BootAdvEntity> getBootPageAdvert(String version){
       return RetrofitHelper.getNetService().getBootPageAdvert(version);
    }

    public Observable<TokenEntity> getAccessToken(String appId, String deviceId, String version, String deviceType, String os){
        return RetrofitHelper.getNetService().getAccessToken(appId,deviceId,version,deviceType,os);
    }

    public Observable<TokenEntity> refreshToken(String refreshToken, String appId){
        return RetrofitHelper.getNetService().refreshToken(refreshToken,appId);
    }
}
