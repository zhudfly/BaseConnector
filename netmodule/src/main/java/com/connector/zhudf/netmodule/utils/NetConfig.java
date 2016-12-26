package com.connector.zhudf.netmodule.utils;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.wireless.security.jaq.JAQException;
import com.alibaba.wireless.security.jaq.SecurityCipher;
import com.alibaba.wireless.security.jaq.SecurityInit;
import com.connector.zhudf.utilsmodule.cache.DiskCache;
import com.connector.zhudf.utilsmodule.utils.BaseConfig;
import com.connector.zhudf.utilsmodule.utils.DeviceInfoUtil;
import com.connector.zhudf.netmodule.BuildConfig;
import com.connector.zhudf.netmodule.R;
import com.connector.zhudf.netmodule.net.entity.TokenEntity;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhudf on 2016/10/10.
 * 保存变量，变量可以修改来控制程序执行内容
 */

public class NetConfig {
    /**
     *  single instance
     */
    public static NetConfig buildConfig = null;

    /**
     * Net cache file max size
     **/
    private long netCacheMaxSize = 10 * 1024 * 1024;

    /**
     * Net timeout time
     */
    private long netExpirationTime = 5;

    /**
     * Net timeout unit
     **/
    private TimeUnit netExpirationTimeUnit = TimeUnit.SECONDS;

    //------  app unique config  ------

    /**
     *   current model
     */
    private boolean isDebug = true;
    /**
     * Api host ; eg. http(s)://www.xxx.com
     **/
    private String apiHost = BuildConfig.API_HOST;

    /**
     * SSL certificate file name
     */
    private String sslCerFileName = "";

    /**
     * Net cache file name
     */
    private String netCacheFileName = "";

    /**
     * App Id
     */
    private String appId = BuildConfig.APP_ID;

    /**
     * Sdk Secret
     */
    private String appSdkSecret = BuildConfig.APP_SECRET;

    /**
     * Device Id
     */
    private String deviceId = "";

    /**
     * app version
     */
    private String version = BuildConfig.VERSION_NAME;

    /**
     * os version
     */
    private String osVersion;

    /**
     * Device Type
     */
    private String deviceType = "android";

    /**
     * ALi Encrypt Key
     */
    private String jaqAppKey = BuildConfig.JAQ_APPKEY;

    /**
     * global mContext
     */
    private Context mContext;

    /**
     * global tokenEntity
     */
    private TokenEntity tokenEntity;

    private NetConfig(){
    }

    /**
     * 获得配置文件的对象
     * @return 配置文件的对象
     */
    public static NetConfig getInstance(){
        if(buildConfig == null){
            buildConfig = new NetConfig();
        }
        return buildConfig;
    }

    public void init(Context context) {
        this.mContext = context;

        //阿里聚安全初始化
        try {
            if(SecurityInit.Initialize(context) == 0){
                Logger.e(this.getClass().getSimpleName()
                        ,context.getResources().getString(R.string.init_success));
            }
        } catch (JAQException e) {
            Logger.e(this.getClass().getSimpleName()
                    ,context.getResources().getString(R.string.init_fail) + "Error Code : " + e.getErrorCode());
        }

        //基础module初始化
        BaseConfig.getInstance().init(context);

    }

    public String getAppId() {
        String appIdDecrypt = "";
        try {
            SecurityCipher cipher = new SecurityCipher(mContext);
            appIdDecrypt = cipher.decryptString(appId,jaqAppKey);
        }catch (JAQException e){
            Logger.e(this.getClass().getSimpleName()
                    , mContext.getResources().getString(R.string.decrypt_fail));
        }
        return appIdDecrypt;
    }

    public String getDeviceId() throws Exception {
        if(TextUtils.isEmpty(deviceId)){
            if (mContext == null){
                throw new Exception("NetConfig should be initial");
            }
            deviceId = DeviceInfoUtil.getDeviceId(mContext);
        }
        return deviceId;
    }

    public String getAppSdkSecret() {
        String appSecretDecrypt = "";
        try {
            SecurityCipher cipher = new SecurityCipher(mContext);
            appSecretDecrypt = cipher.decryptString(appSdkSecret,jaqAppKey);
        }catch (JAQException e){
            Logger.e(this.getClass().getSimpleName()
                    , mContext.getResources().getString(R.string.decrypt_fail));
        }
        return appSecretDecrypt;
    }


    public void setTokenEntity(TokenEntity tokenEntity) {
        //保存至本地 tokenEntity
        TokenEntity tempToken;
        if(!DiskCache.getInstance().isHaveData("accessToken")) {
            DiskCache.getInstance().putStringData("accessToken", GsonUtils.gson.toJson(tokenEntity));
            tempToken = tokenEntity;
        }
        else{
            //先取出本地的 tokenEntity
            String oldToken = DiskCache.getInstance().getStringData("accessToken");
            tempToken = GsonUtils.gson.fromJson(oldToken,TokenEntity.class);
            tempToken.setAccess_token(tokenEntity.getAccess_token());

        }

        //判断异常
        if(tempToken == null){
            Logger.e( "accessToken return null");

        }

        this.tokenEntity = tempToken;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getOsVersion() {
        if(TextUtils.isEmpty(osVersion)){
            osVersion = DeviceInfoUtil.getSystemVersionRelease();
        }
        return osVersion;
    }

    public Context getContext() {
        return mContext;
    }

    public TokenEntity getTokenEntity() {
        return tokenEntity;
    }


    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    public String getSslCerFileName() {
        return sslCerFileName;
    }

    public void setSslCerFileName(String sslCerFileName) {
        this.sslCerFileName = sslCerFileName;
    }

    public String getNetCacheFileName() {
        return netCacheFileName;
    }

    public void setNetCacheFileName(String netCacheFileName) {
        this.netCacheFileName = netCacheFileName;
    }

    public long getNetCacheMaxSize() {
        return netCacheMaxSize;
    }

    public void setNetCacheMaxSize(long netCacheMaxSize) {
        this.netCacheMaxSize = netCacheMaxSize;
    }

    public long getNetExpirationTime() {
        return netExpirationTime;
    }

    public void setNetExpirationTime(long netExpirationTime) {
        this.netExpirationTime = netExpirationTime;
    }

    public TimeUnit getNetExpirationTimeUnit() {
        return netExpirationTimeUnit;
    }

    public void setNetExpirationTimeUnit(TimeUnit netExpirationTimeUnit) {
        this.netExpirationTimeUnit = netExpirationTimeUnit;
    }

    public String getApiHost() {
        return apiHost;
    }

    public void setApiHost(String apiHost) {
        this.apiHost = apiHost;
    }

    public String getJaqAppKey() {
        return jaqAppKey;
    }
}
