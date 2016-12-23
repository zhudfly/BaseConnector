package com.connector.zhudf.netmodule.net.netoperate;

import com.connector.zhudf.netmodule.net.entity.BootAdvEntity;
import com.connector.zhudf.netmodule.net.entity.TokenEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhudf on 2016/10/11.
 */

public interface NetServiceInterface {

    /**
     * 获得启动页广告
     * @param version
     * @return
     */
    @GET("v1/extras/getBootpageAdvert")
    Observable<BootAdvEntity> getBootPageAdvert(@Query("version") String version);

    /**
     * 根据appid注册应用获得accesstoken
     * @param appId
     * @param deviceId
     * @param version
     * @param deviceType
     * @param os
     * @return
     */
    @FormUrlEncoded
    @POST("v1/admin/getAccessToken")
    Observable<TokenEntity> getAccessToken(@Field("appId") String appId, @Field("deviceId") String deviceId, @Field("version") String version, @Field("deviceType") String deviceType, @Field("os")String os);

    /**
     * 根据RefreshToken刷新AccessToken
     * @param refreshToken
     * @param appId
     * @return
     */
    @FormUrlEncoded
    @POST("v1/admin/refreshToken")
    Observable<TokenEntity> refreshToken(@Field("refreshToken") String refreshToken,@Field("appId") String appId);
}
