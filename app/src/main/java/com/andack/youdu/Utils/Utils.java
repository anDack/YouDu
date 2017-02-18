package com.andack.youdu.Utils;

import android.content.Context;

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
}
