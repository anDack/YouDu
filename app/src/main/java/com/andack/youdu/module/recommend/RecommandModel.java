package com.andack.youdu.module.recommend;

import com.andack.youdu.module.BaseModel;

import java.util.ArrayList;

/**
 * 项目名称：YouDu
 * 项目作者：anDack
 * 项目时间：2017/2/14
 * 邮箱：    1160083806@qq.com
 * 描述：    TODO
 */

public class RecommandModel extends BaseModel {
    /**
     * json中的两个数据部分
     */
    public ArrayList<RecommandBodyValue>list;
    public RecommandHeadValue head;
}
