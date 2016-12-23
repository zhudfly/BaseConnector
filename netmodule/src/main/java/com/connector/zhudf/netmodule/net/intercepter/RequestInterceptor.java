package com.connector.zhudf.netmodule.net.intercepter;

import com.alibaba.wireless.security.jaq.JAQException;
import com.alibaba.wireless.security.jaq.SecuritySignature;
import com.connector.zhudf.netmodule.R;
import com.connector.zhudf.netmodule.net.entity.TokenEntity;
import com.connector.zhudf.netmodule.utils.Logger;
import com.connector.zhudf.netmodule.utils.NetConfig;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhudf on 2016/12/16.
 */

public class RequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //获取请求
        Request originalRequest = chain.request();
        //请求定制：添加请求头
        Request.Builder requestBuilder = originalRequest.newBuilder()
                .header("Authorization", getAuthor());

        String timestamp = getTimeStamp();
        String sign = getSign(timestamp);

        //请求体定制：统一添加token参数
        if(originalRequest.body() == null && "GET".equals(originalRequest.method())){
            //Get 方法添加参数
            FormBody.Builder newFormBody = new FormBody.Builder();
            newFormBody.addEncoded("timestamp",timestamp);
            newFormBody.addEncoded("sign",sign);
            requestBuilder.method(originalRequest.method(),newFormBody.build());
        }
        else if(originalRequest.body() instanceof FormBody){
            FormBody.Builder newFormBody = new FormBody.Builder();
            FormBody oldFormBody = (FormBody) originalRequest.body();
            for (int i = 0;i < oldFormBody.size();i++){
                newFormBody.addEncoded(oldFormBody.encodedName(i),oldFormBody.encodedValue(i));
            }
            newFormBody.addEncoded("timestamp",timestamp);
            newFormBody.addEncoded("sign",sign);
            requestBuilder.method(originalRequest.method(),newFormBody.build());
        }

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }

    private String getSign(String timestamp) {
        try {
            return sign(NetConfig.getInstance().getDeviceId() + timestamp + NetConfig.getInstance().getAppSdkSecret());
        } catch (Exception e) {
            Logger.e(this.getClass().getSimpleName(), NetConfig.getInstance().getContext().getString(R.string.error_signature) + e.getMessage());
            return "";
        }
    }

    private String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis());
    }

    private String getAuthor() {
        String author = "";
        TokenEntity tokenEntity = NetConfig.getInstance().getTokenEntity();
        if(tokenEntity != null){
            author = tokenEntity.getToken_type() + " " + tokenEntity.getAccess_token();
        }
        return author;
    }

    private String sign(String... args){
        String result=null;
        StringBuffer buffer=new StringBuffer();
        for(String str:args){
            buffer.append(str);
        }
        try {
            //APP_KEY		:签名用的密匙key，预埋在jpg文件中
            SecuritySignature securitySignature = new SecuritySignature(NetConfig.getInstance().getContext());
            result = securitySignature.sign(buffer.toString(), NetConfig.getInstance().getJaqAppKey());
        } catch (JAQException e) {
            Logger.e(this.getClass().getSimpleName(), NetConfig.getInstance().getContext().getString(R.string.error_signature) + e.getErrorCode());
        }
        return result;
    }
}
