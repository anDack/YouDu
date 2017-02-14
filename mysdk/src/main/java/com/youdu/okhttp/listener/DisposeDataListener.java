package com.youdu.okhttp.listener;

/**
 * 项目名称：YouDu
 * 项目作者：anDack
 * 项目时间：2017/2/14
 * 邮箱：    1160083806@qq.com
 * 描述：    自定义事件监听
 * 防止okhttp改名的onSuccess方法等改名字，同时方便扩展
 */

public interface DisposeDataListener {
    /**
     * 请求成功的回调
     */
    public void onSuccess(Object responseObj);
    /**
     * 请求失败的回调
     */
    public void onFailure(Object reasonObj);
}
