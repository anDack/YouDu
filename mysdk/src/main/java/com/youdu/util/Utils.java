package com.youdu.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.youdu.Content.SDKConstant;

/**
 * 项目名称：YouDu
 * 项目作者：anDack
 * 项目时间：2017/2/18
 * 邮箱：    1160083806@qq.com
 * 描述：    工具类
 */

public class Utils {
    public static int dip2px(Context context,float dpValue)
    {
        final  float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale);
    }
    public static int px2dip(Context context,float pxValue)
    {
        final  float scale=context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale);
    }
    public static boolean canAutoPlay(Context context, SDKConstant.AutoPlaySetting autoPlaySetting){
        boolean result=true;
        switch (autoPlaySetting) {
            case AUTO_PLAY_3G_4G_WIFI:
                result=true;
                break;
            case AUTO_PLAY_ONLY_WIFI:
                if (isWifiConnected(context)) {
                    result=true;
                }else {
                    result=false;
                }
                break;
            case AUTO_PLAY_NEVER:
                result =false;
                break;
        }
        return result;

    }
    //判断WIFI是否连接
    public static boolean isWifiConnected(Context context){
        ConnectivityManager connectivityManager= (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=connectivityManager.getActiveNetworkInfo();
        if (info!=null && info.isConnected()
                && info.getType()== ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }
}
