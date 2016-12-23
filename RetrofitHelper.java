package utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import Net.baseclass.NetServiceInterface;
import Net.converter.GConverterFactory;

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
        if (TextUtils.isEmpty(BuildConfig.getInstance().getNetServiceClass())) {
            throw new RuntimeException("RetrofitHelper NetServiceClass can`t be empty");
        }
        if (netService == null) {
            try {
                netService = (NetServiceInterface) getRetrofit().create(Class.forName(BuildConfig.getInstance().getNetServiceClass()));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("RetrofitHelper NetServiceClass can`t be empty");
            }
        }
        return netService;
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Logger.e("RetrofitHelper", message);
                }
            });
            //获得OkHttpClient的对象
            OkHttpClient client = getOkHttpClient();

            //判断是否有apiHost
            if (TextUtils.isEmpty(BuildConfig.getInstance().getApiHost())) {
                throw new RuntimeException("RetrofitHelper Api-host can`t be empty");
            }
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BuildConfig.getInstance().getApiHost())
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
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logger.e("RetrofitHelper", message);
            }
        });
        builder.addInterceptor(interceptor);

        //设置网络缓存路径文件
        if (getContext() != null
                && !TextUtils.isEmpty(BuildConfig.getInstance().getNetCacheFileName())
                && BuildConfig.getInstance().getNetCacheMaxSize() > 0) {
            File httpCacheDirectory = new File(getContext().getExternalCacheDir(), BuildConfig.getInstance().getNetCacheFileName());
            builder.cache(new Cache(httpCacheDirectory, BuildConfig.getInstance().getNetCacheMaxSize()));
        }
        //log请求参数
        builder.addInterceptor(interceptor);

        //ssl设置
        SSLSocketFactory sslSocketFactory = getCertificates();
        if (sslSocketFactory != null) {
            builder.sslSocketFactory(sslSocketFactory);
        }

        //设置网络连接超时时间
        builder.connectTimeout(BuildConfig.getInstance().getNetExpirationTime(), BuildConfig.getInstance().getNetExpirationTimeUnit());

        return builder.build();
    }

    /**
     * 读取assets下的证书
     *
     * @return
     */
    public static SSLSocketFactory getCertificates() {
        if (getContext() == null || TextUtils.isEmpty(BuildConfig.getInstance().getSslCerFileName())) {
            return null;
        }
        AssetManager assetManager = getContext().getAssets();
        InputStream[] inputStream = new InputStream[1];
        try {
            inputStream[0] = assetManager.open(BuildConfig.getInstance().getSslCerFileName());
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
