package com.andack.youdu.Utils;

import com.andack.youdu.module.recommend.RecommandBodyValue;

import java.util.ArrayList;

/**
 * 项目名称：YouDu
 * 项目作者：anDack
 * 项目时间：2017/2/18
 * 邮箱：    1160083806@qq.com
 * 描述：    工具类++
 */

public class Util {
    public static ArrayList<RecommandBodyValue> handleData(RecommandBodyValue value)
    {
        ArrayList<RecommandBodyValue> values=new ArrayList<>();
        String[] titles=value.title.split("@");
        String[] infos=value.info.split("@");
        String[] prices=value.price.split("@");
        String[] texts=value.text.split("@");
        ArrayList<String> urls=value.url;
        int start=0;
        for (int i = 0; i < titles.length; i++) {
            RecommandBodyValue bodyValue=new RecommandBodyValue();
            bodyValue.title=titles[i];
            bodyValue.info=infos[i];
            bodyValue.price=prices[i];
            bodyValue.text=texts[i];
            bodyValue.url=extractData(urls,start,3);
            start+=3;
            values.add(bodyValue);
        }
        return values;
    }

    private static ArrayList<String> extractData(ArrayList<String> source, int start, int interval) {
        ArrayList<String> arrayList=new ArrayList<>();
        for (int i = start; i < start+interval; i++) {
            arrayList.add(source.get(i));
        }
        return arrayList;
    }
}
