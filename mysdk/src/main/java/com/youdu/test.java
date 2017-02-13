package com.youdu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 项目名称：YouDu
 * 项目作者：anDack
 * 项目时间：2017/2/13
 * 邮箱：    1160083806@qq.com
 * 描述：    okHttp测试的代码
 */

public class test extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void sendRequest(){
        //1.创建一个OkhttpClient对象
        OkHttpClient mOkHttpClient=new OkHttpClient();
        //2.创建一个Request
        final Request request=new Request.Builder()
                .url("https://www.imooc.com/")
                .build();
        //3.创建一个Call对象（主要事件）
        Call call=mOkHttpClient.newCall(request);
        //请求调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}
