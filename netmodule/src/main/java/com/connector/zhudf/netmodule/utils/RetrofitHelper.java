package com.connector.zhudf.netmodule.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.connector.zhudf.netmodule.net.intercepter.RequestInterceptor;
import com.connector.zhudf.netmodule.net.netoperate.NetServiceInterface;
import com.connector.zhudf.netmodule.net.converter.GConverterFactory;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

import javax.net.ssl.SSLSocketFactory;

/**
 * Created by zhudf on 2016/10/10.
 */

public class RetrofitHelper {
    public static Retrofit retrofit;
    public static Context context;
    private static NetServiceInterface netService;

    public static void initRetrofit(Context context) {
        setContext(context);
    }

    /**
     * 获得请求接口服务
     **/
    public static NetServiceInterface getNetService() {
        if (netService == null) {
                netService = getRetrofit().create(NetServiceInterface.class);
        }
        return netService;
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            //获得OkHttpClient的对象
            OkHttpClient client = getOkHttpClient();

            //判断是否有apiHost
            if (TextUtils.isEmpty(NetConfig.getInstance().getApiHost())) {
                throw new RuntimeException("RetrofitHelper Api-host can`t be empty");
            }
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(NetConfig.getInstance().getApiHost())
                    .addConverterFactory(GConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * 获得OkHttpClient的对象
     */
    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //log请求参数的拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logger.e("RetrofitHelper", message);
            }
        });
        builder.addInterceptor(interceptor);

        //添加请求参数的拦截器
        RequestInterceptor requestInterceptor = new RequestInterceptor();
        builder.addInterceptor(requestInterceptor);

        //设置网络缓存路径文件
        if (getContext() != null
                && !TextUtils.isEmpty(NetConfig.getInstance().getNetCacheFileName())
                && NetConfig.getInstance().getNetCacheMaxSize() > 0) {
            File httpCacheDirectory = new File(getContext().getExternalCacheDir(), NetConfig.getInstance().getNetCacheFileName());
            builder.cache(new Cache(httpCacheDirectory, NetConfig.getInstance().getNetCacheMaxSize()));
        }

        //ssl设置
        SSLSocketFactory sslSocketFactory = getCertificates();
        if (sslSocketFactory != null) {
            builder.sslSocketFactory(sslSocketFactory);
        }

        //设置网络连接超时时间
        builder.connectTimeout(NetConfig.getInstance().getNetExpirationTime(), NetConfig.getInstance().getNetExpirationTimeUnit());

        return builder.build();
    }

    /**
     * 读取assets下的证书
     *
     * @return
     */
    public static SSLSocketFactory getCertificates() {
        if (getContext() == null || TextUtils.isEmpty(NetConfig.getInstance().getSslCerFileName())) {
            return null;
        }
        AssetManager assetManager = getContext().getAssets();
        InputStream[] inputStream = new InputStream[1];
        try {
            inputStream[0] = assetManager.open(NetConfig.getInstance().getSslCerFileName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        SSLSocketFactory sslSocketFactory = HttpsUtils.getSslSocketFactory(inputStream, null, null);
        return sslSocketFactory;
    }


    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        RetrofitHelper.context = context;
    }
}
