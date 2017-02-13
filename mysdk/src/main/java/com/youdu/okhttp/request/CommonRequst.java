package com.youdu.okhttp.request;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * 项目名称：YouDu
 * 项目作者：anDack
 * 项目时间：2017/2/13
 * 邮箱：    1160083806@qq.com
 * 描述：    公共Requst类
 * function 为我们生成requst对象
 */

public class CommonRequst {
    /**
     * 可以用构建者模式
     */
    /**
     *
     * @param url
     * @param params
     * @return 返回一个已经创建好的Request
     */
    public static Request createPostRequest(String url,RequestParams params)
    {
        FormBody.Builder mFormBodyBuild=new FormBody.Builder();
        if (params!=null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                //将请求参数逐一的添加到我们的请求构建类中
                mFormBodyBuild.add(entry.getKey(),entry.getValue());

            }
        }
        //通过请求构建类的Build方法获取真正的请求体对象
        FormBody formBody=mFormBodyBuild.build();
        return new Request.Builder().url(url).post(formBody).build();
    }

    /**
     *
     * @param url
     * @param params
     * @return 通过参数返回一个get类型已经创建好的Request
     */
    public static Request createGetRequest(String url,RequestParams params)
    {
        StringBuilder urlBuilder=new StringBuilder(url).append("?");
        if (params!=null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                urlBuilder.append(entry.getKey()).append("=")
                        .append(entry.getValue()).append("&");
            }
        }
        return new Request.Builder().url(urlBuilder.substring(0,urlBuilder.length()-1)).
                get().build();
    }
}
