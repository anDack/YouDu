package com.andack.youdu.networks;

/**
 * 项目名称：YouDu
 * 项目作者：anDack
 * 项目时间：2017/2/14
 * 邮箱：    1160083806@qq.com
 * 描述：    http常量
 */

public class HttpConstants {
    /**
     * 实际开发的时候可能在测试服务器中进行，所以，这样可以减少修改量
     * 方便我们切换服务器地址
     */
    public static final String ROOT_URL="http://imooc.com/api";
    /**
     * 首页产品请求接口
     */
    public static String HOME_RECOMMAND = ROOT_URL + "/product/home_recommand.php";
}
