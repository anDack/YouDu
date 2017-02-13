package com.youdu.okhttp.request;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 项目名称：YouDu
 * 项目作者：anDack
 * 项目时间：2017/2/13
 * 邮箱：    1160083806@qq.com
 * 描述：    Request参数
 * @function    封装所有的请求参数到HashMap中。
 */

public class RequestParams {
    public ConcurrentHashMap<String,String> urlParams=new ConcurrentHashMap<>();
    public ConcurrentHashMap<String,Object> fileParams=new ConcurrentHashMap<>();
    public RequestParams(){
        this((Map<String,String>)null);
    }
    public RequestParams(Map<String,String> source)
    {
        if (source!=null) {
            for (Map.Entry<String, String> stringEntry : source.entrySet()) {
                put(stringEntry.getKey(),stringEntry.getValue());
            }
        }
    }

    /**
     * @function    将key与value设置在hashMap中
     * @param key
     * @param value
     */
    public void put(String key,String value)
    {
        if (key!=null&& value!=null) {
            urlParams.put(key, value);
        }
    }

    /**
     * @function    设置文件的key与Value到hashMap中
     * @param key
     * @param object
     * @throws FileNotFoundException
     */
    public void put(String key,Object object)throws FileNotFoundException{
        if (key!=null) {
            fileParams.put(key,object);
        }
    }

    /**
     * @function 参数是否为空
     * @return   true有参数，false没有参数
     */
    public boolean hasParams()
    {
        if (urlParams.size()>0||fileParams.size()>0)
        {
            return true;
        }
        return false;
    }
}
