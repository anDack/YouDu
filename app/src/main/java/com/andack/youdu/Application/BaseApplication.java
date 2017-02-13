package com.andack.youdu.Application;

import android.app.Application;

/**
 * 项目名称：YouDu
 * 项目作者：anDack
 * 项目时间：2017/2/13
 * 邮箱：    1160083806@qq.com
 * 描述：    基础的Application类
 *           通常是单例的(涉及到单例设计模式)
 */

public class BaseApplication extends Application {
    private static BaseApplication baseApplication=null;
    @Override

    public void onCreate() {
        super.onCreate();
        baseApplication=this;
    }
    public static BaseApplication getApplicationInstance()
    {
       return baseApplication;
    }
}
