package com.youdu.okhttp.exception;

/**
 * 项目名称：YouDu
 * 项目作者：anDack
 * 项目时间：2017/2/14
 * 邮箱：    1160083806@qq.com
 * 描述：    自定义异常处理
 */

public class OkHttpException extends Exception {
    private static final long serialVersionUID=1L;
    /**
     * 服务器返回的code
     */
    private int ecode;
    /**
     * 服务器返回的错误信息
     */
    private Object emsg;
    public OkHttpException(int ecode,Object emsg)
    {
        this.ecode=ecode;
        this.emsg=emsg;
    }

    public int getEcode() {
        return ecode;
    }

    public Object getEmsg() {
        return emsg;
    }
}
