package com.youdu.okhttp.response;

import android.os.Handler;
import android.os.Looper;

import com.youdu.okhttp.exception.OkHttpException;
import com.youdu.okhttp.listener.DisposeDataHandle;
import com.youdu.okhttp.listener.DisposeDataListener;
import com.youdu.util.ResponseEntityToModule;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 项目名称：YouDu
 * 项目作者：anDack
 * 项目时间：2017/2/14
 * 邮箱：    1160083806@qq.com
 * 描述：    TODO
 */

public class CommonJsonCallback implements Callback {
    //与服务器返回字段的一一对应关系
    protected final String RESULT_CODE="ecode";//有返回说明http请求是成功的
    protected final int RESULT_CODE_VALUE=0;
    protected final String ERROR_MSG="emsg";
    protected final String EMPIY_MSG="";
    /**
     * 自定义异常类型
     */
    //网络解析错误
    protected final int NETWORK_ERROR=-1;
    //JSON解析错误
    protected final int JSON_ERROR=-2;
    //未知错误
    protected final int OTHER_ERROR=-3;

    private Handler mDeliveryHandler;//与UI进行数据交互的Handler
    private DisposeDataListener mListener;
    private Class<?> mClass;
    public CommonJsonCallback(DisposeDataHandle handle)
    {
        this.mListener=handle.mListener;
        this.mClass=handle.mClass;
        //这里我们直接调用的是主线程
        this.mDeliveryHandler=new Handler(Looper.getMainLooper());
    }
    //请求失败处理

    @Override
    public void onFailure(Call call, final IOException e) {
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFailure(new OkHttpException(NETWORK_ERROR,e));
            }
        });
    }
    //服务器有响应
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final String result=response.body().string();
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                handlerResponse(result);
            }
        });
    }

    /**
     * 处理服务器返回的响应数据
     * @param responseObj
     */
    private void handlerResponse(Object responseObj) {
        if (responseObj==null && responseObj.toString().trim().equals("")) {
            mListener.onFailure(new OkHttpException(NETWORK_ERROR,EMPIY_MSG));
            return;
        }
        try {
            JSONObject reuslt=new JSONObject(responseObj.toString());
            if (reuslt.has(RESULT_CODE))
            {
                //从json对象中取出我们的响应码，若为0，
                // 则是正确的响应
                if (reuslt.getInt(RESULT_CODE)==RESULT_CODE_VALUE)
                {
                    if (mClass==null)
                    {
                        mListener.onSuccess(responseObj);
                    }else {
                        //需要我们将json对象转化为实体对象
                        Object object= ResponseEntityToModule.parseJsonObjectToModule(reuslt,
                                mClass);
                        //不为空正确
                        if (object!=null)
                        {
                            mListener.onSuccess(object);
                        }else {
                            //返回的不是合法JSON
                            mListener.onFailure(new OkHttpException(JSON_ERROR,EMPIY_MSG));
                        }
                    }
                }else {
                    mListener.onFailure(new OkHttpException(OTHER_ERROR,
                            reuslt.get(RESULT_CODE)));

                }
            }

        }catch (Exception e)
        {
            mListener.onFailure(new OkHttpException(OTHER_ERROR,e.getMessage()));
        }
    }
}
