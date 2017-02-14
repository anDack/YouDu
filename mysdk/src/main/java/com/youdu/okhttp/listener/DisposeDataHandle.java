package com.youdu.okhttp.listener;

/**
 * 项目名称：YouDu
 * 项目作者：anDack
 * 项目时间：2017/2/14
 * 邮箱：    1160083806@qq.com
 * 描述：    与UI进行交互的东西
 */

public class DisposeDataHandle {
    public DisposeDataListener mListener;
    public Class<?> mClass;

    /**
     * 如果只是提供listener将数据直接返回
     * @param listener
     */
    public DisposeDataHandle(DisposeDataListener listener)
    {
        this.mListener=listener;
    }

    /**
     * 提供实体类，我们就解析实体类
     * @param listener
     * @param mClass    实体类
     */
    public DisposeDataHandle(DisposeDataListener listener,
                             Class<?> mClass)
    {
        this.mListener=listener;
        this.mClass=mClass;
    }
}
