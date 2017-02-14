package com.youdu.okhttp;

import com.youdu.okhttp.response.CommonJsonCallback;
import com.youdu.util.HttpsUtil;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 项目名称：YouDu
 * 项目作者：anDack
 * 项目时间：2017/2/13
 * 邮箱：    1160083806@qq.com
 * 描述：    请求的发送，请求参数的配置，https的支持。
 */

public class CommonOkHttpClient {
    private static final long TIME_OUT=30;
    private static OkHttpClient okHttpClient;
    static {
        //创建一个Okhttp构建者对象
        OkHttpClient.Builder okHttpBuilder=new OkHttpClient.Builder();
        //设置Okhttp的超时时间
        okHttpBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        //设置读的超时时间
        okHttpBuilder.readTimeout(TIME_OUT,TimeUnit.SECONDS);
        //设置写的超时时间
        okHttpBuilder.writeTimeout(TIME_OUT,TimeUnit.SECONDS);
        //设置是否支持重定向
        okHttpBuilder.followRedirects(true);
        //设置支持自加密的Https
        okHttpBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        okHttpBuilder.sslSocketFactory(HttpsUtil.getSslSocketFactory());
        //生成我们的client对象
        okHttpClient=okHttpBuilder.build();
    }

    /**
     * 发送具体的http/https请求
     * @param request
     * @param commCallback
     * @return Call的实例
     *
     */
    public static Call sendRequset(Request request,
                                   CommonJsonCallback commCallback)
    {
        Call call=okHttpClient.newCall(request);
        call.enqueue(commCallback);
        return call;
    }


}
