package com.andack.youdu.networks;

import com.andack.youdu.module.recommend.BaseRecommandModel;
import com.youdu.okhttp.CommonOkHttpClient;
import com.youdu.okhttp.listener.DisposeDataHandle;
import com.youdu.okhttp.listener.DisposeDataListener;
import com.youdu.okhttp.request.CommonRequst;
import com.youdu.okhttp.request.RequestParams;
import com.youdu.okhttp.response.CommonJsonCallback;

/**
 * 项目名称：YouDu
 * 项目作者：anDack
 * 项目时间：2017/2/14
 * 邮箱：    1160083806@qq.com
 * 描述：    数据请求中心
 */

public class RequestCenter {
    //根据参数发送所有的post请求
    public static void  postRequest(String url, RequestParams params, DisposeDataListener listener,Class<?> clazz)
    {
        CommonOkHttpClient.sendRequset(CommonRequst.createGetRequest(url,params),
                new CommonJsonCallback(new DisposeDataHandle(listener,clazz)));
    }
    /**
     * 请求首页参数
     */
    public static void requestRecommandData(DisposeDataListener listener)
    {
        postRequest(HttpConstants.HOME_RECOMMAND
                ,null,listener,
                BaseRecommandModel.class);
    }

}
